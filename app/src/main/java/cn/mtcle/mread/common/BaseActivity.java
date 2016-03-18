package cn.mtcle.mread.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.Window;

import java.lang.reflect.Method;

import cn.mtcle.mread.util.okhttp.OkHttpUtil;
import cn.newcapec.nfc.core.BaseApplication;
import cn.newcapec.nfc.core.util.DebugUtil;
import cn.newcapec.nfc.core.util.PreferencesUtil;
import cn.newcapec.nfc.core.util.StringUtils;
import cn.newcapec.nfc.core.util.factory.BeanFactoryHelper;
import cn.newcapec.nfc.core.util.task.LogUploadAnsyTask;

/**
 * 类名：BaseActivity
 * 类描述：定义Activity的样式和初始化功能
 * 创建时间：2012-7-11
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class BaseActivity extends Activity{
	protected final static String TAG = BaseActivity.class.getSimpleName();
	protected Context mContext = BaseActivity.this;
	private static Vibrator vibrator;
	protected ProgressDialog pdpd, pdpdUnicom;
	protected PreferencesUtil mPreferUtil;
	protected int screenWidth = 480;
	protected int screenHeight = 800;
	protected OkHttpUtil mHttpUtil;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		//outState.putSerializable(Constants.C_IPARAM_CARDBASEINFO, cardBaseInfo);
		outState.putInt("screenWidth", screenWidth);
		outState.putInt("screenHeight", screenHeight);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		//cardBaseInfo = (CardBaseInfo)savedInstanceState.getSerializable(Constants.C_IPARAM_CARDBASEINFO);
		screenWidth = savedInstanceState.getInt("screenWidth", 480);
		screenHeight = savedInstanceState.getInt("screenHeight", 800);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 先去除应用程序标题栏 注意：一定要在setContentView之前
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		BaseApplication.getInstance().push(this);
		
		mPreferUtil = BeanFactoryHelper.getBeanFactory().getBean(PreferencesUtil.class);
		mHttpUtil=new OkHttpUtil(mContext);
		/********************获取屏幕分辨率_begin********************/
		int ver = Build.VERSION.SDK_INT;
		DisplayMetrics dm = new DisplayMetrics();
		android.view.Display display = getWindowManager().getDefaultDisplay();
		display.getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = 800;
		if (ver < 13 || ver > 16) {
			screenHeight = dm.heightPixels;
		} else if (ver == 13) {
			try {
				Method mt = display.getClass().getMethod("getRealHeight");
				screenHeight = (Integer) mt.invoke(display);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (ver > 13) {
			try {
				Method mt = display.getClass().getMethod("getRawHeight");
				screenHeight = (Integer) mt.invoke(display);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(screenWidth + "\t" + screenHeight);
		/********************获取屏幕分辨率_end********************/
	}

	@Override
	protected void onResume() {
		super.onResume();
		
	}
	
	/**
	 * initView
	 * 描述：初始化界面的方法，供继承本类的Activity实现。
	 * 创建人：lmy
	 * 创建日期：2013-6-18 上午2:58:47
	 * 修改人：
	 * 修改日期：
	 * 修改备注：
	 */
	protected void initView(){
		
	}
	
	@Override
	protected void onDestroy() {
		if(!BaseApplication.getInstance().isEmptyStack()){
			BaseApplication.getInstance().remove(this);
		}
		super.onDestroy();
	}
	
	protected void vibrate(){
		vibrate(50L);
	}

	/**
	 * vibrate
	 * 描述：震动
	 * 创建人：李满义
	 * 创建日期：2013-6-14 上午1:04:42
	 * @param paramLong
	 * 修改人：
	 * 修改日期：
	 * 修改备注：
	 */
	protected void vibrate(long paramLong) {
		try {
			if (vibrator == null)
				//vibrator = (Vibrator)getSystemService("vibrator");
			vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
			vibrator.vibrate(paramLong);
		} catch (Exception e) {
			DebugUtil.error(TAG, e);
		}
	}
	protected void showProgressDialog(int messageResId){
		showProgressDialog(getString(messageResId));
	}
	protected void showProgressDialog(String message){
		showProgressDialog(message,true);
	}
	protected void showProgressDialog(String title, String message,boolean canBack){
		closeProgressDialog();
		pdpd = ProgressDialog.show(mContext, StringUtils.isNotBlank(title) ? title : "",message, true,true);
		pdpd.setCanceledOnTouchOutside(false);
		pdpd.setCancelable(canBack);
	}
	protected void showProgressDialog(String message,boolean canBack){
		showProgressDialog(null, message, canBack);
	}
	protected void updateProgressDialog(int messageResId){
		updateProgressDialog(getString(messageResId));
	}
	protected void updateProgressDialog(String message){
		updateProgressDialog(message, true);
	}
	protected void updateProgressDialog(String title, String message, boolean canBack){
		if(pdpd != null && pdpd.isShowing()){	
			if (StringUtils.isNotBlank(title)) {				
				pdpd.setTitle(title);
			}
			pdpd.setMessage(message);
			pdpd.setCancelable(canBack);
		}else{
			showProgressDialog(title, message, canBack);
		}
	}
	protected void updateProgressDialog(String message, boolean canBack){
		updateProgressDialog(null, message, canBack);
	}
	protected void closeProgressDialog(){
		if(pdpd != null && pdpd.isShowing())
			pdpd.dismiss();
	}
	
	protected void errorLogUpload(String tag, String... desc){
		errorLogUpload(tag, null, desc);
	}
	protected void errorLogUpload(String tag , Throwable ex, String... desc){
		String errorDesc = "";
		if (desc != null && desc.length > 0) {
			for (String string : desc) {
				errorDesc += string;
			}
		}
		String content = String.format("%s ==== %s", tag , ex == null ? errorDesc : DebugUtil.getCrashInfo(ex));
		errorLogUpload(content);
	}
	protected void errorLogUpload(Throwable ex, String... desc){
		errorLogUpload(TAG, ex, desc);
	}
	protected void errorLogUpload(String desc){
		doErrorLogUpload("", desc);
		DebugUtil.error(TAG, null, desc);
	}
	/**
	 * 描述：错误日志上传
	 * </br>创建人：李满义</br>
	 * 创建时间：2015-7-9 上午11:44:25
	 * @param sno
	 * @param desc
	 */
	protected void doErrorLogUpload(String sno, String desc){
		new LogUploadAnsyTask().execute(desc);
	}
	
}
