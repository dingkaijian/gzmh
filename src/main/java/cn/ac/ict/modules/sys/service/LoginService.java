package cn.ac.ict.modules.sys.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;


/**
 * 用户登录service
 * @author changyanbo
 *
 */
@Service
@Transactional(readOnly = true)
public class LoginService extends BaseService {

	/**
	 * 通过shiro管理的用户session，获取用户原始请求的URL地址
	 * @param request
	 * @return
	 */
	public String getOriginalSavedRequestURL(HttpServletRequest request){
		// 从shiro中获取用户请求系统时的原始请求
		SavedRequest userSavedRequest = WebUtils.getSavedRequest(request);
		// 从用户的原始请求中取得用户的原始请求地址
		String requestUrl = "";
		if(userSavedRequest != null){
			requestUrl = userSavedRequest.getRequestUrl();
		}
		return requestUrl;
	}
	
	/**
	 * 从请求地址的参数中获取登录类型
	 * @param requestUrl
	 * @return
	 */
	public String getLoginType(String requestUrl){
		if(StringUtils.isBlank(requestUrl)){
			return "";
		}		
		// 截取请求地址中携带的参数
		String[] parameters = requestUrl.substring(requestUrl.indexOf("?")+1).split("&");
		// 登录类型，用于区分不同的登录页面
		String logintype = "";
		for(String param : parameters){
			if(param.startsWith("logintype")){
				logintype = param.substring(param.indexOf("=")+1);
			}
		}
		return logintype;
	}
	
	
}
