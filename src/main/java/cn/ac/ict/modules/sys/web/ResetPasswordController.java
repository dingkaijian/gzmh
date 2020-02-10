package cn.ac.ict.modules.sys.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import cn.ac.ict.common.listener.WeakPwInitialized;
import cn.ac.ict.common.security.encryption.sm.SM2Util;
import cn.ac.ict.common.utils.Constants;
import cn.ac.ict.common.utils.ResultUtils;
import cn.ac.ict.modules.sms.service.SmsService;
import cn.ac.ict.modules.sys.entity.PwBO;
import cn.ac.ict.modules.sys.service.ResetPasswordService;

@Controller
@RequestMapping(value = "${adminPath}/resetpw")
public class ResetPasswordController {
	
	@Autowired
	ResetPasswordService resetPasswordService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private SmsService smsService;
	
	@RequestMapping("/updatePw")
	public String updatePw(PwBO pwBO, Model model, HttpServletRequest request) {
		User user = UserUtils.getUser();
		pwBO.setUserid(user.getId());
		String sm2Pw = pwBO.getAuthBO().getPw();
		String sm3Pw = SM2Util.doPassword(sm2Pw, "1");
		String number = systemService.getSwitch("passwordDifferentNumber");
		pwBO.setNumber(number);
		pwBO.getAuthBO().setPw(sm3Pw);
		List<String> messageList = new ArrayList<String>();// 存校验未通过的提示信息
		// 密码校验（与前n次不相同）
		if ("true".equals(Global.getConfig("mobile.validate.code.number.function"))) {
			if (!resetPasswordService.validatePw(pwBO)) {
				messageList.add("与前" + number + "次密码重复，不安全，请重新输入密码！");
			}
		}
		// 弱密码检测
		if (!WeakPwInitialized.getWeakPwService().validateWeakPw(WeakPwInitialized.weakPws, pwBO.getAuthBO().getPw())) {
			messageList.add("该密码为弱密码，不安全，请重新输入！");
		}
		
		// 校验未通过返回提示信息
		if (messageList.size() > 0) {
			model.addAttribute("pwBO", pwBO);
			model.addAttribute("message", messageList.toString());
			validateFailReturn(messageList, request);
			return "modules/sys/userModifyPwd";
		}
		// 修改密码
		boolean flag = resetPasswordService.update(pwBO, request);
		if (!flag) {
			model.addAttribute("message", "修改密码失败！请联系管理员");
			return "modules/sys/userModifyPwd";
		}
		//清除用户缓存信息
		UserUtils.clearCache();
		model.addAttribute("message", "重置密码已完成");
		return "modules/sys/userModifyPwd";
	} 
	
	private void validateFailReturn(List<String> messageList, HttpServletRequest request) {
		// 校验失败生成新的提交token 便于用户再次提交
		String newToken = IdGen.uuid();
		request.getSession().setAttribute(Constants.SUBMIT_TOKEN_NAME, newToken);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(Constants.SUBMIT_TOKEN_NAME, newToken);
		data.put("messageList", messageList);
		//LogUtils.saveLog(null, null, null, "注册系统", "R", "1", "忘记密码-校验失败生成新的提交token 便于用户再次提交", null, null, request,null);
	}
	
	/**
	 * 发送手机验证码
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sendMobileCode", method = RequestMethod.POST)
	@ResponseBody
	public String sendMobileCode(String mobile,String loginName, HttpSession session) {
		if (StringUtils.isBlank(mobile)) {
			return ResultUtils.buildJsonError("手机号不能为空");
		}
		// 每次发送验证码前，先检查手机号是否为预留手机号
		User user = systemService.getUserByLoginName(loginName);
		if(!mobile.equals(user.getMobile())) {
			return ResultUtils.buildJsonError("预留手机号错误");
		}
		// 发送短信
		try {
			//判断是否可再次发送验证码
			String re=smsService.isSend(mobile);
			if("".equals(re)){
				boolean result = smsService.sendMobileCodeAndSetSession(Global.getConfig("system.name"), mobile, session);
				if(result) {
					return ResultUtils.buildJsonNormal("手机验证码已发送，请您查收");
				} else {
					return ResultUtils.buildJsonError("手机验证码发送失败");
				}
			}else{
				return ResultUtils.buildJsonError(re);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.buildJsonError("手机验证码发送失败");
		}
	}	
}
