package com.thinkgem.jeesite.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 此类存在的意义仅仅是在反序列化用户session，解决政务平台门户Principal对象的依赖关系
 * @author dingkaijian
 *
 */
public class UserLoginInformation implements Serializable
{
	
	@Override
	public String toString() {
		return "UserLoginInformation [userid=" + userid + ", login=" + login + ", username=" + username + ", userip="
				+ userip + ", orgid=" + orgid + ", orgname=" + orgname + ", roles=" + roles + ", userroles=" + userroles
				+ ", mobile=" + mobile + ", usertype=" + usertype + "]";
	}

	private static final long serialVersionUID = 1L;
	//用户ID
	private String userid = "";
	//用户登录名
	private String login = "";
	//用户姓名
	private String username = "";
	//用户IP地址
	private String userip = "";
	//单位ID
	private String orgid = "";
	//单位名称
	private String orgname = "";
	//用户角色
	private String roles = "";
	//用户角色
	private Map<String, String> userroles = new HashMap<String, String>();
	//用户手机号
	private String mobile="";
	//用户类型
	private String usertype="";
	//用户类型
	private String id="";
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserip() {
		return userip;
	}

	public void setUserip(String userip) {
		this.userip = userip;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public Map<String, String> getUserroles() {
		return userroles;
	}

	public void setUserroles(Map<String, String> userroles) {
		this.userroles = userroles;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
}