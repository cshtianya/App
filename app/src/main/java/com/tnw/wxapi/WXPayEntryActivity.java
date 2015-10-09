package com.tnw.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.app.pay.wxpay.WXConstants;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tnw.R;
import com.tnw.activities.OrderListManageActivity;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
	private IWXAPI msgApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_wxpay_activity);

        msgApi = WXAPIFactory.createWXAPI(this, WXConstants.APP_ID);
		msgApi.handleIntent(getIntent(), this);
    }

    @Override   
	 protected void onNewIntent(Intent intent) {     
	 super.onNewIntent(intent);      
	 	setIntent(intent);       
	   msgApi.handleIntent(intent, this);
	 }
    
	@Override
	public void onReq(BaseReq req) {
		
	}

	@Override
	public void onResp(BaseResp resp) {
		if(resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX){
			if(resp.errCode == BaseResp.ErrCode.ERR_OK){//支付成功
				Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
				OrderListManageActivity.launcher(this, R.id.radoNoDelivery);
			}else{
				Toast.makeText(this, "支付失败"+resp.errCode, Toast.LENGTH_SHORT).show();
				OrderListManageActivity.launcher(this, R.id.radoNoPayment);
				finish();
			}
		}
	}

}