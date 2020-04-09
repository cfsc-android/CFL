package com.chanfinecloud.cfl.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.chanfinecloud.cfl.CFLApplication;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.DataCleanManager;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.FileSizeUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

import static android.view.View.VISIBLE;
import static com.chanfinecloud.cfl.config.Config.LOCAL_PATH;

/**
 * Created by Shuaige on 2020/3/28.
 * Version: 1.0
 * Describe:  清理缓存Activity
 */
public class SettingDetailActivity extends BaseActivity {
    @BindView(R.id.toolbar_tv_title)
    TextView toolbarTvTitle;
    @BindView(R.id.ll_setting_detail_notice)
    LinearLayout llSettingDetailNotice;
    @BindView(R.id.ll_setting_detail_cache)
    LinearLayout llsettingDetailCache;
    @BindView(R.id.s_setting_detail_notice)
    Switch SSettingDetailNotice;

    @Override
    protected void initData() {
        setContentView(R.layout.activity_setting_detail);
        ButterKnife.bind(this);
        String type=getIntent().getExtras().getString("type");
        if("0".equals(type)){
            llSettingDetailNotice.setVisibility(VISIBLE);
            if(!FileManagement.getNotificationFlag()){
                new AlertDialog.Builder(SettingDetailActivity.this)
                        .setTitle("通知权限")
                        .setMessage("应用通知权限关闭，去开启")
                        .setCancelable(false)
                        .setNegativeButton("去开启",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                JPushInterface.goToAppNotificationSettings(CFLApplication.getAppContext());
                            }
                        }).
                        setNeutralButton("下次再说", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        }else if("1".equals(type)){
            llsettingDetailCache.setVisibility(VISIBLE);
        }
        toolbarTvTitle.setText(getIntent().getExtras().getString("title"));
        //暂时未接入极光推送，所以暂时未设置=================================
        if(FileManagement.getPushFlag()){
            SSettingDetailNotice.setChecked(true);
        }else{
            SSettingDetailNotice.setChecked(false);
        }
        SSettingDetailNotice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    FileManagement.setPushFlag(true);
                    JPushInterface.resumePush(getApplicationContext());
                }else{
                    FileManagement.setPushFlag(false);
                    JPushInterface.stopPush(getApplicationContext());
                }
            }
        });

    }

    @OnClick({R.id.toolbar_btn_back,R.id.ll_setting_detail_cache_cache,R.id.ll_setting_detail_cache_file})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.ll_setting_detail_cache_cache:
                String fileSize="0KB";
                try {
                    fileSize= DataCleanManager.getCacheSize(getApplicationContext().getExternalCacheDir());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new AlertDialog.Builder(SettingDetailActivity.this)
                        .setTitle("清理缓存")
                        .setMessage("发现可清理缓存"+fileSize)
                        .setNegativeButton("确认清理", (dialog, which) -> DataCleanManager.cleanExternalCache(getApplicationContext()))
                        .setNeutralButton("暂不清理", (dialog, which) -> dialog.dismiss()).show();
                break;
            case R.id.ll_setting_detail_cache_file:
                new AlertDialog.Builder(SettingDetailActivity.this)
                        .setTitle("清理文件")
                        .setMessage("发现可清理文件"+ FileSizeUtil.getAutoFileOrFilesSize(LOCAL_PATH))
                        .setNegativeButton("确认清理", (dialog, which) -> {
                            deleteFile(new File(LOCAL_PATH));
                            showToast("完成文件清理");
                        }).
                        setNeutralButton("暂不清理", (dialog, which) -> dialog.dismiss()).show();
                break;
        }
    }

    /**
     * 删除文件
     * @param file 文件
     */
    private void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                deleteFile(f);
            }
            //file.delete();//如要保留文件夹，只删除文件，请注释这行
        } else if (file.exists()) {
            file.delete();
        }
    }
}
