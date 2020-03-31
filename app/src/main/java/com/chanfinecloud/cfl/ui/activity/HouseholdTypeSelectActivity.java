package com.chanfinecloud.cfl.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.smart.HouseholdType;
import com.chanfinecloud.cfl.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HouseholdTypeSelectActivity extends BaseActivity {

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
    @BindView(R.id.household_type_property_iv)
    ImageView householdTypePropertyIv;
    @BindView(R.id.household_type_property_ll)
    LinearLayout householdTypePropertyLl;
    @BindView(R.id.household_type_family_iv)
    ImageView householdTypeFamilyIv;
    @BindView(R.id.household_type_family_ll)
    LinearLayout householdTypeFamilyLl;
    @BindView(R.id.household_type_rent_iv)
    ImageView householdTypeRentIv;
    @BindView(R.id.household_type_rent_ll)
    LinearLayout householdTypeRentLl;
    @BindView(R.id.household_type_select_btn)
    Button householdTypeSelectBtn;

    private HouseholdType type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_household_type_select);
        ButterKnife.bind(this);
        toolbarTvTitle.setText("选择身份");
        type=HouseholdType.Property;
        householdTypePropertyIv.setImageResource(R.drawable.single_selected);
        householdTypeFamilyIv.setImageResource(R.drawable.single_unselected);
        householdTypeRentIv.setImageResource(R.drawable.single_unselected);

    }

    @OnClick({R.id.toolbar_btn_back, R.id.household_type_property_ll, R.id.household_type_family_ll, R.id.household_type_rent_ll, R.id.household_type_select_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.household_type_property_ll:
                type=HouseholdType.Property;
                householdTypePropertyIv.setImageResource(R.drawable.single_selected);
                householdTypeFamilyIv.setImageResource(R.drawable.single_unselected);
                householdTypeRentIv.setImageResource(R.drawable.single_unselected);
                break;
            case R.id.household_type_family_ll:
                type=HouseholdType.Family;
                householdTypePropertyIv.setImageResource(R.drawable.single_unselected);
                householdTypeFamilyIv.setImageResource(R.drawable.single_selected);
                householdTypeRentIv.setImageResource(R.drawable.single_unselected);
                break;
            case R.id.household_type_rent_ll:
                type=HouseholdType.Rent;
                householdTypePropertyIv.setImageResource(R.drawable.single_unselected);
                householdTypeFamilyIv.setImageResource(R.drawable.single_unselected);
                householdTypeRentIv.setImageResource(R.drawable.single_selected);
                break;
            case R.id.household_type_select_btn:
                Bundle bundle=new Bundle();
                bundle.putString("roomId",getIntent().getExtras().getString("roomId"));
                bundle.putString("type",type.getType());
                startActivity(HouseholdAuditActivity.class,bundle);
                break;
        }
    }
}
