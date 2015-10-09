package com.tnw.mvp;

/**
 * Created by Administrator on 2015/7/3 0003.
 */
public interface NodeListView<T> extends MVPView{

    void showNodeList(T t, boolean isEnd);

    void appendNodeList(T t, boolean isEnd);

    boolean isListEmpty();

    void showActionLabel();

    void hideActionLabel();

}
