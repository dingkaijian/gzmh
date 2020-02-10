package cn.ac.ict.shiro.oauth2;

import org.apache.shiro.authc.AuthenticationToken;

import cn.ac.ict.common.UserLoginInformation;


/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-18
 * <p>Version: 1.0
 */
public class OAuth2Token implements AuthenticationToken {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OAuth2Token(String authCode) {
        this.authCode = authCode;
    }

    private String authCode;
    private String principal;
    private UserLoginInformation ui;
    private String dynamickey;
    //private UserLoginInfo userLoginInfo;

    public UserLoginInformation getUi() {
		return ui;
	}

	public void setUi(UserLoginInformation ui) {
		this.ui = ui;
	}

	public String getDynamickey() {
		return dynamickey;
	}

	public void setDynamickey(String dynamickey) {
		this.dynamickey = dynamickey;
	}

	public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

	public Object getCredentials() {
		 return authCode;
	}

	/*public UserLoginInfo getUserLoginInfo() {
		return userLoginInfo;
	}

	public void setUserLoginInfo(UserLoginInfo userLoginInfo) {
		this.userLoginInfo = userLoginInfo;
	}*/

}
