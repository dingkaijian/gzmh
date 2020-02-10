package com.thinkgem.jeesite.modules.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.dao.UserAffairDao;
import com.thinkgem.jeesite.modules.sys.entity.UserAffair;
/**
 * 关注信息Service
 * @author bixue
 * 20200103
 */
@Service
@Transactional(readOnly = false)
public class UserAffairService extends CrudService<UserAffairDao, UserAffair> {
	@Autowired
	private UserAffairDao dao;
	/*
	 * 添加关注、取消关注
	 */
	public boolean userAffair(UserAffair userAffair,String type){
		int num=0;
		boolean tf=false;
		if("0".equals(type)){//添加关注
			num=dao.addUserAffair(userAffair);
		}
		else if("1".equals(type)){//取消关注
			num=dao.cancleUserAffair(userAffair);
		}
		if(num>0){
			tf=true; 
		}
		return tf;
	}
}