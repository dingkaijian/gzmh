package cn.ac.ict.modules.oauth.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.web.BaseController;

import cn.ac.ict.modules.oauth.handler.CodeAuthorizeHandler;
import cn.ac.ict.modules.oauth.utils.MyOAuthUtils;
import cn.ac.ict.modules.sys.utils.LogUtils;

@Controller
@RequestMapping("${adminPath}/oauth/")
public class OAuthAuthorizeController extends BaseController {

	/**
	 * 非主入口 暂时未用
	 * 当用户是否登录交由shiro的拦截器判断时，该方法将不再被执行（因goLogin()和submitLogin()方法被已注释掉了）
	 * 若不交给shiro处理，则打开goLogin()和submitLogin()方法的注释，
	 * 同时spring-context-shiro.xml文件将路径sso/oauth/login调为anno，
	 * 那么是否跳转到登录页面将由CodeAuthorizeHandler类的handle方法判断
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "login")
	public String oauthLogin(HttpServletRequest request, Model model) {
		// 生成秘钥，用于密码加密
		//HttpSession session = request.getSession();
		//String randomEncStr = PasswordSecurity.generatorKey(session);
		//session.setAttribute("key", randomEncStr);
		//model.addAttribute("key", randomEncStr);
		return "modules/sys/sysLogin";
	}

	/**
	 * 主入口
	 * 处理response_type='code' grant_type="authorization_code" ->
	 * response_type="code"
	 * oauth/authorize?response_type=code&scope=read,write&client_id
	 * =[client_id]&redirect_uri=[redirect_uri]&state=[state]
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("authorize")
	public void authorize(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("===================authorize");
		// 如果用户没有完成注册的全部过程，跳转至注册系统，继续完成注册
		/*String regPage = userAuthService.redirectRegPage(false);
		if(StringUtils.isNotBlank(regPage)){
			response.sendRedirect(regPage);
		}*/
		
		try {
			// 构建OAuth授权请求
			OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);

			if (MyOAuthUtils.isCode(oauthRequest)) {
				// 处理code请求
				logger.debug("Go to response_type = 'code'");
				//新日志添加 20190802
				LogUtils.saveLog("R","1","======response_type是code，处理code请求=======", request, null);
				CodeAuthorizeHandler codeAuthorizeHandler = new CodeAuthorizeHandler(
						oauthRequest, request, response);
				codeAuthorizeHandler.handle();
			} else {
				//新日志添加 20190802
				LogUtils.saveLog("R","1","======不支持的response_type=======", request, null);
				unsupportResponseType(oauthRequest, response);
			}

		} catch (OAuthProblemException e) {
			e.printStackTrace();
			//新日志添加 20190802
			LogUtils.saveLog("R","2","======构建Oauth2授权是发生异常，执行出错处理程序=======", request, e);
			// 出错处理
			OAuthResponse oAuthResponse = OAuthASResponse
					.errorResponse(HttpServletResponse.SC_ACCEPTED)
					.location(e.getRedirectUri()).error(e).buildJSONMessage();
			MyOAuthUtils.writeOAuthJsonResponse(response, oAuthResponse);
		}

	}

	/**
	 * 不支持的response_type
	 * 
	 * @param oauthRequest
	 * @param response
	 * @throws OAuthSystemException
	 */
	private void unsupportResponseType(OAuthAuthzRequest oauthRequest,
			HttpServletResponse response) throws OAuthSystemException {
		final String responseType = oauthRequest.getResponseType();
		logger.warn("Unsupport response_type '{}' by client_id '{}'",
				responseType, oauthRequest.getClientId());

		OAuthResponse oAuthResponse = OAuthResponse
				.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
				.setError(OAuthError.CodeResponse.UNSUPPORTED_RESPONSE_TYPE)
				.setErrorDescription(
						"Unsupport response_type '" + responseType + "'")
				.buildJSONMessage();
		MyOAuthUtils.writeOAuthJsonResponse(response, oAuthResponse);
	}
	
}