package cn.ac.ict.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.security.shiro.session.SessionDAO;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import cn.ac.ict.common.servlet.ValidateCodeServlet;
import cn.ac.ict.modules.sys.service.LoginService;
import cn.ac.ict.modules.sys.utils.Constants;
import cn.ac.ict.modules.sys.utils.LogUtils;

@Controller
public class GzLoginController extends BaseController{
	
	@Autowired
	private SessionDAO sessionDAO;
	@Autowired
	private LoginService loginService;
	@Autowired
	private SystemService systemService;
	
	/**
	 * 登录（GET方法）
	 */
	@RequestMapping(value = "${adminPath}/gzmh/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.debug("====工作门户登录=====");
		System.out.println("====工作门户登录=====");
		Principal principal = UserUtils.getPrincipal();
		
		if (logger.isDebugEnabled()){
			logger.debug("登录（GET）。活跃的session个数：{}", sessionDAO.getActiveSessions(false).size());
		}
		String mobileCodeSwitch = systemService.getSwitch("mobileCode");//手机验证码开关
		model.addAttribute("mobileCodeSwitch", mobileCodeSwitch);
		// 如果已经登录，则跳转到首页
		if(principal != null ){
			// 执行LoginController类的index方法
			//新日志添加 20190802
			//LogUtils.saveLog(null, null, null, Constants.SYSTEM_NAME, "R","1", "用户已登录，跳转到首页", principal.getLoginName(), principal.getName(), request, null);
			return "redirect:" + adminPath+"/gzmh";
		}
		
		//String userOriginRequestUrl = loginService.getOriginalSavedRequestURL(request);
		// 用户的原始请求地址中包含client_id参数
		//新日志添加 20190802
		//LogUtils.saveLog("R", "1", "跳转登录首页", request, null);
		return "modules/sys/Login";
	}

	/**
	 * 登录失败执行，真正登录的POST请求由IctFormAuthenticationFilter完成
	 */
	@RequestMapping(value = "${adminPath}/gzmh/login", method = RequestMethod.POST)
	public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.debug("====工作门户登录post======");
		System.out.println("====工作门户登录post======");
		Principal principal = UserUtils.getPrincipal();
		// 如果已经登录，则跳转到首页
		if(principal != null){
			// 执行Controller类的index方法
			return "redirect:" + adminPath+"/gzmh";
		}
		
		String username = WebUtils.getCleanParam(request, Constants.REQUEST_LOGINNAME);
		String exception = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String message = (String)request.getAttribute(Constants.DEFAULT_MESSAGE_PARAM);
		
		if (StringUtils.isBlank(message) || StringUtils.equals("null",message)){
			message = "用户或密码错误，请重试。";
		}

		model.addAttribute(Constants.REQUEST_LOGINNAME, username);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
		model.addAttribute(Constants.DEFAULT_MESSAGE_PARAM, message);
		
		if (logger.isDebugEnabled()){
			logger.debug("登录失败，活跃的session个数：{}，消息：{}，异常：{}", 
					sessionDAO.getActiveSessions(false).size(), message, exception);
		}
		
		// 验证失败清空验证码
		request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());
		
		if (logger.isDebugEnabled()){
			logger.debug("用户【" + username +"】登录失败！");
		}
		//新日志添加 20190731
		LogUtils.saveLog(null, null, null, null, "R", "1", message, null, username, request, null);
		return login(request, response, model);
	}

	/**
	 * 登录成功，进入首页
	 * 该方法写@RequiresPermissions("user")注解的目的是让shiro通过AOP方式
	 * 走获取授权信息的方法（getAuthorizationInfo和doGetAuthorizationInfo）
	 * 同时需要有user权限否则会进入无权限页面
	 * 
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "${adminPath}/gzmh")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("====工作门户登录index======");
		System.out.println("====工作门户登录index======");
		if (logger.isDebugEnabled()){
			logger.debug("登录成功，进入首页。活跃的session个数：{}", sessionDAO.getActiveSessions(false).size());
		}

		// 如果用户没有完成注册的全部过程，跳转至注册系统，继续完成注册
		//String regPage = userAuthService.redirectRegPage(true);
		/*if(StringUtils.isNotBlank(regPage)){
			// regPage不为空则重定向至注册页
			return regPage;
		}*/
		
		// 登录成功后，如果原始请求包含client_id参数，则重定向至获取授权码，即用户输完账号密码且认证通过后，开始执行获取token的一系列过程
		String requestURL = loginService.getOriginalSavedRequestURL(request);
		if(StringUtils.isNotEmpty(requestURL) && requestURL.indexOf("client_id") > 0){
			// 请求时将原始请求所有参数进行保留
			String oldUrl = WebUtils.getSavedRequest(request).getRequestUrl();
	    	String url = oldUrl.substring(oldUrl.indexOf("?"));
	    	//新日志添加 20190805
	    	//LogUtils.saveLog("R","1","登录成功，开始获取token==="+adminPath+"/oauth/authorize" + url, request,null);
			return "redirect:" + adminPath + "/oauth/authorize" + url;
		}else{// 登录成功后，如果原始请求不包含client_id参数，则跳转到动态门户首页
			//新日志添加 20190805
	    	//LogUtils.saveLog("R","1","登录成功，原始请求不包含client_id,跳转动态门户首页=="+Global.getConfig("gsp.dynamic.index") , request,null);
			return "redirect:"+Global.getConfig("gsp.dynamic.index");
		}
	}
	
}
