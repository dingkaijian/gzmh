package cn.ac.ict.modules.sys.security.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.utils.StringUtils;

import cn.ac.ict.common.utils.CertUtils;
import cn.ac.ict.modules.sys.security.token.GzToken;
import cn.ac.ict.modules.sys.security.token.JcToken;
import cn.ac.ict.modules.sys.utils.Constants;
import cn.ac.ict.modules.sys.utils.ExceptionUtils;
import cn.ac.ict.modules.sys.utils.LogUtils;

/**
 * 工作门户登录入口
 * 
 */
@Service
public class GzLoginFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

	private static final Logger logger =LoggerFactory.getLogger(GzLoginFilter.class);
	private String validateCodeParam = Constants.DEFAULT_VALIDATE_CODE_PARAM;
	private String messageParam = Constants.DEFAULT_MESSAGE_PARAM;
	private String keyParam = Constants.DEFAULT_KEY_PARAM;

	
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);	
		String validateCode = getValidateCode(request);
		String mobileCode = getMobileCode(request);
		//从客户端证书中读取用户姓名
		String caUserName = CertUtils.getClientCertCNameWithNginx(request);
		// 获取ip地址
		String host = StringUtils.getRemoteAddr((HttpServletRequest)request);
		logger.debug("=====Gzip==="+host);
		System.out.println("=====Gzip==="+host);
		// 获取验证码
		//String validateCode = getValidateCode(request);
		// 获取加密用的key
		String key = getKey(request);
		AuthenticationToken token = new GzToken(username,password.toCharArray(),mobileCode,host,validateCode,key,caUserName);
		//新日志添加 20190802
		//LogUtils.saveLog(null, null, null, Constants.SYSTEM_NAME, "R","1","=====集成登录时获取token==========",null, username,(HttpServletRequest)request, null);
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
		//新日志添加 20190802
		//LogUtils.saveLog(null, null, null, Constants.SYSTEM_NAME, "R","1","======获取username："+username,null, username,(HttpServletRequest)request, null);
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
		logger.debug("-----Gzsuccess-----");
		SavedRequest savedRequest = WebUtils.getSavedRequest(request);		
		String str = savedRequest.getRequestUrl();
		logger.debug("-----Gzsuccessurl-----"+str);
		//新日志添加 20190802
		//LogUtils.saveLog("R","1","======执行onLoginSuccess方法=========", (HttpServletRequest)request, null);
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
		//新日志添加 20190802
		//LogUtils.saveLog("R","1","======获取password："+password, (HttpServletRequest)request, null);
		return password;
	}
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		if(!subject.isAuthenticated()){
			//saveRequestAndRedirectToLogin(request, response);
			redirectToLogin(request, response);
		}
		return executeLogin(request, response);
	}
	
	@Override
	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		setLoginUrl("/a/login"); 
	}	
	
	@Override
	public void setLoginUrl(String loginUrl) {
		super.setLoginUrl(loginUrl);
	}

	/**
	 * 登录失败调用事件
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request, ServletResponse response) {
		String className = e.getClass().getName();
		String message = ExceptionUtils.getLoginMessage(e);
        request.setAttribute(getFailureKeyAttribute(), className);
        request.setAttribute(getMessageParam(), message);
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
		super.setSuccessUrl("a?index");
	    return super.getSuccessUrl();
		//return ;
	}
	
	@Override
	protected void issueSuccessRedirect(ServletRequest request,
			ServletResponse response) throws Exception {
//		Principal p = UserUtils.getPrincipal();
//		if (p != null && !p.isMobileLogin()){
			//新日志添加 20190802
			//LogUtils.saveLog("R","1","======重定向至："+getSuccessUrl(), (HttpServletRequest)request, null);
			WebUtils.issueRedirect(request, response,"?index", null, true);
//		}else{
//			super.issueSuccessRedirect(request, response);
//		}
	}

}
