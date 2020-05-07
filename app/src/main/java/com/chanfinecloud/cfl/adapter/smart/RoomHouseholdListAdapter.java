package com.chanfinecloud.cfl.adapter.smart;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.smart.RoomHouseholdEntity;
import com.chanfinecloud.cfl.weidgt.photopicker.GlideRoundTransform;

import java.util.List;

/**
 * Created by Loong on 2020/2/17.
 * Version: 1.0
 * Describe:
 */
public class RoomHouseholdListAdapter extends BaseQuickAdapter<RoomHouseholdEntity, BaseViewHolder> {
    private Context context;
    public RoomHouseholdListAdapter(Context context, @Nullable List<RoomHouseholdEntity> data) {
        super(R.layout.item_room_household_layout, data);
        this.context=context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, RoomHouseholdEntity item) {
        helper.setText(R.id.item_room_user_name,TextUtils.isEmpty(item.getNickName())?item.getName():item.getNickName());
        helper.setText(R.id.item_room_user_type, item.getHouseholdTypeDisplay());
        ImageView picView=helper.getView(R.id.item_room_user_avatar);
        if( item.getAvatarResource() != null && !TextUtils.isEmpty(item.getAvatarResource().getUrl())){
            Glide.with(context)
                    .load(item.getAvatarResource().getUrl())
                    .error(R.drawable.ic_default_img)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new CenterCrop(),new GlideRoundTransform(context, 120))
                    .into(picView);
        }else{
            Glide.with(context)
                    .load(R.drawable.icon_user_default)
                    .centerCrop()
                    .into(picView);
        }
    }


}
