package com.tnw.entities;

import java.util.List;

public class OrderNode {

	private String orderId, orderSellerId, orderShopName, orderPrice,
			orderStatus, orderCreateTime, payType, isComment;

	private List<OrderNodeItem> itemList;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderSellerId() {
		return orderSellerId;
	}

	public void setOrderSellerId(String orderSellerId) {
		this.orderSellerId = orderSellerId;
	}

	public String getOrderShopName() {
		return orderShopName;
	}

	public void setOrderShopName(String orderShopName) {
		this.orderShopName = orderShopName;
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

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getIsComment() {
		return isComment;
	}

	public void setIsComment(String isComment) {
		this.isComment = isComment;
	}

	public List<OrderNodeItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<OrderNodeItem> itemList) {
		this.itemList = itemList;
	}

}
