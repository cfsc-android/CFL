package com.chanfinecloud.cfl.ui.fragment.mainfrg;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.ImageBanner;
import com.chanfinecloud.cfl.entity.LoginUserEntity;
import com.chanfinecloud.cfl.entity.NoticeEntity;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.entity.smart.NoticeListEntity;
import com.chanfinecloud.cfl.entity.smart.NoticeReceiverType;
import com.chanfinecloud.cfl.entity.smart.NoticeType;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.activity.homehead.CarLock;
import com.chanfinecloud.cfl.ui.activity.homehead.UnLock;
import com.chanfinecloud.cfl.ui.activity.homehead.VisitorActivity;
import com.chanfinecloud.cfl.ui.base.BaseFragment;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.weidgt.ADTextView;
import com.chanfinecloud.cfl.weidgt.OnAdConetentClickListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoaderInterface;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.chanfinecloud.cfl.config.Config.ARTICLE;
import static com.chanfinecloud.cfl.config.Config.BASE_URL;

/**
 * Damien
 * 首页
 */
public class HomeFragment extends BaseFragment {


    @BindView(R.id.iv_project_icon)
    ImageView ivProjectIcon;
    @BindView(R.id.tv_project_logo_name)
    TextView tvProjectLogoName;
    @BindView(R.id.tv_to_menjin)
    TextView tvToMenjin;
    @BindView(R.id.tv_to_baoxiu)
    TextView tvToBaoxiu;
    @BindView(R.id.tv_to_tousu)
    TextView tvToTousu;
    @BindView(R.id.tv_to_visitor)
    TextView tvToVisitor;
    @BindView(R.id.tv_to_video_call)
    TextView tvToVideoCall;
    @BindView(R.id.iv_to_jiesuo)
    TextView ivToJiesuo;
    @BindView(R.id.ad_textview)
    ADTextView adTextview;
    @BindView(R.id.ll_new_home_notice_detail)
    LinearLayout llNewHomeNoticeDetail;
    @BindView(R.id.banner_home_ad)
    Banner bannerHomeAd;
    @BindView(R.id.tv_home_page_ll)
    TextView tvHomePageLl;
    @BindView(R.id.tv_home_page_parking_payment)
    TextView tvHomePageParkingPayment;
    @BindView(R.id.tv_service)
    TextView tvService;
    @BindView(R.id.tv_project_progress)
    TextView tvProjectProgress;
    @BindView(R.id.tv_property_right)
    TextView tvPropertyRight;
    @BindView(R.id.tv_to_more)
    TextView tvToMore;
    @BindView(R.id.tv_to_tongzhi)
    TextView tvToTongzhi;
    @BindView(R.id.tv_to_gonggao)
    TextView tvToGonggao;
    @BindView(R.id.tv_to_shjf)
    TextView tvToShjf;
    @BindView(R.id.tv_complaint)
    TextView tvComplaint;
    @BindView(R.id.tv_shoping)
    TextView tvShoping;
    @BindView(R.id.tv_repair)
    TextView tvRepair;
    @BindView(R.id.tv_to_zhoubian)
    TextView tvToZhoubian;
    private Unbinder unbinder;
    private LoginUserEntity loginUserEntity;
    private boolean bind;
    private ArrayList<NoticeEntity> hotTopicList=new ArrayList<>();
    private List<NoticeEntity> bannerList=new ArrayList<>();
    private List<ImageBanner> imageUrls=new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventBusMessage message) {
        if ("bind".equals(message.getMessage())) {
            Log.e("bind", "Home_bind");
            bind = true;
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        setContentView(view);

        loginUserEntity = FileManagement.getLoginUserEntity();
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void initData() {

        bannerHomeAd.setOffscreenPageLimit(5);
        bannerHomeAd.setImageLoader(new ImageLoaderInterface(){
            @Override
            public void displayImage(Context context, Object path, View imageView) {
                Glide.with(context).load(((ImageBanner)path).getImageUrl()).into((ImageView) imageView);
//                XUtilsImageUtils.display((ImageView) imageView,((ImageBanner)path).getImageUrl());
            }

            @Override
            public View createImageView(Context context) {
                return null;
            }
        });
        bannerHomeAd.setOnBannerListener(new OnBannerListener(){
            @Override
            public void OnBannerClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("title","新闻动态");
                bundle.putString("noticeId", bannerList.get(position).getId());
                // TODO: 2020/3/27   z新闻详情页
                //startActivity(NoticeDetailActivity.class, bundle);
            }
        });
        bannerHomeAd.start();

    }

    @Override
    protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();
        getHotTips();
        getWheelPlanting();

    }

    private void getHotTips(){
        RequestParam requestParam = new RequestParam(BASE_URL +ARTICLE + "smart/content/pages", HttpMethod.Get);
        Map<String,String> map=new HashMap<>();
        map.put("projectId","ec93bb06f5be4c1f19522ca78180e2i9");
        map.put("receiver", NoticeReceiverType.全部.getType()+","+ NoticeReceiverType.业主.getType());
        map.put("announcementTypeId", NoticeType.热点关注.getType());
        map.put("auditStatus","1");
        map.put("pageNo","1");
        map.put("pageSize","10");
        requestParam.setRequestMap(map);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtil.d(result);
                BaseEntity<NoticeListEntity> baseEntity= JsonParse.parse(result,NoticeListEntity.class);
                if(baseEntity.isSuccess()){
                    hotTopicList.clear();
                    hotTopicList.addAll(baseEntity.getResult().getData());
                    if(hotTopicList.size()>0){
                        llNewHomeNoticeDetail.setVisibility(View.VISIBLE);
                        initADTextView();
                    }else{
                        llNewHomeNoticeDetail.setVisibility(View.GONE);
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

        });

        sendRequest(requestParam, false);
        
    }

    private void getWheelPlanting(){
        RequestParam requestParam = new RequestParam(BASE_URL +ARTICLE + "smart/content/pages", HttpMethod.Get);
        Map<String,String> map=new HashMap<>();
        map.put("projectId","ec93bb06f5be4c1f19522ca78180e2i9");
        map.put("receiver", NoticeReceiverType.全部.getType()+","+NoticeReceiverType.业主.getType());
        map.put("announcementTypeId", NoticeType.轮播动态.getType());
        map.put("auditStatus","1");
        map.put("pageNo","1");
        map.put("pageSize","10");
        requestParam.setRequestMap(map);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtil.d(result);
                BaseEntity<NoticeListEntity> baseEntity= JsonParse.parse(result,NoticeListEntity.class);
                if(baseEntity.isSuccess()){
                    bannerList.clear();
                    imageUrls.clear();
                    bannerList.addAll(baseEntity.getResult().getData());
                    for (NoticeEntity noticeEntity : bannerList) {
                        imageUrls.add(new ImageBanner(noticeEntity.getDetailUrl(),noticeEntity.getCoverUrl()));
                    }
                    bannerHomeAd.update(imageUrls);

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


    private void initADTextView() {
        adTextview.setSpeed(2);
        adTextview.setData(hotTopicList);
        adTextview.setMode(ADTextView.RunMode.UP);
        adTextview.setOnAdConetentClickListener(new OnAdConetentClickListener() {
            @Override
            public void OnAdConetentClickListener(int index, NoticeEntity noticeEntity) {
                NoticeEntity selectNotice = hotTopicList.get(index);
                Bundle bundle = new Bundle();
                bundle.putString("title","热点关注");
                bundle.putString("noticeId", selectNotice.getId());
                // TODO: 2020/3/27  新建  NoticeDetailActivity  然后取消一下注释
                //startActivity(NoticeDetailActivity.class, bundle);


            }
        });
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }

        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @OnClick({R.id.tv_to_menjin, R.id.tv_to_baoxiu, R.id.tv_to_tousu, R.id.tv_to_visitor, R.id.tv_to_video_call, R.id.iv_to_jiesuo, R.id.tv_home_page_ll, R.id.tv_home_page_parking_payment, R.id.tv_service, R.id.tv_project_progress, R.id.tv_property_right, R.id.tv_to_more, R.id.tv_to_tongzhi, R.id.tv_to_gonggao, R.id.tv_to_shjf, R.id.tv_complaint, R.id.tv_shoping, R.id.tv_repair, R.id.tv_to_zhoubian})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_to_menjin:
                if(bind){
                    startActivity(UnLock.class);
                }else{
                    EventBus.getDefault().post(new EventBusMessage<>("unbind"));
                }
                break;
            case R.id.tv_to_baoxiu:
                break;
            case R.id.tv_to_tousu:
                break;
            case R.id.tv_to_visitor:
                if(bind){
                    startActivity(VisitorActivity.class);
                }else{
                    EventBus.getDefault().post(new EventBusMessage<>("unbind"));
                }
                break;
            case R.id.tv_to_video_call:
                if(bind){
                    //startActivity(VideoCallActivity.class);
                }else{
                    EventBus.getDefault().post(new EventBusMessage<>("unbind"));
                }
                break;
            case R.id.iv_to_jiesuo:
                if(bind){
                    startActivity(CarLock.class);
                }else{
                    EventBus.getDefault().post(new EventBusMessage<>("unbind"));
                }
                break;
            case R.id.tv_home_page_ll:
                //startActivity(NeighborhoodActivity.class);
                break;
            case R.id.tv_home_page_parking_payment:
                //startActivity(ParkingPaymentActivity.class);
                break;
            case R.id.tv_service:
                Bundle bundle1 = new Bundle();
                bundle1.putBoolean("service", true);
                //startActivity(StoreListActivity.class, bundle1);
                break;
            case R.id.tv_project_progress:
                Bundle projectBundle=new Bundle();
                projectBundle.putString("notice_type",NoticeType.工程进程.getType());
              //  startActivity(NoticeActivity.class,projectBundle);
                break;
            case R.id.tv_property_right:
                Bundle bundle_b=new Bundle();
                bundle_b.putString("title","产证查询");
                bundle_b.putString("url","http://szjw.changsha.gov.cn/ywcx/");
                bundle_b.putString("rightAction","share");
               // startActivity(NewsInfoActivity.class,bundle_b);
                break;
            case R.id.tv_to_more:
                Bundle joinBundle=new Bundle();
                joinBundle.putString("notice_type",NoticeType.入伙.getType());
               // startActivity(NoticeActivity.class,joinBundle);
                break;
            case R.id.tv_to_tongzhi:
                Bundle noticeBundle=new Bundle();
                noticeBundle.putString("notice_type",NoticeType.社区公告.getType());
                //startActivity(NoticeActivity.class,noticeBundle);
                break;
            case R.id.tv_to_gonggao:
                break;
            case R.id.tv_to_shjf:
                //startActivity(LifePaymentActivity.class);
                break;
            case R.id.tv_complaint:
                if(bind){
                    // TODO: 2020/3/27  
                    //startActivity(ComplainActivity.class);
                }else{
                    EventBus.getDefault().post(new EventBusMessage<>("unbind"));
                }
                break;
            case R.id.tv_shoping:
                //startActivity(SecondHandActivity.class);
                break;
            case R.id.tv_repair:
                if(bind){
                    // TODO: 2020/3/27  
                    //startActivity(RepairsActivity.class);
                }else{
                    EventBus.getDefault().post(new EventBusMessage<>("unbind"));
                }
                break;
            case R.id.tv_to_zhoubian:
                Bundle a_bundle=new Bundle();
                a_bundle.putString("title","周边服务");
                a_bundle.putString("url","https://map.baidu.com/mobile/webapp/index/index");
               // startActivity(NewsInfoActivity.class,a_bundle);
                break;
        }
    }
}
