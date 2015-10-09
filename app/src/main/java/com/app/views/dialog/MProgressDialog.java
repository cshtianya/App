package com.app.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tnw.R;

public class MProgressDialog extends Dialog {

	private ImageView ivCancel;
	private LinearLayout layCancelView;

	public MProgressDialog(Context context) {
		super(context, R.style.dialogStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_prigress_layout);
		ivCancel = (ImageView) findViewById(R.id.ivCancel);
		layCancelView = (LinearLayout)findViewById(R.id.layCancelView);
		layCancelView.setVisibility(View.GONE);
		ivCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(onCancelListener!=null)
					onCancelListener.onCancel(MProgressDialog.this);
			}
		});
	}

	public interface OnCancelListener {
		void onCancel(Dialog dialog);
	};

	private OnCancelListener onCancelListener;

	public void setOnCancelListener(OnCancelListener onCancelListener) {
		this.onCancelListener = onCancelListener;
	}
	
}
