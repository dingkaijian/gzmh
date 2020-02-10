package cn.ac.ict.modules.pw.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
/**
 * 所对应的表 WEAK_PW
 * 常见弱密码Entity
 */
public class WeakPw extends DataEntity<WeakPw>{
	
	private static final long serialVersionUID = 1L;
	private String password;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	
}
