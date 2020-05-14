package com.chanfinecloud.cfl.adapter.smart;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.ImageBanner;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.util.BannerUtils;

import java.util.List;

/**
 * 自定义布局，图片
 */
public class ImageAdapter extends BannerAdapter<ImageBanner, ImageAdapter.ImageHolder> {

    public ImageAdapter(List<ImageBanner> mDatas) {
        //设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
        super(mDatas);
    }

    //更新数据
    public void updateData(List<ImageBanner> data) {
        //这里的代码自己发挥，比如如下的写法等等
        mDatas.addAll(data);
        notifyDataSetChanged();
    }


    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new ImageHolder(BannerUtils.getView(parent, R.layout.banner_image));
    }

    @Override
    public void onBindView(ImageHolder holder, ImageBanner data, int position, int size) {

        Glide.with(holder.itemView)
                .load(data.getImageUrl())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(100)))
                .into(holder.imageView);
    }

    public class ImageHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ImageHolder(@NonNull View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.image_view);
        }
    }

}
