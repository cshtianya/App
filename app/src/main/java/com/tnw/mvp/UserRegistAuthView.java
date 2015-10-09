package com.tnw.mvp;

/**
 * Created by Administrator on 2015/9/21 0021.
 */
public interface UserRegistAuthView extends MVPView{

    void isSendSuccessed(boolean isSuccessed,String msg);

    void isRigistSuccessed(boolean isSuccessed,String msg);

    void isResetSuccessed(boolean isSuccessed,String msg);

}
