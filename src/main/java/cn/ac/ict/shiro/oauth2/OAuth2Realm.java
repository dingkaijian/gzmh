package cn.ac.ict.shiro.oauth2;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import cn.ac.ict.common.UserLoginInformation;
import cn.ac.ict.modules.user.entity.UserInfo;
public class OAuth2Realm extends AuthorizingRealm {

	private static final Logger logger = LoggerFactory.getLogger(OAuth2Realm.class);
	private String clientId;
	private String clientSecret;
	private String accessTokenUrl;
	private String userInfoUrl; 
	private String redirectUrl;
	private SystemService systemService;
	
    public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public String getAccessTokenUrl() {
		return accessTokenUrl;
	}
	public void setAccessTokenUrl(String accessTokenUrl) {
		this.accessTokenUrl = accessTokenUrl;
	}
	public String getUserInfoUrl() {
		return userInfoUrl;
	}
	public void setUserInfoUrl(String userInfoUrl) {
		this.userInfoUrl = userInfoUrl;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	@Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("==============OAuth2RealmdoGetAuthenticationInfo==============");
		OAuth2Token oAuth2Token = (OAuth2Token) token;
		String code = oAuth2Token.getAuthCode();
		UserLoginInformation userLoginInformation = extractUsername(code, oAuth2Token);
		UserInfo userInfo = new UserInfo();
		userInfo.setId(userLoginInformation.getUserid());
		userInfo.setUserName(userLoginInformation.getUsername());
		userInfo.setLogin(userLoginInformation.getLogin());
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(new Principal(userInfo, false),code,getName());
        return authenticationInfo;
    }
    /**
   	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
   	 */
   	@Override
   	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) { 
   		System.out.println("==============OAuth2RealmdoGetAuthorizationInfo==============");
   		Principal principal = (Principal) getAvailablePrincipal(principals);
   		if (!Global.TRUE.equals(Global.getConfig("user.multiAccountLogin"))) {
			Collection<Session> sessions = getSystemService().getSessionDao().getActiveSessions(true,principal,UserUtils.getSession());
			if (sessions.size()>0) {
				if (UserUtils.getSubject().isAuthenticated()) {
					for (Session session : sessions) {
						getSystemService().getSessionDao().delete(session);
					}
				}else{
					UserUtils.getSubject().logout();
					throw new AuthenticationException("msg:账号已在其它地方登录，请重新登录。");
				}
			}
		}
   		if (principal != null) {
   			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
   			info.addStringPermission("user");
   			return info;
   		}else{
   			return null;
   		}
		
    }
   	
    private UserLoginInformation extractUsername(String code, OAuth2Token token) {
    	System.out.println("==============OAuth2RealmextractUsername==============");
    	OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
    	try {
			OAuthClientRequest accessTokenRequest = OAuthClientRequest.tokenLocation(accessTokenUrl)
					.setGrantType(GrantType.AUTHORIZATION_CODE)
					.setClientId(clientId)
					.setClientSecret(clientSecret)
					.setCode(code)
					.setRedirectURI(redirectUrl)
					.buildQueryMessage();
			OAuthAccessTokenResponse oAuthResponse = oAuthClient.accessToken(accessTokenRequest, OAuth.HttpMethod.POST);
			String accessToken = oAuthResponse.getAccessToken();
			Long expiresIn = oAuthResponse.getExpiresIn();
			
			OAuthClientRequest userInfoRequest = new OAuthBearerClientRequest(userInfoUrl).setAccessToken(accessToken).buildQueryMessage();
			
			OAuthResourceResponse resourceResponse = oAuthClient.resource(userInfoRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
			String responseBody = resourceResponse.getBody();
			
			 //将返回数据转换为map对象
            @SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>)JSON.parse(responseBody);
            //从map中取得userInfo对象
            UserLoginInformation userInfo = getUserInfoFromResource(map, token);
            return userInfo;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new OAuth2AuthenticationException(e);
		}
    	
    }
    /**
     * 从资源服务3.0系统获取数据
     * @param map 资源服务3.0系统返回的json字符串，转换成的Map<String,Object> map
     * @param token 不是认证过程中的Token令牌，相当于UsernamePassWordToken，用于封装信息
     * @return 返回解析后的数据
     */
    private UserLoginInformation getUserInfoFromResource(Map<String, Object> map, OAuth2Token token){
    	System.out.println("==============OAuth2RealmgetUserInfoFromResource==============");
    	UserLoginInformation userInfo = null;
    	UserInfo ui = null;
    	String code = (String) map.get("code");
    	//code是资源服务返回的状态值，0表示获取用户信息成功
		if("0".equals(code)){// 获取成功
			@SuppressWarnings("unchecked")
			Map<String, Object> data = (Map<String, Object>) map.get("data");
			@SuppressWarnings("unchecked")
			Map<String, Object> infoMap = (Map<String, Object>) data.get("infoMap");
			//用户信息
			String userInfo_json = JSON.toJSONString(infoMap.get("userInfo"));
			ui = JSON.parseObject(userInfo_json, UserInfo.class);
			userInfo = JSON.parseObject(userInfo_json, UserLoginInformation.class);
	        userInfo.setUserid(userInfo.getId()==null?"":userInfo.getId());
	        userInfo.setLogin(ui.getUserAuth().getLogin());
	        token.setUi(userInfo);
	        //动态秘钥，用于加密解密
	        String dynamicKey = infoMap.get("dynamicKey").toString();
	        token.setDynamickey(dynamicKey);
	        /*if (userInfo ==null || "".equals(anObject)) {
				response.setContentType("text/html; charset=" + ENCODING);
				response.getWriter()
						.write("<script language='javascript'>alert('用户未登陆或会话已超时，请重新登陆！');window.close();</script>");
				return false;
			}*/
		} else {
			/*String message = (String) map.get("message");
			if(message == null || "".equals(message)){
				message = "获取用户信息失败！";
			}
			response.setContentType("text/html; charset=" + ENCODING);
			response.getWriter()
					.write("<script language='javascript'>alert('"+ message+"');window.close();</script>");
			return false;*/
		}
		System.out.println("*************userInfo:"+userInfo);
		return userInfo;
    }
   
    public SystemService getSystemService() {
		if (systemService == null){
			systemService = SpringContextHolder.getBean(SystemService.class);
		}
		return systemService;
	}
    /**
   	 * 授权用户信息
   	 */
   	public static class Principal implements Serializable {

   		private static final long serialVersionUID = 1L;
   		
   		private String id; // 编号
   		private String loginName; // 登录名
   		private String name; // 姓名
   		private boolean mobileLogin; // 是否手机登录

   		public Principal(UserInfo user, boolean mobileLogin) {
   			this.id = user.getId();
			this.loginName = user.getLogin();
			this.name = user.getUserName();
			this.mobileLogin = mobileLogin;
   		}
   		
   		public String getId() {
   			return id;
   		}

   		public String getLoginName() {
   			return loginName;
   		}

   		public String getName() {
   			return name;
   		}

   		public boolean isMobileLogin() {
   			return mobileLogin;
   		}
   		

//   		@JsonIgnore
//   		public Map<String, Object> getCacheMap() {
//   			if (cacheMap==null){
//   				cacheMap = new HashMap<String, Object>();
//   			}
//   			return cacheMap;
//   		}


		/**
   		 * 获取SESSIONID
   		 */
   		public String getSessionid() {
   			try{
   				return (String) UserUtils.getSession().getId();
   			}catch (Exception e) {
   				e.printStackTrace();
   				return "";
   			}
   		}
   		
   		@Override
   		public String toString() {
   			return id;
   		}

   	}
   	
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;//表示此Realm只支持OAuth2Token类型
    }
    
    
}
