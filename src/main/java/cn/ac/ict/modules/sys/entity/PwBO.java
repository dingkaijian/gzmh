package cn.ac.ict.modules.sys.entity;

import cn.ac.ict.modules.user.entity.UserInfo;

public class PwBO {
	
	private AuthBO authBO;
	
	private String reson;//密码修改原因
	
	private String validateCode;//验证码
	
	private String userid;
	
	private String oldpw;
	
	private String type;
	
	private String number;
	
	
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOldpw() {
		return oldpw;
	}

	public void setOldpw(String oldpw) {
		this.oldpw = oldpw;
	}


	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public AuthBO getAuthBO() {
		return authBO;
	}

	public String getReson() {
		return reson;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setAuthBO(AuthBO authBO) {
		this.authBO = authBO;
	}

	public void setReson(String reson) {
		this.reson = reson;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	
	public UserInfo convertUserInfo(boolean isNewRecord) {
		UserInfo userInfo=new UserInfo();
		userInfo.setId(userid);
		userInfo.setLogin(authBO.getLogin());
		userInfo.setMobile(authBO.getMobile());
		return userInfo;
	}

	@Override
	public String toString() {
		return "PwBO [authBO=" + authBO + ", reson="
				+ reson + ", validateCode=" + validateCode + ", userid=" + userid + "]";
	}

	
}
