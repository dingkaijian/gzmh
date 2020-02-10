/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.xtjh.entity;

import java.util.List;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;/**
 * 协同交互Entity
 * @author Duliuqing
 * @version 2020-01-19
 */
public class XtjhInfo extends DataEntity<XtjhInfo> {
	
	private static final long serialVersionUID = 1L;
	private String type = "d";	// 类型	d:单发	q:群发
	private String title;	// 标题
	private String content;	// 内容
	private String files;	// 附件
	private String bySms = "0";		// 发送短信
	private String byEmail = "0";		// 发送邮件
	
	private boolean isSelf;		// 是否只查询自己的通知
	private User to;			// 单发消息的"对方", 相对于当前用户的对方
	private List<XtjhInfoRecord> xtjhInfoRecordList = Lists.newArrayList();
	
	public XtjhInfo() {
		super();
	}

	public XtjhInfo(String id){
		super(id);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public String getBySms() {
		return bySms;
	}

	public void setBySms(String bySms) {
		this.bySms = bySms;
	}

	public String getByEmail() {
		return byEmail;
	}

	public void setByEmail(String byEmail) {
		this.byEmail = byEmail;
	}

	public boolean isSelf() {
		return isSelf;
	}

	public void setSelf(boolean isSelf) {
		this.isSelf = isSelf;
	}

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}

	public List<XtjhInfoRecord> getXtjhInfoRecordList() {
		return xtjhInfoRecordList;
	}

	public void setXtjhInfoRecordList(List<XtjhInfoRecord> xtjhRecordList) {
		this.xtjhInfoRecordList = xtjhRecordList;
		System.out.println("------");
		System.out.println(xtjhRecordList);
	}

	/**
	 * 获取通知发送记录用户ID
	 * @return
	 */
	public String getXtjhInfoRecordIds() {
		return Collections3.extractToString(xtjhInfoRecordList, "user.id", ",") ;
	}
	
	/**
	 * 设置通知发送记录用户ID
	 * @return
	 */
	public void setXtjhInfoRecordIds(String xtjhInfoRecord) {
		this.xtjhInfoRecordList = Lists.newArrayList();
		for (String id : StringUtils.split(xtjhInfoRecord, ",")){
			XtjhInfoRecord entity = new XtjhInfoRecord();
			entity.setId(IdGen.uuid());
			entity.setXtjhInfo(this);
			entity.setUser(new User(id));
			entity.setReadFlag("0");
			this.xtjhInfoRecordList.add(entity);
		}
	}

	/**
	 * 获取通知发送记录用户Name
	 * @return
	 */
	public String getXtjhInfoRecordNames() {
		return Collections3.extractToString(xtjhInfoRecordList, "user.name", ",") ;
	}
	
	/**
	 * 设置通知发送记录用户Name
	 * @return
	 */
	public void setXtjhInfoRecordNames(String xtjhRecord) {
		// 什么也不做
	}
}