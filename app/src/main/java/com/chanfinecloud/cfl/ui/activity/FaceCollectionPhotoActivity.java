package com.chanfinecloud.cfl.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.entity.eventbus.FaceCollectionEventBusData;
import com.chanfinecloud.cfl.entity.smart.FileEntity;
import com.chanfinecloud.cfl.entity.smart.HouseholdRoomEntity;
import com.chanfinecloud.cfl.entity.smart.UserInfoEntity;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.ParamType;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.util.UserInfoUtil;
import com.chanfinecloud.cfl.weidgt.CameraPreview;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.BASIC;
import static com.chanfinecloud.cfl.config.Config.FILE;
import static com.chanfinecloud.cfl.config.Config.IOT;

public class FaceCollectionPhotoActivity extends BaseActivity {

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
    @BindView(R.id.iv_face_collection_photo)
    ImageView ivFaceCollectionPhoto;
    @BindView(R.id.btn_take_photo)
    ImageButton btnTakePhoto;
    @BindView(R.id.btn_take_photo_cancel)
    ImageButton btnTakePhotoCancel;
    @BindView(R.id.btn_take_photo_sure)
    ImageButton btnTakePhotoSure;

    private static final String PATH_IMAGES = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "chainfl";
    @BindView(R.id.cp_face_collection_photo)
    CameraPreview cpFaceCollectionPhoto;

    private File photoFile;

    private String id, name;

    private boolean update;
    private List<HouseholdRoomEntity> roomList;
    private FileEntity file;
    private int flag;

    Map<String, String> roomMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_face_collection_photo);
        ButterKnife.bind(this);

        Bundle bundle=getIntent().getExtras();
        id=bundle.getString("id");
        name=bundle.getString("name");
        update=bundle.getBoolean("update");
        roomList = FileManagement.getUserInfo().getRoomList();
        if(Build.VERSION.SDK_INT>=23){
            int hasPermission = checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(hasPermission!= PackageManager.PERMISSION_GRANTED){
                String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this,mPermissionList,123);
            }
        }


    }

    @OnClick({R.id.toolbar_btn_back, R.id.btn_take_photo, R.id.btn_take_photo_cancel, R.id.btn_take_photo_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.btn_take_photo:
                cpFaceCollectionPhoto.takePicture(mShutterCallback, rawPictureCallback, jpegPictureCallback);
                break;
            case R.id.btn_take_photo_cancel:
                btnTakePhoto.setVisibility(VISIBLE);
                ivFaceCollectionPhoto.setVisibility(VISIBLE);
                btnTakePhotoCancel.setVisibility(GONE);
                btnTakePhotoSure.setVisibility(GONE);
                cpFaceCollectionPhoto.startPreview();
                break;
            case R.id.btn_take_photo_sure:
                uploadFace();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnTakePhoto.setVisibility(VISIBLE);
        ivFaceCollectionPhoto.setVisibility(VISIBLE);
        btnTakePhotoCancel.setVisibility(GONE);
        btnTakePhotoSure.setVisibility(GONE);
    }

    private Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {

        }
    };
    private Camera.PictureCallback rawPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

        }
    };
    private Camera.PictureCallback jpegPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            camera.stopPreview();
            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
            bmp=rotateBitmapByDegree(bmp,270);
            String fileName = "test_" + System.currentTimeMillis() + ".jpg";
            photoFile=saveBitmapFile(bmp, PATH_IMAGES + File.separator + fileName);
            btnTakePhoto.setVisibility(GONE);
            ivFaceCollectionPhoto.setVisibility(GONE);
            btnTakePhotoCancel.setVisibility(VISIBLE);
            btnTakePhotoSure.setVisibility(VISIBLE);
        }
    };

    /**
     * 图片旋转
     * @param bm
     * @param degree
     * @return
     */
    private Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                    bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    /**
     * 保存照片返回文件
     * @param bitmap
     * @param filePath
     * @return
     */
    private File saveBitmapFile(Bitmap bitmap, String filePath) {
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs(); // 创建文件夹
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024>200) { //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        try {
            BufferedOutputStream bos = new BufferedOutputStream( new FileOutputStream(file));
            baos.writeTo(bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    protected boolean isClickable() {
        return super.isClickable();
    }

    /**
     * 下发人脸
     */
    private void faceAccess(){
        flag=0;
        if (roomMap.size() > 0 ){
            roomMap.clear();
        }
        for (int i = 0; i < roomList.size(); i++) {
            String phaseId = roomList.get(i).getPhaseId();
            String unitIds = roomList.get(i).getUnitId();
            if (roomMap.containsKey(phaseId)){
                String curUnitIds = roomMap.get(phaseId);
                String[] idsList = curUnitIds.split(",");
                int hasCode = 0;
                for (int j = 0; j < idsList.length; j++ ){
                    if (idsList[j].equals(unitIds)){
                        hasCode = 1;
                    }
                }
                if (hasCode == 0){
                    roomMap.put(phaseId,roomMap.get(phaseId) + "," + unitIds);
                }
            }else{
                roomMap.put(phaseId, unitIds);
            }
        }
        for(Map.Entry<String, String> entry : roomMap.entrySet()){

            Map<String,Object> requestMap=new HashMap<>();
            requestMap.put("id",id);
            requestMap.put("name",name);
            requestMap.put("phaseId",entry.getKey());
            requestMap.put("unitIds",entry.getValue());
            RequestParam requestParam = new RequestParam(BASE_URL+IOT+"community/api/access/v1/face/"+file.getId(), HttpMethod.Put);

//            if(update){
//                requestParam.setMethod(HttpMethod.Put);
//                requestParam.setUrl(BASE_URL+IOT+"community/api/access/v1/face/"+file.getId());
//                Log.e( "faceAccess: ", "更新");
//            }else{
//                Log.e( "faceAccess: ", "不更新");
//                requestParam.setMethod(HttpMethod.Post);
//                requestParam.setUrl(BASE_URL+IOT+"community/api/access/v1/face/"+file.getId());
//            }

            requestParam.setMethod(HttpMethod.Post);
            requestParam.setUrl(BASE_URL+IOT+"community/api/access/v1/face/"+file.getId());

            requestParam.setParamType(ParamType.Json);
            requestParam.setRequestMap(requestMap);
            Log.d( "faceAccess: ", requestMap.toString() );
            requestParam.setCallback(faceAccessBack);

            sendRequest(requestParam,false);
        }
    }

    /**
     * 下发人脸接口回调处理
     */
    private MyCallBack faceAccessBack=new MyCallBack<String>(){
        @Override
        public void onSuccess(String result) {
            super.onSuccess(result);
            LogUtils.d(result);
            flag++;
            BaseEntity baseEntity= JsonParse.parse(result);
            if(!baseEntity.isSuccess()){
                showToast(baseEntity.getMessage());
                stopProgressDialog();
            }
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            super.onError(ex, isOnCallback);
            flag++;
            showToast(ex.getMessage());
            stopProgressDialog();
        }

        @Override
        public void onFinished() {
            super.onFinished();
            if(flag==roomMap.size()){
                EventBusMessage<FaceCollectionEventBusData> eventBusMessage=new EventBusMessage<>("faceCollection");
                String createTime = file.getCreateTime();
                createTime=createTime.replace("T"," ");
                createTime=createTime.substring(0,19);
                eventBusMessage.setData(new FaceCollectionEventBusData(createTime,file.getDomain()+file.getUrl()));
                EventBus.getDefault().post(eventBusMessage);
                updateHouseholdFace(file.getId());
            }
        }
    };

    /**
     * 上传照片
     */
    private void uploadFace(){

        Map<String,Object> requestMap=new HashMap<>();

        requestMap.put("UploadFile",photoFile);
        RequestParam requestParam = new RequestParam(BASE_URL+FILE+"files-anon", HttpMethod.Upload);
        requestParam.setRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity<FileEntity> baseEntity= JsonParse.parse(result,FileEntity.class);
                if(baseEntity.isSuccess()){
                    file=baseEntity.getResult();
                    faceAccess();
                }else{
                    showToast(baseEntity.getMessage());
                    stopProgressDialog();
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

    /**
     * 更新用户人脸信息
     * @param fileId 人脸ID
     */
    private void updateHouseholdFace(String fileId){
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        map.put("faceId",fileId);
        RequestParam requestParam = new RequestParam(BASE_URL+BASIC+"basic/householdInfo/specificField", HttpMethod.Put);
        requestParam.setRequestMap(map);
        requestParam.setParamType(ParamType.Json);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity baseEntity= JsonParse.parse(result);
                if(baseEntity.isSuccess()){
                    UserInfoUtil.refreshUserInfoByServerCache(new UserInfoUtil.OnRefreshListener() {
                        @Override
                        public void onSuccess() {
                            finish();
                        }

                        @Override
                        public void onFail(String msg) {
                            showToast(msg);
                        }
                    });

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
        sendRequest(requestParam, false);


    }

}
