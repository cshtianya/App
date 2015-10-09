package com.tnw.controller;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.tnw.MApplication;
import com.tnw.api.APIDataListener;
import com.tnw.api.Base64_Encode_PHP;
import com.tnw.api.action.ActionEnum;
import com.tnw.api.action.DeliveryAction;
import com.tnw.api.apifig.ApiConfig;
import com.tnw.api.apifig.ApiParma;
import com.tnw.entities.ResultMsg;
import com.tnw.mvp.MVPView;

import java.util.HashMap;

/**
 * Created by CSH on 2015/7/10 0010.
 */
public class DeliveryOpreateController extends AbsBaseController{

    private final DeliveryAction action;
    private final MVPView mView;

    private final HashMap<String, String> map;

    public DeliveryOpreateController(MVPView mvpView) {
        this.action = (DeliveryAction) ActionEnum.getDeliveryAction.getInstance();
        this.mView = mvpView;
        map = new HashMap<>();
    }

    public void createDelivery(String deliveryId,String province,String city
            ,String area,String address,String name,String mobile,String zipCode,String isDefault){
        mView.showLoading();

        map.clear();
        map.put(ApiParma.userId.getKey(), MApplication.getInstance().getUserId());
        if(!TextUtils.isEmpty(deliveryId)){
            map.put(ApiParma.deliveryId.getKey(), deliveryId);
        }
        map.put("name", name);
        map.put("province", province);
        map.put("city", city);
        map.put("area", area);
        map.put("address", address);
        map.put("telephone", "");
        map.put("mobile", mobile);
        map.put("zipcode", zipCode);
        map.put("isDefault", isDefault);

        String param = new Gson().toJson(map);
        if(ApiConfig.IS_DEBUG)
            Log.v("csh", "addCart=======>" + param);
        param = Base64_Encode_PHP.encode(param);

        action.deliveryEdit(param, opreateListener);

    }

    public void deliveryDelete(String deliveryId){
        mView.showLoading();

        map.clear();
        map.put(ApiParma.userId.getKey(), MApplication.getInstance().getUserId());
        map.put(ApiParma.deliveryId.getKey(), deliveryId);
        String param = new Gson().toJson(map);
        if(ApiConfig.IS_DEBUG)
            Log.v("csh", "addCart=======>" + param);
        param = Base64_Encode_PHP.encode(param);

        action.deliveryDelete(param, opreateListener);

    }


    private APIDataListener<ResultMsg> opreateListener = new APIDataListener<ResultMsg>() {
        @Override
        public void success(ResultMsg result) {
           if (isStop)return;
            mView.hideLoading();
            if(TextUtils.equals("0",result.getError())){
                mView.showMsg(result.getError());
            }else{
                mView.showMsg(result.getErrorMessage());
            }
        }

        @Override
        public void failure(String errorMsg) {
            if (isStop) return;
            mView.showMsg(errorMsg);
            mView.hideLoading();
        }
    };

}
