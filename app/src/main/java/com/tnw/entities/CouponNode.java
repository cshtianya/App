package com.tnw.entities;

public class CouponNode {


	/**
	 * itemId : 14374
	 * itemName : 10元优惠券
	 * itemTime : 2015.09.01-2015.09.01
	 * itemState : 1
	 * itemImage : http://tenongwang.oss-cn-qingdao.aliyuncs.com/commodity/1437405919660247927.jpg
	 */

	private String itemId;
	private String itemName;
	private String itemTime;
	private String itemState;
	private String itemImage;

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setItemTime(String itemTime) {
		this.itemTime = itemTime;
	}

	public void setItemState(String itemState) {
		this.itemState = itemState;
	}

	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}

	public String getItemId() {
		return itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public String getItemTime() {
		return itemTime;
	}

	public String getItemState() {
		return itemState;
	}

	public String getItemImage() {
		return itemImage;
	}
}
