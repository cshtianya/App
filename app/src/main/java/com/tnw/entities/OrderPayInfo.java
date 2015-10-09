package com.tnw.entities;

public class OrderPayInfo extends ResultMsg{

	private PayInfo data;


	public PayInfo getData() {
		return data;
	}

	public void setData(PayInfo data) {
		this.data = data;
	}

	public class PayInfo {
		private String orderId, orderPrice, orderTitle, orderContent,
				notifyUrl;

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		public String getOrderPrice() {
			return orderPrice;
		}

		public void setOrderPrice(String orderPrice) {
			this.orderPrice = orderPrice;
		}

		public String getOrderTitle() {
			return orderTitle;
		}

		public void setOrderTitle(String orderTitle) {
			this.orderTitle = orderTitle;
		}

		public String getOrderContent() {
			return orderContent;
		}

		public void setOrderContent(String orderContent) {
			this.orderContent = orderContent;
		}

		public String getNotifyUrl() {
			return notifyUrl;
		}

		public void setNotifyUrl(String notifyUrl) {
			this.notifyUrl = notifyUrl;
		}

	}

}
