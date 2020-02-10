package cn.ac.ict.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.ac.ict.modules.sys.entity.AppConfig;
import cn.ac.ict.modules.sys.service.AppConfigService;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.JedisUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 系统配置表控制器
 * @author hujie
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/appconfig")
public class AppConfigController extends BaseController{
	
	@Autowired
	private AppConfigService appConfigService;
	

	@ModelAttribute("appconfig")
	public AppConfig get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			AppConfig appConfig = new AppConfig();
			appConfig = appConfigService.get(id);
			return appConfig;
		}else{
			return new AppConfig();
		}
	}

	
	/**
	 * 系统配置列表--系统管理员权限
	 * @param appConfig
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:appconfig:view")
	@RequestMapping(value = {"list",""})
	public String list(AppConfig appConfig,HttpServletRequest request,HttpServletResponse response,Model model){
		Page<AppConfig> page = appConfigService.findPage(new Page<AppConfig>(request, response), appConfig);
		model.addAttribute("page", page);
		return "modules/sys/appConfigList";
	}
	/**
	 * 添加修改系统配置--系统管理员
	 */
	@RequiresPermissions("sys:appconfig:sys")
	@RequestMapping(value = "saveBySys")  
	public String saveBySys(Model model, RedirectAttributes redirectAttributes,AppConfig appConfig){
		appConfig.setCreatetime(DateUtils.getDateTime());
		appConfig.setUpdatetime(DateUtils.getDateTime());
		JedisUtils.delObject("config_"+appConfig.getConfigname());
		appConfigService.save(appConfig);
		addMessage(redirectAttributes, "添加系统配置'" +  appConfig.getConfigname() + "'成功");
		return "redirect:" + adminPath + "/sys/appconfig/";
	}
	/**
	 * 删除--系统管理员
	 */
	@RequiresPermissions("sys:appconfig:sys")
	@RequestMapping(value = "deleteBySys")
	public String deleteBySys(AppConfig appConfig,Model model,RedirectAttributes redirectAttributes){
		appConfigService.delete(appConfig);
		JedisUtils.delObject("config_"+appConfig.getConfigname());
		addMessage(redirectAttributes, "删除成功");
		return "redirect:" + adminPath + "/sys/appconfig/";
	}
	/**
	 * 添加修改跳转--系统管理员
	 * @param appConfig
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:appconfig:sys")
	@RequestMapping(value = "form2")
	public String form(AppConfig appConfig,Model model){
		if(appConfig.getId()!=null){
			appConfig = get(appConfig.getId());
		}
		model.addAttribute("appconfig", appConfig);
		return "modules/sys/appConfigForm2";
	}
	
	/**
	 * 开启和关闭--系统管理员
	 */
	@RequiresPermissions("sys:appconfig:sys")
	@RequestMapping(value = "pushAndCloseBySys")
	public String pushAndCloseBySys(@Param("id")String id,@Param("configstate") String configstate,RedirectAttributes redirectAttributes,@Param("configname")String configname){
		boolean flag = appConfigService.pushAndClose(id, configstate);
		JedisUtils.delObject("config_"+configname);
		if(flag){
			addMessage(redirectAttributes, "操作成功");
		}else{
			addMessage(redirectAttributes, "操作失败，请联系管理员!");
		}
		return "redirect:" + adminPath + "/sys/appconfig/";
	}
	
	/**
	 * 添加修改跳转--安全员
	 * @param appConfig
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:appconfig:auditor")
	@RequestMapping(value = "form")
	public String form2(AppConfig appConfig,Model model){
		if(appConfig.getId()!=null){
			appConfig = get(appConfig.getId());
		}
		model.addAttribute("appconfig", appConfig);
		return "modules/sys/appConfigForm";
	}

	/**
	 * 开启和关闭--安全员
	 */
	@RequiresPermissions("sys:appconfig:auditor")
	@RequestMapping(value = "pushAndCloseByAuditor")
	public String pushAndCloseByAuditor(@Param("id")String id,@Param("configstate") String configstate,RedirectAttributes redirectAttributes,@Param("configname")String configname){
		boolean flag = appConfigService.pushAndClose(id, configstate);
		JedisUtils.delObject("config_"+configname);
		if(flag){
			addMessage(redirectAttributes, "操作成功");
		}else{
			addMessage(redirectAttributes, "操作失败，请联系管理员!");
		}
		return "redirect:" + adminPath + "/sys/appconfig/";
	}
	/**
	 * 删除--安全员
	 */
	@RequiresPermissions("sys:appconfig:auditor")
	@RequestMapping(value = "deleteByAuditor")
	public String deleteByAuditor(AppConfig appConfig,Model model,RedirectAttributes redirectAttributes){
		appConfigService.delete(appConfig);
		JedisUtils.delObject("config_"+appConfig.getConfigname());
		addMessage(redirectAttributes, "删除策略成功");
		return "redirect:" + adminPath + "/sys/appconfig/";
	}
	/**
	 * 添加修改系统配置--安全员
	 */
	@RequiresPermissions("sys:appconfig:auditor")
	@RequestMapping(value = "saveByAuditor")
	public String saveByAuditor(Model model, RedirectAttributes redirectAttributes,AppConfig appConfig){
		appConfig.setCreatetime(DateUtils.getDateTime());
		appConfig.setUpdatetime(DateUtils.getDateTime());
		appConfigService.save(appConfig);
		JedisUtils.delObject("config_"+appConfig.getConfigname());
		addMessage(redirectAttributes, "添加系统配置'" +  appConfig.getConfigname() + "'成功");
		return "redirect:" + adminPath + "/sys/appconfig/";
	}
	
	@RequiresPermissions("sys:appconfig:view")
	@RequestMapping(value = "getConfigs")
	public AppConfig getConfigs(String name){
		AppConfig appConfig = appConfigService.getByConfigName(name);
		System.out.println(appConfig.toString());
		System.out.println(appConfig.getConfigstate());
		return appConfig;
	}
	
}
