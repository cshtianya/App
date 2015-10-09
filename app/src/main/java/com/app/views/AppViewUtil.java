package com.app.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
/**
 * @description 对应用中资源操作的工具类 
 * @author JiaBF
 * @date 2014-6-11 上午9:59:29
 * @update 2014-6-11
 * @version V1.0
 */
public class AppViewUtil {
	
	/**
	 * @description 设置textView中间线
	 * @param @param textView 
	 * @return void 
	 * @author jiaBF
	 */
	public static void setTextViewCenterLine(TextView textView)
	{
		textView.getPaint().setFlags(TextPaint.STRIKE_THRU_TEXT_FLAG);
	}

	/**
	 * @description 获取txt文本宽度
	 * @param txt
	 *            绘制的文本 paint 绘制画笔 需要获取画笔字体大小，来计算宽度
	 * @return void
	 * @author jiaBF
	 */
	public static float getTxtWidth(String txt, Paint paint) {
		TextPaint textPaint = new TextPaint();
		textPaint.setTextSize(paint.getTextSize());
		return Layout.getDesiredWidth(txt, textPaint);
	}

	/**
	 * @description 获取txt文本高度
	 * @param txt
	 *            绘制的文本 paint 绘制画笔 需要获取画笔字体大小，来计算宽度
	 * @return void
	 * @author jiaBF
	 */
	public static double getTxtHeight(Paint textPaint) {
		FontMetrics fontMetrics = textPaint.getFontMetrics();
		return (Math.ceil(fontMetrics.bottom - fontMetrics.ascent));
	}
	
	
	public int getTextWidth(String text, Paint paint) {
		 Rect bounds = new Rect();
		 paint.getTextBounds(text, 0, text.length(), bounds);
		 int width = bounds.left + bounds.width();
		 return width;
	}

	
	public int getTextHeight(String text, Paint paint) {
		 Rect bounds = new Rect();
		 paint.getTextBounds(text, 0, text.length(), bounds);
		 int height = bounds.bottom + bounds.height();
		 return height;
	}
	
	/**
	 * @description 引入一个view
	 * @param @param context
	 * @param @param layoutId 引入的布局id
	 * @param @return 
	 * @return View 
	 * @author jiaBF
	 */
	public static View inflateView(Context context,int layoutId)
	{
		if(null == context) return null;
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return layoutInflater.inflate(layoutId, null);
	}
	

	/**
	 * 描述：重置AbsListView的高度. item 的最外层布局要用
	 * RelativeLayout,如果计算的不准，就为RelativeLayout指定一个高度
	 * 
	 * @param absListView
	 *            the abs list view
	 * @param lineNumber
	 *            每行几个 ListView一行一个item
	 * @param verticalSpace
	 *            the vertical space
	 */
	public static void setAbsListViewHeight(AbsListView absListView,
			int lineNumber, int verticalSpace) {

		int totalHeight = getAbsListViewHeight(absListView, lineNumber,
				verticalSpace);
		ViewGroup.LayoutParams params = absListView.getLayoutParams();
		params.height = totalHeight;
		((MarginLayoutParams) params).setMargins(0, 0, 0, 0);
		absListView.setLayoutParams(params);
	}

	/**
	 * 描述：获取AbsListView的高度.
	 * 
	 * @param absListView
	 *            the abs list view
	 * @param lineNumber
	 *            每行几个 ListView一行一个item
	 * @param verticalSpace
	 *            the vertical space
	 */
	public static int getAbsListViewHeight(AbsListView absListView,
			int lineNumber, int verticalSpace) {
		int totalHeight = 0;
		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		absListView.measure(w, h);
		ListAdapter mListAdapter = absListView.getAdapter();
		if (mListAdapter == null) {
			return totalHeight;
		}

		int count = mListAdapter.getCount();
		if (absListView instanceof ListView) {
			for (int i = 0; i < count; i++) {
				View listItem = mListAdapter.getView(i, null, absListView);
				listItem.measure(w, h);
				totalHeight += listItem.getMeasuredHeight();
			}
			if (count == 0) {
				totalHeight = verticalSpace;
			} else {
				totalHeight = totalHeight
						+ (((ListView) absListView).getDividerHeight() * (count - 1));
			}

		} else if (absListView instanceof GridView) {
			int remain = count % lineNumber;
			if (remain > 0) {
				remain = 1;
			}
			if (mListAdapter.getCount() == 0) {
				totalHeight = verticalSpace;
			} else {
				View listItem = mListAdapter.getView(0, null, absListView);
				listItem.measure(w, h);
				int line = count / lineNumber + remain;
				totalHeight = line * listItem.getMeasuredHeight() + (line - 1)
						* verticalSpace;
			}

		}
		return totalHeight;

	}

	/**
	 * 测量这个view，最后通过getMeasuredWidth()获取宽度和高度.
	 * 
	 * @param v
	 *            要测量的view
	 * @return 测量过的view
	 */
	public static void measureView(View v) {
		if (v == null) {
			return;
		}
		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		v.measure(w, h);
	}

	/**
	 * @说明：获取屏幕触摸的某个点，是否在某个view之内
	 * @作者：jiaBF
	 * @时间：2014-1-6
	 */
	public static boolean constansPoint(View view, int x, int y) {
		Rect outRect = new Rect();
		final int[] location = new int[2];
		view.getLocationOnScreen(location);
		outRect.top = location[1];
		outRect.bottom = outRect.top + view.getHeight();
		outRect.left = location[0];
		outRect.right = outRect.left + view.getWidth();
		return outRect.contains(x, y);
	}

	/**
	 * 获取触摸的View在屏幕的Rect
	 * 
	 * @param view
	 * @param x
	 * @param y
	 * @return
	 */
	public static Rect getViewRectInWindow(View view) {
		Rect outRect = new Rect();
		final int[] location = new int[2];
		view.getLocationOnScreen(location);
		outRect.top = location[1];
		outRect.bottom = outRect.top + view.getHeight();
		outRect.left = location[0];
		outRect.right = outRect.left + view.getWidth();
		return outRect;
	}
	
	/**
	 * 获取触摸的View在屏幕的Rect
	 * 
	 * @param view
	 * @param x
	 * @param y
	 * @return
	 */
	public static int[] getViewTopInWindow(View view) {
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		return location;
	}

	/**
	 * @description 根据资源id获取资源Drawable
	 * @param context
	 * @param drawableId
	 * @return Drawable
	 * @author jiaBF
	 */
	public static Drawable getDrawableById(Context context, int drawableId) {
		return context.getResources().getDrawable(drawableId);
	}

	/**
	 * @description 根据资源id获取资源Bitmap
	 * @param context
	 * @param drawableId
	 * @return Drawable
	 * @author jiaBF
	 */
	public static Bitmap getBitmapById(Context context, int drawableId) {
		BitmapDrawable bitmapDrawable = (BitmapDrawable) context.getResources()
				.getDrawable(drawableId);
		return bitmapDrawable.getBitmap();
	}

	/**
	 * @description 根据资源id获取资源颜色值
	 * @param context
	 * @param colorId
	 *            color.xml中的资源id
	 * @return int
	 * @author jiaBF
	 */
	public static int getColorById(Context context, int colorId) {
		return context.getResources().getColor(colorId);
	}

	/**
	 * @description 根据资源id获取资源Int值
	 * @param context
	 * @param dimenId
	 *            dimens.xml中的资源id
	 * @return int
	 * @author jiaBF
	 */
	public static float getDimenById(Context context, int dimenId) {
		return context.getResources().getDimension(dimenId);
	}

	/**
	 * @description 将int值转化为屏幕密度对应的DIP值
	 * @param context
	 * @param value
	 *            转换的数值
	 * @return int
	 * @author jiaBF
	 */
	public static float getDipDimenById(Context context, int value) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
				context.getResources().getDisplayMetrics());
	}

	/**
	 * @description 将int值转化为屏幕密度对应的Px值
	 * @param context
	 * @param value
	 *            转换的数值
	 * @return int
	 * @author jiaBF
	 */
	public static float getPxDimenById(Context context, int value) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, value,
				context.getResources().getDisplayMetrics());
	}

	/**
	 * @description 将int值转化为屏幕密度对应的Sp值
	 * @param context
	 * @param value
	 *            转换的数值
	 * @return int
	 * @author jiaBF
	 */
	public static float getSpDimenById(Context context, int value) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value,
				context.getResources().getDisplayMetrics());
	}
	
	/**
	 * @description  动态设置TextView Button的 Drawable Left、right、top、bottom
	 * @param @param resId
	 * @param @param view
	 * @param @param orientation 
	 * @return void 
	 * @author jiaBF
	 */
	public static void setHasOrientionDrawable(int resId,TextView view,Orientation orientation)
	{
		Drawable drawable= view.getContext().getResources().getDrawable(resId);  
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        if(orientation == Orientation.LEFT)
        {
        	 view.setCompoundDrawables(drawable,null,null,null);
        }
        else if(orientation == Orientation.RIGHT)
        {
       	 	view.setCompoundDrawables(null,null,drawable,null);
        }
        else if(orientation == Orientation.TOP)
        {
       	 	view.setCompoundDrawables(null,drawable,null,null);
        }
        else
        {
       	 	view.setCompoundDrawables(null,null,null,drawable);
        }
	}
	
	public enum Orientation{
		LEFT,RIGHT,TOP,BOTTOM
	}

}
