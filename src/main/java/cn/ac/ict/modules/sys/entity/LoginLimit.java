package cn.ac.ict.modules.sys.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/** 
* @author liguoqiang
* @version 2019年11月27日 下午4:43:19
*/
public class LoginLimit extends DataEntity<LoginLimit>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date loginTime;
	private Integer locked;
	private Integer loginCount;
	
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public Integer getLocked() {
		return locked;
	}
	public void setLocked(Integer locked) {
		this.locked = locked;
	}
	public Integer getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}
	
	

}
