package com.app.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @description 用于gridView嵌套在ScrollView或者Listview时使用
 * @author JiaBF
 * @date 2014-11-13
 * @update 2014-11-13
 * @version V1.0
 */
public class UIScrollViewNestGridView
        extends
        GridView
{
	    public UIScrollViewNestGridView(Context context) {   
	        super(context);   
	    }   
	    public UIScrollViewNestGridView(Context context, AttributeSet attrs) {   
	        super(context, attrs);   
	    }   
	    public UIScrollViewNestGridView(Context context, AttributeSet attrs, int defStyle) {   
	        super(context, attrs, defStyle);   
	    }   
	    @Override   
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {   
	    	 int expandSpec = MeasureSpec.makeMeasureSpec(  
	    	                       	                   Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);  
	    	                       	         
	    	                       	        super.onMeasure(widthMeasureSpec, expandSpec);     
	    }     
}
