package com.tnw.api.action;

import com.tnw.api.APIDataListener;
import com.tnw.entities.DeliveryNode;
import com.tnw.entities.ResultMsg;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by CSH on 2015/9/21 0021.
 */
public class DeliveryAction extends BaseAction {

    /**
     * 收货地址管理
     * @param param 参数
     * @param l 回调监听
     */
    public void deliveryList(String param,final APIDataListener<List<DeliveryNode>> l){
        dataApi.deliveryList(param, new Callback<List<DeliveryNode>>() {
            @Override
            public void success(List<DeliveryNode> list, Response response) {
                l.success(list);
            }

            @Override
            public void failure(RetrofitError error) {
                l.failure(error.getMessage());
            }
        });

    }

    /**
     * 收货地址添加/修改
     * @param param 参数
     * @param l 回调监听
     */
    public void deliveryEdit(String param,final APIDataListener<ResultMsg> l){

        dataApi.deliveryEdit(param, new Callback<ResultMsg>() {
            @Override
            public void success(ResultMsg userInfo, Response arg1) {
                // TODO Auto-generated method stub
                l.success(userInfo);
            }

            @Override
            public void failure(RetrofitError error) {
                // TODO Auto-generated method stub
                l.failure(error.getMessage());
            }
        });

    }

    /**
     * 收货地址删除
     * @param param 参数
     * @param l 回调监听
     */
    public void deliveryDelete(String param,final APIDataListener<ResultMsg> l){

        dataApi.deliveryDelete(param, new Callback<ResultMsg>() {
            @Override
            public void success(ResultMsg userInfo, Response arg1) {
                // TODO Auto-generated method stub
                l.success(userInfo);
            }

            @Override
            public void failure(RetrofitError error) {
                // TODO Auto-generated method stub
                l.failure(error.getMessage());
            }
        });

    }




}
