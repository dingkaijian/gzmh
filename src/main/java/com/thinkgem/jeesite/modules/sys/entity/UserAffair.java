package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import javax.validation.constraints.NotNull;


/**
 * 关注信息Entity
 * @author liguoqiang
 * @version 2019-01-16
 */
public class UserAffair extends DataEntity<UserAffair> {
	
	private static final long serialVersionUID = 1L;
	private String affairid;		// id值
	private String userid;		// 用户id
	private String affairname;		// 业务名称
	private String affairurl;		// 业务访问路径
	private Date createTime;		// 添加关注时间
	private String attOrder;		// 预留字段，之后可以做排序使用
	
	public UserAffair() {
		super();
	}

	public UserAffair(String id){
		super(id);
	}

	@Length(min=1, max=64, message="id值长度必须介于 1 和 64 之间")
	public String getAffairid() {
		return affairid;
	}

	public void setAffairid(String affairid) {
		this.affairid = affairid;
	}
	
	@Length(min=1, max=20, message="用户id长度必须介于 1 和 20 之间")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@Length(min=0, max=200, message="业务名称长度必须介于 0 和 200 之间")
	public String getAffairname() {
		return affairname;
	}

	public void setAffairname(String affairname) {
		this.affairname = affairname;
	}
	
	@Length(min=0, max=100, message="业务访问路径长度必须介于 0 和 100 之间")
	public String getAffairurl() {
		return affairurl;
	}

	public void setAffairurl(String affairurl) {
		this.affairurl = affairurl;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="添加关注时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=50, message="预留字段，之后可以做排序使用长度必须介于 0 和 50 之间")
	public String getAttOrder() {
		return attOrder;
	}

	public void setAttOrder(String attOrder) {
		this.attOrder = attOrder;
	}
	
}