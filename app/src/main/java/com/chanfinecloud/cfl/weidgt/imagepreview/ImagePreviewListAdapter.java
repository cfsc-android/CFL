package com.chanfinecloud.cfl.weidgt.imagepreview;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chanfinecloud.cfl.R;


import java.util.List;

/**
 * Created by Loong on 2020/2/18.
 * Version: 1.0
 * Describe: 图片预览适配器
 */
public class ImagePreviewListAdapter extends BaseQuickAdapter<ImageViewInfo, BaseViewHolder> {
    private Context context;
    public ImagePreviewListAdapter(Context context,int layoutResId, List<ImageViewInfo> data) {
        super(layoutResId, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ImageViewInfo item) {
        IconImageView imageView = helper.getView(R.id.iiv_item_image_preview);
        imageView.setIsShowIcon(item.getVideoUrl() != null);
        if("plus".equals(item.getUrl())){
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_image_add));
        }else{
            Glide.with(context)
                    .load(item.getUrl())
                    .thumbnail(0.7f)
                    .placeholder(R.drawable.ic_default_img)
                    .into(imageView);
        }
    }
}
