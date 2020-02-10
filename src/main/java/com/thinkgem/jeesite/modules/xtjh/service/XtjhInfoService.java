/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.xtjh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.xtjh.dao.XtjhInfoDao;
import com.thinkgem.jeesite.modules.xtjh.dao.XtjhInfoRecordDao;
import com.thinkgem.jeesite.modules.xtjh.entity.XtjhInfo;
import com.thinkgem.jeesite.modules.xtjh.entity.XtjhInfoRecord;

/**
 * 协同交互Service
 * @author Duliuqing
 * @version 2020-01-19
 */
@Service
@Transactional(readOnly = true)
public class XtjhInfoService extends CrudService<XtjhInfoDao, XtjhInfo> {

	@Autowired
	private XtjhInfoDao xtjhInfoDao;

	@Autowired
	private XtjhInfoRecordDao xtjhInfoRecordDao;

	public XtjhInfo get(String id) {
		return super.get(id);
	}
	
	/**
	 * 获取通知发送记录
	 * @param oaNotify
	 * @return
	 */
	public XtjhInfo getRecordList(XtjhInfo xtjhInfo) {
		xtjhInfo.setXtjhInfoRecordList(xtjhInfoRecordDao.findList(new XtjhInfoRecord(xtjhInfo)));
		return xtjhInfo;
	}
	
	public Page<XtjhInfo> find(Page<XtjhInfo> page, XtjhInfo xtjhInfo) {
		xtjhInfo.setPage(page);
		page.setList(dao.findList(xtjhInfo));
		return page;
	}
	
	public List<XtjhInfo> findList(XtjhInfo xtjhInfo) {
		return super.findList(xtjhInfo);
	}
	
	public Page<XtjhInfo> findPage(Page<XtjhInfo> page, XtjhInfo xtjhInfo) {
		return super.findPage(page, xtjhInfo);
	}
	
	public Page<XtjhInfo> findListPage(Page<XtjhInfo> page, XtjhInfo xtjhInfo) {
		xtjhInfo.setPage(page);
		page.setList(xtjhInfoDao.findMessageList(xtjhInfo));
		return page;
	}
	
	public Page<XtjhInfo> findGroupPage(Page<XtjhInfo> page, XtjhInfo xtjhInfo) {
		xtjhInfo.setPage(page);
		page.setList(xtjhInfoDao.findGroup(xtjhInfo));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(XtjhInfo xtjhInfo) {
		super.save(xtjhInfo);
		// 更新发送接受人记录
		xtjhInfoRecordDao.deleteByXtjhInfoId(xtjhInfo.getId());
		if (xtjhInfo.getXtjhInfoRecordList().size() > 0){
			xtjhInfoRecordDao.insertAll(xtjhInfo.getXtjhInfoRecordList());
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(XtjhInfo xtjhInfo) {
		super.delete(xtjhInfo);
	}
	
}