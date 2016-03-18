package cn.mtcle.mread.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 首页的在线列表的基类 friends_id：好友id字符串 user_name：用户名 user_id：用户id user_sex：用户性别
 * user_ico：用户头像 reside_province：用户常驻地-省 reside_city：用户常驻地-市 dressup：用户中心装扮
 * user_rights：用户权限 money：用户金币 user_level：用户等级（0：普通1高级2vip） user_zan：给我点赞数
 * msgnum：用户未读信息条数
 * 
 * @author mtcle
 * 
 */
public class Persion extends CommonResp implements Serializable {

	private static final long serialVersionUID = 1272229826821549802L;

	private String friends_id;// ：好友id字符串
	private String user_name;// ：用户名
	private String user_id;// ：用户id
	private String user_sex;// ：用户性别
	private String user_ico;// ：用户头像
	private String reside_province;// ：用户常驻地-省
	private String reside_city;// ：用户常驻地-市
	private String dressup;// ：用户中心装扮
	private String user_rights;// ：用户权限
	private float money;// ：用户金币
	private String user_level;// ：用户等级（0：普通1高级2vip）
	private String user_zan;// ：给我点赞数
	private String msgnum;// ：用户未读信息条数

	private String token;//token
	public Persion() {
	}

	
	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public String getFriends_id() {
		return friends_id;
	}


	public void setFriends_id(String friends_id) {
		this.friends_id = friends_id;
	}


	public String getUser_name() {
		return user_name;
	}


	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}


	public String getUser_id() {
		return user_id;
	}


	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}


	public String getUser_sex() {
		return user_sex;
	}


	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
	}


	public String getUser_ico() {
		return user_ico;
	}


	public void setUser_ico(String user_ico) {
		this.user_ico = user_ico;
	}


	public String getReside_province() {
		return reside_province;
	}


	public void setReside_province(String reside_province) {
		this.reside_province = reside_province;
	}


	public String getReside_city() {
		return reside_city;
	}


	public void setReside_city(String reside_city) {
		this.reside_city = reside_city;
	}


	public String getDressup() {
		return dressup;
	}


	public void setDressup(String dressup) {
		this.dressup = dressup;
	}


	public String getUser_rights() {
		return user_rights;
	}


	public void setUser_rights(String user_rights) {
		this.user_rights = user_rights;
	}


	public float getMoney() {
		return money;
	}


	public void setMoney(float money) {
		this.money = money;
	}


	public String getUser_level() {
		return user_level;
	}


	public void setUser_level(String user_level) {
		this.user_level = user_level;
	}


	public String getUser_zan() {
		return user_zan;
	}


	public void setUser_zan(String user_zan) {
		this.user_zan = user_zan;
	}


	public String getMsgnum() {
		return msgnum;
	}


	public void setMsgnum(String msgnum) {
		this.msgnum = msgnum;
	}


	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}
