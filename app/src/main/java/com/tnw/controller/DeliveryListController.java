package com.tnw.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.tnw.MApplication;
import com.tnw.api.APIDataListener;
import com.tnw.api.Base64_Encode_PHP;
import com.tnw.api.action.ActionEnum;
import com.tnw.api.action.DeliveryAction;
import com.tnw.api.apifig.ApiConfig;
import com.tnw.api.apifig.ApiParma;
import com.tnw.entities.DeliveryNode;
import com.tnw.mvp.NodeListView;

import java.util.HashMap;
import java.util.List;

public class DeliveryListController extends BaseController<String>{

	private final DeliveryAction action;
    private final NodeListView<List<DeliveryNode>> mPView;

	private final HashMap<String, String> mMap;

	private int mCurentPage = 1;
	private boolean isLoading = false;


	public DeliveryListController(NodeListView<List<DeliveryNode>> l){
		this.mPView = l;
		mMap = new HashMap<>(3);

		action = (DeliveryAction) ActionEnum.getDeliveryAction.getInstance();
	}

	@Override
	public void excute(String str) {
		// TODO Auto-generated method stub

		if(mPView.isListEmpty()){
			mPView.showLoading();
		}else{
			mPView.showActionLabel();
		}

		mMap.put(ApiParma.userId.getKey(), MApplication.getInstance().getUserId());
		mMap.put(ApiParma.pageSize.getKey(), ApiParma.pageSize.getValue());
		mMap.put(ApiParma.currentPage.getKey(), mCurentPage+"");
		
		String param = new Gson().toJson(mMap);
		if(ApiConfig.IS_DEBUG)
			Log.v("csh", "deliveryList =======>"+param);

		param = Base64_Encode_PHP.encode(param);
		
		action.deliveryList(param, listener);
		
	}

	public void onEndListReached(){
		++mCurentPage;
        excute("");
		isLoading = true;
	}
	
	private APIDataListener<List<DeliveryNode>> listener = new APIDataListener<List<DeliveryNode>>() {

		@Override
		public void success(List<DeliveryNode> result) {
			// TODO Auto-generated method stub

			if(isStop)return;

			if(mPView.isListEmpty()){
				mPView.showNodeList(result,result.size()<ApiConfig.PAGESIZE);
				mPView.hideLoading();
			}else{
				if(mCurentPage>1){
					mPView.appendNodeList(result,result.size()<ApiConfig.PAGESIZE);
				}else{
					mPView.showNodeList(result,result.size()<ApiConfig.PAGESIZE);
				}

				mPView.hideActionLabel();
			}
			
			isLoading = false;
		}

		@Override
		public void failure(String errorMsg) {
			// TODO Auto-generated method stub
			if(isStop)return;
			mPView.showMsg(errorMsg);
			mPView.hideLoading();
			mPView.hideActionLabel();
			isLoading = false;
			
		}
		
	};

	public boolean isLoading() {
		return isLoading;
	}


}
