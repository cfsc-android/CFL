package com.chanfinecloud.cfl.adapter.smart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.smart.VisitorEntity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Loong on 2020/2/17.
 * Version: 1.0
 * Describe:
 */
public class VisitorListAdapter extends BaseQuickAdapter<VisitorEntity, BaseViewHolder> {
    public VisitorListAdapter(@Nullable List<VisitorEntity> data) {
        super(R.layout.visitor_record_list_item, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, VisitorEntity item) {
        helper.setText(R.id.tv_visitor_record_name,item.getVisitorName());
        helper.setText(R.id.tv_visitor_record_valid_time,item.getExpireTime());
        String valid="";
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(new Date().getTime()>sdf.parse(item.getExpireTime()).getTime()){
                valid="已作废";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        helper.setText(R.id.tv_visitor_record_valid,valid);
    }


}
