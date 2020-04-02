package com.chanfinecloud.cfl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.ProjectInfo;
import com.chanfinecloud.cfl.util.XUtilsImageUtils;

import java.util.ArrayList;

/**
 * Created by zengx on 2019/7/30.
 * Describe:
 */
public class ProjectListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ProjectInfo> list;
    private ArrayList<Integer> checkList=new ArrayList<>();
    private boolean edit;

    public ProjectListAdapter(Context context, ArrayList<ProjectInfo> list) {
        this.context = context;
        this.list = list;
    }

    public void setEdit(boolean edit){
        this.edit=edit;
        this.notifyDataSetChanged();
    }

    public boolean isEdit() {
        return edit;
    }

    public void checkItem(int position){
        if(checkList.contains(position)){
            checkList.remove(Integer.valueOf(position));
        }else{
            checkList.add(position);
        }
        this.notifyDataSetChanged();
    }

    public void clearCheckItem(){
        checkList.clear();
    }

    public ArrayList<Integer> getCheckList(){
        return checkList;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProjectHolder holder;
        if(convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.project_list_item,null);
            holder=new ProjectHolder();
            holder.projectName= (TextView) convertView.findViewById(R.id.project_list_item_name);
            holder.projectAddress= (TextView) convertView.findViewById(R.id.project_list_item_address);
            holder.projectLogo= (ImageView) convertView.findViewById(R.id.project_list_item_logo);
            holder.iv_item_check= (ImageView) convertView.findViewById(R.id.iv_item_check);
            convertView.setTag(holder);
        }else{
            holder= (ProjectHolder) convertView.getTag();
        }
        ProjectInfo info=list.get(position);
        holder.projectName.setText(info.getProjectName());
        holder.projectAddress.setText(info.getProjectAddress());
        if(info.getProjectLogo()!=null&&!"".equals(info.getProjectLogo())){
            XUtilsImageUtils.display(holder.projectLogo,info.getProjectLogo());
        }
        if(edit){
            holder.iv_item_check.setVisibility(View.VISIBLE);
            if(position>1){
                boolean flag=false;
                for (Integer integer : checkList) {
                    if(position == integer){
                        flag=true;
                        break;
                    }
                }
                if(flag){
                    holder.iv_item_check.setImageResource(R.drawable.btn_check_on_normal);
                }else{
                    holder.iv_item_check.setImageResource(R.drawable.btn_check_off_normal);
                }
            }else{
                holder.iv_item_check.setImageResource(R.drawable.btn_check_off_disable);
            }

        }else{
            holder.iv_item_check.setVisibility(View.GONE);
        }
        return convertView;
    }

    public class ProjectHolder{
        TextView projectName;
        TextView projectAddress;
        ImageView projectLogo;
        ImageView iv_item_check;
    }
}
