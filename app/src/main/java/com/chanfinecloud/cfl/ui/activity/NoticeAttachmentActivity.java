package com.chanfinecloud.cfl.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.adapter.AttachmentListAdapter;
import com.chanfinecloud.cfl.config.Config;
import com.chanfinecloud.cfl.entity.ResourceEntity;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.MyProgressCallBack;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.FilePathUtil;
import com.chanfinecloud.cfl.view.RecyclerViewDivider;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chanfinecloud.cfl.config.Config.ARTICLE;
import static com.chanfinecloud.cfl.config.Config.SD_APP_DIR_NAME;
import static com.chanfinecloud.cfl.config.Config.File_DIR_NAME;

/**
 * 此类描述的是:公告附件列表activity
 *
 * @author Shuaige
 * create at 2020/3/30
 * */
public class NoticeAttachmentActivity extends BaseActivity {

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
    @BindView(R.id.notice_attachment_rlv)
    RecyclerView noticeAttachmentRlv;
    private AttachmentListAdapter adapter;
    private List<ResourceEntity> data;

    @Override
    protected void initData() {
        setContentView(R.layout.activity_notice_attachment);
        ButterKnife.bind(this);
        data = (List<ResourceEntity>) getIntent().getExtras().getSerializable("resourceList");
        adapter = new AttachmentListAdapter(data);
        noticeAttachmentRlv.setLayoutManager(new LinearLayoutManager(this));
        noticeAttachmentRlv.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
        noticeAttachmentRlv.setAdapter(adapter);
        noticeAttachmentRlv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                downloadAttachment(data.get(position).getUrl(), data.get(position).getName(), data.get(position).getContentType());
            }
        });
    }

    private void downloadAttachment(String url, String path, final String type) {
        String _Path = FilePathUtil.createPathIfNotExist("/" + SD_APP_DIR_NAME + "/" + File_DIR_NAME);
        final File file = new File(_Path + "/" + path);
        if (file.exists()) {
            openFile(file, type);
        } else {
            RequestParam requestParam=new RequestParam(url, HttpMethod.Download);
            requestParam.setFilepath(_Path + "/" + path);
            requestParam.setProgressCallback(new MyProgressCallBack<File>() {
                @Override
                public void onSuccess(File result) {
                    super.onSuccess(result);
                    openFile(result, type);
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
            sendRequest(requestParam, true);
        }

    }


    private void openFile(File file, String type) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//设置标记
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setAction(Intent.ACTION_VIEW);//动作，查看
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String authority = Config.PROVIDER_AUTHORITY;
            uri = FileProvider.getUriForFile(this, authority, file);
        } else {
            uri = Uri.fromFile(file);
        }
        intent.setDataAndType(uri, type);//设置类型
        startActivity(intent);
    }

    @OnClick({R.id.toolbar_btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
        }
    }

}
