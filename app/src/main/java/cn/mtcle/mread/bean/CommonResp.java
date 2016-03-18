package cn.mtcle.mread.bean;

import java.io.Serializable;

public class CommonResp implements Serializable {

	private static final long serialVersionUID = 3899597286183365036L;
	private String msg;
	private String ok;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getOk() {
		return ok;
	}
	public void setOk(String ok) {
		this.ok = ok;
	}
	
}
