package cn.mtcle.mread.util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.newcapec.nfc.core.util.StringUtils;

/**
 * Created by mtcle on 2016/3/18.
 */
public class CheckAuthUtil {
    public final static String APP_NAME = "app_name";
    public final static String APP_RECENT_TIME = "recent_time";
    public final static String CHECKAPPAUTH = "checkAppAuth";

    public static boolean checkAuth(String jsonRespond) {
        if (StringUtils.isNotBlank(jsonRespond)) {
            JSONObject jsonObject = JSONObject.parseObject(jsonRespond);
            JSONArray array = jsonObject.getJSONArray("results");
            if (array != null && array.size() > 0) {
                JSONObject object = array.getJSONObject(0);
                String canUse = object.getString("canUsed");
                if (canUse.equals("true")) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
        return false;
    }
}
