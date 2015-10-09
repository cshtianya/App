package com.tnw.controller;

public abstract class AbsBaseController {

	protected boolean isStop = false;
	public void stop(){
		isStop = true;
	}

}
