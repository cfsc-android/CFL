package com.chanfinecloud.cfl.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.smart.ResourceEntity;

import java.util.List;

/**
 * Created by Loong on 2020/2/28.
 * Version: 1.0
 * Describe:
 */
public class AttachmentListAdapter extends BaseQuickAdapter<ResourceEntity, BaseViewHolder> {
    public AttachmentListAdapter(@Nullable List<ResourceEntity> data) {
        super(R.layout.item_attachment_list,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ResourceEntity item) {
        helper.setText(R.id.attachment_tv_name,item.getName());
    }
}
