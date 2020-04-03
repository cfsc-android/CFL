package com.chanfinecloud.cfl.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
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
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.entity.smart.KeyTitleEntity;
import com.chanfinecloud.cfl.entity.smart.ProjectTreeEntity;
import com.chanfinecloud.cfl.entity.smart.UserInfoEntity;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.ParamType;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.MainActivity;
import com.chanfinecloud.cfl.ui.activity.minefeatures.HouseHoldActivity;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.util.LynActivityManager;
import com.chanfinecloud.cfl.weidgt.RecyclerViewDivider;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.BASIC;

public class ProjectSelectActivity extends BaseActivity {

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
    @BindView(R.id.project_select_rlv)
    RecyclerView projectSelectRlv;

    private ProjectSelectAdapter adapter;
    private List<KeyTitleEntity> data=new ArrayList<>();

    private String openFrom="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_project_select);
        ButterKnife.bind(this);

        toolbarTvTitle.setText("选择社区");
        openFrom=getIntent().getExtras().getString("openFrom");
        if("Login".equals(openFrom)||"Register".equals(openFrom)){
            toolbarBtnBack.setVisibility(View.INVISIBLE);
        }
        adapter=new ProjectSelectAdapter(this,data);
        projectSelectRlv.setLayoutManager(new LinearLayoutManager(this));
        projectSelectRlv.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
        projectSelectRlv.setAdapter(adapter);
        projectSelectRlv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(!TextUtils.isEmpty(data.get(position).getKey())){
                    bindProject(data.get(position).getKey());
                }
            }
        });
        getProjectTree();

    }

    @OnClick(R.id.toolbar_btn_back)
    public void onViewClicked() {
        finish();
    }

    private void bindProject(String projectId){

        Map<String,String> map=new HashMap<>();
        map.put("projectId",projectId);
        map.put("householdId", FileManagement.getUserInfo().getId());
        RequestParam requestParam = new RequestParam(BASE_URL+BASIC+"basic/current/bind", HttpMethod.Post);
        requestParam.setRequestMap(map);
        requestParam.setParamType(ParamType.Json);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity baseEntity= JsonParse.parse(result);
                if(baseEntity.isSuccess()){
                    getUserInfo();
                }else{
                    showToast(baseEntity.getMessage());
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
                stopProgressDialog();
            }

        });
        sendRequest(requestParam, true);


    }

    //获取项目树结构
    private void getProjectTree(){

        RequestParam requestParam = new RequestParam(BASE_URL+BASIC+"basic/project/tree", HttpMethod.Get);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity baseEntity= JsonParse.parse(result);
                if(baseEntity.isSuccess()){
                    Type type = new TypeToken<List<ProjectTreeEntity>>() {}.getType();
                    List<ProjectTreeEntity> list= (List<ProjectTreeEntity>) JsonParse.parseList(result,type);
                    getProjectListData(list);
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

    private void getProjectListData(List<ProjectTreeEntity> list){
        data.add(new KeyTitleEntity("全部社区",""));
        for (int i = 0; i < list.size(); i++) {
            data.add(new KeyTitleEntity(list.get(i).getTitle(),list.get(i).getKey()));
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 当按下返回键时所执行的命令
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if("Login".equals(openFrom)||"Register".equals(openFrom)){
                showToast("请选择项目");
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo(){
        RequestParam requestParam = new RequestParam(BASE_URL+BASIC+"basic/householdInfo/currentHousehold", HttpMethod.Get);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity<UserInfoEntity> baseEntity= JsonParse.parse(result,UserInfoEntity.class);
                if(baseEntity.isSuccess()){
                    FileManagement.setUserInfo(baseEntity.getResult());//缓存用户信息
                    finish();
                    if(LynActivityManager.getInstance().getActivityByClass(HouseManageActivity.class)!=null){
                        LynActivityManager.getInstance().finishActivity(HouseManageActivity.class);
                        if (LynActivityManager.getInstance().getActivityByClass(HouseHoldActivity.class)!=null){
                            LynActivityManager.getInstance().finishActivity(HouseHoldActivity.class);
                        }
                        EventBus.getDefault().post(new EventBusMessage<>("projectSelect"));

                    }else{
                        startActivity(MainActivity.class);
                    }
                }else{
                    showToast(baseEntity.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
            }

            @Override
            public void onFinished() {
                super.onFinished();
                stopProgressDialog();
            }
        });
        sendRequest(requestParam,false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
