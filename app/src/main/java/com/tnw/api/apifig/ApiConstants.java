package com.tnw.api.apifig;

public interface ApiConstants extends ApiConfig {
	
	/**单个商品广告*/
	int A_30001 = 30001;
	/**商家广告*/
	int A_30002 = 30002;
	/**促销广告*/
	int A_30003 = 30003;
	/**热卖推荐*/
	int C_40001 = 40001;
	/**皮革女装*/
	int C_40002 = 40002;

	int C_40003 = 40003;

	int C_40004 = 40004;

    int C_40005 = 40005;
	/**品牌*/
	int C_40006 = 40006;

	/**测试*/
	int C_41000 = 41000;
	int C_41001 = 41001;
	int C_41002 = 41002;
	int C_41003 = 41003;
	int C_41004 = 41004;
	int C_41005 = 41005;
	int C_41006 = 41006;
	int C_41007 = 41007;
	int C_41008 = 41008;
	int C_41009 = 41009;
	int C_41010 = 41010;
	int C_41011 = 41011;

	
	/**----------------------------------------------------------------------
	 * 升级模块
	 * --------------------------------------------------------------------*/
	/** 版本升级*/
	String getUpdate = "/user/checkAndroidVersion";
	
	
	/**----------------------------------------------------------------------
	 * 首页信息以及首页广告模块
	 * --------------------------------------------------------------------*/
	
	/** 首页列表信息 旧接口 更新至 indexActivityList */
	@Deprecated
	String indexData = "/page/index";
	/** 首页列表信息  新接口*/
	String indexActivityList = "/page/indexActivityList";
	/** 首页广告信息列表  旧接口*/
	String indexADV = "/ad/adList";
	/** 首页广告信息列表   新接口  暂时不用*/
	@SuppressWarnings(value = { "暂时不用" })
	String indexTopActivity = "/page/indexTopActivity";
	
	
	/**----------------------------------------------------------------------
	 * 商品分类信息
	 * --------------------------------------------------------------------*/
	/** 分类列表信息*/
	String category = "/category/categoryList";
	
	/**----------------------------------------------------------------------
	 * 商品信息模块
	 * --------------------------------------------------------------------*/
	/** 商品列表信息*/
	String commodityList = "/commodity/commodityList";
	/** 商品详情信息*/
	String goodsDetials = "/commodity/commodityDetail";
	/** 商品图文详情*/
	String goodsImageText = "/commodity/commodityContentImage";
	/** 商品交易记录*/
	String goodsRecord = "/commodity/commodityTradeList";
	/** 获取商品规格列表 */
	String commodityStandard =  "/commodity/commodityStandard";
	
	/**----------------------------------------------------------------------
	 * 商品评论模块
	 * --------------------------------------------------------------------*/
	/** 商品评论列表 */
	String productComments = "/comment/commentCommodityList";
	/** 添加商品评论*/
	String addCommentCommodity = "/comment/addCommentCommodity";
	/** 我的评论 */
	String userCommodityCommentList = "/comment/userCommodityCommentList";
	/** 我的评论删除*/
	String deleteCommodityComment = "/comment/deleteCommodityComment";
	/** 商品添加删除/收藏*/
	String operatecollection = "/order/operatecollection";
	
	
	/**----------------------------------------------------------------------
	 * 用户数据模块
	 * --------------------------------------------------------------------*/
	/** 用户登录*/
	String userLogin = "/user/login";
	/** 用户注册*/
	String buyerRegister = "/user/buyerRegister";
	/** 获取验证码*/
	String userSendSms = "/user/userSendSms";
	/** 重置密码*/
	String userPasswordMobile = "/user/userPasswordMobile";
	
	/** 第三方登录 **/
	String wxLogin = "/user/thirdpass";
	/** 第三方注册 **/
	String wxRegister = "/user/thirdregister";
	/** 第三方绑定 **/
	String wxBind = "/user/thirdbind";
	
	/** 购物车列表*/
	String cartList = "/user/cartList";
	/** 添加或修改购物车商品 */
	String cartEdit = "/user/cartEdit";
	/** 删除购物车商品 */
	String cartDelete = "/user/cartDelete";
	
	/** 收货地址列表*/
	String deliveryList ="/user/deliveryList";
	/** 收货地址添加/修改*/
	String deliveryEdit = "/user/deliveryEdit";
	/** 收货地址删除*/
	String deliveryDelete = "/user/deliveryDelete";
	/** 收货地址设置为默认*/
	String deliveryDefaultSetting = "/user/deliveryDefaultSetting";
	
	
	/**-----------------------------------------------------------------------------------
	 * 
	 * 整个发表模块考虑去掉
	 * 
	 * ---------------------------------------------------------------------------------*/
	/** 发票列表*/
	String userInvoiceList ="/user/listInvoice";
	/** 发票添加/修改*/
	String invoiceEdit = "/user/InvoiceEdit";
	/** 发票删除*/
	String invoiceDelete = "/user/invoiceDelete";
	/** 发票设置为默认*/
	String invoiceDefault = "/user/invoiceDefault";
	
	
	/**----------------------------------------------------------------------
	 * 
	 * 订单数据模块
	 * 
	 * --------------------------------------------------------------------*/
	/** 我的收藏 */
	String collenctionList = "/order/mycollection";
	/** 订单检测 */
	String checkOrder = "/order/checkOrder";
	/** 订单详情 */
	String buyerOrderDetail = "/order/buyerOrderDetail";
	/** 订单列表 */
	String buyerOrderList = "/order/buyerOrderList";
	/** 提交订单 */
	String addOrder = "/order/addOrder";
	/** 取消订单 */
	String buyerCancelOrder = "/order/buyerCancelOrder";
	/** 提醒卖家发货 */
	String remindOrder = "/order/remindOrder";
	/** 获取订单支付信息 */
	String buyerPaymentOrder = "/order/buyerPaymentOrder";
	/** 订单物流信息 */
	String orderWaybill = "/order/orderWaybill";
	/** 确认订单 */
	String buyerCheckDeliveryOrder = "/order/buyerCheckDeliveryOrder";
	/** 申请售后 */
	String buyerAddReturnGoods = "/order/buyerAddReturnGoods";
	
	
}
