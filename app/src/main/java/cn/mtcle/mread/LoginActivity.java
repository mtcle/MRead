package cn.mtcle.mread;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import cn.mtcle.mread.common.BaseFirstLevActivity;
import cn.mtcle.mread.util.ViewUtil;
import cn.mtcle.mread.util.okhttp.OkHttpUtil;
import cn.newcapec.nfc.core.util.StringUtils;


public class LoginActivity extends BaseFirstLevActivity implements
        OnClickListener {

    private TextView tv_forgetPwd;
    private EditText et_username, et_password;
    private String uAccount, uPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.initView();
        super.tvBarTitle.setText(R.string.login);
        setChildContentView(R.layout.activity_login);
        initControl();
    }

    private void initControl() {

        tv_forgetPwd = (TextView) findViewById(R.id.tv_forgetPwd);
        tv_forgetPwd.setOnClickListener(this);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_pwd);
        String uAccountCache = mPreferUtil.getString("uAccount", "");
        et_username.setText(uAccountCache);
        if (StringUtils.isNotBlank(uAccountCache)) {
            et_password.setText(mPreferUtil.getString("uPassword", ""));
        }
        (findViewById(R.id.btnlogin)).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_forgetPwd:
                Intent i = new Intent();
                //	i.setClass(mContext, ForgotPWDActivity.class);
                //	startActivity(i);
                break;
            case R.id.btnlogin:
              // new TestTask().execute();//TODO
                if (validateInput()) {// 验证
                    login(uAccount,uPassword);
                }
                break;
            default:
                break;
        }

    }

    private boolean validateInput() {
        uAccount = et_username.getText().toString().trim();
        uPassword = et_password.getText().toString().trim();
        if (StringUtils.isBlank(uAccount)) {
            ViewUtil.showToast(mContext, "请输入姓名！");
            et_username.setFocusable(true);
            et_username.setFocusableInTouchMode(true);
            et_username.requestFocus();
            return false;
        } else if (StringUtils.isBlank(uPassword)) {
            ViewUtil.showToast(mContext, "密码不能为空！");
            et_password.setFocusable(true);
            et_password.setFocusableInTouchMode(true);
            et_password.requestFocus();
            return false;
        }
        return true;
    }

    private void login(final String userName,final String  userPassword) {
        final BmobUser bu2 = new BmobUser();
        bu2.setUsername(userName);
        bu2.setPassword(userPassword);
        showProgressDialog("登陆中...");
        bu2.login(this, new SaveListener() {

            @Override
            public void onSuccess() {
                ViewUtil.showToast(mContext, "登陆成功");
                //testGetCurrentUser();
                mPreferUtil.saveString("uAccount", userName);
                mPreferUtil.saveString("uPassword", userPassword);
                Intent intent = new Intent();
              //  intent.setClass(mContext, MainActivity.class);
                //mContext.startActivity(intent);
                //finish();
            }

            @Override
            public void onFailure(int code, String msg) {
                ViewUtil.showToast(mContext," "+msg+"[error code="+code+"]");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                closeProgressDialog();
            }
        });
    }

    private void testOkhttp(){
        OkHttpUtil mHttp=new OkHttpUtil(mContext);
        Map<String,String> maps=new HashMap<String,String>();
        maps.put("1","test1");
        maps.put("2","test2");
        mHttp.PostKVRequest("http://www.mtcle.ml",maps);
    }

    private class TestTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            testOkhttp();
            return null;
        }
    }
}
