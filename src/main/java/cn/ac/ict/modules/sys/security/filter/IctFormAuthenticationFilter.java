package cn.ac.ict.modules.sys.security.filter;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;

import cn.ac.ict.common.utils.CertUtils;
import cn.ac.ict.modules.client.entity.OAuthClient;
import cn.ac.ict.modules.client.service.OAuthClientService;
import cn.ac.ict.modules.sys.security.token.UsernamePasswordToken;
import cn.ac.ict.modules.sys.service.UserService;
import cn.ac.ict.modules.sys.utils.Constants;
import cn.ac.ict.modules.sys.utils.ExceptionUtils;
import cn.ac.ict.modules.sys.utils.LogUtils;
import cn.ac.ict.modules.user.entity.UserAuth;
import cn.ac.ict.modules.user.entity.UserOrg;
import cn.ac.ict.modules.user.service.UserAuthService;

/**
 * 基于Form表单的身份验证过滤器
 * @author changyanbo
 *
 */
@Service
public class IctFormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

	private String validateCodeParam = Constants.DEFAULT_VALIDATE_CODE_PARAM;
	private String messageParam = Constants.DEFAULT_MESSAGE_PARAM;
	private String keyParam = Constants.DEFAULT_KEY_PARAM;
	
	@Autowired
	private UserAuthService userAuthService;
	@Autowired
	private OAuthClientService oAuthClientService;
	@Autowired
	private UserService userService;
	

	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		AuthenticationToken token=null;
		try{
			String username = getUsername(request);
			String password = getPassword(request);
			String password_pbe = getPasswordPbe(request); 
			String userType = getUserType(request);
			String mobileCode = getMobileCode(request);
			// 获取ip地址
			String host = StringUtils.getRemoteAddr((HttpServletRequest)request);
			// 获取验证码
			String validateCode = getValidateCode(request);
			//从客户端证书中读取用户姓名
			String caUserName = CertUtils.getClientCertCNameWithNginx(request);
			// 获取加密用的key
			//String key = getKey(request);
			token = new UsernamePasswordToken(username, password.toCharArray(), mobileCode,host, validateCode, password_pbe,userType,caUserName);
		}catch(Exception e){
			e.printStackTrace();
			throw new AuthenticationException("msg:系统错误");
		}
		return token;
	}
	
	/**
	 * 获取登录用户名
	 */
	protected String getUsername(ServletRequest request) {
		String username = request.getParameter(Constants.REQUEST_LOGINNAME);//super.getUsername(request);
		if (StringUtils.isBlank(username)){
			username = StringUtils.toString(request.getAttribute(getUsernameParam()), StringUtils.EMPTY);
		}
		return username;
	}
	/**
	 * 获取手机验证码
	 * @param request
	 * @return
	 */
	protected String getMobileCode(ServletRequest request) {
		return request.getParameter("mobileCode");
	}
	
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		HttpServletRequest rq = (HttpServletRequest)request;
		HttpSession session = rq.getSession();
		UserAuth ui = userAuthService.getByLoginName(getUsername(request));
			List<UserOrg> userOrgList  = userService.get(ui.getId());
			ui.setEmail("自然人");
		ui.setUserip(StringUtils.getRemoteAddr((HttpServletRequest)request));
		SavedRequest savedRequest = WebUtils.getSavedRequest(request);
		ui.setSystem(Global.getConfig("onLineSystem"));
		if(savedRequest != null){
			String str = savedRequest.getRequestUrl();
			if(StringUtils.isNotEmpty(str) && str.indexOf("client_id") > 0){
				String clientId = str.substring(str.indexOf("client_id")+10, str.indexOf("&",str.indexOf("client_id")));
				OAuthClient oAuthClient = oAuthClientService.getClientByClientId(clientId);
				if (oAuthClient!=null) ui.setSystem(oAuthClient.getClientName());
			}
		}
		session.setAttribute("onlineUser", ui);
		return super.onLoginSuccess(token, subject, request, response);
	}

	/**
	 * 获取登录密码
	 */
	@Override
	protected String getPassword(ServletRequest request) {
		String password = request.getParameter(Constants.REQUEST_PASSWORD);//super.getPassword(request);
		if (StringUtils.isBlank(password)){
			password = StringUtils.toString(request.getAttribute(getPasswordParam()), StringUtils.EMPTY);
		}
		return password;
	}
	
	/**
	 * 获取登录密码PBE
	 */
	protected String getPasswordPbe(ServletRequest request) {
		String passwordPbe = request.getParameter(Constants.REQUEST_PASSWORD_PBE);
		if (StringUtils.isBlank(passwordPbe)){
			passwordPbe = StringUtils.toString(request.getAttribute("password_pbe"), StringUtils.EMPTY);
		}
		return passwordPbe;
	}
	
	protected String getUserType(ServletRequest request){
		String userType = request.getParameter(Constants.REQUEST_USER_TYPE);
		if (StringUtils.isBlank(userType)){
			userType = StringUtils.toString(request.getAttribute("userType"), StringUtils.EMPTY);
		}
		return userType;
	}
	
	/**
	 * 登录失败调用事件
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request, ServletResponse response) {
		try{
			UsernamePasswordToken userToken = (UsernamePasswordToken) token;
			String className = e.getClass().getName();
			String message = userToken.getMessage()==null?ExceptionUtils.getLoginMessage(e):userToken.getMessage();
	        request.setAttribute(getFailureKeyAttribute(), className);
	        request.setAttribute(getMessageParam(), message);
	      //新日志添加 20190802
			LogUtils.saveLog("R", "1", "登录失败调用事件", (HttpServletRequest)request, null);
		}catch(Exception e1){
			e1.printStackTrace();
			//新日志添加 20190802
			LogUtils.saveLog("R", "2", "登录失败调用事件时发生错误！", (HttpServletRequest)request, e1);
			throw new AuthenticationException("msg:系统错误");
		}
        return true;
	}
	
	public String getValidateCodeParam() {
		return validateCodeParam;
	}

	protected String getValidateCode(ServletRequest request) {
		return WebUtils.getCleanParam(request, getValidateCodeParam());
	}

	public String getMessageParam() {
		return messageParam;
	}
	
	public String getKeyParam() {
		return keyParam;
	}
	
	protected String getKey(ServletRequest request) {
		return WebUtils.getCleanParam(request, getKeyParam());
	}

	/**
	 * 登录成功之后跳转URL
	 */
	public String getSuccessUrl() {
		return super.getSuccessUrl();
	}
	
	@Override
	protected void issueSuccessRedirect(ServletRequest request,
			ServletResponse response) throws Exception {
//		Principal p = UserUtils.getPrincipal();
//		if (p != null && !p.isMobileLogin()){
			WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
//		}else{
//			super.issueSuccessRedirect(request, response);
//		}
	}

}
