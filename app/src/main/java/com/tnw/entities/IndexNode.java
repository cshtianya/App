package com.tnw.entities;

import java.io.Serializable;

public class IndexNode implements Serializable{

    private static final long serialVersionUID = 482533283077429549L;


    private String itemId, itemType, itemImage, itemStatus, itemStatusName,
	itemStartTime, itemEndTime, itemName, brandName, price;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemImage() {
		return itemImage;
	}

	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}

	public String getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getItemStatusName() {
		return itemStatusName;
	}

	public void setItemStatusName(String itemStatusName) {
		this.itemStatusName = itemStatusName;
	}

	public String getItemStartTime() {
		return itemStartTime;
	}

	public void setItemStartTime(String itemStartTime) {
		this.itemStartTime = itemStartTime;
	}

	public String getItemEndTime() {
		return itemEndTime;
	}

	public void setItemEndTime(String itemEndTime) {
		this.itemEndTime = itemEndTime;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	
}
