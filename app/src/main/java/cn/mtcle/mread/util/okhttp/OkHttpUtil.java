package cn.mtcle.mread.util.okhttp;

import android.content.Context;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import cn.mtcle.mread.util.Cmd;
import cn.newcapec.nfc.core.util.DebugUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by mtcle on 2016/3/18.
 */
public class OkHttpUtil {
    private final static String TAG = "OkHttpUtil";
    private static OkHttpClient M_OK_HTTP_CLIENT = new OkHttpClient();
    private Context mContext;

    public OkHttpUtil(Context mContext) {
        this.mContext = mContext;
        M_OK_HTTP_CLIENT.newBuilder().connectTimeout(30, TimeUnit.SECONDS).build();
    }


    /**
     * 不开启异步线程
     *
     * @param request
     * @return
     * @throws IOException
     * @author wangsong 2015-10-9
     */
    private static Response execute(Request request) throws IOException {
        return M_OK_HTTP_CLIENT.newCall(request).execute();
    }

    /**
     * 开启异步线程访问，访问结果自行处理
     *
     * @param request
     * @param responseCallback
     * @author wangsong 2015-10-9
     */
    public static void enqueue(Request request, Callback responseCallback) {
        M_OK_HTTP_CLIENT.newCall(request).enqueue(responseCallback);
    }

    /**
     * 开启异步线程访问,不对访问结果进行处理
     *
     * @param request
     * @param
     */
    public static void enqueue(Request request) {
        M_OK_HTTP_CLIENT.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    /**
     * 为HttpGet请求拼接多个参数
     *
     * @param url
     * @param
     * @param
     * @author wangsong 2015-10-9
     */
    public static String jointURL(String url, Map<String, String> values) {
        StringBuffer result = new StringBuffer();
        result.append(url).append("?");
        Set<String> keys = values.keySet();
        for (String key : keys) {
            result.append(key).append("=").append(values.get(key)).append("&");
        }
        return result.toString().substring(0, result.toString().length() - 1);
    }

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * json请求
     *
     * @param url
     * @param jsonParma
     * @return
     */
    public String PostJsonRequest(String url, String jsonParma) {
        RequestBody body = RequestBody.create(JSON, jsonParma);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = M_OK_HTTP_CLIENT.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
            DebugUtil.error(TAG, "error:" + e.getMessage());
            return null;
        }
    }

    /**
     * post表单提交 map
     *
     * @param url
     * @param reqData
     * @return
     */
    public String PostKVRequest(String url, Map<String, String> reqData) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : reqData.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        DebugUtil.error(TAG, "请求入参:" + reqData);
        RequestBody formBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        try {
            Response response = M_OK_HTTP_CLIENT.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
            DebugUtil.error(TAG, "error:" + e.getMessage());
            return null;
        }
    }

    /**
     * get请求
     *
     * @param file
     * @param type
     * @param reqData
     * @return
     */
    public String GetKVRequest(String file, String type, Map<String, String> reqData) {
        Request request = new Request.Builder().url(getUrl(file, type, reqData)).build();
        try {
            Response response = M_OK_HTTP_CLIENT.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
            DebugUtil.error(TAG, "error:" + e.getMessage());
            return null;
        }
    }


    /**
     * 拼接请求参数
     *
     * @param file
     * @param type
     * @param params
     * @return
     */
    private String getUrl(String file, String type, Map<String, String> params) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(Cmd.CheckAppBaseUrl);
        urlBuilder.append(file);
        urlBuilder.append("?");
//        if (type != null) {
//            urlBuilder.append("type=" + type);
//        }

        try {
            if (null != params) {
                urlBuilder.append("&");
            } else {
                return urlBuilder.toString();
            }
            Iterator<Map.Entry<String, String>> iterator = params.entrySet()
                    .iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> param = iterator.next();
                urlBuilder.append(URLEncoder.encode(param.getKey(), "UTF-8"))
                        .append('=')
                        .append(URLEncoder.encode(param.getValue(), "UTF-8"));
                if (iterator.hasNext()) {
                    urlBuilder.append('&');
                }
            }
            DebugUtil.debug(TAG, "get请求入参：" + urlBuilder.toString());
            return urlBuilder.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
