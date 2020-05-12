package com.chanfinecloud.cfl.ui.fragment.mainfrg;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chanfinecloud.cfl.CFLApplication;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.adapter.CommunityEventsListAdapter;
import com.chanfinecloud.cfl.adapter.smart.ImageAdapter;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.ImageBanner;
import com.chanfinecloud.cfl.entity.NoticeEntity;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.entity.smart.EventsEntity;
import com.chanfinecloud.cfl.entity.smart.EventsListEntity;
import com.chanfinecloud.cfl.entity.smart.NoticeListEntity;
import com.chanfinecloud.cfl.entity.smart.NoticeType;
import com.chanfinecloud.cfl.entity.smart.UserInfoEntity;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.activity.CommunityEventsDetailActivity;
import com.chanfinecloud.cfl.ui.activity.CommunityEventsMoreActivity;
import com.chanfinecloud.cfl.ui.activity.ComplainActivity;
import com.chanfinecloud.cfl.ui.activity.LifePaymentActivity;
import com.chanfinecloud.cfl.ui.activity.NewsInfoActivity;
import com.chanfinecloud.cfl.ui.activity.NoticeActivity;
import com.chanfinecloud.cfl.ui.activity.NoticeDetailActivity;
import com.chanfinecloud.cfl.ui.activity.RepairsActivity;
import com.chanfinecloud.cfl.ui.activity.homehead.CarLock;
import com.chanfinecloud.cfl.ui.activity.homehead.UnLock;
import com.chanfinecloud.cfl.ui.activity.homehead.VideoCall2Activity;
import com.chanfinecloud.cfl.ui.activity.homehead.VisitorActivity;
import com.chanfinecloud.cfl.ui.base.BaseFragment;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.weidgt.ADTextView;
import com.chanfinecloud.cfl.weidgt.OnAdConetentClickListener;
import com.google.android.material.snackbar.Snackbar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.listener.OnPageChangeListener;

import com.youth.banner.transformer.ZoomOutPageTransformer;
import com.youth.banner.util.BannerUtils;

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
import static com.chanfinecloud.cfl.util.UserInfoUtil.getCurrentHouseholdType;

/**
 * Damien
 * 首页
 */
public class HomeFragment extends BaseFragment  implements OnPageChangeListener {


    @BindView(R.id.iv_project_icon)
    ImageView ivProjectIcon;
    @BindView(R.id.tv_project_logo_name)
    TextView tvProjectLogoName;
    @BindView(R.id.tv_to_menjin)
    TextView tvToMenjin;
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
    @BindView(R.id.tv_project_progress)
    TextView tvProjectProgress;
    @BindView(R.id.tv_property_right)
    TextView tvPropertyRight;
    @BindView(R.id.tv_to_more)
    TextView tvToMore;
    @BindView(R.id.tv_to_tongzhi)
    TextView tvToTongzhi;
    @BindView(R.id.tv_to_shjf)
    TextView tvToShjf;
    @BindView(R.id.tv_complaint)
    TextView tvComplaint;
    @BindView(R.id.tv_repair)
    TextView tvRepair;
    @BindView(R.id.tv_to_zhoubian)
    TextView tvToZhoubian;
    @BindView(R.id.home_activity_rlv)
    RecyclerView homeActivityRlv;
    @BindView(R.id.home_fragment_smart_refresh)
    SmartRefreshLayout homeFragmentSmartRefresh;


    private Unbinder unbinder;

    private ArrayList<NoticeEntity> hotTopicList = new ArrayList<>();
    private List<NoticeEntity> bannerList = new ArrayList<>();
    private List<ImageBanner> imageUrls = new ArrayList<>();
    private UserInfoEntity userInfo;
    private CommunityEventsListAdapter communityEventsListAdapter;
    private ArrayList<EventsEntity> eventsEntityArrayList = new ArrayList<>();

    @Override
    protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();
//        userInfo = FileManagement.getUserInfo();
//        tvProjectLogoName.setText(userInfo.getCurrentDistrict().getProjectName());
//        getHotTips();
//        getWheelPlanting();
        bannerHomeAd.start();
    }

    @Override
    protected void onFragmentStopLazy() {
        super.onFragmentStopLazy();
        bannerHomeAd.stop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventBusMessage message) {
        LogUtils.d(message.getMessage());
        if ("projectSelect".equals(message.getMessage())) {
            userInfo = FileManagement.getUserInfo();
            tvProjectLogoName.setText(userInfo.getCurrentDistrict().getProjectName());
            getHotTips();
            getWheelPlanting();
        } else if ("NoticeRefresh".equals(message.getMessage())) {
            getHotTips();
            getWheelPlanting();
        }
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        setContentView(view);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void initData() {
        userInfo = FileManagement.getUserInfo();
        tvProjectLogoName.setText(userInfo.getCurrentDistrict().getProjectName());
        getHotTips();
        getWheelPlanting();
        getEventsData(1);

        //设置适配器
        bannerHomeAd.setAdapter(new ImageAdapter(imageUrls));
        //设置指示器
        bannerHomeAd.setIndicator(new CircleIndicator(getActivity()));
        //设置点击事件
        bannerHomeAd.setOnBannerListener((data, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", "新闻动态");
            bundle.putString("noticeId", bannerList.get(position).getId());
            startActivity(NoticeDetailActivity.class, bundle);

        });
        //添加切换监听
        bannerHomeAd.addOnPageChangeListener(this);
        //圆角
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bannerHomeAd.setBannerRound2(BannerUtils.dp2px(10));
        }

        bannerHomeAd.setBannerGalleryMZ(30, 0.88f);
        //添加画廊效果，可以参考我给的参数自己调试(不要和其他PageTransformer同时使用)
        //banner.setBannerGalleryEffect(25, 40, 0.14f);
        //banner.setBannerGalleryEffect(31, 60, 0.12f);
        //设置组合PageTransformer
        bannerHomeAd.addPageTransformer(new ZoomOutPageTransformer());
        // banner.addPageTransformer(new DepthPageTransformer());
        // banner.addPageTransformer(new MZScaleInTransformer(0.88f));
        bannerHomeAd.start();

        EventBus.getDefault().register(this);

        homeFragmentSmartRefresh.setEnableLoadMore(false);
        homeFragmentSmartRefresh.setEnableRefresh(true);
        homeFragmentSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {

                freshData();
                homeFragmentSmartRefresh.finishRefresh(3000);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        homeActivityRlv.setLayoutManager(linearLayoutManager);
        communityEventsListAdapter = new CommunityEventsListAdapter(getActivity(), eventsEntityArrayList, 1);
        homeActivityRlv.setAdapter(communityEventsListAdapter);
        homeActivityRlv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                String id = eventsEntityArrayList.get(position).getId();
                Bundle bundle = new Bundle();
                bundle.putString("eventsID", id);
                startActivity(CommunityEventsDetailActivity.class, bundle);
            }
        });

    }


    /**
     * 刷新首页数据
     */
    private void freshData(){

        getEventsData(2);
    }

    /**
     * 获取社区活动列表
     */
    private void getEventsData(int freshType) {

        RequestParam requestParam = new RequestParam(BASE_URL + ARTICLE + "smart/event/page", HttpMethod.Get);
        Map<String, String> map = new HashMap<>();
        map.put("pageNo", "1");
        map.put("pageSize", "5");
        map.put("isClosed", "0");
        map.put("isEnd", "0");
        requestParam.setRequestMap(map);
        requestParam.setCallback(new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtil.d(result);
                BaseEntity<EventsListEntity> baseEntity = JsonParse.parse(result, EventsListEntity.class);
                if (baseEntity.isSuccess()) {
                    eventsEntityArrayList.clear();
                    eventsEntityArrayList.addAll(baseEntity.getResult().getData());
                    communityEventsListAdapter.notifyDataSetChanged();

                } else {
                    showToast(baseEntity.getMessage());
                }
                if (freshType == 2){
                    getWheelPlanting();
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

    /**
     * 获取热点关注
     */
    private void getHotTips() {
        RequestParam requestParam = new RequestParam(BASE_URL + ARTICLE + "smart/content/pages", HttpMethod.Get);
        Map<String, String> map = new HashMap<>();
        map.put("projectId", FileManagement.getUserInfo().getCurrentDistrict().getProjectId());
        map.put("receiver", getCurrentHouseholdType());
        map.put("announcementTypeId", NoticeType.热点关注.getType());
        map.put("auditStatus", "1");
        map.put("pageNo", "1");
        map.put("pageSize", "10");
        requestParam.setRequestMap(map);
        requestParam.setCallback(new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtil.d(result);
                BaseEntity<NoticeListEntity> baseEntity = JsonParse.parse(result, NoticeListEntity.class);
                if (baseEntity.isSuccess()) {
                    hotTopicList.clear();
                    hotTopicList.addAll(baseEntity.getResult().getData());
                    if (hotTopicList.size() > 0) {
                        llNewHomeNoticeDetail.setVisibility(View.VISIBLE);
                        initADTextView();
                    } else {
                        llNewHomeNoticeDetail.setVisibility(View.GONE);
                    }

                } else {
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

    /**
     * 获取轮播图
     */
    private void getWheelPlanting() {
        RequestParam requestParam = new RequestParam(BASE_URL + ARTICLE + "smart/content/pages", HttpMethod.Get);
        Map<String, String> map = new HashMap<>();
        // TODO: 2020/4/10  动态获取
        map.put("projectId", FileManagement.getUserInfo().getCurrentDistrict().getProjectId());
        map.put("receiver", getCurrentHouseholdType());
        map.put("announcementTypeId", NoticeType.轮播动态.getType());
        map.put("auditStatus", "1");
        map.put("pageNo", "1");
        map.put("pageSize", "10");
        requestParam.setRequestMap(map);
        requestParam.setCallback(new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtil.d(result);
                BaseEntity<NoticeListEntity> baseEntity = JsonParse.parse(result, NoticeListEntity.class);
                if (baseEntity.isSuccess()) {
                    bannerList.clear();
                    imageUrls.clear();
                    bannerList.addAll(baseEntity.getResult().getData());
                    for (NoticeEntity noticeEntity : bannerList) {
                        imageUrls.add(new ImageBanner(noticeEntity.getDetailUrl(), noticeEntity.getCoverUrl()));
                    }
                    bannerHomeAd.setDatas(imageUrls);

                } else {
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

    /**
     * 初始化热点关注
     */
    private void initADTextView() {
        adTextview.setSpeed(2);
        adTextview.setData(hotTopicList);
        adTextview.setMode(ADTextView.RunMode.UP);
        adTextview.setOnAdConetentClickListener(new OnAdConetentClickListener() {
            @Override
            public void OnAdConetentClickListener(int index, NoticeEntity noticeEntity) {
                NoticeEntity selectNotice = hotTopicList.get(index);
                Bundle bundle = new Bundle();
                bundle.putString("title", "热点关注");
                bundle.putString("noticeId", selectNotice.getId());
                startActivity(NoticeDetailActivity.class, bundle);
            }
        });
    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.tv_to_menjin, R.id.tv_to_visitor, R.id.tv_to_video_call, R.id.iv_to_jiesuo,
            R.id.tv_project_progress, R.id.tv_property_right, R.id.tv_to_more, R.id.tv_to_tongzhi,
            R.id.tv_to_shjf, R.id.tv_complaint, R.id.tv_repair, R.id.tv_to_zhoubian, R.id.home_event_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_to_menjin:
                if (CFLApplication.bind) {
                    startActivity(UnLock.class);
                } else {
                    EventBus.getDefault().post(new EventBusMessage<>("unbind"));
                }
                break;
            case R.id.tv_to_visitor:
                if (CFLApplication.bind) {
                    startActivity(VisitorActivity.class);
                } else {
                    EventBus.getDefault().post(new EventBusMessage<>("unbind"));
                }
                break;
            case R.id.tv_to_video_call:
                if (CFLApplication.bind) {
                    // startActivity(VideoCallActivity.class);
                    startActivity(VideoCall2Activity.class);
                } else {
                    EventBus.getDefault().post(new EventBusMessage<>("unbind"));
                }
                break;
            case R.id.iv_to_jiesuo:
                if (CFLApplication.bind) {
                    startActivity(CarLock.class);
                } else {
                    EventBus.getDefault().post(new EventBusMessage<>("unbind"));
                }
                break;
            case R.id.tv_project_progress:
                Bundle projectBundle = new Bundle();
                projectBundle.putString("notice_type", NoticeType.工程进度.getType());
                startActivity(NoticeActivity.class, projectBundle);
                break;
            case R.id.tv_property_right:
                Bundle bundle_b = new Bundle();
                bundle_b.putString("title", "产证查询");
                bundle_b.putString("url", "http://szjw.changsha.gov.cn/ywcx/");
                bundle_b.putString("rightAction", "share");
                startActivity(NewsInfoActivity.class, bundle_b);
                break;
            case R.id.tv_to_more:
                Bundle joinBundle = new Bundle();
                joinBundle.putString("notice_type", NoticeType.入伙.getType());
                startActivity(NoticeActivity.class, joinBundle);
                break;
            case R.id.tv_to_tongzhi:
                Bundle noticeBundle = new Bundle();
                noticeBundle.putString("notice_type", NoticeType.社区公告.getType());
                startActivity(NoticeActivity.class, noticeBundle);
                break;
            case R.id.tv_to_shjf:
                startActivity(LifePaymentActivity.class);
                break;
            case R.id.tv_complaint:
                if (CFLApplication.bind) {
                    startActivity(ComplainActivity.class);
                } else {
                    EventBus.getDefault().post(new EventBusMessage<>("unbind"));
                }
                break;
            case R.id.tv_repair:
                if (CFLApplication.bind) {
                    startActivity(RepairsActivity.class);
                } else {
                    EventBus.getDefault().post(new EventBusMessage<>("unbind"));
                }
                break;
            case R.id.tv_to_zhoubian:
                Bundle a_bundle = new Bundle();
                a_bundle.putString("title", "周边服务");
                a_bundle.putString("url", "https://map.baidu.com/mobile/webapp/index/index");
                startActivity(NewsInfoActivity.class, a_bundle);
                break;
            case R.id.home_event_more:
                if (CFLApplication.bind) {
                    startActivity(CommunityEventsMoreActivity.class);
                } else {
                    EventBus.getDefault().post(new EventBusMessage<>("unbind"));
                }
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.e("homefragment", "onPageSelected:" + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
