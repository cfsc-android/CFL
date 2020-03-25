package com.chanfinecloud.cfl.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Loong on 2020/3/25.
 * Version: 1.0
 * Describe: Simple Adapter
 */
public class SimpleAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public SimpleAdapter(@Nullable List<String> data) {
        super(android.R.layout.simple_list_item_1,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.setText(android.R.id.text1,item);
    }
}
