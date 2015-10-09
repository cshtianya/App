package com.app.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.pay.PayType;
import com.tnw.R;
import com.tnw.entities.DeliveryNode;

public class UIPayCouponView extends LinearLayout implements OnClickListener
	,RadioGroup.OnCheckedChangeListener{

	public static final int RESULTCODE = 20110;

	private RadioGroup rdgPayType;
	private RelativeLayout rlayCoupon;
	private TextView txtCouponName;
	private TextView txtCouponPrice;

	private String couponPrice;
	private String couponId;
	private String payType;

	public String getPayType() {
		return payType;
	}

	public String getCouponPrice() {
		return couponPrice;
	}

	public String getCouponId() {
		return couponId;
	}

	public UIPayCouponView(Context context) {
		this(context, null, 0);
		// TODO Auto-generated constructor stub
	}

	public UIPayCouponView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public UIPayCouponView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		setOrientation(VERTICAL);
	}
	
	public void initView(){
		rdgPayType = (RadioGroup) findViewById(R.id.rdgPayType);
		rlayCoupon = (RelativeLayout) findViewById(R.id.rlayCoupon);
		txtCouponName = (TextView)findViewById(R.id.txtCouponName);
		txtCouponPrice = (TextView)findViewById(R.id.txtCouponPrice);

		rdgPayType.setOnCheckedChangeListener(this);
		rlayCoupon.setOnClickListener(this);

	}

	public void setShowCouponView(boolean isShow){
		rlayCoupon.setVisibility(isShow?VISIBLE:GONE);
	}

	/**
	 * 设置设置优惠券信息
	 * @param info
	 */
	public void setCoupon(DeliveryNode info){
		if(info!=null){
			txtCouponName.setText(info.getItemName());
			txtCouponPrice.setText(info.getItemId());
		}else{
			rlayCoupon.setVisibility(TextUtils.isEmpty(couponId)?View.VISIBLE:View.GONE);
		}
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rlayCoupon:
//			Intent intent = new Intent(this.getContext(),DeliveryActivity.class);
//			intent.putExtra("order", true);
//			intent.putExtra("couponId", couponId);
//			((Activity) this.getContext()).startActivityForResult(intent, RESULTCODE);
			break;
		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup rdo, int vid) {
		// TODO Auto-generated method stub
		switch (vid) {
			case R.id.rdoAlipay:
				payType = PayType.alipay;
				break;
			case R.id.rdoWXPay:
				payType =  PayType.wxpay;
				break;
			default:
				break;
		}
	}
}
