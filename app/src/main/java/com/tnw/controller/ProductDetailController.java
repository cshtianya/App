package com.tnw.controller;

import android.text.TextUtils;
import android.util.Log;

import com.tnw.MApplication;
import com.tnw.api.APIDataListener;
import com.tnw.api.Base64_Encode_PHP;
import com.tnw.api.action.ActionEnum;
import com.tnw.api.action.ProductsAction;
import com.tnw.api.apifig.ApiConfig;
import com.tnw.api.apifig.ApiParma;
import com.tnw.entities.ProductDetial;
import com.tnw.entities.ResultMsg;
import com.tnw.mvp.CollectView;
import com.tnw.mvp.ProductDetailView;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by CSH on 2015/7/10 0010.
 */
public class ProductDetailController extends BaseController<String>{

    private final ProductsAction action;
    private final ProductDetailView mView;

    private final HashMap<String, String> map;

    public ProductDetailController(ProductDetailView productDetailView) {
        this.action = (ProductsAction) ActionEnum.getProductsAction.getInstance();
        this.mView = productDetailView;
        map = new HashMap(4);
    }


    @Override
    public void excute(String str) {
        mView.showLoading();
        map.clear();
        map.put(ApiParma.userId.getKey(), MApplication.getInstance().getUserId());
        map.put(ApiParma.commodityId.getKey(), str);
        String param = new Gson().toJson(map);
        if(ApiConfig.IS_DEBUG)
            Log.v("csh", "ProductDetailController=======>" + param);
        param = Base64_Encode_PHP.encode(param);

        action.getProductDetial(param,listener);

    }


    public void addCart(String productId,String attrFristId){
        mView.showLoading();
        map.clear();
        map.put(ApiParma.userId.getKey(), MApplication.getInstance().getUserId());
        map.put(ApiParma.firstId.getKey(), attrFristId);
        map.put(ApiParma.commodityId.getKey(), productId);
        map.put(ApiParma.secondId.getKey(), "");
        map.put(ApiParma.stock.getKey(), "1");

        String param = new Gson().toJson(map);
        if(ApiConfig.IS_DEBUG)
            Log.v("csh", "addCart=======>" + param);
        param = Base64_Encode_PHP.encode(param);

        action.cartEdit(param, addCarttListener);
    }

    private APIDataListener<ProductDetial> listener = new APIDataListener<ProductDetial>() {
        @Override
        public void success(ProductDetial result) {
            if (isStop)return;
            mView.showProduct(result);
            mView.hideLoading();
        }

        @Override
        public void failure(String errorMsg) {
            if (isStop)return;
            mView.showMsg(errorMsg);
            mView.hideLoading();
        }
    };

    private APIDataListener<ResultMsg> addCarttListener = new APIDataListener<ResultMsg>() {
        @Override
        public void success(ResultMsg result) {
           if (isStop)return;
            mView.showMsg(result.getErrorMessage());
            mView.hideLoading();
        }

        @Override
        public void failure(String errorMsg) {
            if (isStop) return;
            mView.showMsg(errorMsg);
            mView.hideLoading();
        }
    };

}
