package cn.ac.ict.common.listener;

import java.util.List;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.thinkgem.jeesite.common.service.BaseService;
import cn.ac.ict.modules.pw.entity.WeakPw;
import cn.ac.ict.modules.pw.service.WeakPwService;

/**
 * 弱密码初始化监听器
 *
 */
 public  class WeakPwInitialized extends BaseService implements ServletContextListener{
	
	public static List<WeakPw> weakPws;//存放常见的弱密码

	public static WeakPwService weakPwService;	
	
	/**
	 * 常见弱密码清单
	 */
	public static List<WeakPw> getWeakPws() {
		if(weakPws == null || weakPws.size() == 0){
			weakPws = weakPwService.getAll();
		}
		return weakPws;
	}
	
	public static WeakPwService getWeakPwService() {
		return weakPwService;
	}

	@Override
	public void contextInitialized(ServletContextEvent sce){
		weakPwService = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext())
		.getBean(WeakPwService.class);
		getWeakPws();
	}
	@Override
	public void contextDestroyed(ServletContextEvent sce){

	}	
}
