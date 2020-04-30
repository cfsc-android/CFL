package com.chanfinecloud.cfl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.adapter.smart.CarEntity;
import com.chanfinecloud.cfl.entity.smart.ApprovalStatusType;
import com.chanfinecloud.cfl.util.Utils;
import com.chanfinecloud.cfl.util.XUtilsImageUtils;

import java.util.ArrayList;
import java.util.Date;

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

    public void  setData(ArrayList<CarEntity> carManageList){
        this.carManageList = carManageList;
        notifyDataSetChanged();
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
        String picUrl = "";

        if(carManage.getVehicleImageResource()!=null&&!"".equals(carManage.getVehicleImageResource())){
            picUrl =carManage.getVehicleImageResource().getUrl();
        }else{
            picUrl = "https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E6%B1%BD%E8%BD%A6%E5%9B%BE%E7%89%87&step_word=&hs=0&pn=6&spn=0&di=5830&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=2035462553%2C3805072346&os=2284628956%2C130487509&simid=3407802275%2C474022487&adpicid=0&lpn=0&ln=1568&fr=&fmq=1585883534723_R&fm=&ic=undefined&s=undefined&hd=undefined&latest=undefined&copyright=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F5%2F546efb213fb32.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Botg9aaa_z%26e3Bv54AzdH3Fowssrwrj6_1jpwts_cmbba_z%26e3Bip4s&gsm=5&rpstart=0&rpnum=0&islist=&querylist=&force=undefined";
        }

        Glide.with(context).load(picUrl)
                .error(R.drawable.car_manage_test)
                .into(carManageHolder.carPhoto);
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
        if (!Utils.isEmpty(carManage.getParkEnddate()) && !Utils.isEmpty(carManage.getParkStartdate())){
            long nTime = new Date().getTime();
            if (Utils.getDateTimeByStringAndFormat(carManage.getParkStartdate(), "yyyy-MM-dd") > nTime){
                carManageHolder.payMode.setText("包期-未开始");
            }else if (Utils.getDateTimeByStringAndFormat(carManage.getParkStartdate(), "yyyy-MM-dd") <= nTime
            && Utils.getDateTimeByStringAndFormat(carManage.getParkEnddate(), "yyyy-MM-dd") >= nTime){
                carManageHolder.payMode.setText("已包期");
            }else if (Utils.getDateTimeByStringAndFormat(carManage.getParkEnddate(), "yyyy-MM-dd") < nTime){
                carManageHolder.payMode.setText("包期-已过期");
            }else{
                carManageHolder.payMode.setText("包期-日期错误");
            }
        }else{
            carManageHolder.payMode.setText("未包期");
        }


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

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);

        CarEntity carEntity = carManageList.get(position);
        int ntype = 1;
        if (carEntity != null && carEntity.getAuditStatus() == 0){
            ntype = 2;
        }

        return  ntype;

    }
}
