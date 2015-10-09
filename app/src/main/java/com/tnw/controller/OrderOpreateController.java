package com.tnw.controller;

import android.text.TextUtils;
import android.util.Log;

import com.tnw.api.APIDataListener;
import com.tnw.api.Base64_Encode_PHP;
import com.tnw.api.action.ActionEnum;
import com.tnw.api.action.OrderAction;
import com.tnw.api.apifig.ApiConfig;
import com.tnw.entities.OrderDetailInfo;
import com.tnw.entities.OrderPayInfo;
import com.tnw.entities.OrderPreInfo;
import com.tnw.entities.ResultMsg;
import com.tnw.mvp.OrderView;

import java.util.HashMap;

public class OrderOpreateController extends BaseController<String>{

	private final OrderAction action;
    private final OrderView mPView;

	private final HashMap<String, String> mMap;

	private boolean isLoading = false;

	public OrderOpreateController(OrderView orderView){
		this.mPView = orderView;
        mMap = new HashMap<>(1);
		action = (OrderAction) ActionEnum.getOrderAction.getInstance();
	}

	@Override
	public void excute(String param) {
		// TODO Auto-generated method stub
		mPView.showLoading();

		if(ApiConfig.IS_DEBUG)
			Log.v("csh", "checkOrder =======>" + param);
		param = Base64_Encode_PHP.encode(param);
		action.checkOrder(param, listener);
	}

	/**
	 * 生成订单
	 * @param param
	 */
	public void excutePayOrder(String param,boolean isNewOrder){
		mPView.showLoading();
		if(ApiConfig.IS_DEBUG)
			Log.v("csh", "checkOrder =======>" + param);
		param = Base64_Encode_PHP.encode(param);
		if(isNewOrder){
			action.addOrder(param, orderlistener);
		}else{
			action.buyerPaymentOrder(param,orderlistener);
		}
	}

	/**
	 * 生成订单
	 * @param param
	 */
	public void showOrderDetail(String param){
		mPView.showLoading();
		if(ApiConfig.IS_DEBUG)
			Log.v("csh", "buyerOrderDetail =======>" + param);
		param = Base64_Encode_PHP.encode(param);
		action.buyerOrderDetail(param, detaillistener);
	}


	/**
	 * 生成订单
	 * @param param
	 */
	public void opreateOrder(int orderState,String param){
		mPView.showLoading();
		if(ApiConfig.IS_DEBUG)
			Log.v("csh", "orderOperate =======>" + param);
		param = Base64_Encode_PHP.encode(param);
		action.orderOperate(orderState,param, new MAPIDataListern(orderState));
	}

	APIDataListener<OrderPreInfo> listener = new APIDataListener<OrderPreInfo>() {

		@Override
		public void success(OrderPreInfo result) {
			// TODO Auto-generated method stub
			if (isStop)return;
			mPView.showPreOrder(result);
			mPView.hideLoading();

			isLoading = false;
		}

		@Override
		public void failure(String errorMsg) {
			// TODO Auto-generated method stub
			mPView.showMsg(errorMsg);
			isLoading = false;
			mPView.hideLoading();

		}

	};

	APIDataListener<OrderPayInfo> orderlistener = new APIDataListener<OrderPayInfo>() {

		@Override
		public void success(OrderPayInfo result) {
			// TODO Auto-generated method stub
			if (isStop)return;

			if(TextUtils.equals("0",result.getError())){
				mPView.excutePayOrder(result.getData());
			}else{
				mPView.showMsg(result.getErrorMessage());
			}

			mPView.hideLoading();
			isLoading = false;
		}

		@Override
		public void failure(String errorMsg) {
			// TODO Auto-generated method stub
			mPView.showMsg(errorMsg);
			mPView.hideLoading();
			isLoading = false;

		}

	};

	APIDataListener<OrderDetailInfo> detaillistener = new APIDataListener<OrderDetailInfo>() {

		@Override
		public void success(OrderDetailInfo result) {
			// TODO Auto-generated method stub
			if (isStop)return;

			mPView.hideLoading();
			mPView.showOrderDetail(result);

			isLoading = false;
		}

		@Override
		public void failure(String errorMsg) {
			// TODO Auto-generated method stub
			mPView.hideLoading();

			mPView.showMsg(errorMsg);
			isLoading = false;

		}

	};

	class MAPIDataListern implements APIDataListener<ResultMsg>{

		private final int mOrderState;

		public MAPIDataListern(int orderState){
			this.mOrderState = orderState;
		}

		@Override
		public void success(ResultMsg result) {
			if (isStop)return;
			mPView.hideLoading();
			mPView.showMsg(result.getErrorMessage());

			mPView.opreateState(mOrderState,TextUtils.equals("0", result.getError()));
		}

		@Override
		public void failure(String errorMsg) {
			if (isStop) return;
			mPView.hideLoading();
			mPView.showMsg(errorMsg);
		}
	};

	public boolean isLoading() {
		return isLoading;
	}

}
