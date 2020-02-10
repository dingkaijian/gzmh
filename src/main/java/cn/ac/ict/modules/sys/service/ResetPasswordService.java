package cn.ac.ict.modules.sys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.sys.entity.User;

import cn.ac.ict.common.security.encryption.sm.SM2Util;
import cn.ac.ict.modules.pw.entity.PwUpdateRecord;
import cn.ac.ict.modules.pw.service.PwUpdateRecordService;
import cn.ac.ict.modules.sys.entity.PwBO;
import cn.ac.ict.modules.user.service.UserInfoService;

/**
 * 重置密码service
 *
 */
@Service
public class ResetPasswordService {
	
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private PwUpdateRecordService pwUpdateRecordService;
	/**
	 * 通过登录名获取用户信息
	 * @param login
	 * @return
	 *//*
	public PwBO getByLogin(AuthBO authBO){
		PwBO pwBO=new PwBO();
		UserInfo userInfo=userInfoService.getByLoginForUpdatePw(authBO);
		OrgInfo orgInfo = userInfoService.getByLoginForUpdatePwFr(authBO);
		if(userInfo==null&&orgInfo==null){
			return null;
		}else if(userInfo!=null){
			authBO.setMobile(userInfo.getMobile());
			pwBO.setAuthBO(authBO);
			pwBO.setUserid(userInfo.getId());
			pwBO.setType(userInfo.getUserAuth().getType());
			return pwBO;
		}else{
			authBO.setMobile(orgInfo.getDbrTelephone());
			pwBO.setAuthBO(authBO);
			pwBO.setOrgName(orgInfo.getOrgname());
			pwBO.setSocialcreditcode(orgInfo.getSocialcreditcode());
			pwBO.setUserid(orgInfo.getId());
			pwBO.setType(orgInfo.getOrgAuth().getType());
			return pwBO;
		}
		
	}
	
	
	/**
	 * 修改密码
	 * @param pwBO
	 * @param request
	 * @return
	 */
	@Transactional(readOnly = false,rollbackFor=Exception.class)
	public boolean update(PwBO pwBO,HttpServletRequest request){
		User user=userInfoService.get(pwBO.getUserid());
		if (user==null) {
			return false;
		}
		user.setPassword(pwBO.getAuthBO().getPw());
		
		try {
			userInfoService.updatePw(user);
			if(Global.getConfig("password.update.days.function").equals("true")){
				PwUpdateRecord pwUpdateRecord=new PwUpdateRecord();
				int count = pwUpdateRecordService.getCount();
				pwUpdateRecord.setCount(count+1);
				pwUpdateRecord.setIsNewRecord(true);
				pwUpdateRecord.setPw(user.getPassword());
				pwUpdateRecord.setUserid(user.getId());
				pwUpdateRecord.setUpdateReason(pwBO.getReson());
				pwUpdateRecordService.save(pwUpdateRecord);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
			
		}
	}
	
	
	
	public boolean validatePw(PwBO pwBO){
		PwUpdateRecord pwUpdateRecord=new PwUpdateRecord();
		pwUpdateRecord.setPw(pwBO.getAuthBO().getPw());
		pwUpdateRecord.setUserid(pwBO.getUserid());
		pwUpdateRecord.setNumber(Integer.valueOf(pwBO.getNumber()));
		List<PwUpdateRecord> pws=pwUpdateRecordService.findList(pwUpdateRecord);
		if(pws.size()!=0){
			return false;//密码与前n次相同了
		}else{
			return true;//密码与前n次没有重复的
		}				
	}
	
	
	

}
