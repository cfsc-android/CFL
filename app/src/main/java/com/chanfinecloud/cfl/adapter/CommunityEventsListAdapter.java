package com.chanfinecloud.cfl.adapter;

import android.content.Context;
import android.content.pm.LauncherActivityInfo;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.NoticeEntity;
import com.chanfinecloud.cfl.entity.smart.EventsEntity;
import com.chanfinecloud.cfl.util.DateUtil;

import java.util.Date;
import java.util.List;

import static com.chanfinecloud.cfl.config.Config.DAY_MILLISECOND;

/**
 * Created by Loong on 2020/2/23.
 * Version: 1.0
 * Describe:
 */
public class CommunityEventsListAdapter extends BaseQuickAdapter<EventsEntity, BaseViewHolder> {
    private Context context;
    private  int viewType;
    public CommunityEventsListAdapter(Context context, List<EventsEntity> data, int viewType) {
        super(R.layout.item_community_events,data);
        this.context=context;
        this.viewType = viewType;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, EventsEntity item) {

        if (item != null){
            ImageView picView=helper.getView(R.id.item_events_iv_pic);
            if(null != item.getCoverImageResource() && !TextUtils.isEmpty(item.getCoverImageResource().getUrl())){

                Glide.with(context)
                        .load(item.getCoverImageResource().getUrl())
                        .error(R.drawable.car_manage_test)
                        .centerCrop()
                        .into(picView);
            }else{
                Glide.with(context)
                        .load(R.drawable.car_manage_test)
                        .centerCrop()
                        .into(picView);
            }

            helper.setText(R.id.item_events_tv_title,item.getTitle());
            helper.setText(R.id.item_events_tv_address,"地址："+item.getLocation());
            helper.setText(R.id.item_events_tv_date,"报名截止："+item.getRegistrationDeadline());
            Date deadline = DateUtil.stringToDate(item.getRegistrationDeadline(), DateUtil.FORMAT_DATE);
            Date endLine = DateUtil.stringToDate(item.getEndTime(), DateUtil.FORMAT_DATE);
            Date currentDate = new Date();
            Log.d(TAG, "convert: "+ deadline.getTime()+ "currentDate:" + currentDate.getTime());
            if (currentDate.getTime() < deadline.getTime() + DAY_MILLISECOND){
                helper.setText(R.id.item_events_tv_status, "报名已截止");
                helper.setBackgroundRes(R.id.item_events_tv_status, R.drawable.bg_stroke_gray);
                helper.setTextColor(R.id.item_events_tv_status,context.getResources().getColor(R.color.text_gray) );

            }else if (currentDate.getTime() < endLine.getTime() + DAY_MILLISECOND){
                helper.setText(R.id.item_events_tv_status, "活动已结束");
                helper.setBackgroundRes(R.id.item_events_tv_status, R.drawable.bg_stroke_gray);
                helper.setTextColor(R.id.item_events_tv_status,context.getResources().getColor(R.color.text_gray) );
            }else{

                if (!item.isParticipate()){
                    helper.setText(R.id.item_events_tv_status, "去参加");
                    helper.setBackgroundRes(R.id.item_events_tv_status, R.drawable.bg_stroke_blue);
                    helper.setTextColor(R.id.item_events_tv_status,context.getResources().getColor(R.color.blue) );
                }else{
                    helper.setText(R.id.item_events_tv_status, "已报名");
                    helper.setBackgroundRes(R.id.item_events_tv_status, R.drawable.bg_stroke_gray);
                    helper.setTextColor(R.id.item_events_tv_status,context.getResources().getColor(R.color.green) );
                }
            }

            helper.setText(R.id.item_events_tv_num, item.getEnrollmentNumber()+"人已参与");
        }
    }
}
