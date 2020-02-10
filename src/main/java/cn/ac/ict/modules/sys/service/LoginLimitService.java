package cn.ac.ict.modules.sys.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;

import cn.ac.ict.modules.sys.dao.LoginLimitDao;
import cn.ac.ict.modules.sys.entity.LoginLimit;
@Service
public class LoginLimitService extends CrudService<LoginLimitDao,LoginLimit>{
	
	@Transactional(readOnly = false)
	public int updateLoginLimit(LoginLimit loginLimit){
		if (loginLimit.getIsNewRecord()) {
			return dao.insert(loginLimit);
		}else {
			return dao.updateLoginLimit(loginLimit);
		}
		
	}
	
}
