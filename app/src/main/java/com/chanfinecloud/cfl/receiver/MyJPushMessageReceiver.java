package com.chanfinecloud.cfl.receiver;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.chanfinecloud.cfl.CFLApplication;
import com.chanfinecloud.cfl.entity.enumtype.JpushType;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.entity.smart.NoticePushEntity;
import com.chanfinecloud.cfl.ui.activity.CarManageActivity;
import com.chanfinecloud.cfl.ui.activity.ComplainDetailActivity;
import com.chanfinecloud.cfl.ui.activity.HouseholdAuditListActivity;
import com.chanfinecloud.cfl.ui.activity.NoticeDetailActivity;
import com.chanfinecloud.cfl.ui.activity.RepairsDetailActivity;
import com.chanfinecloud.cfl.ui.activity.minefeatures.HouseHoldActivity;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.util.UserInfoUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.android.api.CmdMessage;
import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;
import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by Loong on 2020/3/26.
 * Version: 1.0
 * Describe: 自定义推送服务
 */
public class MyJPushMessageReceiver extends JPushMessageReceiver {

    public MyJPushMessageReceiver() {
        super();
    }

    @Override
    public Notification getNotification(Context context, NotificationMessage notificationMessage) {
        LogUtils.d("getNotification:"+notificationMessage.toString());
        return super.getNotification(context, notificationMessage);
    }

    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        super.onMessage(context, customMessage);
        //透传消息
        LogUtils.d("onMessage:"+customMessage.toString());
    }

    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
//        super.onNotifyMessageOpened(context, notificationMessage);
        //点击推送通知
        LogUtils.d("onNotifyMessageOpened:"+notificationMessage.toString());
        Gson gson=new Gson();
        NoticePushEntity noticePush=gson.fromJson(notificationMessage.notificationExtras,NoticePushEntity.class);
        if((JpushType.News.getType()).equals(noticePush.getType())){//新闻类推送
            Intent intent=new Intent(context, NoticeDetailActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle=new Bundle();
            bundle.putString("noticeId",noticePush.getBusinessId());
            bundle.putString("title",notificationMessage.notificationTitle);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }else if((JpushType.Orders.getType()).equals(noticePush.getType())){//工单推送
            Intent intent=new Intent(context, RepairsDetailActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle=new Bundle();
            bundle.putString("order_id",noticePush.getBusinessId());
            intent.putExtras(bundle);
            context.startActivity(intent);
        }else if((JpushType.Complain.getType()).equals(noticePush.getType())){//投诉推送
            Intent intent=new Intent(context, ComplainDetailActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle=new Bundle();
            bundle.putString("complain_id",noticePush.getBusinessId());
            intent.putExtras(bundle);
            context.startActivity(intent);
        }else if((JpushType.OwnerVerifyPass.getType()).equals(noticePush.getType())){//审核通过的推送
            Intent intent=new Intent(context, HouseHoldActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }else if((JpushType.OwnerVerifying.getType()).equals(noticePush.getType())){//收到审核的推送
            Intent intent=new Intent(context, HouseholdAuditListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle=new Bundle();
            bundle.putString("roomId",noticePush.getBusinessId());
            intent.putExtras(bundle);
            context.startActivity(intent);
        }else if((JpushType.CarVerifyPass.getType()).equals(noticePush.getType())){//收到车辆审核通过的推送
            Intent intent=new Intent(context, CarManageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }else if((JpushType.CarVerifyRefuse.getType()).equals(noticePush.getType())){//收到车辆审核不通过的推送
            Intent intent=new Intent(context, CarManageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }


    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageArrived(context, notificationMessage);
        //收到通知消息
        LogUtils.d("onNotifyMessageArrived:"+notificationMessage.toString());
        // TODO: 2020/4/20  可能需要分类型添加小红标
        int badgeCount = 1;
        ShortcutBadger.applyCount(context, badgeCount); //for 1.1.4+
        //EventBus.getDefault().post(new EventBusMessage<>("OrderNotice"));
        Gson gson=new Gson();
        NoticePushEntity noticePush=gson.fromJson(notificationMessage.notificationExtras,NoticePushEntity.class);
        if((JpushType.OwnerVerifyPass.getType()).equals(noticePush.getType())){
            UserInfoUtil.refreshUserInfoByServerCache(new UserInfoUtil.OnRefreshListener() {
                @Override
                public void onSuccess() {
                    EventBus.getDefault().post(new EventBusMessage<>("AuditPass"));
                }

                @Override
                public void onFail(String msg) {

                }
            });
        }else if((JpushType.News.getType()).equals(noticePush.getType())){
            EventBus.getDefault().post(new EventBusMessage<>("NoticeRefresh"));
        }else if((JpushType.Orders.getType()).equals(noticePush.getType())){

            EventBus.getDefault().post(new EventBusMessage<>("OrderNotice"));
        }else if((JpushType.Complain.getType()).equals(noticePush.getType())){

            EventBus.getDefault().post(new EventBusMessage<>("ComplaintNotice"));
        }else if((JpushType.CarVerifyPass.getType()).equals(noticePush.getType())){

            EventBus.getDefault().post(new EventBusMessage<>("carAdd"));
        }else if((JpushType.CarVerifyRefuse.getType()).equals(noticePush.getType())){

            EventBus.getDefault().post(new EventBusMessage<>("carAdd"));
        }
    }

    @Override
    public void onNotifyMessageDismiss(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageDismiss(context, notificationMessage);
        LogUtils.d("onNotifyMessageDismiss:"+notificationMessage.toString());
    }

    @Override
    public void onRegister(Context context, String s) {
        super.onRegister(context, s);
        LogUtils.d("onRegister:"+s);
    }

    @Override
    public void onConnected(Context context, boolean b) {
        super.onConnected(context, b);
        //连接是否成功
        LogUtils.d("onConnected:"+b);
    }

    @Override
    public void onCommandResult(Context context, CmdMessage cmdMessage) {
        super.onCommandResult(context, cmdMessage);
        LogUtils.d("onCommandResult:"+cmdMessage.toString());
    }

    @Override
    public void onMultiActionClicked(Context context, Intent intent) {
        super.onMultiActionClicked(context, intent);
        LogUtils.d("onMultiActionClicked:"+intent.toString());
    }

    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onTagOperatorResult(context, jPushMessage);
        //设置标签回调
       // LogUtils.d("onTagOperatorResult:"+jPushMessage.toString());
        //设置不成功就继续设置
        if(jPushMessage.getErrorCode()!=0){
            JPushInterface.setTags(CFLApplication.getAppContext(),jPushMessage.getSequence(),jPushMessage.getTags());
        }

    }

    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onCheckTagOperatorResult(context, jPushMessage);
        LogUtils.d("onCheckTagOperatorResult:"+jPushMessage.toString());
    }

    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onAliasOperatorResult(context, jPushMessage);
        //设置别名回调
      //  LogUtils.d("onAliasOperatorResult:"+jPushMessage.toString());
        //设置不成功就继续设置
        if(jPushMessage.getErrorCode()!=0){
            JPushInterface.setAlias(CFLApplication.getAppContext(),jPushMessage.getSequence(),jPushMessage.getAlias());
        }
    }

    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onMobileNumberOperatorResult(context, jPushMessage);
        LogUtils.d("onMobileNumberOperatorResult:"+jPushMessage.toString());
    }

    @Override
    public void onNotificationSettingsCheck(Context context, boolean b, int i) {
        super.onNotificationSettingsCheck(context, b, i);
        //注册是否成功
        LogUtils.d("onNotificationSettingsCheck:"+b+","+i);
        FileManagement.setNotificationFlag(b);
    }

}
