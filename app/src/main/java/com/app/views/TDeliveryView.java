package com.app.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tnw.R;
import com.tnw.activities.DeliveryActivity;
import com.tnw.entities.DeliveryNode;

public class TDeliveryView extends LinearLayout implements OnClickListener {

	public static final int REQUEST_CODE = 20010;
	
	private RelativeLayout rlayDeliveryAddress;
	private TextView txtRecipient;
	private TextView txtPhoneNum;
	private TextView txtTip,txtGo;
	private TextView txtAddress;

	private Activity mActivity;

	private String deliveryId;
	public String getDeliveryId() {
		return deliveryId;
	}

	public TDeliveryView(Context context) {
		this(context, null, 0);
		// TODO Auto-generated constructor stub
	}
	
	public TDeliveryView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}
	
	public TDeliveryView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	public void initView(Activity activity){

		this.mActivity = activity;

		rlayDeliveryAddress = (RelativeLayout)findViewById(R.id.rlayDeliveryAddress);
		txtRecipient = (TextView)findViewById(R.id.txtRecipient);
		txtPhoneNum = (TextView)findViewById(R.id.txtPhoneNum);
		txtTip = (TextView)findViewById(R.id.txtTip);
		txtAddress = (TextView)findViewById(R.id.txtAddress);
		txtGo = (TextView)findViewById(R.id.txtGo);

		rlayDeliveryAddress.setOnClickListener(this);
		txtTip.setOnClickListener(this);

	}


	public void setClickEnable(boolean isEnable){
		txtTip.setEnabled(isEnable);
		rlayDeliveryAddress.setEnabled(isEnable);
		txtGo.setVisibility(isEnable?VISIBLE:INVISIBLE);
	}

	/**
	 * 设置收货地址信息
	 * @param info
	 */
	public void setAddress(DeliveryNode info){
		if(info!=null){
			this.deliveryId = info.getItemId();
			txtTip.setVisibility(GONE);

			txtRecipient.setText(info.getItemName());
			txtPhoneNum.setText(info.getItemPhone());
			txtAddress.setText(
					info.getItemProvince() + " " +
					info.getItemCity() + " " +
					info.getItemArea() + " " +
					info.getItemAddress());

		}else{
			txtTip.setVisibility(TextUtils.isEmpty(deliveryId)?View.VISIBLE:View.GONE);
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.txtTip:
				rlayDeliveryAddress.performClick();
				break;
			case R.id.rlayDeliveryAddress:
				DeliveryActivity.launcher(mActivity,true,REQUEST_CODE);
				break;
			default:
				break;
		}
	}

}
