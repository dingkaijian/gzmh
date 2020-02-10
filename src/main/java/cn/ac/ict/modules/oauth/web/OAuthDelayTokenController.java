package cn.ac.ict.modules.oauth.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

import cn.ac.ict.modules.oauth.service.OAuthRedisService;
import cn.ac.ict.modules.oauth.service.impl.OAuthRedisServiceImpl;
import cn.ac.ict.modules.sys.utils.LogUtils;

@Controller
@RequestMapping("${adminPath}/oauth/")
public class OAuthDelayTokenController extends BaseController {

	OAuthRedisService oAuthRedisService = SpringContextHolder.getBean(OAuthRedisServiceImpl.class);
	@RequestMapping("delayToken")
	@ResponseBody
	public String delayToken(String tokenID, HttpServletRequest request, HttpServletResponse response){
		System.out.println("===================delayToken");
		boolean flag = false;
		try {
			flag = oAuthRedisService.delayToken(tokenID);
		} catch (Exception e) {
			//新日志添加 20190802
			LogUtils.saveLog("R","2","======执行delayToken方法时发生异常=======", request, e);
			e.printStackTrace();
		}
		return StringUtils.toString(flag, "false");
			
	}
	
	
}
