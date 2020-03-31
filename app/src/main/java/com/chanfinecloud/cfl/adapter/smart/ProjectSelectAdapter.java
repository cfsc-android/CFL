package com.chanfinecloud.cfl.adapter.smart;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.smart.KeyTitleEntity;

import java.util.List;

/**
 * Created by Loong on 2020/2/23.
 * Version: 1.0
 * Describe:
 */
public class ProjectSelectAdapter extends BaseQuickAdapter<KeyTitleEntity, BaseViewHolder> {
    private Context context;

    public ProjectSelectAdapter(Context context, @Nullable List<KeyTitleEntity> data) {
        super(R.layout.item_project_select_layout,data);
        this.context=context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, KeyTitleEntity item) {
        if(TextUtils.isEmpty(item.getKey())){
            helper.setText(R.id.project_select_label,item.getTitle());
            helper.setGone(R.id.project_select_label,true);
            helper.setGone(R.id.project_select_title_ll,false);
        }else{
            helper.setText(R.id.project_select_title,item.getTitle());
            helper.setGone(R.id.project_select_label,false);
            helper.setGone(R.id.project_select_title_ll,true);
        }

    }
}
