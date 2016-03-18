package cn.mtcle.mread;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bmob.BmobConfiguration;
import com.bmob.BmobPro;

import java.util.HashMap;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.mtcle.mread.common.BaseFirstLevActivity;
import cn.mtcle.mread.util.CheckAuthUtil;
import cn.mtcle.mread.util.Cmd;
import cn.mtcle.mread.util.DateUtil;
import cn.mtcle.mread.util.ViewUtil;
import cn.newcapec.nfc.core.util.DebugUtil;
import cn.newcapec.nfc.core.util.DeviceUtil;
import cn.newcapec.nfc.core.util.StringUtils;


public class SplashActivity extends BaseFirstLevActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.initView();
        setContentView(R.layout.splash);
        CheckTask task = new CheckTask();
        task.execute("");
    }

    class CheckTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            if (!DeviceUtil.checkNetWorkStatus(mContext)) {
                ViewUtil.showToast(mContext, "网络不可用");
                finish();
            }
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            initConfig(getApplicationContext());
            Map<String, String> map = new HashMap<String, String>();
            map.put(CheckAuthUtil.APP_NAME, "mread");
            map.put(CheckAuthUtil.APP_RECENT_TIME, DateUtil.getCurrDateTime());
            return mHttpUtil.GetKVRequest(CheckAuthUtil.CHECKAPPAUTH, null, map);
        }

        @Override
        protected void onPostExecute(String result) {
            if (StringUtils.isNotBlank(result)) {
                if (CheckAuthUtil.checkAuth(result)) {
                    toMain();
                } else {
                    ViewUtil.showAalert(mContext, false, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }, "本软件授权时间结束，请联系开发人员！");
                }
            } else {
                toMain();
            }
            super.onPostExecute(result);
        }
    }

    private void toMain() {
        Intent i = new Intent();
        i.setClass(mContext, LoginActivity.class);
        mContext.startActivity(i);
        finish();
    }

    /**
     * 初始化bmob配置
     *
     * @param context
     */
    private static void initConfig(Context context) {
        Bmob.initialize(context, Cmd.APPID);
        BmobConfiguration config = new BmobConfiguration.Builder(context).customExternalCacheDir("mRead").build();
        BmobPro.getInstance(context).initConfig(config);
        DebugUtil.debug("mtcle", "bmob init success!");
    }
}
