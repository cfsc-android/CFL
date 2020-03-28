package com.chanfinecloud.cfl.util;

import android.os.Environment;

/**
 * 常量类
 */
public class Constants {

    public static String OPENAPIURL = "";

    /**
     * Unknown network class
     */
    public static final int NETWORK_CLASS_UNKNOWN = 0;

    /**
     * wifi net work
     */
    public static final int NETWORK_WIFI = 1;

    /**
     * "2G" networks
     */
    public static final int NETWORK_CLASS_2_G = 2;

    /**
     * "3G" networks
     */
    public static final int NETWORK_CLASS_3_G = 3;

    /**
     * "4G" networks
     */
    public static final int NETWORK_CLASS_4_G = 4;

    /**
     * 联网失败
     */
    public static final int NET_FAILED = 0;
    /**
     * 联网成功
     */
    public static final int NET_SUCCESS = 1;
    /**
     * 获取数据成功
     */
    public static final int SUCCESS = 200;
    /**
     * 获取数据失败
     */
    public static final int FAILED = -1;
    /**
     * 从相册获取
     */
    public static final int PHOTOS_ALBUM = 1;

    /**
     * 拍照获取
     */
    public static final int TAKE_PHOTOS = 2;
    /**
     * 我的报修筛选返回参数
     */
    public static final int MY_REPAIRS_REQUEST_CODE = 1;
    /**
     * 我的投诉筛选返回参数
     */
    public static final int MY_COMPLAIN_REQUEST_CODE = 2;
    /**
     * 支付列表筛选返回参数
     */
    public static final int LIFE_PAY_REQUEST_CODE = 3;
    /**
     * 发帖成功返回参数
     */
    public static final int POST_CODE = 1;

    /**
     * 用于判断添加的图片数量是否为3张：false：未达到，true：3张
     */
    public static boolean GRIDVIEW_IMAGE_COUNT = false;

    /**
     * 屏幕宽高
     */
    public static int SWidth = 0;
    public static int SHeight = 0;

    public static final String mimeType = "text/html";
    public static final String encoding = "utf-8";
    public static final String channel = "Mobile";
    public static final String terminalType = "ANDROID";
    public static final String channelIp = "channelIp";//请求方ip地址
    public static final String clientId = "zhangchao";
    public static final String APPKEY = "";

    public static final int CODETIME = 60;//验证码失效，单位：秒

//    public static final String BASEHOST = "http://192.168.0.44:8081/xiandao";
    //    public static final String BASEHOST = "http://120.24.37.216:8080/xiandao";//测试环境
//        public static final String BASEHOST = "http://121.199.41.147/xiandao";//测试环境
//    public static final String BASEHOST = "http://192.168.0.66:8080";
//    public static final String BASEHOST = "http://192.168.0.75:10086/xiandao";
//    public static final String BASEHOST = "http://49.4.66.251:8081/smart";//先导外网地址

    /** 新增服务器地址 edit：zxl time：2019-4-3  */
//    public static final String BASEHOST = "http://172.19.13.118:8080/smartxd/";

//    public static final String BASEHOST="http://10.10.222.110:9081/smartxd/";
//      public static final String BASEHOST="http://dev.chanfine.com:9082//smartxd/";
//     public static final String BASEHOST="http://192.168.63.135:8090/smartxd/";


//    public static final String BASEHOST = "http://134.175.27.151:8080/smartXd";//先导外网地址
//    public static final String BASEHOST = "http://192.168.1.25:8080/";//先导外网地址
//    public static final String BASEHOST = "http://222.240.16.134:8081/xiandao";//先导外网地址

//      public static String BASEHOST = "http://172.19.13.243:8080/smartxd/";
//    public static String BASEHOST="http://172.19.13.196:8080/smartxd/";
            //生产环境
//    public static String BASEHOST="http://dev.chanfine.com:9082/smartxd/";
            //测试环境
//    public static String BASEHOST="http://10.10.222.112:9082/smartxd/";
    public static String BASEHOST="";

    public static String HOST ="";



    //海康服务器
    public static final String HIKHOST = "http://10.10.222.100";
    public static final String SERVICETIME = "/ApiDate/nowDateAddHour.action";//服务器时间
    //==============登录和退出登录================
    public static final String SMSSEND = "/getLoginCode.action";//获取验证码
    public static final String USERLOGIN = "/loginOwner.action";//登录
    public static final String CHECKOWNER = "/checkOwner.action";//获取用户信息
    public static final String LOGOUT = "/logoutOwner.action";//退出登录

    //==============首页================
    public static final String GETHOMEINFORM = "/owner/getHomeInform.action";//获取首页通知

    //==============投诉建议================
    public static final String COMPLAININFOCOMMIT = "/owner/complains/complainInfoCommit.action";//投诉信息提交
    public static final String GETCOMPLAINTYPE = "/owner/getComplainType.action";//获取投诉类型

    //==============报事报修================
    public static final String GETREPAIRSADDRESS = "/owner/getRepairsAddress.action";//获取报修地址
    public static final String GETREPAIRSTYPE = "/owner/getRepairsType.action";//获取报修类别
    public static final String REPAIRSINFOCOMMIT = "/job/add.action";//报修信息提交

    //==============通知公告================
    public static final String QUERYNOTICELIST = "/findnotices.action";//获取公告列表
    public static final String QUERYTURNSANDHOTTOPICS = "/getTurnsAndHottopics.action";//获取轮播与热点
    public static final String QUERYINFORMLIST = "/owner/queryInformList.action";//获取通知列表
    public static final String GETREADNUMBER = "/readNumber.action";//获取公告详情阅读数
    public static final String GETPOINTNUMBER = "/pointNumber.action";//获取公告详情点赞数

    //==============生活缴费================
    public static final String LIFEPAYQUERY = "/pay/query.action";//查询缴费信息
    public static final String CREATEPAYINFO = "/pay/createpayinfo.action";//获取订单信息
    //==============我的投诉================
    public static final String QUERYMYCOMPLAINLIST = "/owner/complains/queryMyComplainList.action";//获取我的投诉列表
    public static final String GETCOMLAINDETAIL = "/owner/complains/getComplainDetail.action"; //获取我的投诉详情信息
    public static final String ISACCEPTSOLUTIONCOMPLAIN ="/owner/complains/customIsAcceptSolutionComplain.action";//业主是否接受填写方案
    public static final String QUERYMYCOMLAINPLAN = "/owner/complains/queryMyComplainPlan.action";//获取我的投诉进度
    public static final String CONFIRMRESULT ="/owner/complains/customIsSatisfactionSolutionComplain.action";//业主是否满意处理
    public static final String CUSTOMACCEPTREFIRM = "/owner/complains/customAcceptMeasureComplain.action";//业主是否接受整改
    public static final String COMMENTCOMPLAIN = "/owner/complains/customCommentComplain.action";//评价投诉

    //===============我的报修================
    public static final String QUERYMYREPAIRSPLAN = "/job/queryJobDetailProcessInfo.action";//获取我的报修进度
    public static final String QUERYREPAIRSLIST = "/owner/queryJobList.action";//获取报修列表
    public static final String GETREPAIRSDETAIL = "/job/queryJobDetail.action";//获取报修详情
    public static final String ACCEPTPRICE = "/owner/acceptPrice.action";//业主 是否接受价格
    public static final String CONFIRMWORKORDERFINISH = "/owner/confirmWorkOrderFinish.action";//用来判断是去评价还是支付
    public static final String COMMENTWORKORDER = "/owner/commentWorkOrder.action";//评价工单
    public static final String REPAIRSCODE = "/patrol/queryPatrolAddressByCode.action";//通过二维码获取地址

    //===============我的资料=================
    public static final String CREATELINKMAN = "/owner/createLinkman.action";//新建联系人
    public static final String EDITPERSONALINFORMATION = "/owner/editPersonalInformation.action";//编辑个人资料
    public static final String GETLINKMANLIST = "/owner/getLinkmanList.action";//获取联系人列表
    public static final String QUERYPROJECTS = "/owner/queryProjects.action";//查询楼盘
    public static final String QUERYBUILDINGS = "/owner/queryBuildings.action";//查询楼栋
    public static final String QUERYCELLS = "/owner/queryCells.action";//查询单元
    public static final String QUERYROOMS = "/owner/queryRooms.action";//查询房间
    public static final String CREATECOMMONADDRESS = "/owner/createCommonAddress.action";//创建常用地址
    public static final String GETCOMMONADDRESSLIST = "/owner/getCommonAddressList.action";//获取常用地址
    public static final String LINLIPOST = "/postmanagement/addpost.action";//邻里发帖

    //===============购物=================
    public static final String QUERYSHOPHOME = "/shop/getHomeType.action";//获取购物首页--商品分类
    public static final String QUERYGOODSLIST = "/shop/getTypeGood.action";//获取商品列表
    public static final String QUERYSTORELIST = "/shop/getShopHomeList.action";//获取商户列表
    public static final String ADDBUYCAR = "/shop/addGoods.action";//添加到购物车
    public static final String UPDATECARNUM = "/shop/updateCartNumber.action";//修改购物车数量
    public static final String DELETECART = "/shop/delCartDetail.action";//删除购物车
    public static final String COMMITORDER = "/shop/createOrder.action";//提交订单
    public static final String QUERYMYORDER = "/shop/getMyOders.action";//查询我的订单
    public static final String UPDATEORDERSTATUS = "/shop/updateOrderStatus.action";//修改订单状态
    public static final String COMMITMYRECEIVINGADDRESS = "/shop/updateAddress.action";//提交我的收货地址
    public static final String QUERYSHOPCARLIST = "/shop/myShopCar.action";//查询购物车列表
    public static final String QUERYSHOPCOMMENTLIST = "/shop/getCommentByGoodsId.action";//查询商品评价列表
    public static final String COMMITCOMMENT = "/shop/addComment.action";//评价

    //===================微信支付====================
    public static final String APP_ID = "wx4b10935662bcf67c";
    public static final String GETWXPAYPARAMS = "/wxpay/getPayParam.action";//获取微信支付参数

    public static class ShowMsgActivity {
        public static final String STitle = "showmsg_title";
        public static final String SMessage = "showmsg_message";
        public static final String BAThumbData = "showmsg_thumb_data";
    }

    //保存本地文件夹
    public final static String DATAPATH = Environment.getExternalStorageDirectory().getPath() + "/xiandao/";

    //====================发现===============================
    public static final String ALLFIND = "/theme/getAllTheme.action";//所有帖子
    public static final String MYFIND = "/theme/getMyFollow.action";//我关注的帖子
    public static final String FOLLOWFIND="/theme/getAllThemmByOwnerid.action";//我发的贴子
    public static final String FINDDETAILS = "/theme/getByThemeId.action";//帖子详情
    public static final String THUMBSUP = "/theme/upTheme.action";//点赞
    public static final String FOLLOW = "/theme/follow.action";//关注
    public static final String CANCELFOLLOW = "/theme/cancelfollow.action";//取消关注
    public static final String RELEASETHEME = "/theme/addTheme.action";//发布新鲜事
    public static final String DELETETHEME = "/theme/delByThemeId.action";//删除贴子


    //=============================访客二维码========================
    public static final String NEWVISITOR = "/getHIKVisionVisitorQRcode.action";
    //==============================海康==============================
    public static final String SUBORGLIST = "/artemis/api/resource/v1/org/parentOrgIndexCode/subOrgList";//测试
    public static final String PERSONINFO = "/artemis/api/resource/v1/person/phoneNo/personInfo";//获取用户
    public static final String CARLIST = "/artemis/api/resource/v1/vehicle/advance/vehicleList";//获取用户车辆信息
    public static final String ALARMCAR = "/artemis/api/pms/v1/alarmCar/page";//查询车辆布控信息
    public static final String ALARMCARADDITION = "/artemis/api/pms/v1/alarmCar/addition";//车辆布控
    public static final String ALARMCARDELETION = "/artemis/api/pms/v1/alarmCar/deletion";//车辆取消布控
    public static final String CARCROSSRECORD = "/artemis/api/pms/v1/crossRecords/page";//车辆出入记录
    public static final String CARDLIST = "/artemis/api/irds/v1/card/advance/cardList";//获取用户车辆信息
    public static final String PARKINGPAYMENT = "/artemis/api/pms/v1/pay/quickPreBill";//获取停车账单
    public static final String RECEIPT = "/artemis/api/pms/v1/pay/receipt";//账单支付确认
//    public static final String NEWVISITOR = "/artemis/api/cis/v1/card/bindings";//新增访客
    public static final String VISITORID = "/artemis/api/resource/v1/person/certificateNo/personInfo";//获取访客
    public static final String PARKLIST="/artemis/api/resource/v1/park/parkList";//车库列表
    public static final String CARCHARGE="/artemis/api/pms/v1/car/charge";//车辆包期
    public static final String CARCHARGEDELETION="/artemis/api/pms/v1/car/charge/deletion";//取消车辆包期
    public static final String CARCHARGEINFO="/artemis/api/pms/v1/car/charge/page";//获取车辆包期信息

    //=========================新增===============================

    public static final String FACEGROUP = "/artemis/api/frs/v1/face/group";//获取人脸分组
    public static final String ADDFACE = "/artemis/api/frs/v1/face/single/addition";//新增人脸
    public static final String QUERYFACE = "/artemis/api/frs/v1/face";//查询人脸
    public static final String UPDATEFACE = "/artemis/api/frs/v1/face/single/update";//修改人脸
    public static final String DELETEFACE = "/artemis/api/frs/v1/face/deletion";//删除人脸
}


