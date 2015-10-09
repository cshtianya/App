package com.app.views.custom_views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.tnw.R;

/**
 *
 */
public class UIOrderTabView extends LinearLayout implements View.OnClickListener {

    private RadioButton radoTabAll,radoNoPayment,radoNoDelivery,radoNoReceived,radoCompleted;

    private int cacheId;

    public UIOrderTabView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public UIOrderTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        this.setOrientation(HORIZONTAL);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        changeView((RadioButton) v);
        if (onChangedListener != null)
            onChangedListener.onChanged(v, false);

        cacheId = v.getId();
    }

    public void initView() {
        radoTabAll = (RadioButton) findViewById(R.id.radoTabAll);
        radoNoPayment = (RadioButton) findViewById(R.id.radoNoPayment);
        radoNoDelivery = (RadioButton) findViewById(R.id.radoNoDelivery);
        radoNoReceived = (RadioButton) findViewById(R.id.radoNoReceived);
        radoCompleted = (RadioButton) findViewById(R.id.radoCompleted);

        radoTabAll.setOnClickListener(this);
        radoNoPayment.setOnClickListener(this);
        radoNoDelivery.setOnClickListener(this);
        radoNoReceived.setOnClickListener(this);
        radoCompleted.setOnClickListener(this);

    }


    private void changeView(RadioButton itemView) {
        radoTabAll.setChecked(false);
        radoNoPayment.setChecked(false);
        radoNoDelivery.setChecked(false);
        radoNoReceived.setChecked(false);
        radoCompleted.setChecked(false);

        itemView.setChecked(true);

    }

    public int getCacheId() {
        return cacheId;
    }

    public interface OnChangedListener {
        void onChanged(View v, boolean isRefresh);
    }

    private OnChangedListener onChangedListener;

    public void setOnChangedListener(OnChangedListener onChangedListener) {
        this.onChangedListener = onChangedListener;
    }

}
