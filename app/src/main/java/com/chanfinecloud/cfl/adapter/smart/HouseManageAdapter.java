package com.chanfinecloud.cfl.adapter.smart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.adapter.BaseSwipListAdapter;
import com.chanfinecloud.cfl.entity.smart.ApprovalStatusType;
import com.chanfinecloud.cfl.entity.smart.HouseholdRoomEntity;


import java.util.List;

/**
 * Created by Loong on 2020/3/18.
 * Version: 1.0
 * Describe:
 */
public class HouseManageAdapter extends BaseSwipListAdapter {
    private Context context;
    private List<HouseholdRoomEntity> data;
    private boolean showLine;

    public HouseManageAdapter(Context context, List<HouseholdRoomEntity> data) {
        this.context = context;
        this.data = data;
        this.showLine=true;
    }

    public HouseManageAdapter(Context context, List<HouseholdRoomEntity> data, boolean showLine) {
        this.context = context;
        this.data = data;
        this.showLine = showLine;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HouseManageHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_room_list_layout,null);
            holder=new HouseManageHolder();
            holder.project_name=convertView.findViewById(R.id.item_project_name);
            holder.room_name=convertView.findViewById(R.id.item_room_name);
            holder.room_status=convertView.findViewById(R.id.item_room_status);
            holder.room_list_line=convertView.findViewById(R.id.room_list_line);
            holder.room_nav_next=convertView.findViewById(R.id.item_room_nav_next);
            convertView.setTag(holder);
        }else{
            holder= (HouseManageHolder) convertView.getTag();
        }
        HouseholdRoomEntity householdRoomEntity=data.get(position);
        holder.room_name.setText(householdRoomEntity.getFullName());
        holder.project_name.setText(householdRoomEntity.getProjectName());
        if(householdRoomEntity.getApprovalStatus()== ApprovalStatusType.Audit.getType()){
            holder.room_status.setText("等待审核");
            holder.room_status.setBackgroundResource(R.drawable.bg_desc_shape);
        }else if(householdRoomEntity.getApprovalStatus()== ApprovalStatusType.Refuse.getType()){
            holder.room_status.setText("审核被拒");
            holder.room_status.setBackgroundResource(R.drawable.bg_desc_shape);
        }else if(householdRoomEntity.getApprovalStatus()== ApprovalStatusType.Pass.getType()){
            holder.room_status.setText(householdRoomEntity.getHouseholdTypeDisplay());
            holder.room_status.setBackgroundResource(R.drawable.bg_green_shape);
        }
        if(showLine){
            holder.room_list_line.setVisibility(View.VISIBLE);
        }else{
            holder.room_list_line.setVisibility(View.INVISIBLE);
        }
        if(householdRoomEntity.getHouseholdType() != null && householdRoomEntity.getHouseholdType().equals("YZ")){
            holder.room_nav_next.setVisibility(View.VISIBLE);
        }else{
            holder.room_nav_next.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }


    public class HouseManageHolder {
        TextView room_status;//状态
        TextView project_name;//项目名称
        TextView room_name;//房间名称
        TextView room_list_line;
        ImageView room_nav_next;
    }

    @Override
    public boolean getSwipEnableByPosition(int position) {
        HouseholdRoomEntity householdRoomEntity=data.get(position);
        if(householdRoomEntity.getApprovalStatus()== ApprovalStatusType.Audit.getType()){
            return true;
        }
        return false;

    }
}
