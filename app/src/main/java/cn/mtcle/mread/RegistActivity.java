package cn.mtcle.mread;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import cn.bmob.v3.listener.SaveListener;
import cn.mtcle.mread.bean.CommonResp;
import cn.mtcle.mread.bean.User;
import cn.mtcle.mread.common.BaseFirstLevActivity;
import cn.mtcle.mread.util.ViewUtil;
import cn.newcapec.nfc.core.util.DebugUtil;
import cn.newcapec.nfc.core.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

public class RegistActivity extends BaseFirstLevActivity implements
        OnClickListener {

    private Button btn_commit;
    private EditText et_username, et_mail, et_password;
    private RadioGroup rg_gender;

    private TextView tv_birthday;
    private String uAccount, uPassword, uEmail, uBirthday, uSex = "0", uYear,
            uMonth, uDay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.initView();
        super.setChildContentView(R.layout.activity_regist_next);
        initControl();
    }

    private void initControl() {
        btn_commit = (Button) findViewById(R.id.btnrigst_next);
        btn_commit.setOnClickListener(this);
        et_username = (EditText) findViewById(R.id.et_regist_username);
        et_mail = (EditText) findViewById(R.id.et_regist_email);
        et_password = (EditText) findViewById(R.id.et_regist_password);
        rg_gender = (RadioGroup) findViewById(R.id.rg_gender);
        tv_birthday = (TextView) findViewById(R.id.tv_regist_brithday);
        tv_birthday.setOnClickListener(this);
        rg_gender.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 1男0女
                if (checkedId == R.id.rb_man) {
                    uSex = "1";
                } else if (checkedId == R.id.rb_woman) {
                    uSex = "0";
                }
                DebugUtil.debug("选择：" + uSex);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnrigst_next:
                if (validateInput()) {
                    showProgressDialog("注册中...");
                    doRegister();
                }
                break;
            case R.id.tv_regist_brithday:
                showDataPicker();
                break;
            default:
                break;
        }
    }

    private void doRegister() {
        /**
         * 注册用户
         */
        User myUser = new User();
        myUser.setUsername(uAccount);
        myUser.setPassword(uPassword);
        myUser.setBirthday(uBirthday);
        myUser.setSex(uSex);
        myUser.setEmail(uEmail);
        myUser.setEmailVerified(true);
        myUser.signUp(this, new SaveListener() {

            @Override
            public void onSuccess() {
                ViewUtil.showToast(mContext, "恭喜您，注册成功！");
                mContext.startActivity(new Intent(mContext,
                        LoginActivity.class));
                finish();
            }

            @Override
            public void onFailure(int code, String msg) {
                ViewUtil.showToast(mContext, "注册失败:" + msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                closeProgressDialog();
            }
        });
}

    private void showDataPicker() {
        final Calendar c = Calendar.getInstance();
        Dialog datePickerDialog = new DatePickerDialog(mContext,
                new OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        DebugUtil.debug(TAG, ":" + year + ";" + monthOfYear
                                + ";" + dayOfMonth);
                        tv_birthday.setText(year + "/" + (monthOfYear + 1)
                                + "/" + dayOfMonth);
                        uYear = String.valueOf(year);
                        uMonth = String.valueOf(monthOfYear + 1);
                        uDay = String.valueOf(dayOfMonth);
                        uBirthday = tv_birthday.getText().toString();
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private boolean validateInput() {
        uAccount = et_username.getText().toString().trim();
        uPassword = et_password.getText().toString().trim();
        uEmail = et_mail.getText().toString().trim();
        if (StringUtils.isBlank(uAccount)) {
            ViewUtil.showToast(mContext, "请输入用户名！");
            et_username.setFocusable(true);
            et_username.setFocusableInTouchMode(true);
            et_username.requestFocus();
            return false;
        } else if (uAccount.length() < 6) {
            ViewUtil.showToast(mContext, "用户名至少六位长度！");
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
        } else if (StringUtils.isBlank(uEmail)) {
            ViewUtil.showToast(mContext, "邮箱不能为空！");
            et_mail.setFocusable(true);
            et_mail.setFocusableInTouchMode(true);
            et_mail.requestFocus();
            return false;
        } else if (StringUtils.isBlank(uSex)) {
            ViewUtil.showToast(mContext, "请选择性别！");
            return false;
        } else if (StringUtils.isBlank(uBirthday)) {
            ViewUtil.showToast(mContext, "请选择生日！");
            return false;
        }
        return true;
    }
}
