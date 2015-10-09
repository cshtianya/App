package com.tnw.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by CSH on 2015/7/13 0013.
 */
public class BaseFragment extends Fragment{

    protected void showToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    protected void showToast(Context context,int strId){
        showToast(context,getString(strId));
    }

}
