package cn.mtcle.mread.bean;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;

/**
 * Created by mtcle on 2016/3/18.
 */
public class User extends BmobUser implements Serializable {

    private String u_sex;
    private String birthday;

    public String getU_sex() {
        return u_sex;
    }
    public void setU_sex(String u_sex) {
        this.u_sex = u_sex;
    }

    public String getSex() {
        return u_sex;
    }

    public void setSex(String sex) {
        this.u_sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
