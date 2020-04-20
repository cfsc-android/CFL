package com.chanfinecloud.cfl.adapter.smart;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.smart.WorkflowProcessesEntity;

import java.util.List;

/**
 * Created by Loong on 2020/2/20.
 * Version: 1.0
 * Describe:
 */
public class WorkflowStepAdapter extends BaseQuickAdapter<WorkflowProcessesEntity, BaseViewHolder> {
    private Context context;
    public WorkflowStepAdapter(Context context, @Nullable List<WorkflowProcessesEntity> data) {
        super(R.layout.item_workflow_step_list, data);
        this.context=context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, WorkflowProcessesEntity item) {
        if(helper.getAdapterPosition()==0){
            helper.setGone(R.id.workflow_step_up_line,false);
        }else{
            helper.setGone(R.id.workflow_step_up_line,true);
        }
        helper.setText(R.id.workflow_step_date,item.getUpdateTime().substring(0,10));
        helper.setText(R.id.workflow_step_time,item.getUpdateTime().substring(11));
        ImageView avatar=helper.getView(R.id.workflow_step_avatar);
        Glide.with(context)
                .load(item.getAvatarUrl())
                .error(R.drawable.icon_user_default)
                .circleCrop()
                .into(avatar);
        helper.setText(R.id.workflow_step_press,item.getNodeName());
    }
}
