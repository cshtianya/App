package com.tnw.api.action;


import com.tnw.MApplication;
import com.tnw.api.DatabaseAPI;

public class BaseAction {

	protected final DatabaseAPI dataApi;
	
	public BaseAction(){
        dataApi = MApplication.getInstance().initialize();
	}


}
