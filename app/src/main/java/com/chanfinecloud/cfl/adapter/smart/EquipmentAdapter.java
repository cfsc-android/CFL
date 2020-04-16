package com.chanfinecloud.cfl.adapter.smart;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.smart.EquipmentInfoBo;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Loong on 2020/4/2.
 * Version: 1.0
 * Describe:
 */
public class EquipmentAdapter extends BaseQuickAdapter<EquipmentInfoBo, BaseViewHolder> {
    private Context context;
    private List<Boolean> statusList =new ArrayList<>();
    public EquipmentAdapter(Context context, @Nullable List<EquipmentInfoBo> data) {
        super(R.layout.item_equipment_list,data);
        this.context=context;
        if (data != null){
            for (int i = 0; i < data.size(); i++) {
                statusList.add(false);
            }
        }

    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, EquipmentInfoBo item) {

        if (statusList != null){
            helper.setText(R.id.equipment_tv_name,item.getDeviceName());
            boolean status=statusList.get(helper.getAdapterPosition());
            if(status){
                helper.setText(R.id.equipment_tv_status,"门已打开");
                helper.setTextColor(R.id.equipment_tv_status,context.getResources().getColor(R.color.blue_color));
            }else{
                helper.setText(R.id.equipment_tv_status,"点击开门");
                helper.setTextColor(R.id.equipment_tv_status,context.getResources().getColor(R.color.text_warn));
            }
        }

    }

    public void setEquipmentOpen(int position){
        statusList.set(position,true);
        notifyDataSetChanged();
    }

    public boolean getEquipmentOpen(int position){
        return statusList.get(position);
    }
}
