package com.tnw.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.app.pay.PayType;
import com.app.pay.alipay.AliPayInfo;
import com.app.pay.alipay.AliPayUtils;
import com.app.pay.wxpay.WXPayInfo;
import com.app.pay.wxpay.WXPayUtils;
import com.app.views.TDeliveryView;
import com.app.views.UIOrderView;
import com.app.views.dialog.MProgressDialog;
import com.google.gson.Gson;
import com.tnw.MApplication;
import com.tnw.OrderState;
import com.tnw.R;
import com.tnw.adapters.OrderDetialsAdapter;
import com.tnw.api.apifig.ApiParma;
import com.tnw.controller.OrderOpreateController;
import com.tnw.entities.OrderDetailInfo;
import com.tnw.entities.OrderNodeItem;
import com.tnw.entities.OrderPayInfo;
import com.tnw.entities.OrderPreInfo;
import com.tnw.mvp.OrderView;
import com.tnw.utils.NetUtils;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CSH on 2015/9/24 0024.
 * ,OrderDetialsAdapter.OnItemClickListener
 */
public class OrderDetialActivity extends BaseActivity implements View.OnClickListener
        ,OrderView{

    @Bind(R.id.toolbar)    Toolbar mToolbar;
    @Bind(R.id.bottomToolbar) Toolbar bottomToolbar;
    @Bind(R.id.appListView)    ListView appListView;
    @Bind(R.id.txtCancel)    TextView txtCancel;
    @Bind(R.id.btnBuyNow)    Button btnBuyNow;
    @Bind(R.id.txtTotalPrice)    TextView txtTotalPrice;

    private MProgressDialog mPDialog;
    private TDeliveryView deliveryView;
    private UIOrderView orderView;

    private OrderOpreateController controller;
    private OrderDetialsAdapter adapter;

    private String orderParame;
    private String orderId;
    private int orderState;
    private String payType;

    public static void launcher(Fragment fragment,String orderId,String payType
            ,int orderState,int requestCode){
        Intent intent = new Intent(fragment.getActivity(),OrderDetialActivity.class);
        intent.putExtra("orderId",orderId);
        intent.putExtra("orderState",orderState);
        intent.putExtra("payType",payType);
        fragment.startActivityForResult(intent,requestCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        controller.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detial);
        ButterKnife.bind(this);

        orderState = getIntent().getIntExtra("orderState", -1);
        orderId = getIntent().getStringExtra("orderId");
        payType = getIntent().getStringExtra("payType");

        mPDialog = new MProgressDialog(this);
        mPDialog.setCancelable(false);

        HashMap<String,String> map = new HashMap<>(2);
        map.put(ApiParma.userId.getKey(), MApplication.getInstance().getUserId());
        map.put(ApiParma.orderId.getKey(), orderId);
        orderParame = new Gson().toJson(map);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnBuyNow.setText(R.string.order_pw_payment);
        btnBuyNow.setOnClickListener(this);
        txtCancel.setOnClickListener(this);
        txtCancel.setVisibility(View.GONE);

        bottomToolbar.setVisibility(orderState==0?View.VISIBLE:View.GONE);

        LayoutInflater inflater = LayoutInflater.from(this);
        deliveryView = (TDeliveryView) inflater.inflate(R.layout.order_delivery_address, null);
        deliveryView.initView(this);
        deliveryView.setClickEnable(false);
        appListView.addHeaderView(deliveryView, null, false);
        View navView = inflater.inflate(R.layout.mlayout_order_nav_item, null);
        navView.findViewById(R.id.btnOpreate).setVisibility(View.GONE);
        appListView.addHeaderView(navView, null, false);

        orderView = (UIOrderView)inflater.inflate(R.layout.mlayout_order_info_view, null);
        orderView.initView();
        appListView.addFooterView(orderView);
        adapter = new OrderDetialsAdapter(this,orderState);
        appListView.setAdapter(adapter);

        controller = new OrderOpreateController(this);
        if(NetUtils.isNetworkAvailable(this)){
            controller.showOrderDetail(orderParame);
        }else{
            showTost(R.string.netNotAvailable);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txtCancel:{
               switch (orderState){
                   case OrderState.WAIT_PAY://取消订单
                   case OrderState.PAID://提醒发货
                   case OrderState.DELIVERED://确认收货-->跳转到完成
                       if(NetUtils.isNetworkAvailable(this)){
                           controller.opreateOrder(orderState, orderParame);
                       }else{
                           showTost(R.string.netNotAvailable);
                       }
                       break;
                   case OrderState.COMPLETED://查看物流-->跳转到物流页面

                       break;
               }
            }
                break;
            case R.id.btnBuyNow:
                btnBuyNow.setEnabled(false);
                if(NetUtils.isNetworkAvailable(this)){
                    controller.excutePayOrder(orderParame, false);
                }else{
                    showTost(R.string.netNotAvailable);
                }
                break;
        }
    }

//    @Override
//    public void onItemClick(OrderNodeItem pInfo, OrderDetialsAdapter.Operate operate) {
//        // TODO Auto-generated method stub
//        switch (operate) {
//            case DETAILS:
//                intent.setClass(OrderDetailsActivity.this
//                        ,GNDetailActivity.class);
//                intent.putExtra("goodsId", pInfo.getItemId());
//
//                startActivity(intent);
//                break;
//            case RETURN:
//                intent.setClass(OrderDetailsActivity.this
//                        ,OrderReturnActivity.class);
//                intent.putExtra("orderId", orderId);
//                intent.putExtra("itemInfo", pInfo);
//                intent.putExtra("returnType", "2");
//
//                startActivityForResult(intent, AliPayUtils.ORDER_PAY_REQUEST_CODE);
//                break;
//            case COMMENTS:
//                intent.setClass(OrderDetailsActivity.this
//                        ,PCommentsActivity.class);
//                intent.putExtra("orderId", orderId);
//                intent.putExtra("itemInfo", pInfo);
//
//                startActivityForResult(intent, AliPayUtils.ORDER_PAY_REQUEST_CODE);
//                break;
//            case REFUND:
//                intent.setClass(OrderDetailsActivity.this
//                        ,OrderReturnActivity.class);
//                intent.putExtra("orderId", orderId);
//                intent.putExtra("itemInfo", pInfo);
//                intent.putExtra("returnType", "1");
//
//                startActivityForResult(intent, AliPayUtils.ORDER_PAY_REQUEST_CODE);
//                break;
//            default:
//                break;
//        }
//    }

    @Override
    public void showPreOrder(OrderPreInfo pInfo) {

    }

    @Override
    public void excutePayOrder(OrderPayInfo.PayInfo pInfo) {

        if(TextUtils.equals(payType, PayType.alipay)){
            new AliPayUtils().excutePay(this
                    , new AliPayInfo(pInfo.getOrderId()
                    , pInfo.getOrderTitle()
                    , pInfo.getOrderContent()
                    , pInfo.getOrderPrice()
                    , pInfo.getNotifyUrl()));
        }else if(TextUtils.equals(payType,PayType.wxpay)){
            new WXPayUtils(this)
                    .executePay(new WXPayInfo(pInfo.getOrderId()
                            , pInfo.getOrderTitle()
                            , pInfo.getOrderContent()
                            , pInfo.getOrderPrice()
                            , pInfo.getNotifyUrl()));
        }
    }

    @Override
    public void showOrderDetail(OrderDetailInfo info) {

        try{

            txtCancel.setVisibility(View.VISIBLE);

            deliveryView.setAddress(info.getDelivery());
            orderView.setOrderInfo(info);

            adapter.setShowReturn(info.getReturnInfo() == null);
            OrderDetailInfo.OrderShopInfo shopInfo = info.getShopList();
            adapter.setList(shopInfo.getItemList(),true);

            if(orderState == OrderState.WAIT_PAY){
                txtCancel.setText("取消订单");
            }else if(orderState == OrderState.PAID){
                txtCancel.setText("提醒发货");
            }else if(orderState == OrderState.DELIVERED){
                txtCancel.setText("确认收货");
            }else if(orderState == OrderState.COMPLETED){
                txtCancel.setText("查物流");
            }

            txtTotalPrice.setText(info.getOrderPrice());

        }catch (Exception e){

        }

    }

    @Override
    public void opreateState(int orderState,boolean isSuccessed) {
        switch (orderState){
            case OrderState.DELIVERED://确认收货-->跳转到完成

                break;
            case OrderState.WAIT_PAY://取消订单
                setResult(RESULT_OK);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showLoading() {
        mPDialog.show();
    }

    @Override
    public void hideLoading() {
        mPDialog.hide();
    }

    @Override
    public void showMsg(String msg) {
        showTost(msg);
        mPDialog.show();
    }


}
