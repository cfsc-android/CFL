package com.chanfinecloud.cfl.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.smart.EquipmentInfoBo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Loong on 2020/4/8.
 * Version: 1.0
 * Describe:
 */
public class VideoListAdapter extends BaseQuickAdapter<EquipmentInfoBo, BaseViewHolder> {
    private String[] pics=new String[]{
            "yxf_dm_01.png",
            "yxf_dm_02.png",
            "yxf_dm_03.png",
            "yxf_dm_04.png",
            "yxf_dm_05.png",
            "yxf_dm_06.png",
    };
    private Context context;
    public VideoListAdapter(Context context,@Nullable List<EquipmentInfoBo> data) {
        super(R.layout.video_list_item,data);
        this.context=context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, EquipmentInfoBo item) {
        helper.setText(R.id.video_list_item_name,item.getDeviceName());
        ImageView picView=helper.getView(R.id.video_list_item_pic);
        Glide.with(context)
                .load("file:///android_asset/"+pics[helper.getAdapterPosition()%6])
                .into(picView);
    }
}
