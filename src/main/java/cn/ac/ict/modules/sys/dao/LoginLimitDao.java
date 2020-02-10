package cn.ac.ict.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

import cn.ac.ict.modules.sys.entity.LoginLimit;

/** 
* @author liguoqiang
* @version 2019年11月27日 下午4:47:19
*/
@MyBatisDao
public interface LoginLimitDao  extends CrudDao<LoginLimit>{
	
	int updateLoginLimit(LoginLimit loginLimit);
}
