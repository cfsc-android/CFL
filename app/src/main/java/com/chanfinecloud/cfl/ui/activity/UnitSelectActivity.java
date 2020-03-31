package com.chanfinecloud.cfl.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.adapter.smart.ProjectSelectAdapter;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.smart.CurrentDistrictEntity;
import com.chanfinecloud.cfl.entity.smart.KeyTitleEntity;
import com.chanfinecloud.cfl.entity.smart.ProjectTreeEntity;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.weidgt.RecyclerViewDivider;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.util.LogUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.BASIC;

public class UnitSelectActivity extends BaseActivity {

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
    @BindView(R.id.unit_select_rlv)
    RecyclerView unitSelectRlv;

    private ProjectSelectAdapter adapter;
    private List<KeyTitleEntity> data=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_unit_select);
        ButterKnife.bind(this);

        toolbarTvTitle.setText("选择房屋");
        adapter=new ProjectSelectAdapter(this,data);
        unitSelectRlv.setLayoutManager(new LinearLayoutManager(this));
        unitSelectRlv.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
        unitSelectRlv.setAdapter(adapter);
        unitSelectRlv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                String key=data.get(position).getKey();
                String title=data.get(position).getTitle();
                if(!TextUtils.isEmpty(key)){
                    Bundle bundle=new Bundle();
                    bundle.putString("unitId",key);
                    bundle.putString("title",title);
                    startActivity(RoomSelectActivity.class,bundle);
                }
            }
        });
        getProjectTree();

    }

    private void getProjectTree(){

        RequestParam requestParam = new RequestParam(BASE_URL+BASIC+"basic/project/tree", HttpMethod.Get);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtil.d(result);
                BaseEntity baseEntity= JsonParse.parse(result);
                if(baseEntity.isSuccess()){
                    Type type = new TypeToken<List<ProjectTreeEntity>>() {}.getType();
                    List<ProjectTreeEntity> list= (List<ProjectTreeEntity>) JsonParse.parseList(result,type);
                    getUnitListData(list);
                }else{
                    showToast(baseEntity.getMessage());
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
            }

        });
        sendRequest(requestParam, false);


    }

    private void getUnitListData(List<ProjectTreeEntity> list){
        CurrentDistrictEntity currentDistrict= FileManagement.getUserInfoEntity().getCurrentDistrict();
        ProjectTreeEntity projectTree=null;
        for (int i = 0; i < list.size(); i++) {
            if(currentDistrict.getProjectId().equals(list.get(i).getKey())){
                projectTree=list.get(i);
            }
        }
        if(projectTree!=null){
            String projectTitle=projectTree.getTitle();
            for (int i = 0; i < projectTree.getChildren().size(); i++) {
                ProjectTreeEntity phase=projectTree.getChildren().get(i);
                data.add(new KeyTitleEntity(projectTitle+phase.getTitle(),""));
                String phaseTitle=phase.getTitle();
                for (int i1 = 0; i1 < phase.getChildren().size(); i1++) {
                    ProjectTreeEntity build=phase.getChildren().get(i1);
                    String buildTitle=build.getTitle();
                    for (int i2 = 0; i2 < build.getChildren().size(); i2++) {
                        ProjectTreeEntity unit=build.getChildren().get(i2);
                        String unitTitle=unit.getTitle();
                        data.add(new KeyTitleEntity(projectTitle+phaseTitle+buildTitle+unitTitle,unit.getKey()));
                    }
                }
            }

        }
        adapter.notifyDataSetChanged();
    }


    @OnClick(R.id.toolbar_btn_back)
    public void onViewClicked() {
        finish();
    }
}
