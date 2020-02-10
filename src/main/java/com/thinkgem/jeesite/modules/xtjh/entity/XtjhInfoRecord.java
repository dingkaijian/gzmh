/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.xtjh.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 协同交互Entity
 * @author Duliuqing
 * @version 2020-01-19
 */
public class XtjhInfoRecord extends DataEntity<XtjhInfoRecord> {
	
	private static final long serialVersionUID = 1L;
	private XtjhInfo xtjhInfo;	// 信息id
	private User user;		// 接受人
	private String readFlag;	// 已读标记
	private Date readDate;	// 已读时间
	
	public XtjhInfoRecord() {
		super();
	}

	public XtjhInfoRecord(String id){
		super(id);
	}
	
	public XtjhInfoRecord(XtjhInfo xtjhInfo){
		this.xtjhInfo = xtjhInfo;
	}

	public XtjhInfo getXtjhInfo() {
		return xtjhInfo;
	}

	public void setXtjhInfo(XtjhInfo xtjhInfo) {
		this.xtjhInfo = xtjhInfo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}

	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}
	
}