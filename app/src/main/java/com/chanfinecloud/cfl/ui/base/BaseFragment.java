package com.chanfinecloud.cfl.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.chanfinecloud.cfl.CFLApplication;
import com.chanfinecloud.cfl.entity.core.Transition;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.util.AtyTransitionUtil;
import com.chanfinecloud.cfl.weidgt.ProgressDialogView;
import com.squareup.leakcanary.RefWatcher;

import org.xutils.x;

import androidx.fragment.app.Fragment;

import static com.chanfinecloud.cfl.CFLApplication.activityTrans;
import static com.chanfinecloud.cfl.ui.base.BaseHandler.HTTP_CANCEL;
import static com.chanfinecloud.cfl.ui.base.BaseHandler.HTTP_REQUEST;


/**
 * Created by Loong on 2020/2/12.
 * Version: 1.0
 * Describe: Fragment基础类
 */
public class BaseFragment extends Fragment {

    private Context context;
    private boolean injected = false;
    private ProgressDialogView progressDialogView = null;
    protected static BaseHandler handler;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        handler=new BaseHandler(getActivity());//初始化BaseHandler
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        return x.view().inject(this, inflater, container);//注入View
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());//注入View
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消菊花
        if (progressDialogView != null) {
            progressDialogView.stopLoad();
            progressDialogView = null;
        }
        handler.sendEmptyMessage(HTTP_CANCEL);//取消http请求
        RefWatcher refWatcher = CFLApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);//LeakCanary监听
    }

    /**
     * 发送一个请求
     * @param requestParam 请求体
     * @param showProgressDialog 是否转菊花
     */
    protected void sendRequest(RequestParam requestParam, boolean showProgressDialog){
        if(((BaseActivity)getActivity()).isNetConnect){
            if(showProgressDialog){
                startProgressDialog();
            }
            Message message=new Message();
            message.what=HTTP_REQUEST;
            Bundle bundle=new Bundle();
            bundle.putSerializable("request",requestParam);
            message.setData(bundle);
            handler.sendMessage(message);
        }else{
            showToast("没有网络，请前往网络设置检查");
        }
    }
    /**
     * 启动新的Activity 默认Trans-LeftIn
     * @param clazz Class
     */
    public void startActivity(Class clazz){
        Transition transition= Transition.RightIn;
        activityTrans.put(clazz,transition);
        startActivity(new Intent(getActivity(), clazz));
        executeTransition(transition);
    }

    /**
     * 启动新的Activity
     * @param clazz Class
     * @param bundle Bundle
     */
    public void startActivity(Class clazz,Bundle bundle){
        Transition transition=Transition.RightIn;
        activityTrans.put(clazz,transition);
        Intent intent = new Intent();
        intent.setClass(getActivity(), clazz);
        intent.putExtras(bundle);
        startActivity(intent);
        executeTransition(transition);
    }

    /**
     * 启动新的Activity
     * @param clazz Class
     * @param transition 转场动画
     */
    public void startActivity(Class clazz, Transition transition){
        activityTrans.put(clazz,transition);
        startActivity(new Intent(getActivity(), clazz));
        executeTransition(transition);
    }

    /**
     * 启动新的Activity
     * @param clazz Class
     * @param bundle Bundle
     * @param transition 转场动画
     */
    public void startActivity(Class clazz, Bundle bundle,Transition transition){
        activityTrans.put(clazz,transition);
        Intent intent = new Intent();
        intent.setClass(getActivity(), clazz);
        intent.putExtras(bundle);
        startActivity(intent);
        executeTransition(transition);
    }

    /**
     * 启动新的Activity
     * @param clazz Class
     * @param bundle Bundle
     * @param requestCode 返回码
     * @param transition 转场动画
     */
    public void startActivityForResult( Class clazz, Bundle bundle, int requestCode,Transition transition) {
        activityTrans.put(clazz,transition);
        Intent intent = new Intent();
        intent.setClass(getActivity(), clazz);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
        executeTransition(transition);
    }

    /**
     * 执行转场动画
     * @param transition Transition转场动画
     */
    private void executeTransition(Transition transition){
        switch (transition){
            case TopIn:
                AtyTransitionUtil.enterFromTop(getActivity());
                break;
            case TopOut:
                AtyTransitionUtil.exitToTop(getActivity());
                break;
            case LeftIn:
                AtyTransitionUtil.enterFromLeft(getActivity());
                break;
            case LeftOut:
                AtyTransitionUtil.exitToLeft(getActivity());
                break;
            case BottomIn:
                AtyTransitionUtil.enterFromBottom(getActivity());
                break;
            case BottomOut:
                AtyTransitionUtil.exitToBottom(getActivity());
                break;
            case RightIn:
                AtyTransitionUtil.enterFromRight(getActivity());
                break;
            case RightOut:
                AtyTransitionUtil.exitToRight(getActivity());
                break;
        }
    }

    /**
     * 启动加载框
     */
    protected void startProgressDialog() {
        if (progressDialogView == null) {
            progressDialogView = new ProgressDialogView();
        }
        progressDialogView.startLoad(context, "",false);
    }
    /**
     * 启动加载框
     * @param msg 提示文字
     */
    protected void startProgressDialog(String msg) {
        if (progressDialogView == null) {
            progressDialogView = new ProgressDialogView();
        }
        progressDialogView.startLoad(context, msg,false);
    }
    /**
     * 启动加载框
     * @param cancelable 是否可关闭
     */
    protected void startProgressDialog(boolean cancelable) {
        if (progressDialogView == null) {
            progressDialogView = new ProgressDialogView();
        }
        progressDialogView.startLoad(context, "",cancelable);
    }
    /**
     * 启动加载框
     * @param msg 提示文字
     * @param cancelable 是否可关闭
     */
    protected void startProgressDialog(String msg,boolean cancelable) {
        if (progressDialogView == null) {
            progressDialogView = new ProgressDialogView();
        }
        progressDialogView.startLoad(context, msg,cancelable);
    }
    /**
     * 关闭加载框
     */
    protected void stopProgressDialog() {
        if (progressDialogView != null) {
            progressDialogView.stopLoad();
        }
    }
    /**
     * 显示Toast
     * @param content Toast文字
     */
    protected void showToast(String content){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }
}