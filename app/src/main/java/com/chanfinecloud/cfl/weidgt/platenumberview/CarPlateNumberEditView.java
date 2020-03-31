package com.chanfinecloud.cfl.weidgt.platenumberview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.chanfinecloud.cfl.R;

import java.util.ArrayList;

/**
 * Created by zengx on 2019/5/20.
 * Describe:
 */
public class CarPlateNumberEditView extends LinearLayout {
    private Context context;
    private ArrayList<String> plateList;
    private TextView[] plateViews;
    private TextView tv_1,tv_2,tv_3,tv_4,tv_5,tv_6,tv_7,tv_8;
    private FrameLayout fl_8;
    private boolean focus;
    private OnPlateNumberValid onPlateNumberValid;

    public CarPlateNumberEditView(Context context) {
        super(context);
        this.context=context;
    }

    public CarPlateNumberEditView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    public CarPlateNumberEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
    }
    //初始化
    private void init(){
        focus=false;
        plateList=new ArrayList<>();
        LayoutInflater inflater=LayoutInflater.from(context);
        inflater.inflate(R.layout.view_car_plate_number,this,true);
        tv_1= (TextView) findViewById(R.id.tv_car_plate_number_1);
        tv_2= (TextView) findViewById(R.id.tv_car_plate_number_2);
        tv_3= (TextView) findViewById(R.id.tv_car_plate_number_3);
        tv_4= (TextView) findViewById(R.id.tv_car_plate_number_4);
        tv_5= (TextView) findViewById(R.id.tv_car_plate_number_5);
        tv_6= (TextView) findViewById(R.id.tv_car_plate_number_6);
        tv_7= (TextView) findViewById(R.id.tv_car_plate_number_7);
        tv_8= (TextView) findViewById(R.id.tv_car_plate_number_8);
        fl_8= (FrameLayout) findViewById(R.id.fl_car_plate_number_8);
        plateViews=new TextView[]{tv_1,tv_2,tv_3,tv_4,tv_5,tv_6,tv_7,tv_8};
    }
    //更新车牌号视图
    private void initPlateNumberView(){
        if(plateList.size()>6){
            onPlateNumberValid.plateNumberValid(true);
        }else{
            onPlateNumberValid.plateNumberValid(false);
        }
        for (int i = 0; i < plateViews.length; i++) {
            if(i<plateList.size()){
                plateViews[i].setText(plateList.get(i));
                if(i==plateViews.length-1){
                    fl_8.setBackgroundResource(R.drawable.shape_plate_edit_blur);
                    plateViews[i].setVisibility(VISIBLE);
                }else{
                    plateViews[i].setBackgroundResource(R.drawable.shape_plate_edit_blur);
                }
            }else{
                plateViews[i].setText("");
                if(i==plateViews.length-1){
                    plateViews[i].setVisibility(GONE);
                    fl_8.setBackgroundResource(R.drawable.shape_plate_edit_xly);
                    if(plateList.size()==i&&focus) {
                        fl_8.setBackgroundResource(R.drawable.shape_plate_edit_focus);
                    }
                }else{
                    plateViews[i].setBackgroundResource(R.drawable.shape_plate_edit_blur);
                    if(plateList.size()==i&&focus) {
                        plateViews[i].setBackgroundResource(R.drawable.shape_plate_edit_focus);
                    }
                }
            }
        }
    }

    /**
     * 清空车牌号
     */
    public void clearPlateNumber(){
        plateList.clear();
        initPlateNumberView();
    }

    /**
     * 删除车牌号最后一位
     */
    public void deletePlateNumber(){
        if(plateList.size()>0){
            plateList.remove(plateList.size()-1);
            initPlateNumberView();
        }
    }

    /**
     * 往后新增车牌号
     * @param plateNumber
     */
    public void setPlateNumber(String plateNumber){
        for (int i = 0; i < plateNumber.length(); i++) {
            plateList.add(plateNumber.substring(i,i+1));
        }
        initPlateNumberView();

    }

    /**
     * 获取车牌号位数
     * @return
     */
    public int getPlateNumberPosition(){
        return plateList.size();
    }

    /**
     * 获取车牌号码
     * @return
     */
    public String getPlateNumberText(){
        String result="";
        for (String s : plateList) {
            result += s;
        }
        return result;
    }

    /**
     * 设置焦点
     * @param focus
     */
    public void setFocus(boolean focus){
        this.focus=focus;
        initPlateNumberView();
    }

    public void setOnPlateNumberValid(OnPlateNumberValid onPlateNumberValid){
        this.onPlateNumberValid=onPlateNumberValid;
    }

    public interface OnPlateNumberValid{
        void plateNumberValid(boolean valid);
    }
}
