package cn.ac.ict.modules.pw.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import cn.ac.ict.common.annotation.MobileCodeConstraint;

import javax.validation.groups.Default;

import org.hibernate.validator.constraints.NotBlank;

import com.thinkgem.jeesite.common.config.Global;
/**
 *  所对应的表 PW_UPDATE_MESSAGE
 * 
 *  用户口令修改记录Entity
 * 
 *  密码修改记录的管理（口令毎90天要更换一次，每次修改的口令与前n次不相同）
 *  
 */
public class PwUpdateRecord extends DataEntity<PwUpdateRecord> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userid;//用户ID
    
    private String pw;//密码（保存的都是在前端用MD5加密的，服务端看不到明文密码的，主要记录用户修改的一些记录）

    //private String updateDate;//密码修改日期

    private String pwId;//密码修改记录这张表的ID

    private String updateReason;//修改密码的原因
    
	@NotBlank(groups = Default.class, message = "姓名不能为空")
    private String name;//用户真实姓名

	@NotBlank(groups = Default.class, message = "用户类型不能为空")
    private String userType;//用户类型 
	@NotBlank(groups = Default.class, message = "证件类型不能为空")
	private String certificateType;//证件类型
	@NotBlank(groups = Default.class, message = "身份证号码不能为空")
	private String certificateno;//用户身份证号码
	
	private String area;//地区
	private String dz;//大洲
	private String country;//国家
	
    @NotBlank(groups = Default.class, message = "手机号码不能为空")
    private String mobile;//用户手机号码
    
    private String idcard1;//用户身份证正面
    
    private String idcard2;//用户身份证反面
    
    private String status;//审核状态
    
    @NotBlank(groups = Default.class, message = "验证码不能为空")
	@MobileCodeConstraint(groups = Default.class, message = "请输入正确的手机验证码")
	private String code;// 验证码
    
	private int number;//前N次密码不相同，在配置文件中设置的
	
	private int count;//记录总条数
	
	

	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getNumber() {
		return number;
	}   
	public String getPwId() {
		return pwId;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}


	public void setPwId(String pwId) {
		this.pwId = pwId;
	}

	public String getUpdateReason() {
		return updateReason;
	}

	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}

	public String getName() {
			return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCertificateno() {
		return certificateno;
	}
	public void setCertificateno(String certificateno) {
		this.certificateno = certificateno;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getIdcard1() {
		return idcard1;
	}
	public void setIdcard1(String idcard1) {
		this.idcard1 = idcard1;
	}
	public String getIdcard2() {
		return idcard2;
	}
	public void setIdcard2(String idcard2) {
		this.idcard2 = idcard2;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getCertificateType() {
		return certificateType;
	}
	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getDz() {
		return dz;
	}
	public void setDz(String dz) {
		this.dz = dz;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	@Override
	public String toString() {
		return "PwUpdateRecord [userid=" + userid + ", pw=" + pw + ", updateDate=" + updateDate + ", pwId=" + pwId
				+ ", updateReason=" + updateReason + ", name=" + name + ", certificateno=" + certificateno + ", mobile="
				+ mobile + ", idcard1=" + idcard1 + ", idcard2=" + idcard2 + ", status=" + status + ", code=" + code
				+ "]";
	}
}
