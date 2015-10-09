package com.tnw.mvp;

public interface MVPView {

	android.content.Context getContext();

    void showLoading();

    void hideLoading();

    void showMsg(String msg);

}
