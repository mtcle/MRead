/**
 * 
 */
package cn.mtcle.mread.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


/**
 * ViewUtil
 * 类描述：
 * 创建人：李满义
 * 创建日期：2013-5-30 上午3:11:07
 * 修改人：
 * 修改日期：
 * 修改备注：
 */
public class ViewUtil {
	
	private static Toast mToast;
	private static Drawable icon;
	public static void showToast(Context context,
			int toastId) {
		if(mToast!=null){
			mToast.cancel();
		}
		mToast = Toast.makeText(context, context.getText(toastId).toString(), Toast.LENGTH_SHORT);
		mToast.show();
	}
	
	public static void showToast(Context context,String msg){
		if(mToast!=null){
			mToast.cancel();
		}
		mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		mToast.show();
	}

	public static void showToastLongTime(Context context,String formatStr) {
		if(mToast!=null){
			mToast.cancel();
		}
		mToast = Toast.makeText(context, formatStr, Toast.LENGTH_LONG);
		mToast.show();
	}
	
	public static void showToastLongTime(Context context,int formatStr) {
		if(mToast!=null){
			mToast.cancel();
		}
		mToast = Toast.makeText(context, formatStr, Toast.LENGTH_LONG);
		mToast.show();
	}
	
	public static void cancelToast(){
		if(mToast!=null){
			mToast.cancel();
		}
	}
	
	/**
	 * showAalert
	 * 描述：
	 * 创建人：李满义
	 * 创建日期：2013-6-15 上午11:20:34
	 * @param context
	 * @param titleMsg
	 * 修改人：
	 * 修改日期：
	 * 修改备注：
	 */
	public static void showAalert(Context context,CharSequence... titleMsg){
		showAalert(context,false, new OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}, titleMsg);
	}
	public static void showAalertWithCancel(Context context, OnClickListener dialogOnClickListener,CharSequence... titleMsg){
		showAalert(context,true, dialogOnClickListener, titleMsg);
	}
	public static void showAalert(Context context,boolean showCancel, OnClickListener dialogOnClickListener,CharSequence... titleMsg){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(icon);
		if(titleMsg.length == 1){			
			builder.setTitle("温馨提示");
			builder.setMessage(titleMsg[0]);
		}else if(titleMsg.length > 1){
			builder.setTitle(titleMsg[0]);
			builder.setMessage(titleMsg[1]);
		}
		builder.setCancelable(false);
		if(titleMsg.length < 4){			
			builder.setPositiveButton("确定", dialogOnClickListener);
			if (showCancel) {
				builder.setNegativeButton("返回", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
			}
		}else{
			builder.setPositiveButton(titleMsg[2], dialogOnClickListener);
			builder.setNegativeButton(titleMsg[3], new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					
				}
			});
		}
		builder.show();
	}
	
	public static void showAalertWithItems(Context context,String[] items, OnClickListener dialogOnClickListener,CharSequence... titleMsg){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		//builder.setIcon(R.drawable.dialog_icon_warning);
		if(titleMsg == null){			
			builder.setTitle("温馨提示");
		}else if(titleMsg.length == 1){
			builder.setTitle(titleMsg[0]);
		}
		builder.setItems(items, dialogOnClickListener);
		builder.setCancelable(false);
		builder.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss();
			}
		});
		builder.show();
	}
	
	/*public static void showAalert4HtmlWithCancelButton(Context context, CharSequence title, CharSequence message){
		showAalert4Html(context, null, title, message, "取消");
	}*/
	/*public static void showAalert4Html(Context context, DialogInterface.OnClickListener dialogOnClickListener,CharSequence title, CharSequence message, CharSequence... btnTitle){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(icon);
		final View layout = LayoutInflater.from(context).inflate(R.layout.webkit, null);
		builder.setView(layout);
		builder.setTitle((title == null || StringUtils.isBlank(title.toString())) ? "温馨提示" : title);
		WebView wvContent = (WebView) layout.findViewById(R.id.webview);
		wvContent.loadDataWithBaseURL(null, message.toString(), "text/html","UTF-8", null);
		
		if(btnTitle.length == 1){
			builder.setNegativeButton(btnTitle[0], dialogOnClickListener);
		}else if(btnTitle.length == 2){
			builder.setNegativeButton(btnTitle[0], dialogOnClickListener);
			builder.setPositiveButton(btnTitle[1], dialogOnClickListener);
		}
		builder.setCancelable(false);
		builder.show();
	}*/
	private static long lastClickTime;
	/**
	 * isFastDoubleClick
	 * 描述：防止重复点击
	 * 创建人：李满义
	 * 创建日期：2013-10-14 上午9:01:39
	 * @return
	 * 修改人：
	 * 修改日期：
	 * 修改备注：
	 */
	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 800) {
			lastClickTime = time;
			return true;
		}
		return false;
	}
	
	/**
	 * 描述：显示键盘
	 * 创建人：李满义
	 * 创建时间：2014-8-3 下午12:04:55
	 * @param view
	 * 修改人：
	 * 修改时间：
	 */
	public static void setKeyboardFocus(final View view) {
		try {
			view.setFocusable(true);   
			view.setFocusableInTouchMode(true);   
			view.requestFocus();
			(new Handler()).postDelayed(new Runnable() {
				public void run() {
					view.dispatchTouchEvent(MotionEvent.obtain(
							SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
							MotionEvent.ACTION_DOWN, 0, 0, 0));
					view.dispatchTouchEvent(MotionEvent.obtain(
							SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
							MotionEvent.ACTION_UP, 0, 0, 0));
				}
			}, 100);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
