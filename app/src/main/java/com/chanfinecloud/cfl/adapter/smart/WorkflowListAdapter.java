package com.chanfinecloud.cfl.adapter.smart;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.smart.WorkflowEntity;

import java.util.List;

/**
 * Created by Loong on 2020/3/13.
 * Version: 1.0
 * Describe:
 */
public class WorkflowListAdapter extends BaseQuickAdapter<WorkflowEntity, BaseViewHolder> {
    private Context context;
    public WorkflowListAdapter(Context context, @Nullable List<WorkflowEntity> data) {
        super(R.layout.item_order_list, data);
        this.context=context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, WorkflowEntity item) {
        helper.setText(R.id.order_list_tv_title,item.getProblemDesc());
        helper.setText(R.id.order_list_tv_type, item.getTypeName());
        helper.setText(R.id.order_list_tv_time,item.getCreateTime());
        helper.setText(R.id.order_list_tv_workflow,item.getStatusName());
        helper.setText(R.id.order_list_tv_room,item.getBriefDesc());
        ImageView picView=helper.getView(R.id.order_list_iv_pic);
        if(item.getProblemResourceValue()!=null&&item.getProblemResourceValue().size()>0){
            Glide.with(context)
                    .load(item.getProblemResourceValue().get(0).getUrl())
                    .centerCrop()
                    .into(picView);
        }else{
            Glide.with(context)
                    .load(R.drawable.workflow_default)
                    .centerCrop()
                    .into(picView);
        }
    }
}
