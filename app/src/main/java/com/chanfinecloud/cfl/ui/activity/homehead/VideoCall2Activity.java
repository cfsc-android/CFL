package com.chanfinecloud.cfl.ui.activity.homehead;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.adapter.VideoListAdapter;
import com.chanfinecloud.cfl.config.HikConfig;
import com.chanfinecloud.cfl.entity.smart.EquipmentInfoBo;
import com.chanfinecloud.cfl.ui.activity.VideoCallInActivity;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.RxUtils;
import com.chanfinecloud.cfl.util.ScreenOrientationHelper;
import com.chanfinecloud.cfl.util.Utils;
import com.chanfinecloud.cfl.weidgt.RecyclerViewDivider;
import com.hikvision.cloud.sdk.CloudOpenSDK;
import com.hikvision.cloud.sdk.core.CloudVideoPlayer;
import com.videogo.exception.BaseException;
import com.videogo.openapi.EZConstants;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.openapi.bean.EZDeviceInfo;
import com.videogo.openapi.bean.EZVideoQualityInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static android.view.View.VISIBLE;

public class VideoCall2Activity extends BaseActivity {

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
    @BindView(R.id.ss_video_call2_preview_play)
    SurfaceView ssVideoCall2PreviewPlay;
    @BindView(R.id.lv_video_call2_list)
    RecyclerView lvVideoCall2List;
    @BindView(R.id.ss_video_call2_progress)
    ProgressBar ssVideoCall2Progress;
    @BindView(R.id.ss_video_call2_rlv_surface)
    RelativeLayout ssVideoCall2RlvSurface;

    private boolean isHolderCreated = false;
    private VideoListAdapter adapter;
    private List<EquipmentInfoBo> data = new ArrayList<>();

    private String mDeviceSerial; // 设备序列号
    private int mChannelNo; // 通道号
    private String mDeviceCode; // 设备验证码

    private CloudVideoPlayer mRealPlayer;
    private EZDeviceInfo mDeviceInfo;
    private ScreenOrientationHelper mScreenOrientationHelper = null;// 转屏控制器
    private boolean isEncry = false;
    private boolean isSupportPTZ; // 是否支持云台操作
    private EZConstants.EZTalkbackCapability mTalkAbility;//设备对讲信息
    private int mCurrentlevelQuality = EZConstants.EZVideoLevel.VIDEO_LEVEL_FLUNET.getVideoLevel(); // 保存当前的视频码流清晰度
    private ArrayList<EZVideoQualityInfo> mVideoQualityList; // 用来存放监控点清晰度的列表
    private Disposable mPlayerDeviceInfoDisposable;
    private Disposable mPlayerLevelSettingDisposable;
    private boolean isPlayOpenStatus;
    private boolean isOldPlaying; //用于界面不可见和可见切换时，记录是否预览的状态
    private boolean isSoundOpenStatus;
    private int curPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_video_call2);
        ButterKnife.bind(this);
        toolbarTvTitle.setText("可视对讲");
        curPosition = -1;
        adapter = new VideoListAdapter(this, data);
        lvVideoCall2List.setLayoutManager(new LinearLayoutManager(this));
        lvVideoCall2List.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
        lvVideoCall2List.setAdapter(adapter);
        lvVideoCall2List.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                // TODO: 2020/4/26 跳界面需要注释掉  注意onResume
                ssVideoCall2RlvSurface.setVisibility(VISIBLE);
                if (!TextUtils.isEmpty(data.get(position).getDeviceSerial())) {
                    if (curPosition == position){
                        showToast("请勿重复播放");
                        return;
                    }
                    curPosition = position;
                    if (data.get(position).getDeviceSerial() != null)
                        mDeviceSerial = data.get(position).getDeviceSerial();
                    if (data.get(position).getValidateCode() != null)
                        mDeviceCode = data.get(position).getValidateCode();

                    HikConfig.DEVICE_SERIAL = mDeviceSerial;
                    HikConfig.DEVICE_CHANNEL_NO = 1;
                    HikConfig.VERIFY_CODE = mDeviceCode;
                    Bundle bundle = new Bundle();
                    bundle.putInt("openType", 1);
                    //startActivity(VideoCallInActivity.class, bundle);
                    // TODO: 2020/4/26 打开新的界面 就不用需要下面的操作了 需打开上面的 注释下面的  注意onResume
                    handler.sendMessage(handler.obtainMessage(0, data.get(position).getDeviceSerial()));
                } else {
                    showToast("不支持视频预览");
                }
            }
        });


        getVideoList();

        mDeviceSerial = "231857475";
        mChannelNo = 1;
        mDeviceCode = "AOZPFF";
        initPlayer();

    }

    @Override
    protected void onResume() {
        super.onResume();
        // TODO: 2020/4/26 如果跳转界面的话就需要这一句
        //curPosition = -1;
    }

    private void startPlayer() {
        if (isHolderCreated) {

            getDeviceInfo(); // demo这里顺序：先获取设备信息，再开始播放，顺序可按需求自行调整
        } else {

            showToast("surface创建失败");
        }
    }

    private void initPlayer() {
        ssVideoCall2PreviewPlay.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // 可见的时候，创建SurfaceView的holder
                // 每次回到该界面，holder都会被重新创建
                if (!isHolderCreated) {
                    isHolderCreated = true;
                    getDeviceInfo();
                }else{
                    if (!Utils.isEmpty(mDeviceCode))
                        isEncry = true;
                    startPlay(isEncry);
                }
                Log.e("surfaceCreated: ", "success");
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.e("surfaceCreated: ", "surfaceChanged");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // 不可见的时候  销毁SurfaceView的holder
                // 切到后台界面或返回主界面，holder被销毁了
                // isOldPlaying = isPlayOpenStatus;
                isHolderCreated = false;
                Log.e("surfaceCreated: ", "destroyed");
                stopPlay();
            }
        });

    }

    /**
     * 开始预览
     *
     * @param isEncry 是否加密,加密的话，设置设备验证码
     */
    private void startPlay(boolean isEncry) {
        mRealPlayer = CloudOpenSDK.getInstance().createPlayer(mDeviceSerial, mChannelNo);
        mRealPlayer.setSurfaceHolder(ssVideoCall2PreviewPlay.getHolder());
        if (isEncry) {
            mRealPlayer.setPlayVerifyCode(mDeviceCode);
        }
        ssVideoCall2Progress.setVisibility(VISIBLE);
        mRealPlayer.startRealPlay();
        mRealPlayer.setOnRealPlayListener(new CloudVideoPlayer.OnRealPlayListener() {
            @Override
            public void onVideoSizeChanged(int videoWidth, int videoHeight) {

            }

            @Override
            public void onRealPlaySuccess() {
                //mScreenOrientationHelper.enableSensorOrientation();
                isPlayOpenStatus = true;
                // 默认开启声音
                if (mRealPlayer.openSound()) {
                    isSoundOpenStatus = true;
                }

                ssVideoCall2Progress.setVisibility(View.GONE);

            }

            @Override
            public void onStopRealPlaySuccess() {
                isPlayOpenStatus = false;
                isSoundOpenStatus = false;

            }

            /**
             * 播放失败回调,得到失败信息
             *
             * @param errorCode   播放失败错误码
             * @param moduleCode  播放失败模块错误码
             * @param description 播放失败描述
             * @param sulution    播放失败解决方方案
             */
            @Override
            public void onRealPlayFailed(int errorCode, String moduleCode, String description, String sulution) {
                showToast(String.format("errorCode：%d, %s", errorCode, description));
                isPlayOpenStatus = false;
                isSoundOpenStatus = false;

                ssVideoCall2Progress.setVisibility(View.GONE);
                if (errorCode == 400035 || errorCode == 400036) {
                    //
                    //回调时查看errorCode，如果为400035（需要输入验证码）和400036（验证码错误），
                    // 则需要开发者自己处理让用户重新输入验证密码，并调用setPlayVerifyCode设置密码，
                    // 然后重新启动播放

                    showToast("验证码错误");
                    return;
                }

                stopPlay();

            }
        });
    }

    private void stopPlay() {
        //mScreenOrientationHelper.disableSensorOrientation();
        if (null != mRealPlayer) {
            mRealPlayer.closeSound();
            mRealPlayer.stopRealPlay(); // 停止播放
            mRealPlayer.release();
        }
    }


    /**
     * CloudOpenSDK.getEZDeviceInfo()需要在子线程中调用
     */
    private void getDeviceInfo() {
        mPlayerDeviceInfoDisposable = Observable.create((ObservableOnSubscribe<EZDeviceInfo>) emitter -> {
            EZDeviceInfo deviceInfo = CloudOpenSDK.getEZDeviceInfo(mDeviceSerial);
            if (null != deviceInfo) {
                emitter.onNext(deviceInfo);
            } else {
                emitter.onError(new Throwable());
            }
            emitter.onComplete();
        }).compose(RxUtils.io2Main())
                .subscribeWith(new DisposableObserver<EZDeviceInfo>() {

                    @Override
                    public void onNext(EZDeviceInfo deviceInfo) {
                        mDeviceInfo = deviceInfo;
                        // 获取对讲信息,对讲模式类型:
                        // 不支持对讲:EZConstants.EZTalkbackCapability.EZTalkbackNoSupport
                        // 支持全双工对讲:EZConstants.EZTalkbackCapability.EZTalkbackFullDuplex
                        // 支持半双工对讲:EZConstants.EZTalkbackCapability.EZTalkbackHalfDuplex
                        mTalkAbility = mDeviceInfo.isSupportTalk();
                        isSupportPTZ = mDeviceInfo.isSupportPTZ();
                        //获取视频清晰度信息
                        List<EZCameraInfo> cameraInfoList = mDeviceInfo.getCameraInfoList();
                        if (null == cameraInfoList) {
                            return;
                        }
                        for (EZCameraInfo cameraInfo : cameraInfoList) {
                            // 先判断通道号
                            if (cameraInfo.getCameraNo() == mChannelNo) {
                                mVideoQualityList = cameraInfo.getVideoQualityInfos();
                                // 设备默认的清晰度为
                                mCurrentlevelQuality = cameraInfo.getVideoLevel().getVideoLevel();
                                String levelName;
                                if (mCurrentlevelQuality == EZConstants.EZVideoLevel.VIDEO_LEVEL_FLUNET.getVideoLevel()) {
                                    levelName = "流畅";
                                } else if (mCurrentlevelQuality == EZConstants.EZVideoLevel.VIDEO_LEVEL_BALANCED.getVideoLevel()) {
                                    levelName = "均衡";
                                } else if (mCurrentlevelQuality == EZConstants.EZVideoLevel.VIDEO_LEVEL_HD.getVideoLevel()) {
                                    levelName = "高清";
                                } else if (mCurrentlevelQuality == EZConstants.EZVideoLevel.VIDEO_LEVEL_SUPERCLEAR.getVideoLevel()) {
                                    levelName = "超清";
                                } else {
                                    levelName = "流畅";
                                }

                            }
                        }
                        if (!Utils.isEmpty(mDeviceCode))
                            isEncry = true;
                        startPlay(isEncry);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof BaseException) {
                            showToast(e.getMessage());
                        }
                        if (mPlayerDeviceInfoDisposable != null && !mPlayerDeviceInfoDisposable.isDisposed()) {
                            mPlayerDeviceInfoDisposable.dispose();
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (mPlayerDeviceInfoDisposable != null && !mPlayerDeviceInfoDisposable.isDisposed()) {
                            mPlayerDeviceInfoDisposable.dispose();
                        }
                    }
                });
    }


    @OnClick({R.id.toolbar_btn_back, R.id.toolbar_tv_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.toolbar_tv_title:
                break;
        }
    }


    private void getVideoList() {
        List<EquipmentInfoBo> list = FileManagement.getUserInfo().getEquipmentInfoBoList();
        if (list != null) {
            data.addAll(list);
            adapter.notifyDataSetChanged();
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    startPlayer();
                    break;
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (null != mRealPlayer) {
            mRealPlayer.release();
        }

    }

}
