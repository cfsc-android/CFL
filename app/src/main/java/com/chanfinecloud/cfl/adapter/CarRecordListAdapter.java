package com.chanfinecloud.cfl.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.HikCarCrossRecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by damien on 2020/3/30.
 * Describe:
 */
public class CarRecordListAdapter extends RecyclerView.Adapter<CarRecordListAdapter.ViewHolder> {
    
    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<HikCarCrossRecord> carRecordArrayList;

    public CarRecordListAdapter(Context context, ArrayList<HikCarCrossRecord> carRecordArrayList) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.carRecordArrayList = carRecordArrayList;
    }

    public void setData(ArrayList<HikCarCrossRecord> carRecordArrayList) {
        this.carRecordArrayList = carRecordArrayList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_record_list_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        HikCarCrossRecord data = carRecordArrayList.get(position);
        if (data != null){
            String crossTime = data.getCreateTime().replace("T", " ").replace("+08:00", "");
            holder.tvCarRecordCarCode.setText(data.getPlateNo());
            holder.tvCarRecordInTime.setText(data.getVehicleOut() == 0 ? crossTime : "");
            holder.tvCarRecordOutTime.setText(data.getVehicleOut() == 1 ? crossTime : "");
            String parkingTimeLength = "";
            if (data.getVehicleOut() == 1) {
                String lastIntime = getParkingTime(position);
                if (!"".equals(lastIntime)) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date in_date = sdf.parse(lastIntime);
                        Date out_date = sdf.parse(crossTime);
                        long time = out_date.getTime() - in_date.getTime();
                        Log.e("getParkingTime", position + "__" + time);
                        if (time / 1000 / 60 >= 60) {
                            parkingTimeLength = (time / 1000 / 60) % 60 == 0 ? time / 1000 / 60 / 60 + "小时" : time / 1000 / 60 / 60 + "小时" + (time / 1000 / 60) % 60 + "分钟";
                        } else {
                            parkingTimeLength = time / 1000 / 60 + "分钟";
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                Log.e("getParkingTime", position + "__" + getParkingTime(position));
            }
            holder.tvCarRecordTimeLength.setText(parkingTimeLength);
            holder.tvCarRecordCrossMode.setText(getReleaseModeCh(data.getReleaseMode()));
            if (data.getReleaseMode() == 0 || data.getReleaseMode() == 30) {
                holder.tvCarRecordCrossMode.setTextColor(context.getResources().getColor(R.color.follow_background));
                holder.tvCarRecordCrossMode.setBackgroundResource(R.drawable.bg_car_record_mode_fail);
            } else {
                holder.tvCarRecordCrossMode.setTextColor(context.getResources().getColor(R.color.car_lock_theme));
                holder.tvCarRecordCrossMode.setBackgroundResource(R.drawable.bg_car_record_mode_success);
            }
        }
       
    }

    @Override
    public int getItemCount() {
        return carRecordArrayList.size();
    }

    private String getReleaseModeCh(int mode) {
        String result = "其它";
        switch (mode) {
            case 0:
                result = "禁止放行";
                break;
            case 1:
                result = "固定车包期";
                break;
            case 2:
                result = "临时车入场";
                break;
            case 10:
                result = "离线出场";
                break;
            case 11:
                result = "缴费出场";
                break;
            case 12:
                result = "预付费出场";
                break;
            case 13:
                result = "免费出场";
                break;
            case 30:
                result = "非法卡不放行";
                break;
            case 35:
                result = "群组车放行";
                break;
        }
        return result;
    }

    private String getParkingTime(int position) {
        String result = "";
        if (position < carRecordArrayList.size() - 1) {
            if (carRecordArrayList.get(position + 1).getVehicleOut() == 0) {
                result = carRecordArrayList.get(position + 1).getCreateTime();
            } else {
                result = getParkingTime(position + 1);
            }
        }
        if (!"".equals(result)) {
            result = result.replace("T", " ").replace("+08:00", "");
        }
        return result;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_car_record_car_code)
        TextView tvCarRecordCarCode;//汽车牌照
        @BindView(R.id.tv_car_record_cross_mode)
        TextView tvCarRecordCrossMode;
        @BindView(R.id.tv_car_record_in_time)
        TextView tvCarRecordInTime;//入场时间
        @BindView(R.id.tv_car_record_out_time)
        TextView tvCarRecordOutTime;//出场时间
        @BindView(R.id.tv_car_record_time_length)
        TextView tvCarRecordTimeLength;//停车时长

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}