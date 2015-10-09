package com.tnw.controller;

import android.annotation.SuppressLint;

import com.tnw.api.APIDataListener;
import com.tnw.api.action.ActionEnum;
import com.tnw.api.action.IndexAction;
import com.tnw.api.apifig.ApiConfig;
import com.tnw.entities.BannerInfo;
import com.tnw.entities.CategoryNode;
import com.tnw.entities.VantagesNode;
import com.tnw.mvp.BannerView;
import com.tnw.mvp.NodeListView;
import com.tnw.utils.AssetUtils;
import com.tnw.utils.GsonTools;

import java.util.List;

@SuppressLint("UseSparseArrays")
public class VantagesController extends BaseController<String>{

//	private final IndexAction action;
    private final NodeListView<List<VantagesNode>> mPView;

//	private final HashMap<String, String> mMap;

	private int mCurentPage = 1;
	private boolean isLoading = false;


	public VantagesController(NodeListView<List<VantagesNode>> nodeListView){
		this.mPView = nodeListView;
//        mMap = new HashMap<>(2);
//        mMap.put(ApiParma.pageSize.getKey(),ApiParma.pageSize.getValue()+"");
//		action = (IndexAction) ActionEnum.getIndexAction.getInstance();
	}

	@Override
	public void excute(String s) {
		// TODO Auto-generated method stub
		
		if(mPView.isListEmpty()){
			mPView.showLoading();
		}else{
			mPView.showActionLabel();
		}

		String jsonStr = AssetUtils.readFile(mPView.getContext(), "vantages.json");
		mPView.showNodeList(GsonTools.getList(jsonStr, VantagesNode.class) ,true);

		mPView.hideLoading();

//		mMap.put(ApiParma.currentPage.getKey(), mCurentPage+"");
//		String param = new Gson().toJson(mMap);
//		if(ApiConfig.IS_DEBUG)
//			Log.v("csh", "getIndexGridList =======>"+param);
//		param = Base64_Encode_PHP.encode(param);
//		action.getIndexGridList("", listener);
	}

	public void onEndListReached(){
		++mCurentPage;
        excute("");
		isLoading = true;
	}
	
//	APIDataListener<List<VantagesNode>> listener = new APIDataListener<List<VantagesNode>>() {
//
//		@Override
//		public void success(List<VantagesNode> result) {
//			// TODO Auto-generated method stub
//			if(isStop)return;
//			if(mPView.isListEmpty()){
//				mPView.showNodeList(result,result.size() < ApiConfig.PAGESIZE);
//				mPView.hideLoading();
//			}else{
//				mPView.appendNodeList(result, result.size() < ApiConfig.PAGESIZE);
//				mPView.hideActionLabel();
//			}
//
//			isLoading = false;
//		}
//
//		@Override
//		public void failure(String errorMsg) {
//			// TODO Auto-generated method stub
//			if(isStop)return;
//			mPView.showMsg(errorMsg);
//			mPView.hideLoading();
//			mPView.hideActionLabel();
//			isLoading = false;
//
//		}
//
//	};

	public boolean isLoading() {
		return isLoading;
	}

}
