package com.tnw.entities;

import java.util.List;

public class BannerInfo extends ResponseData {

	private List<BannerItem> data;

	public void setData(List<BannerItem> data) {
		this.data = data;
	}

	public List<BannerItem> getData() {
		return data;
	}

	public class BannerItem {

		private String itemId;
		private String itemName;
		private String itemImage;
		private String activityId;
		private String image;
		private String itemType;
		private String packageUrl;
		private String brandId;
		private String title;
		private String content;
		private String str_start_time;
		private String str_end_time;

		public String getItemId() {
			return itemId;
		}

		public void setItemId(String itemId) {
			this.itemId = itemId;
		}

		public String getItemName() {
			return itemName;
		}

		public void setItemName(String itemName) {
			this.itemName = itemName;
		}

		public String getItemImage() {
			return itemImage;
		}

		public void setItemImage(String itemImage) {
			this.itemImage = itemImage;
		}

		public void setActivityId(String activityId) {
			this.activityId = activityId;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public void setItemType(String itemType) {
			this.itemType = itemType;
		}

		public void setPackageUrl(String packageUrl) {
			this.packageUrl = packageUrl;
		}

		public void setBrandId(String brandId) {
			this.brandId = brandId;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public void setStr_start_time(String str_start_time) {
			this.str_start_time = str_start_time;
		}

		public void setStr_end_time(String str_end_time) {
			this.str_end_time = str_end_time;
		}

		public String getActivityId() {
			return activityId;
		}

		public String getImage() {
			return image;
		}

		public String getItemType() {
			return itemType;
		}

		public String getPackageUrl() {
			return packageUrl;
		}

		public String getBrandId() {
			return brandId;
		}

		public String getTitle() {
			return title;
		}

		public String getContent() {
			return content;
		}

		public String getStr_start_time() {
			return str_start_time;
		}

		public String getStr_end_time() {
			return str_end_time;
		}
	}
	
}
