package com.tnw.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.pay.PayType;
import com.app.pay.alipay.AliPayInfo;
import com.app.pay.alipay.AliPayUtils;
import com.app.pay.wxpay.WXPayInfo;
import com.app.pay.wxpay.WXPayUtils;
import com.app.views.IphoneTreeView;
import com.app.views.dialog.MProgressDialog;
import com.google.gson.Gson;
import com.tnw.MApplication;
import com.tnw.OrderState;
import com.tnw.R;
import com.tnw.activities.OrderDetialActivity;
import com.tnw.adapters.OWaitPayAdapter;
import com.tnw.adapters.OWaitPayAdapter.OnExpGroupClickListener;
import com.tnw.api.apifig.ApiParma;
import com.tnw.controller.OrderOpreateController;
import com.tnw.controller.OrderListController;
import com.tnw.entities.OrderDetailInfo;
import com.tnw.entities.OrderNode;
import com.tnw.entities.OrderPayInfo;
import com.tnw.entities.OrderPreInfo;
import com.tnw.mvp.NodeListView;
import com.tnw.mvp.OrderView;
import com.tnw.utils.NetUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 订单-待付款
 */
public class OrderNoPaymentFragment extends BaseFragment implements
        NodeListView<List<OrderNode>>,OrderView{

    @Bind(R.id.expListView)    IphoneTreeView expListView;
    private OWaitPayAdapter adapter;

    private OrderListController controller;
    private OrderOpreateController orderController;

    private String payType;

    private MProgressDialog mPDialog;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        controller.stop();
        orderController.stop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == AliPayUtils.ORDER_PAY_REQUEST_CODE) {
                controller.excute(OrderState.WAIT_PAY+"");
            }
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View baseView = inflater.inflate(R.layout.app_explistview,container,false);
        ButterKnife.bind(this, baseView);

        mPDialog = new MProgressDialog(this.getActivity());
        adapter = new OWaitPayAdapter(this.getActivity(),OrderState.WAIT_PAY);
        expListView.setHeaderView(inflater.inflate(R.layout.o_item_nav_layout, expListView, false));
        expListView.setAdapter(adapter);

        orderController = new OrderOpreateController(this);
        controller = new OrderListController(this);
        if(NetUtils.isNetworkAvailable(this.getActivity())){
            controller.excute("0");
        }else{
            showToast(getActivity(),R.string.netNotAvailable);
        }

        adapter.setOnExpGroupClickListener(new OnExpGroupClickListener() {
            @Override
            public void onGroupClick(OrderNode pInfo, boolean isOpreate) {
                // TODO Auto-generated method stub
                if(pInfo!= null)
                if (isOpreate) {
                    if (NetUtils.isNetworkAvailable(getActivity())) {
                        payType = pInfo.getPayType();
                        mPDialog.show();
                        mPDialog.setCancelable(false);
                        orderController.excutePayOrder(createOrderParam(pInfo.getOrderId()), false);
                    } else {
                        showToast(getActivity(), R.string.netNotAvailable);
                    }
                } else {
                    OrderDetialActivity.launcher(OrderNoPaymentFragment.this
                            , pInfo.getOrderId()
                            , pInfo.getPayType()
                            , OrderState.WAIT_PAY
                            , AliPayUtils.ORDER_PAY_REQUEST_CODE);
                }
            }
        });

        return baseView;
    }

    private String createOrderParam(String orderId){
        HashMap<String,String> map = new HashMap<>(2);
        map.put(ApiParma.userId.getKey(), MApplication.getInstance().getUserId());
        map.put(ApiParma.orderId.getKey(), orderId);
        String param = new Gson().toJson(map);
        return param;
    }

    @Override
    public void showNodeList(List<OrderNode> orderNodes, boolean isEnd) {
        adapter.setList(orderNodes, true);
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            expListView.expandGroup(i);
        }
    }

    @Override
    public void appendNodeList(List<OrderNode> orderNodes, boolean isEnd) {
        adapter.appendList(orderNodes, true);
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            expListView.expandGroup(i);
        }
    }

    @Override
    public boolean isListEmpty() {
        return adapter.getList().isEmpty();
    }

    @Override
    public void showActionLabel() {

    }

    @Override
    public void hideActionLabel() {

    }

    @Override
    public Context getContext() {
        return this.getActivity();
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
    public void opreateState(int orderState, boolean isSuccessed) {

    }

    @Override
    public void showMsg(String msg) {
        showToast(getActivity(),msg);
    }

    @Override
    public void showPreOrder(OrderPreInfo pInfo) {

    }

    @Override
    public void excutePayOrder(OrderPayInfo.PayInfo pInfo) {
        if(TextUtils.equals(payType, PayType.alipay)){
            new AliPayUtils().excutePay(this.getActivity()
                    , new AliPayInfo(pInfo.getOrderId()
                    , pInfo.getOrderTitle()
                    , pInfo.getOrderContent()
                    , pInfo.getOrderPrice()
                    , pInfo.getNotifyUrl()));
        }else if(TextUtils.equals(payType,PayType.wxpay)){
            new WXPayUtils(this.getActivity())
                    .executePay(new WXPayInfo(pInfo.getOrderId()
                            , pInfo.getOrderTitle()
                            , pInfo.getOrderContent()
                            , pInfo.getOrderPrice()
                            , pInfo.getNotifyUrl()));
        }
    }

    @Override
    public void showOrderDetail(OrderDetailInfo info) {

    }


}
