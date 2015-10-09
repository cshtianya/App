package com.tnw.api.action;

public enum ActionEnum {

	getProductsAction(new ProductsAction()),
    getIndexAction(new IndexAction()),
	getCategoryAction(new CategoryAction()),
	getUserAction(new UserAction()),
	getDeliveryAction(new DeliveryAction()),
	getCartAction(new CartAction()),
	getOrderAction(new OrderAction());
	
	private BaseAction instance;

	ActionEnum(BaseAction action){
		this.instance = action;
	}
	
	public BaseAction getInstance() {
		return instance;
	}

	public void setInstance(BaseAction instance) {
		this.instance = instance;
	}
	
	
	
}
