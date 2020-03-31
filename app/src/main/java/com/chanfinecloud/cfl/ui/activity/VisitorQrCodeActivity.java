package com.chanfinecloud.cfl.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VisitorQrCodeActivity extends BaseActivity {

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
    @BindView(R.id.iv_visitor_qr_code)
    ImageView ivVisitorQrCode;
    @BindView(R.id.tv_visitor_valid_time)
    TextView tvVisitorValidTime;
    @BindView(R.id.tv_visitor_valid_num)
    TextView tvVisitorValidNum;
    @BindView(R.id.tv_visitor_name)
    TextView tvVisitorName;
    @BindView(R.id.ll_visitor_qr_code)
    LinearLayout llVisitorQrCode;
    @BindView(R.id.btn_visitor_qr_code_share)
    Button btnVisitorQrCodeShare;
    @BindView(R.id.btn_visitor_qr_code_save)
    Button btnVisitorQrCodeSave;

    private String name;
    private String start;
    private String end;
    private int num;
    private String qrCodeUrl;
    private boolean savePermiss=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_visitor_qr_code);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        qrCodeUrl=bundle.getString("qrCodeUrl");
        name=bundle.getString("name");
        start=bundle.getString("start");
        end=bundle.getString("end");
        num=bundle.getInt("num");
        toolbarTvTitle.setText("邀请码");
        tvVisitorName.setText(name);
        tvVisitorValidNum.setText(num+"");
        tvVisitorValidTime.setText(end);
        Glide.with(this).load(qrCodeUrl).into(ivVisitorQrCode);
//
//
//        Bitmap bm_logo = BitmapFactory.decodeResource(getResources(), R.mipmap.ll_cfl);
//        initQRCode(qrCodeUrl,1000,1000,bm_logo);
        getPromiss();

    }


    private void getPromiss(){
        if(Build.VERSION.SDK_INT>=23){
            int hasPermission = checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(hasPermission!= PackageManager.PERMISSION_GRANTED){
                savePermiss=false;
                String[] mPermissionList = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this,mPermissionList,123);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        if (requestCode == 123) {
            savePermiss=true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.toolbar_btn_back, R.id.btn_visitor_qr_code_share, R.id.btn_visitor_qr_code_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.btn_visitor_qr_code_share:
                share();
                break;
            case R.id.btn_visitor_qr_code_save:
                if(savePermiss){
                    try {
                        saveFile(getGeneratBitmap(),"qr_share"+new Date().getTime()+".jpg","/img");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    showToast("没有读写存储权限，请设置权限");
                }
                break;
        }
    }

    private static final String SAVE_PIC_PATH = Environment.getExternalStorageDirectory()
            + File.separator + Environment.DIRECTORY_DCIM
            +File.separator+"Camera"+File.separator;

    /**
     * 保存开锁二维码到本地
     * 保存的确切位置，根据自己的具体需要来修改
     * @param bm
     * @param fileName
     * @param path
     * @throws IOException
     */
    public void saveFile(Bitmap bm, String fileName, String path) throws IOException {
        String subForder = SAVE_PIC_PATH + path;
        File foder = new File(subForder);
        if (!foder.exists()) {
            foder.mkdirs();
        }
        File myCaptureFile = new File(subForder, fileName);
        if (!myCaptureFile.exists()) {
            myCaptureFile.createNewFile();
        }
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);  // 这是刷新单个文件
        Uri uri = Uri.fromFile(myCaptureFile);
        intent.setData(uri);
        sendBroadcast(intent);
        showToast("保存成功");
    }

    /**
     * 分享
     */
    private void share(){
        UMImage image = new UMImage(VisitorQrCodeActivity.this, getGeneratBitmap());
        new ShareAction(this).withText("开门二维码")
                .withMedia(image)
                .setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                .setCallback(shareListener).open();
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            showToast("成功了");
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Log.d("错误日志",platform.toString());
            Log.d("错误日志",t.toString());
            showToast("失败了");
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            showShortToast("取消了");
        }
    };

    private Bitmap getGeneratBitmap(){
        View dView = getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(dView.getDrawingCache());
        return bitmap;
    }
}
