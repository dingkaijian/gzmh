/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.xtjh.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.xtjh.entity.XtjhInfoRecord;

/**
 * 协同交互DAO接口
 * 
 * @author Duliuqing
 * @version 2020-01-19
 */
@MyBatisDao
public interface XtjhInfoRecordDao extends CrudDao<XtjhInfoRecord> {

	/**
	 * 插入通知记录
	 * @param xtjhInfoRecordList
	 * @return
	 */
	public int insertAll(List<XtjhInfoRecord> xtjhInfoRecordList);
	
	/**
	 * 根据通知ID删除通知记录
	 * @param xtjhInfoId 通知ID
	 * @return
	 */
	public int deleteByXtjhInfoId(String xtjhInfoId);
}