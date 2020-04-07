package com.chanfinecloud.cfl.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LifePaymentActivity extends BaseActivity {

    @BindView(R.id.toolbar_btn_back)
    ImageButton toolbarBtnBack;
    @BindView(R.id.toolbar_tv_title)
    TextView toolbarTvTitle;
    @BindView(R.id.toolbar_tv_action)
    TextView toolbarTvAction;
    @BindView(R.id.toolbar_btn_action)
    ImageButton toolbarBtnAction;
    @BindView(R.id.toolbar_ll_view)
    LinearLayout toolbarLlView;
    @BindView(R.id.gv_life_payment_list)
    GridView gvLifePaymentList;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    // 图片封装为一个数组
    private int[] icon = {R.drawable.pay_property, R.drawable.pay_parking, R.drawable.pay_water, R.drawable.pay_power,
            R.drawable.pay_inter, R.drawable.pay_tel, R.drawable.pay_gas, R.drawable.pay_cable};
    private String[] iconName = {"物业费", "停车缴费", "水费", "电费", "宽带", "固话", "燃气费", "有线电视"};


    @Override
    protected void initData() {
        setContentView(R.layout.activity_life_payment);
        ButterKnife.bind(this);
        toolbarTvTitle.setText("生活缴费");
        data_list = init();
        String[] from = {"image", "text"};
        int[] to = {R.id.iv_item_life_payment_list_icon, R.id.tv_item_life_payment_list_title};
        sim_adapter = new SimpleAdapter(this, data_list, R.layout.item_life_payment_list, from, to);
        //配置适配器
        gvLifePaymentList.setAdapter(sim_adapter);
        gvLifePaymentList.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gvLifePaymentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle a_bundle = new Bundle();
                if ("水费".equals(data_list.get(position).get("text").toString())) {
                    a_bundle.putString("title", "水费查询");
                    a_bundle.putString("url", "https://billcloud.unionpay.com/ccfront/loc/CH5512/search?category=D4");
                    startActivity(NewsInfoActivity.class, a_bundle);
                } else if ("电费".equals(data_list.get(position).get("text").toString())) {
                    a_bundle.putString("title", "电费查询");
                    a_bundle.putString("url", "https://billcloud.unionpay.com/ccfront/loc/CH5512/search?category=D1");
                    startActivity(NewsInfoActivity.class, a_bundle);
                } else if ("停车缴费".equals(data_list.get(position).get("text").toString())) {
                   startActivity(ParkingPaymentActivity.class);
                } else {
                    showToast("暂未集成，敬请期待");
                }
            }
        });
    }

    @OnClick({R.id.toolbar_btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
        }
    }

    private List<Map<String, Object>> init() {
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data.add(map);
        }
        return data;
    }

}
