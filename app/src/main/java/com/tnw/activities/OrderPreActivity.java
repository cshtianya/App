package com.tnw.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.app.pay.PayType;
import com.app.pay.alipay.AliPayInfo;
import com.app.pay.alipay.AliPayUtils;
import com.app.pay.wxpay.WXPayInfo;
import com.app.pay.wxpay.WXPayUtils;
import com.app.views.TDeliveryView;
import com.app.views.UIPayCouponView;
import com.app.views.dialog.MProgressDialog;
import com.google.gson.Gson;
import com.tnw.MApplication;
import com.tnw.R;
import com.tnw.adapters.OrderExpAdapter;
import com.tnw.api.apifig.ApiParma;
import com.tnw.controller.OrderOpreateController;
import com.tnw.entities.DeliveryNode;
import com.tnw.entities.OrderDetailInfo;
import com.tnw.entities.OrderPayInfo;
import com.tnw.entities.OrderPreInfo;
import com.tnw.entities.OrderPreInfo.DataEntity;
import com.tnw.entities.OrderPreInfo.ShopListEntity;
import com.tnw.entities.OrderPreInfo.ItemListEntity;
import com.tnw.mvp.OrderView;
import com.tnw.utils.NetUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 
 * @类名称：OrderListActivity.java 
 * @类描述：TODO(订单提交页面) 
 * @创建人：SHChen
 * @E-mail：csh_tianya@163.com 
 * @创建时间：2015-9-22 下午1:59:14
 * @修改人： 
 * @修改时间： 
 * @修改备注：
 * @version 1.0
 */
public class OrderPreActivity extends BaseActivity implements OnClickListener
	,OrderView{

	@Bind(R.id.toolbar)	Toolbar mToolbar;
	@Bind(R.id.expListView) ExpandableListView expListView;
	@Bind(R.id.btnBuyNow) Button btnBuyNow;
	@Bind(R.id.txtTotalPrice) TextView txtTotalPrice;

	private TDeliveryView deliveryView;
	private UIPayCouponView payCouponView;

	private float totalPrice = 0.0f;

	private HashMap<String,ItemListEntity> allMap;
	private OrderExpAdapter adapter;

	private MProgressDialog mPDialog;
	private OrderOpreateController controller;

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ButterKnife.unbind(this);
		controller.stop();
	}

	@Override
	public Context getContext() {return this;}

	public static void laucher(Activity activity,String orderPrame){
		Intent intent  = new Intent(activity, OrderPreActivity.class);
		intent.putExtra("subOrder", orderPrame);
		activity.startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == TDeliveryView.REQUEST_CODE) {
				if (data != null) {
					DeliveryNode info = (DeliveryNode) data.getSerializableExtra("deliveryInfo");
					deliveryView.setAddress(info);
				}
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pre_order);
		ButterKnife.bind(this);

		allMap = new HashMap<>();
		controller = new OrderOpreateController(this);

		mToolbar.setNavigationOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		mPDialog = new MProgressDialog(this);
		mPDialog.setCancelable(true);

		LayoutInflater inflater = LayoutInflater.from(this);
		deliveryView = (TDeliveryView) inflater.inflate(
				R.layout.order_delivery_address, null);
		deliveryView.initView(this);
		payCouponView = (UIPayCouponView) inflater.inflate(
				R.layout.order_pay_type_coupon, null);
		payCouponView.initView();
		payCouponView.setShowCouponView(false);
		expListView.addHeaderView(deliveryView, null, false);
		expListView.addFooterView(payCouponView, null, false);

		btnBuyNow.setOnClickListener(this);

		adapter = new OrderExpAdapter(this);
		expListView.setAdapter(adapter);
		adapter.setOnExpandListener(new OrderExpAdapter.OnExpandListener() {
			@Override
			public void onExpanded(ShopListEntity pInfo, boolean isExpanded, int groupPosition) {
				if (isExpanded)
					expListView.collapseGroup(groupPosition);
				else {
					expListView.expandGroup(groupPosition);
					expListView.setSelectedGroup(groupPosition);
				}
			}
		});

		btnBuyNow.setEnabled(false);

		if(NetUtils.isNetworkAvailable(this)){
			controller.excute(getIntent().getStringExtra("subOrder"));
		}else{
			showTost(R.string.netNotAvailable);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnBuyNow:
			if(TextUtils.isEmpty(deliveryView.getDeliveryId())){
				showTost(R.string.c_click_select_address);
			}else if(TextUtils.isEmpty(payCouponView.getPayType())){
				showTost(R.string.order_payway);
			}else if(allMap.size()==0){
				showTost(R.string.order_no_order);
			}else{
				if (NetUtils.isNetworkAvailable(this)) {
					mPDialog.show();
					mPDialog.setCancelable(false);
					//支付
					controller.excutePayOrder(createOrderParam(),true);
				} else {
					showTost(R.string.netNotAvailable);
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void showPreOrder(OrderPreInfo result) {
		try {
			if (TextUtils.equals("0",result.getError())) {

				btnBuyNow.setEnabled(true);

				DataEntity itemInfo = result.getData();

				deliveryView.setAddress(itemInfo.getDelivery());
				List<ShopListEntity> list = itemInfo.getShopList();
				adapter.setList(list, false);

				if (adapter.getGroupCount() > 0) {
					expListView.expandGroup(0);

					for (int i = 0; i < list.size(); i++) {
						ShopListEntity pInfo = list.get(i);

						for (int j = 0; j < pInfo.getItemList().size(); j++) {
							ItemListEntity cInfo = pInfo.getItemList().get(j);
							totalPrice += adapter.itemPrice(
									cInfo.getItemPrice(),
									cInfo.getItemStock());
							allMap.put(cInfo.getItemId(), cInfo);
						}
					}
					txtTotalPrice.setText(String.format("%.2f", totalPrice));
				}

			} else {
				showTost(result.getErrorMessage());
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public void excutePayOrder(OrderPayInfo.PayInfo pInfo) {
		if(TextUtils.equals(payCouponView.getPayType(),PayType.alipay)){
			new AliPayUtils().excutePay(this
					, new AliPayInfo(pInfo.getOrderId()
					, pInfo.getOrderTitle()
					, pInfo.getOrderContent()
					, pInfo.getOrderPrice()
					, pInfo.getNotifyUrl()));
		}else if(TextUtils.equals(payCouponView.getPayType(),PayType.wxpay)){
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

	}

	@Override
	public void opreateState(int orderState, boolean isSuccessed) {
		btnBuyNow.setEnabled(true);
	}

	@Override
	public void showMsg(String msg) {
		showTost(msg);
	}

	@Override
	public void showLoading() {
		mPDialog.show();
	}

	@Override
	public void hideLoading() {
		mPDialog.cancel();
	}


	private String createOrderParam() {
		HashMap<String,Object> map = new HashMap<>(7);
		ArrayList<HashMap<String,String>> list = new ArrayList<>(allMap.size());

		for (Iterator<String> it = allMap.keySet().iterator(); it.hasNext();) {
			ItemListEntity info = allMap.get(it.next());

			HashMap<String ,String> itemMap = new HashMap<>(2);
			itemMap.put("id",info.getItemId());
			itemMap.put("stock",info.getItemStock());

			list.add(itemMap);
		}

		map.put(ApiParma.userId.getKey(), MApplication.getInstance().getUserId());
		map.put(ApiParma.deliveryId.getKey(), deliveryView.getDeliveryId());
		map.put(ApiParma.payType.getKey(),payCouponView.getPayType());
		map.put(ApiParma.remark.getKey(),"");    //送货时间
		map.put(ApiParma.invoiceId.getKey(),"");
		map.put(ApiParma.commodityList.getKey(), list);
		String param = new Gson().toJson(map);
		return param;

	}

	
}
