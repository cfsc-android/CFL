package com.chanfinecloud.cfl.adapter.smart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.smart.ApprovalStatusType;
import com.chanfinecloud.cfl.entity.smart.AuditEntity;
import com.chanfinecloud.cfl.entity.smart.HouseholdType;

import java.util.List;

/**
 * Created by Loong on 2020/3/18.
 * Version: 1.0
 * Describe:
 */
public class HouseholdAuditListAdapter extends BaseAdapter {
    private Context context;
    private List<AuditEntity> data;

    public HouseholdAuditListAdapter(Context context, List<AuditEntity> data) {
        this.context = context;
        this.data = data;
    }

    public void setData(List<AuditEntity> data){
        this.data = data;
        notifyDataSetChanged();
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
            convertView= LayoutInflater.from(context).inflate(R.layout.item_household_audit_list_layout,null);
            holder=new HouseManageHolder();
            holder.item_audit_status=convertView.findViewById(R.id.item_audit_status);
            holder.item_audit_tel=convertView.findViewById(R.id.item_audit_tel);
            holder.item_audit_type=convertView.findViewById(R.id.item_audit_type);
            holder.item_audit_remark=convertView.findViewById(R.id.item_audit_remark);
            holder.item_audit_time=convertView.findViewById(R.id.item_audit_time);
            convertView.setTag(holder);
        }else{
            holder= (HouseManageHolder) convertView.getTag();
        }
        AuditEntity auditEntity=data.get(position);
        if(auditEntity.getStatus()== ApprovalStatusType.Pass.getType()){
            holder.item_audit_status.setText("审核通过");
            holder.item_audit_status.setBackgroundResource(R.drawable.bg_green_shape);
        }else if(auditEntity.getStatus()==ApprovalStatusType.Refuse.getType()){
            holder.item_audit_status.setText("审核拒绝");
            holder.item_audit_status.setBackgroundResource(R.drawable.bg_desc_shape);
        }else if(auditEntity.getStatus()==ApprovalStatusType.Audit.getType()){
            holder.item_audit_status.setText("等待审核");
            holder.item_audit_status.setBackgroundResource(R.drawable.bg_desc_shape);
        }
        if(auditEntity.getType().equals(HouseholdType.Family.getType())){
            holder.item_audit_type.setText("家属");
        }else if(auditEntity.getType().equals(HouseholdType.Rent.getType())){
            holder.item_audit_type.setText("租户");
        }
        holder.item_audit_tel.setText(auditEntity.getMobile());
        holder.item_audit_remark.setText(auditEntity.getRemark());
        holder.item_audit_time.setText(auditEntity.getCreateTime());
        return convertView;
    }


    public class HouseManageHolder {
        TextView item_audit_status;//状态
        TextView item_audit_tel;//电话号码
        TextView item_audit_type;//申请类型
        TextView item_audit_remark;//备注
        TextView item_audit_time;//申请时间
    }


    @Override
    public int getItemViewType(int position) {
         super.getItemViewType(position);
         AuditEntity auditEntity=data.get(position);
         int nType = 2;
         if(auditEntity.getStatus()==ApprovalStatusType.Audit.getType()){
             nType = 1;
         }
         return nType;
    }
}
