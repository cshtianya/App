package com.tnw.mvp;

import com.tnw.entities.OrderDetailInfo;
import com.tnw.entities.OrderPreInfo;
import com.tnw.entities.OrderPayInfo.PayInfo;

/**
 * Created by CSH on 2015/7/31 0031.
 */
public interface OrderView extends  MVPView{

    void showPreOrder(OrderPreInfo pInfo);

    void excutePayOrder(PayInfo info);

    void showOrderDetail(OrderDetailInfo info);

    void opreateState(int orderState,boolean isSuccessed);

}
