package com.chanfinecloud.cfl.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.NoticeEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Loong on 2020/2/23.
 * Version: 1.0
 * Describe:
 */
public class NoticeListAdapter extends BaseQuickAdapter<NoticeEntity, BaseViewHolder> {
    private Context context;

    public NoticeListAdapter(Context context,@Nullable List<NoticeEntity> data) {
        super(R.layout.item_notice_list,data);
        this.context=context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, NoticeEntity item) {
        ImageView picView=helper.getView(R.id.notice_iv_pic);
        if(TextUtils.isEmpty(item.getCoverUrl())){
            Glide.with(context)
                    .load(R.drawable.workflow_default)
                    .centerCrop()
                    .into(picView);

        }else{
            Glide.with(context)
                    .load(item.getCoverUrl())
                    .centerCrop()
                    .into(picView);
        }
        helper.setText(R.id.notice_tv_title,item.getTitle());
        helper.setText(R.id.notice_tv_desc,item.getSummary());
    }
}
