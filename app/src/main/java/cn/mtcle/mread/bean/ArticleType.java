package cn.mtcle.mread.bean;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by mtcle on 2016/3/18.
 */
public class ArticleType implements Serializable {
    private String type_name;
    private String type_id;

    private BmobFile type_icon_img;//帖子图片

    public ArticleType() {
    }

    public BmobFile getType_icon_img() {
        return type_icon_img;
    }

    public void setType_icon_img(BmobFile type_icon_img) {
        this.type_icon_img = type_icon_img;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }


    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
