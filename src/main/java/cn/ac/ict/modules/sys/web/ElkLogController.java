package cn.ac.ict.modules.sys.web;

import java.util.ArrayList;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ac.ict.common.utils.ElkUtils;
import cn.ac.ict.modules.sys.entity.ElkLog;

/**
 * ELK日志管理控制器
 * @author hujie
 *  
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/elkLog")
public class ElkLogController {
	
	@RequiresPermissions("sys:elklog:view")
	@RequestMapping(value = {"list",""})
	public String listString(ElkLog elkLog,Model model){
		ArrayList<ElkLog> elkList = new ArrayList<ElkLog>(); 
		elkList = ElkUtils.getElkLog(elkLog);
		model.addAttribute("elkList", elkList);
		return "modules/sys/elkLogList";
	}
}
