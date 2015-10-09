package com.tnw.api.action;

import com.tnw.OrderState;
import com.tnw.api.APIDataListener;
import com.tnw.entities.OrderDetailInfo;
import com.tnw.entities.OrderNode;
import com.tnw.entities.OrderPayInfo;
import com.tnw.entities.OrderPreInfo;
import com.tnw.entities.ResultMsg;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by CSH on 2015/9/22 0022.
 */
public class OrderAction extends BaseAction{


    /**
     * 订单检测
     * @param param 参数
     * @param l 回调监听
     */
    public void checkOrder(String param,final APIDataListener<OrderPreInfo> l){

        dataApi.checkOrder(param, new Callback<OrderPreInfo>() {
            @Override
            public void success(OrderPreInfo orderInfo, Response arg1) {
                // TODO Auto-generated method stub
                l.success(orderInfo);
            }

            @Override
            public void failure(RetrofitError error) {
                // TODO Auto-generated method stub
                l.failure(error.getMessage());
            }
        });

    }

    /**
     * 生成订单
     * @param param 参数
     * @param l 回调监听
     */
    public void addOrder(String param,final APIDataListener<OrderPayInfo> l){

        dataApi.addOrder(param, new Callback<OrderPayInfo>() {
            @Override
            public void success(OrderPayInfo orderInfo, Response arg1) {
                // TODO Auto-generated method stub
                l.success(orderInfo);
            }

            @Override
            public void failure(RetrofitError error) {
                // TODO Auto-generated method stub
                l.failure(error.getMessage());
            }
        });

    }

    /**
     * 直接付款未支付订单(买家订单付款)
     * @param param 参数
     * @param l 回调监听
     */
    public void buyerPaymentOrder(String param,final APIDataListener<OrderPayInfo> l){

        dataApi.buyerPaymentOrder(param, new Callback<OrderPayInfo>() {
            @Override
            public void success(OrderPayInfo orderInfo, Response arg1) {
                // TODO Auto-generated method stub
                l.success(orderInfo);
            }

            @Override
            public void failure(RetrofitError error) {
                // TODO Auto-generated method stub
                l.failure(error.getMessage());
            }
        });

    }

    /**
     * 订单列表
     * @param param 参数
     * @param l 回调监听
     */
    public void buyerOrderList(String param,final APIDataListener<List<OrderNode>> l){

        dataApi.buyerOrderList(param, new Callback<List<OrderNode>>() {
            @Override
            public void success(List<OrderNode> orderInfo, Response arg1) {
                // TODO Auto-generated method stub
                l.success(orderInfo);
            }

            @Override
            public void failure(RetrofitError error) {
                // TODO Auto-generated method stub
                l.failure(error.getMessage());
            }
        });

    }


    /**
     * 订单详情
     * @param param 参数
     * @param l 回调监听
     */
    public void buyerOrderDetail(String param,final APIDataListener<OrderDetailInfo> l){

        dataApi.buyerOrderDetail(param, new Callback<OrderDetailInfo>() {
            @Override
            public void success(OrderDetailInfo orderInfo, Response arg1) {
                // TODO Auto-generated method stub
                l.success(orderInfo);
            }

            @Override
            public void failure(RetrofitError error) {
                // TODO Auto-generated method stub
                l.failure(error.getMessage());
            }
        });

    }

    /**
     * 删除订单、订单发货提醒、买家订单确认收货
     * @param param 参数
     * @param l 回调监听
     */
    public void orderOperate(int orderState,String param,final APIDataListener<ResultMsg> l){
        if(orderState == OrderState.WAIT_PAY){//取消订单
            dataApi.cancelOrder(param, new MCallback(l));
        }else if(orderState == OrderState.PAID){//提醒发货
            dataApi.remindOrder(param, new MCallback(l));
        }else if(orderState == OrderState.DELIVERED){//确认收货
            dataApi.confirmAcciptOrder(param, new MCallback(l));
        }

    }

    class MCallback implements Callback<ResultMsg>{

        final APIDataListener<ResultMsg> l;

        public MCallback(APIDataListener<ResultMsg> listener){
            this.l = listener;
        }

        @Override
        public void success(ResultMsg resultMsg, Response response) {
            l.success(resultMsg);
        }

        @Override
        public void failure(RetrofitError error) {
            l.failure(error.getMessage());
        }
    };

}
