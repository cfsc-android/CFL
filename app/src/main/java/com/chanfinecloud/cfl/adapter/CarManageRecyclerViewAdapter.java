package com.chanfinecloud.cfl.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.adapter.smart.CarEntity;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.ui.activity.CarManageEditActivity;
import com.chanfinecloud.cfl.util.Utils;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.chanfinecloud.cfl.util.EnumUtils.getCarColorString;
import static com.chanfinecloud.cfl.util.EnumUtils.getCarTypeString;
import static com.chanfinecloud.cfl.util.EnumUtils.getPlateColorString;
import static com.chanfinecloud.cfl.util.EnumUtils.getPlateTypeString;

/**
* damien 2020-05-06 第一个recycleView侧滑adapter
*  ImageView carPhoto;//汽车照片
*         TextView plate;//汽车牌照
*         TextView plateColor;//牌照颜色
*         TextView plateType;//牌照类型
*         TextView carColor;//汽车颜色
*         TextView carType;//汽车类型
*         ImageView carAudit;//是否审核
*         ImageView check;//编辑选择
*         TextView payMode;//缴费模式
*/
public class CarManageRecyclerViewAdapter extends RecyclerSwipeAdapter<CarManageRecyclerViewAdapter.SimpleViewHolder> {

    private Context context;
    private ArrayList<CarEntity> carManageList = new ArrayList<>();
    private ArrayList<Integer> checkList = new ArrayList<>();
    private Boolean edit;

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.trash)
        ImageView trash;
        @BindView(R.id.delete)
        Button delete;
        @BindView(R.id.iv_car_manage_check)
        ImageView ivCarManageCheck;
        @BindView(R.id.iv_car_manage_car_photo)
        ImageView ivCarManageCarPhoto;
        @BindView(R.id.tv_car_manage_car_plate)
        TextView tvCarManageCarPlate;
        @BindView(R.id.tv_car_manage_pay_mode)
        TextView tvCarManagePayMode;
        @BindView(R.id.tv_car_manage_plate_color)
        TextView tvCarManagePlateColor;
        @BindView(R.id.tv_car_manage_plate_type)
        TextView tvCarManagePlateType;
        @BindView(R.id.tv_car_manage_car_color)
        TextView tvCarManageCarColor;
        @BindView(R.id.tv_car_manage_car_type)
        TextView tvCarManageCarType;
        @BindView(R.id.iv_car_manage_audit)
        ImageView ivCarManageAudit;
        @BindView(R.id.car_item_swipe)
        SwipeLayout carItemSwipe;
        @BindView(R.id.car_item_ll)
        LinearLayout carItemLl;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public CarManageRecyclerViewAdapter(Context context, ArrayList<CarEntity> carManageList) {
        this.context = context;
        this.carManageList = carManageList;
        for (int i = 0; i < this.carManageList.size(); i++) {
            checkList.add(0);
        }
        this.edit = false;
    }

    public void setData(ArrayList<CarEntity> carManageList) {
        this.carManageList = carManageList;
        notifyDataSetChanged();
        mItemManger.closeAllItems();
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car_manage_list, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {

        if (carManageList == null || carManageList.size() <= 0)
            return;

        CarEntity carManage = carManageList.get(position);

        if (carManage == null)
            return;

        String picUrl = "";
        if (carManage.getVehicleImageResource() != null && !"".equals(carManage.getVehicleImageResource())) {
            picUrl = carManage.getVehicleImageResource().getUrl();
        } else {
            picUrl = "https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E6%B1%BD%E8%BD%A6%E5%9B%BE%E7%89%87&step_word=&hs=0&pn=6&spn=0&di=5830&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=2035462553%2C3805072346&os=2284628956%2C130487509&simid=3407802275%2C474022487&adpicid=0&lpn=0&ln=1568&fr=&fmq=1585883534723_R&fm=&ic=undefined&s=undefined&hd=undefined&latest=undefined&copyright=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F5%2F546efb213fb32.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Botg9aaa_z%26e3Bv54AzdH3Fowssrwrj6_1jpwts_cmbba_z%26e3Bip4s&gsm=5&rpstart=0&rpnum=0&islist=&querylist=&force=undefined";
        }

        Glide.with(context).load(picUrl)
                .error(R.drawable.car_manage_test)
                .into(viewHolder.ivCarManageCarPhoto);
        viewHolder.tvCarManageCarPlate.setText(carManage.getPlateNO());
        viewHolder.tvCarManagePlateColor.setText(getPlateColorString(carManage.getPlateColor()));
        viewHolder.tvCarManagePlateType.setText(getPlateTypeString(carManage.getPlateType()));
        viewHolder.tvCarManageCarColor.setText(getCarColorString(carManage.getVehicleColor()));
        viewHolder.tvCarManageCarType.setText(getCarTypeString(carManage.getVehicleType()));
        if (carManage.getAuditStatus() == 0) {
            viewHolder.ivCarManageAudit.setVisibility(View.VISIBLE);
            viewHolder.tvCarManagePayMode.setVisibility(View.GONE);
        } else {
            viewHolder.tvCarManagePayMode.setVisibility(View.VISIBLE);
            viewHolder.ivCarManageAudit.setVisibility(View.GONE);
        }
        viewHolder.ivCarManageCheck.setVisibility(edit ? View.VISIBLE : View.GONE);
        //  viewHolder.ivCarManageCheck.setImageResource(checkList.get(position)==0?R.drawable.check_normal:R.drawable.check_checked);
        viewHolder.ivCarManageCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkList.get(position) == 0) {
                    checkList.set(position, 1);
                } else {
                    checkList.set(position, 0);
                }
                notifyDataSetChanged();
            }
        });
        if (!Utils.isEmpty(carManage.getParkEnddate()) && !Utils.isEmpty(carManage.getParkStartdate())) {
            long nTime = new Date().getTime();
            if (Utils.getDateTimeByStringAndFormat(carManage.getParkStartdate(), "yyyy-MM-dd") > nTime) {
                viewHolder.tvCarManagePayMode.setText("包期-未开始");
            } else if (Utils.getDateTimeByStringAndFormat(carManage.getParkStartdate(), "yyyy-MM-dd") <= nTime
                    && Utils.getDateTimeByStringAndFormat(carManage.getParkEnddate(), "yyyy-MM-dd") >= nTime) {
                viewHolder.tvCarManagePayMode.setText("已包期");
            } else if (Utils.getDateTimeByStringAndFormat(carManage.getParkEnddate(), "yyyy-MM-dd") < nTime) {
                viewHolder.tvCarManagePayMode.setText("包期-已过期");
            } else {
                viewHolder.tvCarManagePayMode.setText("包期-日期错误");
            }
        } else {
            viewHolder.tvCarManagePayMode.setText("未包期");
        }

        viewHolder.carItemSwipe.setShowMode(SwipeLayout.ShowMode.LayDown);
        viewHolder.carItemSwipe.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                //YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });
        viewHolder.carItemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CarManageEditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("car", carManageList.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //传递删除接口  然后回调 数据更新
                CarEntity carEntity = carManageList.get(position);
                if (carEntity != null && carEntity.getAuditStatus() == 0) {
                    Toast.makeText(context, "审核中，不能删除", Toast.LENGTH_SHORT).show();
                    return;
                }

                EventBusMessage<String> eventBusMessage = new EventBusMessage<>("onCarItemDelete");
                eventBusMessage.setData(position + "");
                EventBus.getDefault().post(eventBusMessage);
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mItemManger.bindView(viewHolder.itemView, position);

    }

    @Override
    public int getItemCount() {
        return carManageList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.car_item_swipe;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
        notifyDataSetChanged();
    }

    public boolean getEdit() {
        return this.edit;
    }


    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
        CarEntity carEntity = carManageList.get(position);
        int ntype = 1;
        if (carEntity != null && carEntity.getAuditStatus() == 0) {
            ntype = 2;
        }

        return ntype;
    }

}
