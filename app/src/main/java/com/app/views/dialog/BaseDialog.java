package com.app.views.dialog;

import android.app.Dialog;
import android.content.Context;

import com.tnw.R;

public abstract class BaseDialog extends Dialog {
	
	protected PositiveListener positiveListener;
	protected NegativeListener negativeListener;
	
	public interface PositiveListener{
		void onClick(BaseDialog dialog);
	};
	
	public interface NegativeListener{
		void onClick(BaseDialog dialog);
	};
	
	protected abstract int setDialogView();
	
	public abstract void setDialogTitle(int strId);
	
	public abstract void setDialogTitle(String text);
	
	
	public BaseDialog(Context context) {
		super(context, R.style.dialogStyle);
		// TODO Auto-generated constructor stub
		setContentView(setDialogView());
		
	}
	
	public void setDialogMsg(int strId) {
		// TODO Auto-generated method stub
		
	}
	
	public void setDialogMsg(String text) {
		// TODO Auto-generated method stub
		
	}
	
	public void setPositiveListener(PositiveListener positiveListener) {
		this.positiveListener = positiveListener;
	}

	public void setNegativeListener(NegativeListener negativeListener) {
		
		this.negativeListener = negativeListener;
	}
	
	
}
