package cn.ac.ict.modules.sys.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;


/**
 * 系统配置表实体类
 * @author hujie
 *
 */
public class AppConfig extends DataEntity<AppConfig>{
	private static final long serialVersionUID = 1L;
	private String id;//主键id
	private String configname;//配置规则名称
	private String configvalue;//规则数值
	private String configdesc;//规则详细描述
	private String createtime;//规则创建时间
	private String updatetime;//规则更新时间
	private String delflag;//规则是否有效
	private String userpermission;//权限标识
	private String configstate;//配置状态
	
	public String getConfigstate() {
		return configstate;
	}
	public void setConfigstate(String configstate) {
		this.configstate = configstate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getConfigname() {
		return configname;
	}
	public void setConfigname(String configname) {
		this.configname = configname;
	}
	public String getConfigvalue() {
		return configvalue;
	}
	public void setConfigvalue(String configvalue) {
		this.configvalue = configvalue;
	}
	public String getConfigdesc() {
		return configdesc;
	}
	public void setConfigdesc(String configdesc) {
		this.configdesc = configdesc;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getDelflag() {
		return delflag;
	}
	public void setDelflag(String delflag) {
		this.delflag = delflag;
	}
	
	public String getUserpermission() {
		return userpermission;
	}
	public void setUserpermission(String userpermission) {
		this.userpermission = userpermission;
	}
	@Override
	public String toString() {
		return "AppConfig [id=" + id + ", configname=" + configname
				+ ", configvalue=" + configvalue + ", configdesc=" + configdesc
				+ ", createtime=" + createtime + ", updatetime=" + updatetime
				+ ", delflag=" + delflag + ", userpermission="
				+ userpermission + "]";
	}
	
	
}
