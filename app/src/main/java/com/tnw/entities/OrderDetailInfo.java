package com.tnw.entities;

import java.util.List;

public class OrderDetailInfo {
	private String orderId, orderPayNo, orderPrice, orderStatus,
			orderCreateTime;

	private DeliveryNode delivery;
	private OrderShopInfo shopList;

	public OrderReturnInfo getReturnInfo() {
		return returnInfo;
	}

	public void setReturnInfo(OrderReturnInfo returnInfo) {
		this.returnInfo = returnInfo;
	}

	private OrderReturnInfo returnInfo;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderPayNo() {
		return orderPayNo;
	}

	public void setOrderPayNo(String orderPayNo) {
		this.orderPayNo = orderPayNo;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(String orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public DeliveryNode getDelivery() {
		return delivery;
	}

	public void setDelivery(DeliveryNode delivery) {
		this.delivery = delivery;
	}

	public OrderShopInfo getShopList() {
		return shopList;
	}

	public void setShopList(OrderShopInfo shopList) {
		this.shopList = shopList;
	}

	public class OrderShopInfo {
		private String shopId, shopName, shopAddress, shopTelephone;
		private List<OrderNodeItem> itemList;

		public String getShopId() {
			return shopId;
		}

		public void setShopId(String shopId) {
			this.shopId = shopId;
		}

		public String getShopName() {
			return shopName;
		}

		public void setShopName(String shopName) {
			this.shopName = shopName;
		}

		public String getShopAddress() {
			return shopAddress;
		}

		public void setShopAddress(String shopAddress) {
			this.shopAddress = shopAddress;
		}

		public String getShopTelephone() {
			return shopTelephone;
		}

		public void setShopTelephone(String shopTelephone) {
			this.shopTelephone = shopTelephone;
		}

		public List<OrderNodeItem> getItemList() {
			return itemList;
		}

		public void setItemList(List<OrderNodeItem> itemList) {
			this.itemList = itemList;
		}

	}
	

}
