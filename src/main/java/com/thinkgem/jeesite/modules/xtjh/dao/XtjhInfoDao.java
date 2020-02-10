/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.xtjh.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.xtjh.entity.XtjhInfo;

/**
 * 协同交互DAO接口
 * 
 * @author Duliuqing
 * @version 2020-01-19
 */
@MyBatisDao
public interface XtjhInfoDao extends CrudDao<XtjhInfo> {
	public List<XtjhInfo> findGroup(XtjhInfo xtjhInfo);

	public List<XtjhInfo> findMessageList(XtjhInfo xtjhInfo);

}