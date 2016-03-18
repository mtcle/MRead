package cn.mtcle.mread.common;

import android.content.Context;
import android.nfc.Tag;

import com.bmob.BmobConfiguration;
import com.bmob.BmobPro;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import cn.bmob.v3.Bmob;
import cn.mtcle.mread.util.Cmd;
import cn.newcapec.nfc.core.BaseApplication;
import cn.newcapec.nfc.core.util.DebugUtil;

public class SystemApplication extends BaseApplication {
	private static SystemApplication mInstance = null;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;

		// CrashHandler crashHandler = CrashHandler.getInstance();
		// crashHandler.delOutTimeFile();
		// crashHandler.init(getApplicationContext());

		ImageLoader.getInstance().init(
				ImageLoaderConfiguration.createDefault(mInstance));
	}

	public static SystemApplication getInstance() {
		return mInstance;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		System.out.println("Application onLowMemory");

	}

}
