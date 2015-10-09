package com.tnw.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.tnw.R;

/**
 * Created by Administrator on 2015/7/8 0008.
 */
public class UIProgressBar extends Dialog{

    public UIProgressBar(Context context) {
        super(context, R.style.dialogStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressbar_layout);
    }
}
