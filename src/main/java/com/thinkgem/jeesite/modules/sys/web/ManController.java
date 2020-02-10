package com.thinkgem.jeesite.modules.sys.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.service.ArticleService;
import com.thinkgem.jeesite.modules.sys.entity.AffairsList;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.entity.UserAffair;
import com.thinkgem.jeesite.modules.sys.service.AffairsListService;
import com.thinkgem.jeesite.modules.sys.service.UserAffairService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
/**
 * 综合业务操作类
 * @author bixue
 * 20200102
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/man")
public class ManController extends BaseController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private AffairsListService affairsListService;
	@Autowired
	private UserAffairService userAffairService;
	
	/**
	 * 通知通告栏目信息展示
	 * @param article
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/tztg")
	public String getTztg(Article article, HttpServletRequest request, HttpServletResponse response, Model model){		
		article.setDelFlag("0");//仅展示已发布信息	
		Page<Article> page = articleService.findPage(new Page<Article>(request, response), article, true); 
	    model.addAttribute("article",article);
		model.addAttribute("page", page);
	     return "modules/sys/tztg";
	}
	/**
	 * 获取事项列表信息
	 * @param affairsList
	 */
	@RequestMapping("/affairslist")
	public String affairList(AffairsList affairsList,Model model){
		List<AffairsList> affairs=affairsListService.findList(affairsList);
		model.addAttribute("affairs",affairs);
		return "modules/sys/affairList";
	}
	/**
	 * 进入我的关注功能
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping("/useraffairs")
	public String goUserAffairs(String userId){
		return "modules/sys/userAffairs";
	}
	/**
	 * 获取用户关注信息
	 * @param userId用户id	 
	 * @return
	 */
	@RequestMapping("/getuseraffairs")
	@ResponseBody
	public String getUserAffairs(String userId){
		Map<String, Object> map = new HashMap<String, Object>();
		User user=UserUtils.getUser();
		if(user!=null){
			userId=user.getId();
		}
		List<AffairsList> list=affairsListService.selectAllByUser(userId);		
		System.out.println("====list===="+list);
		map.put("isNull",true);
		map.put("affairsLists",list);		
		return JSON.toJSONString(map);		
	}
	/**
	 * 添加关注、取消关注
	 * @param userAffair
	 * @param type 操作类型 0：添加 1：取消
	 * @return
	 */
	@RequestMapping("/updateuseraffairs")
	@ResponseBody
	public String userAffair(UserAffair userAffair,String type){
		User user=UserUtils.getUser();
		if(user!=null){
			userAffair.setUserid(user.getId());
			boolean tf=userAffairService.userAffair(userAffair, type);
			if(tf){//成功
				return "success";
			}else{//失败
				return "error";
			}
		}
		return "error";	
	}
}
