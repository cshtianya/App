package com.app.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.pay.alipay.AliPayUtils;
import com.tnw.R;
import com.tnw.entities.OrderDetailInfo;

public class UIOrderView extends RelativeLayout {
	
	private TextView txtOrderState;
	private TextView txtOrderNo;
	private TextView txtPayNo;
	private TextView txtCreateTime;
	
	
	public UIOrderView(Context context) {
		this(context, null, 0);
		// TODO Auto-generated constructor stub
	}
	
	public UIOrderView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}
	
	public UIOrderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	public void initView(){
		txtOrderState = (TextView)findViewById(R.id.txtOrderState);
		txtOrderNo = (TextView)findViewById(R.id.txtOrderNo);
		txtPayNo = (TextView)findViewById(R.id.txtPayNo);
		txtCreateTime = (TextView)findViewById(R.id.txtCreateTime);
	}
	
	/**
	 * 设置收货地址信息
	 * @param info
	 */
	public void setOrderInfo(OrderDetailInfo info){
		if(info!=null){
			txtOrderNo.setText(getString(R.string.oo_order_no)
					+info.getOrderId());
			txtCreateTime.setText(getString(R.string.oo_create_time)
					+info.getOrderCreateTime());
			if(TextUtils.isEmpty(info.getOrderPayNo())){
				txtPayNo.setVisibility(View.GONE);
			}else{
				txtPayNo.setText(getString(R.string.oo_pay_no)
						+info.getOrderPayNo());
			}
			int orderState = Integer.valueOf(info.getOrderStatus());
			txtOrderState.setText(AliPayUtils.orderState.get(orderState));
			
		}
	}

	private String getString(int rId){
		return getResources().getString(rId);
	}
	
}
