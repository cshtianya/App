package com.tnw.api.apifig;

public enum ApiParma {
	
	currentPage("currentPage","1"),
	pageSize("pageSize", ApiConfig.PAGESIZE+""),
	/**首页活动列表状态  [status]状态([0:未发布][1:预告中][2:活动中][3:已结束])*/
	status("status","2"),
	/**上级分类ID，不传则返回顶级分类   默认 "" */
	parentId("parentId",""),
	/**商品名称*/
	commodityName("commodityName",""),
	/**品牌Id*/
	brandId("brandId",""),
	/**分类ID*/
	categoryId("categoryId",""),
    /**活动ID*/
    activityId("activityId",""),
	/**商品ID*/
	commodityId("commodityId",""),
	commodity_id("commodity_id",""),
	commodityList("commodityList",""),
	/**商品属性一*/
	firstId("firstId",""),
	/**商品属性二*/
	secondId("secondId",""),
	/**用户索引ID*/
	userId("userId",""),
	user_id("user_id",""),
	/**卖家ID*/
	sellerId("sellerId",""),
	/**订单索引ID*/
	orderId("orderId",""),
	/**订单支付类型*/
	payType("payType",""),
	/**订单留言*/
	remark("remark",""),
	/**订单发票信息*/
	invoiceId("invoiceId",""),
	/**评论内容*/
	content("content",""),
	/**评论索引ID*/
	commentId("commentId",""),
	/**用户名*/
	username("username",""),
	/**密码*/
	password("password",""),
	/**验证码*/
	code("code",""),
	/**手机号*/
	mobile("mobile",""),
	/**收获地址索引ID*/
	deliveryId("deliveryId",""),
	/**购物车商品索引ID*/
	cartId("cartId",""),
	/**购买数量*/
	stock("stock",""),
	
	/**版本类型([1:买家版][2:卖家版]) 默认 1*/
	type("type","1"),
	/**版本号*/
	versionCode("versionCode",""),
	/**添加删除收藏   2是添加,3是删除*/
	action("action","")
	;
	
	private String key;
	private String value;
	
	ApiParma(String _key,String _value){
		this.key = _key;
		this.value = _value;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
