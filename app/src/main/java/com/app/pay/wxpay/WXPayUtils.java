package com.app.pay.wxpay;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.app.ArithUtil;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tnw.api.apifig.ApiConfig;

public class WXPayUtils {
	
	private static final String TAG = "wxpay";
	private static final boolean DEBUG = true;
	
	private IWXAPI msgApi;
	private PayReq req;
	
	private Activity mActivity;
	
	
	public WXPayUtils(Activity activity){
		this.mActivity = activity;
		
		msgApi = WXAPIFactory.createWXAPI(activity, null);
		msgApi.registerApp(WXConstants.APP_ID);
		
		req = new PayReq();
	}
	
	/**
	 * 调用支付
	 */
	public void executePay(WXPayInfo ifno){
		if(msgApi.isWXAppInstalled()&& msgApi.isWXAppSupportAPI()){
			new GetPrepayIdTask().execute(ifno);
		}else{
			Toast.makeText(mActivity, "您的手机未安装微信客户端", Toast.LENGTH_LONG).show();
		}
	}
	
	private class GetPrepayIdTask extends AsyncTask<WXPayInfo, Void, Map<String,String>> {

		private ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(mActivity, "微信支付", "请稍等，正在加载......");
		}

		@Override
		protected Map<String,String> doInBackground(WXPayInfo... params) {

			String url = String.format(WXConstants.URL_PREPAY_ID);
			String entity = genProductArgs(params[0]);
			
			byte[] buf = WXUtils.httpPost(url, entity);
			String content = new String(buf);
			
			if(DEBUG) Log.i(TAG, content);
			
			Map<String,String> xml=decodeXml(content);
			return xml;
		}
		
		@Override
		protected void onPostExecute(Map<String,String> result) {
			if (dialog != null) {
				dialog.dismiss();
			}
			
			if(result!=null&& result.size()>0){
				if(result.get("return_code").equals("SUCCESS")){
					if(result.get("result_code").equals("FAIL")){
						Toast.makeText(mActivity, result.get("err_code_des"), Toast.LENGTH_LONG).show();
					}else{
						genPayReq(result);
					}
				}else{
					Toast.makeText(mActivity, result.get("return_msg"), Toast.LENGTH_LONG).show();
				}
			}else{
				Toast.makeText(mActivity, "获取订单失败", Toast.LENGTH_LONG).show();
			}
			
		}
		
	}
	
	private void sendPayReq() {
		msgApi.registerApp(WXConstants.APP_ID);
		msgApi.sendReq(req);
	}
	
	
	private String genProductArgs(WXPayInfo info) {
		StringBuffer xml = new StringBuffer();

		try {
			String	nonceStr = genNonceStr();

			xml.append("</xml>");
			List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
			packageParams.add(new BasicNameValuePair("appid", WXConstants.APP_ID));
			packageParams.add(new BasicNameValuePair("body", info.getPayContent()));
			packageParams.add(new BasicNameValuePair("mch_id", WXConstants.MCH_ID));
			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
			packageParams.add(new BasicNameValuePair("notify_url", info.getPayNotifyUrl()));
			packageParams.add(new BasicNameValuePair("out_trade_no",info.getPayOrderId()));
			packageParams.add(new BasicNameValuePair("spbill_create_ip",WXUtils.getIp(mActivity)));
			float totle = Float.valueOf(info.getPayPrice());
			String payPrice = String.valueOf((int) (ArithUtil.mul(totle, 100)));
			if(ApiConfig.IS_DEBUG){
				packageParams.add(new BasicNameValuePair("total_fee", "1"));
			}else{
				packageParams.add(new BasicNameValuePair("total_fee", payPrice));
			}
			packageParams.add(new BasicNameValuePair("trade_type", "APP"));

			String sign = genPackageSign(packageParams);
			packageParams.add(new BasicNameValuePair("sign", sign));

			String xmlstring =toXml(packageParams);

			return xmlstring;

		} catch (Exception e) {
			Log.e(TAG, "genProductArgs fail, ex = " + e.getMessage());
			return null;
		}

	}
	
	private void genPayReq(Map<String,String> resultMap) {

		req.appId = WXConstants.APP_ID;
		req.partnerId = WXConstants.MCH_ID;
		req.prepayId = resultMap.get("prepay_id");
		req.packageValue = "Sign=WXPay";
		req.nonceStr = genNonceStr();
		req.timeStamp = String.valueOf(genTimeStamp());

		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

		req.sign = genAppSign(signParams);

		if(DEBUG)Log.i(TAG, signParams.toString());

		sendPayReq();
		
	}
	
	private String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(WXConstants.PARTNER_KEY);
		String appSign = MD5.getMessageDigest(sb.toString().getBytes());
		
		if(DEBUG)Log.i(TAG,appSign);
		
		return appSign;
	}
	
	private String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}
	
	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}
	
	
	/**
	 生成签名
	 */
	private String genPackageSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(WXConstants.PARTNER_KEY);
		
		String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		if(DEBUG) Log.i(TAG,"packageSign="+packageSign);
		return packageSign;
		
	}
	
	private String toXml(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (int i = 0; i < params.size(); i++) {
			sb.append("<"+params.get(i).getName()+">");
			sb.append(params.get(i).getValue());
			sb.append("</"+params.get(i).getName()+">");
		}
		sb.append("</xml>");
		
		if(DEBUG)Log.i(TAG,sb.toString());

		try {
			return new String(sb.toString().getBytes(), "ISO8859-1");
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}
	
	private Map<String,String> decodeXml(String content) {

		try {
			Map<String, String> xml = new HashMap<String, String>();
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(new StringReader(content));
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				String nodeName=parser.getName();
				switch (event) {
					case XmlPullParser.START_DOCUMENT:

						break;
					case XmlPullParser.START_TAG:

						if("xml".equals(nodeName)==false){
							//实例化student对象
							xml.put(nodeName,parser.nextText());
						}
						break;
					case XmlPullParser.END_TAG:
						break;
				}
				event = parser.next();
			}

			return xml;
		} catch (Exception e) {
			
			if(DEBUG)Log.i(TAG,e.toString());
		}
		return null;

	}
	
	
}
