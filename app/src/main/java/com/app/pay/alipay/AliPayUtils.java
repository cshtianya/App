package com.app.pay.alipay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tnw.R;
import com.tnw.activities.OrderListManageActivity;
import com.tnw.api.apifig.ApiConfig;

import java.net.URLEncoder;
import java.util.HashMap;

/**
 * 
 * 类名称：AliPayUtils   
 * 类描述：支付宝相关操作工具类
 * 创建人：ChenSH   
 * E-mail：csh_tianya@163.com 
 * 创建时间：2014-5-4 上午10:50:16
 * 修改人：
 * 修改时间：2015-01-26
 * 修改备注：更新到支付宝2.0sdk
 * @version 2.0 
 */
public class AliPayUtils {
	public static final int ORDER_PAY_REQUEST_CODE = 9000;
	
	private static final int SDK_PAY_FLAG = 1;
	
	public static final HashMap<Integer, String> orderState;
	
	//([-1:取消][0:未付款][1:已付款][2:已发货][3:完成][4:退货申请中][5:退货中][6:卖家拒绝退货][7:卖家同意退货])
	static {
		orderState = new HashMap<>();
		orderState.put(-1, "交易关闭");
		orderState.put(0, "待付款");
		orderState.put(1, "等待卖家发货");
		orderState.put(2, "确认收货");
		orderState.put(3, "已完成");
		orderState.put(4, "退货申请中");
		orderState.put(5, "退货申请中");
		orderState.put(6, "卖家拒绝退货");
		orderState.put(7, "退货中");
	}
	
	private Activity mActivity;
	private void showToast(Activity activity,String text){
		Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
	}
	
	
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				NAliResult resultObj = new NAliResult((String) msg.obj);
				String resultStatus = resultObj.resultStatus;

				/**
				 * 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				 */
				if (TextUtils.equals(resultStatus, ORDER_PAY_REQUEST_CODE+"")) {
					showToast(mActivity, "支付完成");
					OrderListManageActivity.launcher(mActivity, R.id.radoNoDelivery);
				} else {
					/** 判断resultStatus 为非“9000”则代表可能支付失败
					 * “8000” 代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，
					 * 最终交易是否成功以服务端异步通知为准（小概率状态）
					 */
					if (TextUtils.equals(resultStatus, "8000")) {
						showToast(mActivity,"支付结果确认中");
						mActivity.finish();
					} else {
						showToast(mActivity, "支付失败");
						OrderListManageActivity.launcher(mActivity, R.id.radoNoPayment);
					}
				}
				break;
			}
			default:
				break;
			}
		}
	};
	
	public void excutePay(final Activity activity,final AliPayInfo info){
		mActivity  = activity;
		new Thread() {
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(activity);
				// 调用支付接口
				String result = alipay.pay(getNewOrderInfo(info));
				
//				Log.v("csh", " pay result ===>"+result);
				
				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		}.start();
	}
	
	
	
	/**
	 * 生成订单支付信息
	 * @param info
	 * @return
	 */
	private String getNewOrderInfo(AliPayInfo info) {
		
		String orderStr = ""; 
		
		try {
			
			StringBuilder sb = new StringBuilder();
			// 合作者身份ID
			sb.append("partner=\"");
			sb.append(AliKeys.DEFAULT_PARTNER);
			// 卖家支付宝账号
			sb.append("\"&seller_id=\"");
			sb.append(AliKeys.DEFAULT_SELLER);
			// 商户网站唯一订单号
			sb.append("\"&out_trade_no=\"");
			sb.append(info.getPayOrderId());
			// 商品标题
			sb.append("\"&subject=\"");
			sb.append(info.getPayTitle());
			sb.append("\"&body=\"");
			sb.append(info.getPayContent());
			// 商品金额
			sb.append("\"&total_fee=\"");
			if(ApiConfig.IS_DEBUG){
				sb.append("0.01");
			}else{
				sb.append(info.getPayPrice());
			}
			sb.append("\"&notify_url=\"");
			sb.append(URLEncoder.encode(info.getPayNotifyUrl(),"UTF-8"));
			// 接口名称， 固定值
			sb.append("\"&service=\"mobile.securitypay.pay");
			// 参数编码， 固定值
			sb.append("\"&_input_charset=\"UTF-8");
			// 支付类型， 固定值
			sb.append("\"&payment_type=\"1");
			
			/*
			 *	设置未付款交易的超时时间
			   	默认30分钟，一旦超时，该笔交易就会自动被关闭。
				取值范围：1m～15d。
				m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
				该参数数值不接受小数点，如1.5h，可转换为90m。
			 */
			sb.append("\"&it_b_pay=\"30m\"");
			
			String strInfo = new String(sb);
//			Log.v("csh", "====>"+strInfo);
			String sign = AliRsa.sign(strInfo, AliKeys.PRIVATE);
			sign = URLEncoder.encode(sign, "UTF-8");
			
			orderStr = strInfo+ "&sign=\"" + sign + "\"&sign_type=\"RSA\"";
			
			// 调用银行卡支付，需配置此参数，参与签名， 固定值
			//orderStr += "&paymethod=\"expressGateway\"";
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return orderStr;
	}
	
}
