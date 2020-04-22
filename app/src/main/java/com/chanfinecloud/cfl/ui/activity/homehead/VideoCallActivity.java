package com.chanfinecloud.cfl.ui.activity.homehead;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.adapter.VideoListAdapter;
import com.chanfinecloud.cfl.config.HikConfig;
import com.chanfinecloud.cfl.entity.smart.EquipmentInfoBo;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.weidgt.RecyclerViewDivider;
import com.hikvision.zhyjsdk.ZHYJHandler;
import com.hikvision.zhyjsdk.ZHYJSDK;
import com.hikvision.zhyjsdk.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.VISIBLE;

public class VideoCallActivity extends BaseActivity {

    @BindView(R.id.toolbar_tv_title)
    TextView toolbarTvTitle;

    @BindView(R.id.ss_video_call_preview_play)
    SurfaceView ssVideoCallPreviewPlay;
    @BindView(R.id.lv_video_call_list)
    RecyclerView lvVideoCallList;

    private VideoListAdapter adapter;
    private List<EquipmentInfoBo> data=new ArrayList<>();
    private ZHYJSDK sdk;
    private boolean sdkInit=false;

    @Override
    protected void initData() {
        setContentView(R.layout.activity_video_call);
        ButterKnife.bind(this);
        toolbarTvTitle.setText("可视对讲");

        adapter=new VideoListAdapter(this,data);
        lvVideoCallList.setLayoutManager(new LinearLayoutManager(this));
        lvVideoCallList.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
        lvVideoCallList.setAdapter(adapter);
        lvVideoCallList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                ssVideoCallPreviewPlay.setVisibility(VISIBLE);
                if(!TextUtils.isEmpty(data.get(position).getDeviceSerial())){
                    handler.sendMessage(handler.obtainMessage(0,data.get(position).getDeviceSerial()));
                }else{
                    showToast("不支持视频预览");
                }
            }
        });


        getVideoList();
        initSDK();
    }
    private void initSDK(){
        sdk = ZHYJSDK.getInstance();
        sdk.initConfig(getApplicationContext(), HikConfig.CLIENT_ID, HikConfig.CLIENT_SECRET, new ZHYJSDK.GetEZConfigCallback() {
            @SuppressLint("HandlerLeak")
            @Override
            public void onSuccess(String s, String s1) {
                Log.e("tees",s+"++++++++++++++++++++++++++++"+s1);
                sdk.initSDK(s, s1, new ZHYJHandler(getApplicationContext()) {

                    @Override
                    public void onGetAppkeySuccess(String s, String s1) {
                        Log.e("可视对讲","appkey:  " + s + "  token:  " + s1);
                        sdkInit=true;
                    }

                    @Override
                    public void onStartPlaySuccess() {
                        Log.e("可视对讲","预览成功  设备ID" );
                    }

                    @Override
                    public void onStartPlayFail(com.videogo.errorlayer.ErrorInfo errorInfo) {
                        Log.e("可视对讲","预览失败" + errorInfo.description);
                        showToast("预览失败" + errorInfo.description);
                    }

                    @Override
                    public void onStartTalkSuccess() {

                    }

                    @Override
                    public void onStartTalkFail(com.videogo.errorlayer.ErrorInfo errorInfo) {

                    }
                });
            }

            @Override
            public void onFaile(int i, Throwable throwable) {
                LogUtils.deBug(throwable.getMessage());
            }
        });
        ZHYJSDK.setDebugLogEnable(true);
    }

    private void getVideoList(){
        List<EquipmentInfoBo> list = FileManagement.getUserInfo().getEquipmentInfoBoList();
        if(list!=null){
            data.addAll(list);
            adapter.notifyDataSetChanged();
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    startPreview((String) msg.obj);
                    break;
            }
        }
    };


    private void startPreview(String deviceID){
        Log.d("deviceID",deviceID);
        sdk.stopRealPlay();
        sdk.releasePlayer();
        sdk.startRealPlay(deviceID,ssVideoCallPreviewPlay.getHolder());
    }

    @OnClick({R.id.toolbar_btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(sdkInit){
            sdk.releasePlayer();
            sdk.destroySDK();
        }
    }

}
