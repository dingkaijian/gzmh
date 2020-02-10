package cn.ac.ict.modules.sys.entity;
import javax.validation.groups.Default;
import org.hibernate.validator.constraints.NotBlank;

import cn.ac.ict.common.annotation.EncAndDecDaoField;
import cn.ac.ict.common.annotation.MobileCodeConstraint;
import cn.ac.ict.common.security.encryption.sm.SM2Util;
import cn.ac.ict.modules.user.entity.UserAuth;
public class AuthBO {

	private String userid;// 用户ID
	@NotBlank(groups = Default.class, message = "登录名不能为空")
	private String login;// 用户登录名
	@NotBlank(groups = Default.class, message = "手机号码不能为空")
	private String mobile;// 手机号
	@NotBlank(groups = Default.class, message = "验证码不能为空")
	@MobileCodeConstraint(groups = Default.class, message = "请输入正确的手机验证码")
	private String code;// 验证码
	@NotBlank(groups = Default.class, message = "登录密码不能为空")
	private String pw;// 密码
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public UserAuth convertUserAuth(boolean isNewRecord){
		UserAuth userAuth = new UserAuth();
		userAuth.setId(this.userid);
		userAuth.setLogin(this.login);
		userAuth.setPw(SM2Util.doPassword(this.pw, "1"));
		userAuth.setMobile(this.mobile);
		userAuth.setEncMode("1");//sm3加密方式
		userAuth.setIsNewRecord(isNewRecord);
		return userAuth;
	}
	
	@Override
	public String toString() {
		return "AuthBO [userid=" + userid + ", login=" + login + ", mobile=" + mobile + ", code=" + code + ", pw=" + pw
				+ "]";
	}
	
}
