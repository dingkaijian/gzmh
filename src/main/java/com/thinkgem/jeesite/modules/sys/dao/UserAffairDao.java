package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.UserAffair;
/**
 * 关注信息DAO接口
 * @author bixue
 * 20200103
 */
@MyBatisDao
public interface UserAffairDao extends CrudDao<UserAffair> {
	//添加关注
	public int addUserAffair(UserAffair userAffair);
	//取消关注
	public int cancleUserAffair(UserAffair userAffair);
}