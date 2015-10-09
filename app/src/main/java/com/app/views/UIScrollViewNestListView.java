package com.app.views;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @description	用于ListView嵌套在ScrollView中，gridView高度根据内容自动扩展
 * 				再使用时ListView的高度必须设置为wrap_content
 * @author JiaBF
 * @date 2014-4-16
 * @update 2014-4-16
 * @version V1.0
 */
public class UIScrollViewNestListView extends ListView {
	public UIScrollViewNestListView(Context context) {
		super(context);
	}

	public UIScrollViewNestListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public UIScrollViewNestListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);

		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
