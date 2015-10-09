package com.tnw.controller;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.tnw.MApplication;
import com.tnw.api.APIDataListener;
import com.tnw.api.Base64_Encode_PHP;
import com.tnw.api.action.ActionEnum;
import com.tnw.api.action.DeliveryAction;
import com.tnw.api.action.ProductsAction;
import com.tnw.api.apifig.ApiConfig;
import com.tnw.api.apifig.ApiParma;
import com.tnw.entities.ResultMsg;
import com.tnw.mvp.CollectView;
import com.tnw.mvp.MVPView;

import java.util.HashMap;

/**
 * 收藏 删除
 * * Created by CSH on 2015/7/10 0010.
 */
public class CollectOpreateController extends AbsBaseController{

    private final ProductsAction action;
    private final CollectView mCollectView;
    private final HashMap<String, String> map;

    public CollectOpreateController(CollectView collectView) {
        this.action = (ProductsAction) ActionEnum.getProductsAction.getInstance();
        this.mCollectView = collectView;
        map = new HashMap<>(3);
    }

    public void collectionOperate(boolean isConllect,String productId){
        map.clear();
        map.put(ApiParma.user_id.getKey(), MApplication.getInstance().getUserId());
        map.put(ApiParma.action.getKey(), isConllect?"3":"2");
        map.put(ApiParma.commodity_id.getKey(), productId);
        String param = new Gson().toJson(map);
        if(ApiConfig.IS_DEBUG)
            Log.v("csh", "collectionOperate=======>" + param);
        param = Base64_Encode_PHP.encode(param);

        action.collectionOperate(param, collectListener);
    }

    private APIDataListener<ResultMsg> collectListener = new APIDataListener<ResultMsg>() {
        @Override
        public void success(ResultMsg result) {

            if (isStop)return;

            mCollectView.hideLoading();
            if(TextUtils.equals("1", result.getError())){
                mCollectView.conllectOpreate(true);
            }else{
                mCollectView.conllectOpreate(false);
            }

            mCollectView.showMsg(result.getErrorMessage());

        }

        @Override
        public void failure(String errorMsg) {
            if (isStop) return;
            mCollectView.hideLoading();

            mCollectView.conllectOpreate(false);
            mCollectView.showMsg(errorMsg);
        }
    };

}
