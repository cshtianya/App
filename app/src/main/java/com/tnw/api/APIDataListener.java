package com.tnw.api;

public interface APIDataListener<T> {
	void success(T result);
	void failure(String errorMsg);
}
