package com.chanfinecloud.cfl.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.config.HikConfig;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.Utils;
import com.hikvision.cloud.sdk.CloudOpenSDK;
import com.hikvision.cloud.sdk.core.CloudVideoPlayer;
import com.hikvision.cloud.sdk.core.OnCommonCallBack;
import com.hikvision.cloud.sdk.core.TalkCallInfo;
import com.hikvision.cloud.sdk.cst.HConfigCst;
import com.hikvision.cloud.sdk.cst.enums.CloudErrorCode;
import com.hikvision.cloud.sdk.util.LogUtils;
import com.videogo.errorlayer.ErrorInfo;
import com.videogo.exception.BaseException;
import com.videogo.openapi.EZConstants;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoCallInActivity extends BaseActivity {

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
    @BindView(R.id.video_call_in_surface)
    SurfaceView videoCallInSurface;
    @BindView(R.id.video_call_in_progress)
    ProgressBar videoCallInProgress;
    @BindView(R.id.video_call_in_rlv)
    RelativeLayout videoCallInRlv;
    @BindView(R.id.video_call_in_answer_btn)
    Button videoCallInAnswerBtn;
    @BindView(R.id.video_call_in_hangup_btn)
    Button videoCallInHangupBtn;
    @BindView(R.id.video_call_in_status_tv)
    TextView videoCallInStatusTv;
    
    private CloudVideoPlayer mRealPlayer;
    private CloudVideoPlayer mTalkPlayer;// 可视对讲中，预览和对讲用两个player，避免有回音、啸叫
    private String mDeviceSerial; // 设备序列号
    private int mChannelNo; // 通道号

    private boolean isEncry; //该设备是否加密

    private boolean isPlaying; //是否处于播放状态
    private boolean isOldPlaying; //用于界面不可见和可见切换时，记录是否对讲的状态

    private SurfaceHolder mSurfaceHolder;


    private static String mDeviceCallStatus;
    private TimerTaskThread mTimerTaskThread;
    //打开类型 1 预览打开  2 对讲呼入打开
    private int openType;


    private Handler mCallStatusHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (mDeviceCallStatus) {
                case HConfigCst.CallStatus.IDLE:
                    //预览
                    if (openType == 1){
                        //设备处于空闲状态
                        videoCallInAnswerBtn.setText("开启对讲");
                        videoCallInHangupBtn.setText("结束对讲");
                        videoCallInAnswerBtn.setClickable(true);
                        videoCallInHangupBtn.setClickable(true);
                        videoCallInAnswerBtn.setBackgroundColor(getResources().getColor(R.color.green));
                        videoCallInHangupBtn.setBackgroundColor(getResources().getColor(R.color.red));

                    }else{
                        if (isPlaying) {
                            showToast("对方已挂断");
                            stopPlay();

                        }
                        videoCallInAnswerBtn.setText("接听");
                        videoCallInHangupBtn.setText("挂断");
                        videoCallInAnswerBtn.setClickable(false);
                        videoCallInHangupBtn.setClickable(false);
                        videoCallInAnswerBtn.setBackgroundColor(getResources().getColor(R.color.gray));
                        videoCallInHangupBtn.setBackgroundColor(getResources().getColor(R.color.gray));
                    }
                    break;
                case HConfigCst.CallStatus.RING:
                    //设备响铃中（拨号中）
                    if (openType == 1){
                        openType = 2;
                    }
                    videoCallInAnswerBtn.setText("接听");
                    videoCallInHangupBtn.setText("拒接");
                    videoCallInAnswerBtn.setClickable(true);
                    videoCallInHangupBtn.setClickable(true);
                    videoCallInAnswerBtn.setBackgroundColor(getResources().getColor(R.color.green));
                    videoCallInHangupBtn.setBackgroundColor(getResources().getColor(R.color.red));

                    break;
                case HConfigCst.CallStatus.ONCALL:
                    //设备通话中
                    if (openType == 1){
                        openType = 2;
                    }
                    if (isPlaying) {
                        videoCallInAnswerBtn.setText("接听");
                        videoCallInHangupBtn.setText("挂断");
                        videoCallInAnswerBtn.setClickable(false);
                        videoCallInHangupBtn.setClickable(true);
                        videoCallInAnswerBtn.setBackgroundColor(getResources().getColor(R.color.gray));
                        videoCallInHangupBtn.setBackgroundColor(getResources().getColor(R.color.red));

                    } else {
                        videoCallInAnswerBtn.setText("接听");
                        videoCallInHangupBtn.setText("挂断");
                        videoCallInAnswerBtn.setClickable(false);
                        videoCallInHangupBtn.setClickable(false);
                        videoCallInAnswerBtn.setBackgroundColor(getResources().getColor(R.color.gray));
                        videoCallInHangupBtn.setBackgroundColor(getResources().getColor(R.color.gray));
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_video_call_in);
        ButterKnife.bind(this);

        toolbarTvTitle.setText("可视对讲");
        mDeviceSerial = HikConfig.DEVICE_SERIAL;
        mChannelNo = HikConfig.DEVICE_CHANNEL_NO;

        openType = getIntent().getIntExtra("openType", 1);
        if (openType == 1){

            videoCallInAnswerBtn.setText("开启对讲");
            videoCallInHangupBtn.setText("结束对讲");
            videoCallInAnswerBtn.setClickable(true);
            videoCallInHangupBtn.setClickable(true);

        }else{

            videoCallInAnswerBtn.setText("接听");
            videoCallInHangupBtn.setText("挂断");
            videoCallInAnswerBtn.setClickable(true);
            videoCallInHangupBtn.setClickable(true);
        }
        videoCallInStatusTv.setText("待机中...");

        videoCallInSurface.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mSurfaceHolder = holder;

                if (!Utils.isEmpty(HikConfig.VERIFY_CODE) && HikConfig.VERIFY_CODE.length() > 0)
                    isEncry = true;
                else
                    isEncry = false;
                startPlay(isEncry);

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                isOldPlaying = isPlaying;
                stopPlay();
            }
        });
    }


    /**
     * 这里采用轮询（2s）的方式获取设备状态
     * 也可采用其它的方式：服务端推送的方式获取到设备的状态，需开发者自己实现
     * 在这里简单实现下
     */
    private void startTimerTask() {
        mTimerTaskThread = new TimerTaskThread(mCallStatusHandler);
        mTimerTaskThread.start();
    }

    private void stopTimerTask() {
        if (null != mTimerTaskThread) {
            mTimerTaskThread.isAlive = false;
            mTimerTaskThread = null;
        }
    }



    private Handler msgHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {
                final String status = (String) msg.obj;
                printLog(status);
            }
            return false;
        }
    });

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private void printLog(String callStatus) {
        String content = "呼叫中";
        switch (callStatus) {
            case HConfigCst.CallStatus.IDLE:
                //预览
                content = "待机中...";
                break;
            case HConfigCst.CallStatus.RING:
                //设备响铃中（拨号中）
                content = "被呼叫中...";
                break;
            case HConfigCst.CallStatus.ONCALL:
                content = "通话中...";
                break;
            default:
                break;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(formatter.format(new Date()))
                .append(":")
                .append(content);
        videoCallInStatusTv.setText(builder.toString());
    }

    private class TimerTaskThread extends Thread {
        private Handler mHandler;
        public boolean isAlive = true;

        public TimerTaskThread(Handler mHandler) {
            this.mHandler = mHandler;
        }

        @Override
        public void run() {
            super.run();
            while (this.isAlive) {
                try {
                    mDeviceCallStatus = CloudOpenSDK.getInstance().getVideoIntercomCallStatus(mDeviceSerial);
                    Message message = new Message();
                    message.what = 0;
                    message.obj = mDeviceCallStatus;
                    msgHandler.sendMessage(message);
                    LogUtils.deBug("设备的状态： " + mDeviceCallStatus);
                    if (this.isAlive) {
                        mHandler.sendEmptyMessage(0);
                    }
                } catch (Exception e) {
                    if (e instanceof BaseException) {
                        ErrorInfo errorInfo = ((BaseException) e).getErrorInfo();
                        int errCode = errorInfo.errorCode; //错误码
                        String description = errorInfo.description; //错误提示信息
                        if (errCode == CloudErrorCode.SDK_TOKEN_EXPIRE_ERROR.getCode()) {
                            // 10125,token失效，请刷新,请调用CloudOpenSDK.getInstance().refreshToken()接口
                            // 或者
                            CloudOpenSDK.getInstance().refreshToken(new OnCommonCallBack() {
                                @Override
                                public void onSuccess() {
                                    // 调用其它的业务逻辑
                                }

                                @Override
                                public void onFailed(Exception e) {

                                }
                            });
                        }
                    }
                }
                // 上面是业务逻辑报错，不能与下面try···catch一起用
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 接听
     */
    private void answer() {
        TalkCallInfo talkCallInfo = new TalkCallInfo();
        talkCallInfo.setDeviceSerial(mDeviceSerial);//设备序列号
        talkCallInfo.setRoomNum("102");//房间号，例如102中1是楼层号，02是房间号
        talkCallInfo.setPeriodNumber("1");//期号
        talkCallInfo.setBuildingNumber("1");//楼号
        talkCallInfo.setUnitNumber("1");//单元号
        talkCallInfo.setFloorNumber("1");//层号
        talkCallInfo.setDevIndex("1");//设备序号,非必填
        talkCallInfo.setUnitType(HConfigCst.UnitType.WALL);//类型: outdoor门口机，wall围墙机
        CloudOpenSDK.getInstance().sendVideoIntercomCallSignal(talkCallInfo,
                HConfigCst.CallCommand.CALL_ANSWER,//接听信令
                new OnCommonCallBack() {
                    @Override
                    public void onSuccess() {
                        startPlay(isEncry);
                        startVoiceTalk();
                        showToast("接听成功");
                    }

                    @Override
                    public void onFailed(Exception e) {
                        showToast(e.getMessage());
                        //TODO
                    }
                });

    }

    private final String[] permissions = {Manifest.permission.RECORD_AUDIO};
    private void startVoiceTalk() {

        AndPermission.with(this).runtime()
                .permission(permissions)
                .rationale(new Rationale<List<String>>() {
                    @Override
                    public void showRationale(Context context, List<String> data, RequestExecutor executor) {
                        showToast("对讲开启失败，不再弹出询问框，请前往APP应用设置中打开此权限");
                        executor.execute();
                    }
                }).onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        mTalkPlayer.startVoiceTalk();
                    }
                }).onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        showToast("对讲开启失败，拒绝权限，等待下次询问哦");
                    }
                }).start();

    }

    /**
     * 挂断
     */
    private void hangUp() {
        TalkCallInfo talkCallInfo = new TalkCallInfo();
        talkCallInfo.setDeviceSerial(mDeviceSerial);//设备序列号
        talkCallInfo.setRoomNum("102");//房间号，例如102中1是楼层号，02是房间号
        talkCallInfo.setPeriodNumber("1");//期号
        talkCallInfo.setBuildingNumber("1");//楼号
        talkCallInfo.setUnitNumber("1");//单元号
        talkCallInfo.setFloorNumber("1");//层号
        talkCallInfo.setDevIndex("1");//设备序号,非必填
        talkCallInfo.setUnitType(HConfigCst.UnitType.WALL);//类型: outdoor门口机，wall围墙机
        CloudOpenSDK.getInstance().sendVideoIntercomCallSignal(talkCallInfo,
                HConfigCst.CallCommand.CALL_HANGUP,//挂断信令
                new OnCommonCallBack() {
                    @Override
                    public void onSuccess() {
                        stopPlay();
                        showToast("挂断成功");
                    }

                    @Override
                    public void onFailed(Exception e) {
                        showToast(e.getMessage());
                        //TODO
                    }
                });
    }

    /**
     * 拒接
     */
    private void reject() {
        TalkCallInfo talkCallInfo = new TalkCallInfo();
        talkCallInfo.setDeviceSerial(mDeviceSerial);//设备序列号
        talkCallInfo.setRoomNum("102");//房间号，例如102中1是楼层号，02是房间号
        talkCallInfo.setPeriodNumber("1");//期号
        talkCallInfo.setBuildingNumber("1");//楼号
        talkCallInfo.setUnitNumber("1");//单元号
        talkCallInfo.setFloorNumber("1");//层号
        talkCallInfo.setDevIndex("1");//设备序号,非必填
        talkCallInfo.setUnitType(HConfigCst.UnitType.WALL);//类型: outdoor门口机，wall围墙机
        CloudOpenSDK.getInstance().sendVideoIntercomCallSignal(talkCallInfo,
                HConfigCst.CallCommand.CALL_REJECT, //拒绝信令
                new OnCommonCallBack() {
                    @Override
                    public void onSuccess() {
                        showToast("拒接成功");
                        isPlaying = false;
                    }

                    @Override
                    public void onFailed(Exception e) {
                        showToast(e.getMessage());
                        //TODO
                    }
                });
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        startTimerTask();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopTimerTask();
    }

    /**
     * 开始预览
     *
     * @param isEncry 是否加密，若加密则配置设备的验证码
     */
    private void startPlay(boolean isEncry) {
        mRealPlayer = CloudOpenSDK.getInstance().createPlayer(mDeviceSerial, mChannelNo);
        mRealPlayer.setSurfaceHolder(mSurfaceHolder);
        mRealPlayer.setOnRealPlayListener(onRealPlayListener);
        mTalkPlayer = CloudOpenSDK.getInstance().createPlayer(mDeviceSerial, mChannelNo);
        mTalkPlayer.setOnVoicTalkListener(onVoiceTalkListener);
        videoCallInProgress.setVisibility(View.VISIBLE);
        if (isEncry) {
            mRealPlayer.setPlayVerifyCode(HikConfig.VERIFY_CODE);
            mTalkPlayer.setPlayVerifyCode(HikConfig.VERIFY_CODE);
        }
        mRealPlayer.closeSound();
        mTalkPlayer.closeSound();
        mRealPlayer.startRealPlay();

    }

    /**
     * 对讲监听回调
     */
    private CloudVideoPlayer.OnVoiceTalkListener onVoiceTalkListener = new CloudVideoPlayer.OnVoiceTalkListener() {
        @Override
        public void onStartVoiceTalkSuccess() {
            //开启对讲成功

        }

        @Override
        public void onStopVoiceTalkSuccess() {
            // 停止对讲成功
        }

        @Override
        public void onVoiceTalkFail(int errorCode, String moduleCode, String description, String sulution) {
            //开启对讲失败或停止对讲失败，这里需要开发者自己去判断是开启操作还是停止的操作
            //停止对讲失败后，不影响下一次的start使用
        }
    };

    /**
     * 预览成功、失败、分辨率变化监听回调
     */
    private CloudVideoPlayer.OnRealPlayListener onRealPlayListener = new CloudVideoPlayer.OnRealPlayListener() {
        @Override
        public void onVideoSizeChanged(int videoWidth, int videoHeight) {

        }

        @Override
        public void onRealPlaySuccess() {
            isPlaying = true;
            videoCallInProgress.setVisibility(View.GONE);
            // 获取录音权限
            if (openType == 2)
                startVoiceTalk();
        }

        @Override
        public void onStopRealPlaySuccess() {

        }

        /**
         * 预览失败回调,得到失败信息
         *
         * @param errorCode   播放失败错误码
         * @param moduleCode  播放失败模块错误码
         * @param description 播放失败描述
         * @param sulution    播放失败解决方方案
         */
        @Override
        public void onRealPlayFailed(int errorCode, String moduleCode, String description, String sulution) {
            showToast(String.format("errorCode：%d, %s", errorCode, description));
            videoCallInProgress.setVisibility(View.GONE);

            if (errorCode == 400035 || errorCode == 400036) {
                //
                //回调时查看errorCode，如果为400035（需要输入验证码）和400036（验证码错误），
                // 则需要开发者自己处理让用户重新输入验证密码，并调用setPlayVerifyCode设置密码，
                // 然后重新启动播放
                // TODO
                showToast("设备验证码错误");
                return;
            }
        }
    };


    private void stopPlay() {
        if (null != mRealPlayer) {
            mRealPlayer.stopRealPlay();//停止预览
            mTalkPlayer.stopVoiceTalk(); //停止对讲
            isPlaying = false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mRealPlayer) {
            mRealPlayer.release();
        }
        if (null != mTalkPlayer) {
            mTalkPlayer.release();
        }
        if (null != mCallStatusHandler) {
            mCallStatusHandler.removeCallbacksAndMessages(null);
        }
    }
    
    @OnClick({R.id.toolbar_btn_back, R.id.video_call_in_answer_btn, R.id.video_call_in_hangup_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.video_call_in_answer_btn:
                if (openType == 1){
                    if (isPlaying)
                        startVoiceTalk();
                    else{
                        if (!Utils.isEmpty(HikConfig.VERIFY_CODE))
                            startPlay(true);
                        else
                            startPlay(false);
                        startVoiceTalk();
                    }
                }else{
                    if (HConfigCst.CallStatus.RING.equals(mDeviceCallStatus)) {
                        answer();
                    }
                }
                break;
            case R.id.video_call_in_hangup_btn:
                if (openType == 1){
                    if (isPlaying)
                        mTalkPlayer.stopVoiceTalk();
                }else{
                    if (HConfigCst.CallStatus.RING.equals(mDeviceCallStatus)) {
                        reject();
                    }else if (HConfigCst.CallStatus.ONCALL.equals(mDeviceCallStatus)){
                        hangUp();
                    }
                }

                break;
        }
    }
}
