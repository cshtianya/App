package com.app.pay.alipay;

public class AliPayInfo {

	private String payOrderId, payTitle, payContent, payPrice, payNotifyUrl;

	public AliPayInfo(String payOrderId, String payTitle, String payContent,
			String payPrice, String payNotifyUrl) {
		super();
		this.payOrderId = payOrderId;
		this.payTitle = payTitle;
		this.payContent = payContent;
		this.payPrice = payPrice;
		this.payNotifyUrl = payNotifyUrl;
	}

	public String getPayOrderId() {
		return payOrderId;
	}

	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}

	public String getPayTitle() {
		return payTitle;
	}

	public void setPayTitle(String payTitle) {
		this.payTitle = payTitle;
	}

	public String getPayContent() {
		return payContent;
	}

	public void setPayContent(String payContent) {
		this.payContent = payContent;
	}

	public String getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(String payPrice) {
		this.payPrice = payPrice;
	}

	public String getPayNotifyUrl() {
		return payNotifyUrl;
	}

	public void setPayNotifyUrl(String payNotifyUrl) {
		this.payNotifyUrl = payNotifyUrl;
	}

}
