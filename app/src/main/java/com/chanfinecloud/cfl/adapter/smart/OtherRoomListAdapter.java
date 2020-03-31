package com.chanfinecloud.cfl.adapter.smart;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.smart.ApprovalStatusType;
import com.chanfinecloud.cfl.entity.smart.HouseholdRoomEntity;

import java.util.List;

/**
 * Created by Loong on 2020/2/17.
 * Version: 1.0
 * Describe:
 */
public class OtherRoomListAdapter extends BaseQuickAdapter<HouseholdRoomEntity, BaseViewHolder> {

    private Context context;
    public OtherRoomListAdapter(Context context, @Nullable List<HouseholdRoomEntity> data) {
        super(R.layout.item_room_list_layout, data);
        this.context=context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HouseholdRoomEntity item) {
        helper.setText(R.id.item_project_name,item.getProjectName());
        helper.setText(R.id.item_room_name, item.getFullName());
        if(item.getApprovalStatus()== ApprovalStatusType.Audit.getType()){
            helper.setText(R.id.item_room_status,"等待审核");
            helper.setBackgroundRes(R.id.item_room_status,R.drawable.bg_desc_shape);
        }else if(item.getApprovalStatus()== ApprovalStatusType.Refuse.getType()){
            helper.setText(R.id.item_room_status,"审核被拒");
            helper.setBackgroundRes(R.id.item_room_status,R.drawable.bg_desc_shape);
        }else if(item.getApprovalStatus()== ApprovalStatusType.Pass.getType()){
            helper.setText(R.id.item_room_status,item.getHouseholdTypeDisplay());
            helper.setBackgroundRes(R.id.item_room_status,R.drawable.bg_green_shape);
        }

    }


}
