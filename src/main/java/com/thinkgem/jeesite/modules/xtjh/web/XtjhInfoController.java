/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.xtjh.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.xtjh.entity.XtjhInfo;
import com.thinkgem.jeesite.modules.xtjh.service.XtjhInfoService;

/**
 * 协同交互Controller
 * 
 * @author Duliuqing
 * @version 2020-01-19
 */
@Controller
@RequestMapping(value = "${adminPath}/xtjh/xtjhInfo")
public class XtjhInfoController extends BaseController {

	@Autowired
	private XtjhInfoService xtjhInfoService;

	@ModelAttribute
	public XtjhInfo get(@RequestParam(required = false) String id) {
		XtjhInfo entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = xtjhInfoService.get(id);
		}
		if (entity == null) {
			entity = new XtjhInfo();
		}
		return entity;
	}

	@RequiresPermissions("xtjh:xtjhInfo:view")
	@RequestMapping(value = { "myNotice" })
	public String myNotice(XtjhInfo xtjhInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		// 查找与当前用户有关的信息
		xtjhInfo.setSelf(true);
		// 群发类型
		xtjhInfo.setType("q");
		Page<XtjhInfo> page = xtjhInfoService.find(new Page<XtjhInfo>(request, response), xtjhInfo);
		model.addAttribute("page", page);
		return "modules/xtjh/xtjhInfoNotice";
	}

	@RequiresPermissions("xtjh:xtjhInfo:view")
	@RequestMapping(value = { "allNotice" })
	public String allNotice(XtjhInfo xtjhInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		// 群发类型
		xtjhInfo.setType("q");
		Page<XtjhInfo> page = xtjhInfoService.find(new Page<XtjhInfo>(request, response), xtjhInfo);
		model.addAttribute("page", page);
		return "modules/xtjh/xtjhInfoNotice";
	}

	@RequiresPermissions("xtjh:xtjhInfo:view")
	@RequestMapping(value = { "messageGroup" })
	public String messageGroup(XtjhInfo xtjhInfo, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		// 单发类型
		xtjhInfo.setType("d");
		Page<XtjhInfo> page = xtjhInfoService.findGroupPage(new Page<XtjhInfo>(request, response), xtjhInfo);
		model.addAttribute("page", page);
		return "modules/xtjh/xtjhMessageGroup";
	}

	@RequiresPermissions("xtjh:xtjhInfo:view")
	@RequestMapping(value = "messageList")
	public String list(XtjhInfo xtjhInfo, @RequestParam String toId, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		User u = new User();
		u.setId(toId);
		xtjhInfo.setTo(u);
		Page<XtjhInfo> page = xtjhInfoService.findListPage(new Page<XtjhInfo>(request, response), xtjhInfo);
		model.addAttribute("page", page);
		return "modules/xtjh/xtjhMessageList";
	}

	@RequiresPermissions("xtjh:xtjhInfo:view")
	@RequestMapping(value = "form")
	public String form(XtjhInfo xtjhInfo, Model model) {
		if (StringUtils.isNotBlank(xtjhInfo.getId())) {
			xtjhInfo = xtjhInfoService.getRecordList(xtjhInfo);
		}
		model.addAttribute("xtjhInfo", xtjhInfo);
		return "modules/xtjh/xtjhInfoForm";
	}

	@RequiresPermissions("xtjh:xtjhInfo:edit")
	@RequestMapping(value = "save")
	public String save(XtjhInfo xtjhInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, xtjhInfo)) {
			return form(xtjhInfo, model);
		}
		xtjhInfoService.save(xtjhInfo);
		addMessage(redirectAttributes, "发送信息成功");
		return "redirect:" + Global.getAdminPath() + "/xtjh/xtjhInfo/?repage";
	}

	@RequiresPermissions("xtjh:xtjhInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(XtjhInfo xtjhInfo, RedirectAttributes redirectAttributes) {
		xtjhInfoService.delete(xtjhInfo);
		addMessage(redirectAttributes, "删除信息成功");
		return "redirect:" + Global.getAdminPath() + "/xtjh/xtjhInfo/?repage";
	}

}