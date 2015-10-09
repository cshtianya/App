package com.tnw.api;


import com.tnw.api.apifig.ApiConstants;
import com.tnw.entities.BannerInfo;
import com.tnw.entities.CartNode;
import com.tnw.entities.CategoryNode;
import com.tnw.entities.CollectNode;
import com.tnw.entities.CommentNode;
import com.tnw.entities.DeliveryNode;
import com.tnw.entities.IndexGridNode;
import com.tnw.entities.IndexNode;
import com.tnw.entities.OrderDetailInfo;
import com.tnw.entities.OrderLogistics;
import com.tnw.entities.OrderNode;
import com.tnw.entities.OrderPayInfo;
import com.tnw.entities.OrderPreInfo;
import com.tnw.entities.ProductAttr;
import com.tnw.entities.ProductDetial;
import com.tnw.entities.ProductIntro;
import com.tnw.entities.ProductNode;
import com.tnw.entities.ProductTradeRecord;
import com.tnw.entities.ResultMsg;
import com.tnw.entities.UserBaseInfo;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Interface representing the DatabaseAPI endpoints
 * used by Retrofit
 */
public interface DatabaseAPI {
	
	/**
	 * 升级接口
	 * @param param
	 * @param callback
	 */
	@FormUrlEncoded
	@POST(ApiConstants.getUpdate)
	void checkAndroidVersion(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<IndexNode> callback);
	
	/**---------------------------------------------------------------
	 * 首页模块
	 * -------------------------------------------------------------*/
	@FormUrlEncoded
	@POST(ApiConstants.indexActivityList)
	void getIndexActivityList(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<List<IndexNode>> callback);

	@FormUrlEncoded
	@POST(ApiConstants.indexData)
	void getIndexGridList(
			@Field(ApiConstants.PARAM_KEY) String param,
			Callback<List<IndexGridNode>> callback);

	@FormUrlEncoded
	@POST(ApiConstants.indexADV)
	void getIndexADV(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<List<BannerInfo.BannerItem>> callback);


	@FormUrlEncoded
	@POST(ApiConstants.indexTopActivity)
	void getTopActiivity(
			@Field(ApiConstants.PARAM_KEY) String param,
			Callback<BannerInfo> callback);

	/**---------------------------------------------------------------
	 * 商品分类
	 * -------------------------------------------------------------*/
	@FormUrlEncoded
	@POST(ApiConstants.category)
	void getCategory(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<List<CategoryNode>> callback);
	
	/**---------------------------------------------------------------
	 * 商品模块
	 * -------------------------------------------------------------*/
	@FormUrlEncoded
	@POST(ApiConstants.commodityList)
	void getProductsList(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<List<ProductNode>> callback);
	
	@FormUrlEncoded
	@POST(ApiConstants.goodsDetials)
	void getProductDetials(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<ProductDetial> callback);
	
	@FormUrlEncoded
	@POST(ApiConstants.goodsImageText)
	void getProductIntro(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<List<ProductIntro>> callback);
	
	@FormUrlEncoded
	@POST(ApiConstants.goodsRecord)
	void getProductTradeRecord(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<List<ProductTradeRecord>> callback);
	
	@FormUrlEncoded
	@POST(ApiConstants.commodityStandard)
	void getProductAttr(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<List<ProductAttr>> callback);
	
	
	/**---------------------------------------------------------------
	 * 商品评论模块
	 * -------------------------------------------------------------*/
	@FormUrlEncoded
	@POST(ApiConstants.productComments)
	void getCommentsList(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<List<CommentNode>> callback);
	
	@FormUrlEncoded
	@POST(ApiConstants.userCommodityCommentList)
	void getMyCommentList(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<List<CommentNode>> callback);
	
	@FormUrlEncoded
	@POST(ApiConstants.addCommentCommodity)
	void addComment(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<ResultMsg> callback);
	
	@FormUrlEncoded
	@POST(ApiConstants.deleteCommodityComment)
	void deleteComment(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<ResultMsg> callback);
	
	
	/**---------------------------------------------------------------
	 * 用户模块
	 * -------------------------------------------------------------*/
	@FormUrlEncoded
	@POST(ApiConstants.userLogin)
	void userLogin(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<UserBaseInfo> callback);
	
	@FormUrlEncoded
	@POST(ApiConstants.buyerRegister)
	void userRegister(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<ResultMsg> callback);
	
	@FormUrlEncoded
	@POST(ApiConstants.userSendSms)
	void userAuthCode(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<ResultMsg> callback);
	
	@FormUrlEncoded
	@POST(ApiConstants.userPasswordMobile)
	void userResetPwd(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<ResultMsg> callback);
	
	/**--------------------用户收藏  START --------------------------*/
	@FormUrlEncoded
	@POST(ApiConstants.collenctionList)
	void collenctionList(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<List<CollectNode>> callback);
	@FormUrlEncoded
	@POST(ApiConstants.operatecollection)
	void collectionOperate(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<ResultMsg> callback);
	/**--------------------用户收藏  END --------------------------*/
	
	
	/**--------------------微信登录  START --------------------------*/
	@FormUrlEncoded
	@POST(ApiConstants.wxLogin)
	void wxLogin(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<UserBaseInfo> callback);
	@FormUrlEncoded
	@POST(ApiConstants.wxRegister)
	void wxRegister(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<UserBaseInfo> callback);
	@FormUrlEncoded
	@POST(ApiConstants.wxBind)
	void wxBind(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<UserBaseInfo> callback);
	/**--------------------微信登录  END--------------------------*/
	
	
	
	/**--------------------购物车  START --------------------------*/
	@FormUrlEncoded
	@POST(ApiConstants.cartList)
	void cartList(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<List<CartNode>> callback);
	@FormUrlEncoded
	@POST(ApiConstants.cartEdit)
	void cartEdit(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<ResultMsg> callback);
	@FormUrlEncoded
	@POST(ApiConstants.cartDelete)
	void cartDelete(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<ResultMsg> callback);
	/**--------------------购物车   END--------------------------*/
	
	
	/**--------------------收货地址   START--------------------------*/
	@FormUrlEncoded
	@POST(ApiConstants.deliveryList)
	void deliveryList(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<List<DeliveryNode>> callback);
	@FormUrlEncoded
	@POST(ApiConstants.deliveryEdit)
	void deliveryEdit(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<ResultMsg> callback);
	@FormUrlEncoded
	@POST(ApiConstants.deliveryDelete)
	void deliveryDelete(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<ResultMsg> callback);
	@FormUrlEncoded
	@POST(ApiConstants.deliveryDefaultSetting)
	void deliveryDefaultSetting(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<ResultMsg> callback);
	/**--------------------收货地址   END--------------------------*/
	
	
	/**---------------------------------------------------------------
	 * 商品订单数据模块
	 * -------------------------------------------------------------*/
	@FormUrlEncoded
	@POST(ApiConstants.checkOrder)
	void checkOrder(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<OrderPreInfo> callback);
	
	@FormUrlEncoded
	@POST(ApiConstants.buyerOrderDetail)
	void buyerOrderDetail(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<OrderDetailInfo> callback);
	
	@FormUrlEncoded
	@POST(ApiConstants.buyerOrderList)
	void buyerOrderList(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<List<OrderNode>> callback);
	
	@FormUrlEncoded
	@POST(ApiConstants.addOrder)
	void addOrder(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<OrderPayInfo> callback);
	
	@FormUrlEncoded
	@POST(ApiConstants.buyerCancelOrder)
	void cancelOrder(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<ResultMsg> callback);
	
	@FormUrlEncoded
	@POST(ApiConstants.buyerPaymentOrder)
	void buyerPaymentOrder(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<OrderPayInfo> callback);
	
	@FormUrlEncoded
	@POST(ApiConstants.remindOrder)
	void remindOrder(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<ResultMsg> callback);
	
	@FormUrlEncoded
	@POST(ApiConstants.orderWaybill)
	void orderWaybill(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<OrderLogistics> callback);
	
	@FormUrlEncoded
	@POST(ApiConstants.buyerCheckDeliveryOrder)
	void confirmAcciptOrder(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<ResultMsg> callback);
	
	@FormUrlEncoded
	@POST(ApiConstants.buyerAddReturnGoods)
	void orderAfterService(
            @Field(ApiConstants.PARAM_KEY) String param,
            Callback<ResultMsg> callback);
	
	
}


