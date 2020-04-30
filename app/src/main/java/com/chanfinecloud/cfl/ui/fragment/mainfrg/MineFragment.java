package com.chanfinecloud.cfl.ui.fragment.mainfrg;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chanfinecloud.cfl.CFLApplication;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.entity.smart.ResourceEntity;
import com.chanfinecloud.cfl.entity.smart.UserInfoEntity;
import com.chanfinecloud.cfl.entity.smart.WorkflowType;
import com.chanfinecloud.cfl.ui.MainActivity;
import com.chanfinecloud.cfl.ui.activity.CarManageActivity;
import com.chanfinecloud.cfl.ui.activity.CommentActivity;
import com.chanfinecloud.cfl.ui.activity.NewsInfoActivity;
import com.chanfinecloud.cfl.ui.activity.PersonActivity;
import com.chanfinecloud.cfl.ui.activity.SettingActivity;
import com.chanfinecloud.cfl.ui.activity.minefeatures.HouseHoldActivity;
import com.chanfinecloud.cfl.ui.activity.minefeatures.WorkflowListActivity;
import com.chanfinecloud.cfl.ui.base.BaseFragment;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.Utils;
import com.chanfinecloud.cfl.weidgt.BadgeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;



/**
 * Damien
 * 我的设置页
 */

public class MineFragment extends BaseFragment {


    @BindView(R.id.iv_mine_head)
    ImageView ivMineHead;
    @BindView(R.id.tv_mine_name)
    TextView tvMineName;
    @BindView(R.id.tv_mine_address)
    TextView tvMineAddress;
    @BindView(R.id.ll_mine_head)
    LinearLayout llMineHead;
    @BindView(R.id.tv_mine_gongdan)
    TextView tvMineGongdan;
    @BindView(R.id.tv_mine_tousu)
    TextView tvMineTousu;
    @BindView(R.id.tv_mine_car)
    TextView tvMineCar;

    @BindView(R.id.tv_mine_face)
    TextView tvMineFace;
    @BindView(R.id.tv_mine_express)
    TextView tvMineExpress;
    @BindView(R.id.tv_mine_evaluation)
    TextView tvMineEvaluation;
    @BindView(R.id.tv_mine_data)
    TextView tvMineData;
    @BindView(R.id.tv_mine_setting)
    TextView tvMineSetting;
    private Unbinder unbinder;

    private UserInfoEntity userInfo;

    private BadgeView orderBadgeTextView = null;
    private BadgeView complaintBadgeTextView = null;


    @Override
    protected void initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_mine, null);
        setContentView(view);
        unbinder = ButterKnife.bind(this, view);
        userInfo=FileManagement.getUserInfo();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {
        initViewDara();
    }

    @Override
    protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();
        userInfo=FileManagement.getUserInfo();
        initViewDara();
    }

    /**
     * 初始化视图
     */
    private void initViewDara(){
        tvMineName.setText(userInfo.getNickName());
        if(TextUtils.isEmpty(userInfo.getCurrentDistrict().getRoomId())){
            tvMineAddress.setText("游客");
        }else{
            tvMineAddress.setText(userInfo.getCurrentDistrict().getBuildingName()+userInfo.getCurrentDistrict().getUnitName()+userInfo.getCurrentDistrict().getRoomName());
        }
        ResourceEntity avatar=FileManagement.getAvatarResource();
        if (avatar != null && !TextUtils.isEmpty(avatar.getUrl())) {
            Glide.with(this)
                    .load(avatar.getUrl())
                    .circleCrop()
                    .into(ivMineHead);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventBusMessage message){
        if("refresh".equals(message.getMessage())){
            initViewDara();
        }else if("OrderNotice".equals(message.getMessage())){
            orderBadgeTextView = Utils.toShowBadgeView(getActivity(),tvMineGongdan,1, orderBadgeTextView, 1);
        }else if("ComplaintNotice".equals(message.getMessage())){
            complaintBadgeTextView = Utils.toShowBadgeView(getActivity(), tvMineTousu,1, complaintBadgeTextView, 1);
        }
    }

    @OnClick({R.id.ll_mine_head, R.id.tv_mine_gongdan, R.id.tv_mine_tousu, R.id.tv_mine_car,
            R.id.tv_mine_face, R.id.tv_mine_express, R.id.tv_mine_evaluation, R.id.tv_mine_data,
            R.id.tv_mine_setting})
    public void onViewClicked(View view) {

        Bundle bundle=new Bundle();
        switch (view.getId()) {
            case R.id.ll_mine_head:
                startActivity(PersonActivity.class);
                break;
            case R.id.tv_mine_gongdan:
                Utils.toHideBadgeView(orderBadgeTextView);
                if(CFLApplication.bind){
                    bundle.putSerializable("workflowType", WorkflowType.Order);
                    startActivity(WorkflowListActivity.class,bundle);
                }else{
                    EventBus.getDefault().post(new EventBusMessage<>("unbind"));
                }
                
                break;
            case R.id.tv_mine_tousu:
                Utils.toHideBadgeView(complaintBadgeTextView);
                if (CFLApplication.bind){
                    bundle.putSerializable("workflowType", WorkflowType.Complain);
                    startActivity(WorkflowListActivity.class,bundle);
                }else{
                    EventBus.getDefault().post(new EventBusMessage<>("unbind"));
                }
                
                break;
            case R.id.tv_mine_car:
                if(CFLApplication.bind){
                    startActivity(CarManageActivity.class);
                }else{
                    EventBus.getDefault().post(new EventBusMessage<>("unbind"));
                }
                break;
            case R.id.tv_mine_face:
                startActivity(HouseHoldActivity.class);
                break;
            case R.id.tv_mine_express:
                Bundle a_bundle=new Bundle();
                a_bundle.putString("title","包裹查询");
                a_bundle.putString("url","https://m.kuaidi100.com/app/?coname=hao123");
                startActivity(NewsInfoActivity.class,a_bundle);
                break;
            case R.id.tv_mine_evaluation:
                startActivity(CommentActivity.class);
                break;
            case R.id.tv_mine_data:
                startActivity(PersonActivity.class);
                break;
            case R.id.tv_mine_setting:
                startActivity(SettingActivity.class);
                break;
        }
    }

}
