package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.AffairsList;

import cn.ac.ict.modules.client.entity.OAuthClient;

/**
 * 事项信息列表DAO接口
 * @author bixue
 * 20200103
 */
@MyBatisDao
public interface AffairsListDao extends CrudDao<AffairsList> {
	//获取用户关注信息
	public List<AffairsList> selectAllByUser(String userId);
	//获取获取人类遗传额外按钮
	public List<OAuthClient> selectAffairButton();
}