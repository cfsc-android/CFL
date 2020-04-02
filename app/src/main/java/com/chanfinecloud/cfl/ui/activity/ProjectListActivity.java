package com.chanfinecloud.cfl.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.adapter.ProjectListAdapter;
import com.chanfinecloud.cfl.entity.ProjectInfo;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chanfinecloud.cfl.config.Config.BASE_URL;

public class ProjectListActivity extends BaseActivity {

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
    @BindView(R.id.lv_project_list)
    ListView lvProjectList;
    @BindView(R.id.btn_project_list_add)
    Button btnProjectListAdd;
    @BindView(R.id.ll_project_list_add)
    LinearLayout llProjectListAdd;
    @BindView(R.id.btn_project_list_cancel)
    Button btnProjectListCancel;
    @BindView(R.id.btn_project_list_delete)
    Button btnProjectListDelete;
    @BindView(R.id.ll_project_list_delete)
    LinearLayout llProjectListDelete;

    private ArrayList<ProjectInfo> projectInfos = new ArrayList<>();
    private ProjectListAdapter projectListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_project_list);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);
        toolbarTvTitle.setText("选择小区");
        initProjectInfos();
        projectListAdapter=new ProjectListAdapter(this,projectInfos);
        lvProjectList.setAdapter(projectListAdapter);
        lvProjectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(projectListAdapter.isEdit()){
                    if(position>1){
                        projectListAdapter.checkItem(position);
                    }
                }else{
                    FileManagement.setProjectInfo(projectInfos.get(position));
                    EventBus.getDefault().post(new EventBusMessage<>("projectChange"));
                    finish();
                }
            }
        });
        lvProjectList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                projectListAdapter.setEdit(true);
                llProjectListDelete.setVisibility(View.VISIBLE);
                llProjectListAdd.setVisibility(View.GONE);
                return true;
            }
        });
    }

    @OnClick({R.id.toolbar_btn_back, R.id.btn_project_list_add, R.id.btn_project_list_cancel, R.id.btn_project_list_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.btn_project_list_add:
                startActivity(IpSettingActivity.class);
                break;
            case R.id.btn_project_list_cancel:
                projectListAdapter.clearCheckItem();
                projectListAdapter.setEdit(false);
                llProjectListDelete.setVisibility(View.GONE);
                llProjectListAdd.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_project_list_delete:
                ArrayList<Integer> deleteList=projectListAdapter.getCheckList();
                if(deleteList.size()>0){
                    ArrayList<ProjectInfo> temp=new ArrayList<>();
                    for (int i = 0; i < projectInfos.size(); i++) {
                        if(!deleteList.contains(i)){
                            temp.add(projectInfos.get(i));
                        }
                    }
                    projectInfos.clear();
                    projectInfos.addAll(temp);
                    FileManagement.removeCustomerProject(deleteList);
                    projectListAdapter.clearCheckItem();
                    projectListAdapter.setEdit(false);
                    llProjectListDelete.setVisibility(View.GONE);
                    llProjectListAdd.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(ProjectListActivity.this,"请选择项目",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void initProjectInfos(){
        projectInfos.clear();
        RequestParam requestParam = new RequestParam("http://dev.chanfine.com:9082/smartxd/api/selectProjectForApp.action", HttpMethod.Get);

        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if("0".equals(jsonObject.getString("resultCode"))) {
                        Gson gson=new Gson();
                        Type type = new TypeToken<List<ProjectInfo>>() {}.getType();
                        projectInfos.addAll((Collection<? extends ProjectInfo>) gson.fromJson(jsonObject.getString("data"),type));
                        ArrayList<ProjectInfo> list=FileManagement.getCustomerProjects();
                        if(list!=null){
                            projectInfos.addAll(FileManagement.getCustomerProjects());
                        }
                        projectListAdapter.notifyDataSetChanged();
                    }else{
                        showToast(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
            }

        });
        sendRequest(requestParam, false);

//        projectInfos.add(new ProjectInfo("长房 · 云西府",
//                "岳麓大道与麓谷大道交汇处东北角",
//                "http://dev.chanfine.com:9082/smartxd/",
////                "http://222.240.37.83:9082/smartxd/",
//                "",
//                "YXF"));
//        projectInfos.add(new ProjectInfo("长房 · 金阳府",
//                "浏阳市健寿大道与康万路交汇处西南角",
//                "http://dev.chanfine.com:9082/smartjy/",
////                "http://222.240.37.83:9082/smartxd/",
//                "",
//                "JYF"));
//        projectInfos.add(new ProjectInfo("长房 · 万楼公馆",
//                "湖南省湘潭市雨湖区潭州大道与护谭路交汇处",
//                "http://dev.chanfine.com:9082/smartwl/",
////                "http://222.240.37.83:9082/smartxd/",
//                "",
//                "WLGG"));
//        projectInfos.add(new ProjectInfo("长房 · 雍景湾",
//                "湖南省长沙市开福区盛世路",
//                "http://dev.chanfine.com:9082/smartyjw/",
////                "http://222.240.37.83:9082/smartxd/",
//                "",
//                "YJW"));
//        ArrayList<ProjectInfo> list=FileManagement.getCustomerProjects();
//        if(list!=null){
//            projectInfos.addAll(FileManagement.getCustomerProjects());
//        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventBusMessage message){
        if("customerProject".equals(message.getMessage())){
            initProjectInfos();
            projectListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
