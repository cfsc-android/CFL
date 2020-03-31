package com.chanfinecloud.cfl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.adapter.smart.CarEntity;
import com.chanfinecloud.cfl.util.XUtilsImageUtils;

import java.util.ArrayList;

import static com.chanfinecloud.cfl.util.EnumUtils.getCarColorString;
import static com.chanfinecloud.cfl.util.EnumUtils.getCarTypeString;
import static com.chanfinecloud.cfl.util.EnumUtils.getPlateColorString;
import static com.chanfinecloud.cfl.util.EnumUtils.getPlateTypeString;


/**
 * Created by zengx on 2019/5/22.
 * Describe:
 */
public class CarManageListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<CarEntity> carManageList;
    private ArrayList<Integer> checkList=new ArrayList<>();
    private Boolean edit;

    public CarManageListAdapter(Context context, ArrayList<CarEntity> carManageList) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.carManageList = carManageList;
        for (int i = 0; i < this.carManageList.size(); i++) {
            checkList.add(0);
        }
        this.edit=false;
    }

    @Override
    public int getCount() {
        return carManageList.size();
    }

    @Override
    public Object getItem(int position) {
        return carManageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setEdit(boolean edit){
        this.edit=edit;
        notifyDataSetChanged();
    }

    public boolean getEdit(){
        return this.edit;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CarManageHolder carManageHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.car_manage_list_item, null);
            carManageHolder = new CarManageHolder();
            carManageHolder.carPhoto= (ImageView) convertView.findViewById(R.id.iv_car_manage_car_photo);
            carManageHolder.plate= (TextView) convertView.findViewById(R.id.tv_car_manage_car_plate);
            carManageHolder.plateColor= (TextView) convertView.findViewById(R.id.tv_car_manage_plate_color);
            carManageHolder.plateType = (TextView) convertView.findViewById(R.id.tv_car_manage_plate_type);
            carManageHolder.carColor=  (TextView) convertView.findViewById(R.id.tv_car_manage_car_color);
            carManageHolder.carType = (TextView) convertView.findViewById(R.id.tv_car_manage_car_type);
            carManageHolder.carAudit= (ImageView) convertView.findViewById(R.id.iv_car_manage_audit);
            carManageHolder.check= (ImageView) convertView.findViewById(R.id.iv_car_manage_check);
            carManageHolder.payMode= (TextView) convertView.findViewById(R.id.tv_car_manage_pay_mode);
            convertView.setTag(carManageHolder);
        } else {
            carManageHolder = (CarManageHolder) convertView.getTag();
        }
        CarEntity carManage=carManageList.get(position);
//        if(carManage.getCarPhoto()!=null&&!"".equals(carManage.getCarPhoto())){
//            XUtilsImageUtils.display(carManageHolder.carPhoto,Constants.BASEHOST+carManage.getCarPhoto(),5,context);
//        }else{
//            XUtilsImageUtils.display(carManageHolder.carPhoto, "assets://car_manage_test.png",5,context);
//        }
        XUtilsImageUtils.display(carManageHolder.carPhoto, "assets://car_manage_test.png",5,context);
        carManageHolder.plate.setText(carManage.getPlateNO());
        carManageHolder.plateColor.setText(getPlateColorString(carManage.getPlateColor()));
        carManageHolder.plateType.setText(getPlateTypeString(carManage.getPlateType()));
        carManageHolder.carColor.setText(getCarColorString(carManage.getVehicleColor()));
        carManageHolder.carType.setText(getCarTypeString(carManage.getVehicleType()));
        if(carManage.getAuditStatus()==0){
            carManageHolder.carAudit.setVisibility(View.VISIBLE);
            carManageHolder.payMode.setVisibility(View.GONE);
        }else{
            carManageHolder.payMode.setVisibility(View.VISIBLE);
            carManageHolder.carAudit.setVisibility(View.GONE);
        }
        carManageHolder.check.setVisibility(edit?View.VISIBLE:View.GONE);
        carManageHolder.check.setImageResource(checkList.get(position)==0?R.drawable.check_normal:R.drawable.check_checked);
        carManageHolder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkList.get(position)==0){
                    checkList.set(position,1);
                }else{
                    checkList.set(position,0);
                }
                notifyDataSetChanged();
            }
        });
        carManageHolder.payMode.setText("未包期");
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if(!edit){
            checkList.clear();
            for (int i = 0; i < this.carManageList.size(); i++) {
                checkList.add(0);
            }
        }
    }



    public class CarManageHolder {
        ImageView carPhoto;//汽车照片
        TextView plate;//汽车牌照
        TextView plateColor;//牌照颜色
        TextView plateType;//牌照类型
        TextView carColor;//汽车颜色
        TextView carType;//汽车类型
        ImageView carAudit;//是否审核
        ImageView check;//编辑选择
        TextView payMode;//缴费模式
    }
}
