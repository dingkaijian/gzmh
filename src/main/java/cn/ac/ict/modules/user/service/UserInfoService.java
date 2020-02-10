package cn.ac.ict.modules.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;

import cn.ac.ict.modules.sys.entity.AuthBO;


/**
 * 用户基本信息Service
 */
@Service
@Transactional(readOnly = false)
public class UserInfoService extends CrudService<UserDao,User > {

	/**
	 * 通过登录名获取用户信息
	 */
	public User getById(String id){
		return dao.get(id);
	}
	
	/**
	 * 修改自然人密码
	 */
	public boolean updatePw(User user){
		
		if(dao.updatePw(user)==0){
			return false;
		}else{
			return true;
		}
	}	
}
