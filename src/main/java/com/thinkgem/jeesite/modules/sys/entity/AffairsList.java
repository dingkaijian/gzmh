package com.thinkgem.jeesite.modules.sys.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import cn.ac.ict.modules.client.entity.OAuthClient;
/**
 * 事项信息列表Entity
 * @author bixue
 * 20200103
 */
public class AffairsList extends DataEntity<AffairsList> {

	private static final long serialVersionUID = 1L;
	private String affairid;
	private String affairname; // 事项名称
	private String affairserialno; //
	private String affairno; // 事项序号事项编号
	private String affairicon; // 事项图标
	private String affairbureau; // 主管司局
	private String affaircondition; // 申请条件1
	private String affairprocess; // 事项流程1
	private String affairexplain; //
	private String affairfiles; // 相关事项说明1文件1
	private String affairbyuser; // 按用户分类
	private String affairbycategory; // 按类别分类
	private String affairbybureau; // 按司局分类
	private String affairbysystem; // 按系统分类
	private String affairicon1; // 事项图标1
	private String affairUrl; // 业务链接地址
	private String extraUrls; // 额外链接动态按钮
	private String extraUrls2; // 额外链接2
	private String spare; // 备用字段
	private String permission; // 权限标识

	private String isgz; // 数据库外字段
	private String userId;
	private String state;
	private List<OAuthClient> clientlist = new ArrayList<OAuthClient>();

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	/**
	 * 事项类型
	 */
	private String itemType;

	/**
	 * 设定依据
	 */
	private String settingBasis;

	/**
	 * 权力来源
	 */
	private String power;

	/**
	 * 行使层级
	 */
	private String exerciseLevel;

	/**
	 * 实施编码
	 */
	private String implementCode;

	/**
	 * 业务办理项编码
	 */
	private String businessProcessCode;

	/**
	 * 事项状态
	 */
	private String itemStatus;

	/**
	 * 事项版本
	 */
	private String itemVersion;

	/**
	 * 实施主体
	 */
	private String implementEntity;

	/**
	 * 实施主体性质
	 */
	private String implementNature;

	/**
	 * 实施主体编码
	 */
	private String column14;

	/**
	 * 委托部门
	 */
	private String entrusteDepart;

	/**
	 * 法定办结时限
	 */
	private String statutoryTimeLimit;

	/**
	 * 法定办结时限单位
	 */
	private String statutoryTimeLimitUnit;

	/**
	 * 承诺办结时限
	 */
	private String commitmentTimeLimit;

	/**
	 * 承诺办结时限单位
	 */
	private String commitmentTimeLimitUnit;

	/**
	 * 是否收费
	 */
	private String isCharge;

	/**
	 * 收费依据
	 */
	private String chargeBasis;

	/**
	 * 服务对象
	 */
	private String serviceObject;

	/**
	 * 办件类型
	 */
	private String handbookType;

	/**
	 * 办理形式
	 */
	private String handlForm;

	/**
	 * 到办事现场次数
	 */
	private String tripsSceneNum;

	/**
	 * 特别程序
	 */
	private String specialProcedure;

	/**
	 * 移动端是否对接单点登录
	 */
	private String isMobileSso;

	/**
	 * 移动端办理地址
	 */
	private String mobileAddress;

	/**
	 * 计算机端?是否对接单点登录
	 */
	private String isComputerSso;

	/**
	 * 计算机端在线办理跳转地址?
	 */
	private String computerOnlineUrl;

	/**
	 * 办理地点
	 */
	private String processAddress;

	/**
	 * 办理时间
	 */
	private String processTime;

	/**
	 * 咨询方式
	 */
	private String consultMethod;

	/**
	 * 监督投诉方式
	 */
	private String superviseComplaint;

	/**
	 * 计划生效日期
	 */
	private String effectiveDate;

	/**
	 * 计划取消日期
	 */
	private String cancellationDate;

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getSettingBasis() {
		return settingBasis;
	}

	public void setSettingBasis(String settingBasis) {
		this.settingBasis = settingBasis;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getExerciseLevel() {
		return exerciseLevel;
	}

	public void setExerciseLevel(String exerciseLevel) {
		this.exerciseLevel = exerciseLevel;
	}

	public String getImplementCode() {
		return implementCode;
	}

	public void setImplementCode(String implementCode) {
		this.implementCode = implementCode;
	}

	public String getBusinessProcessCode() {
		return businessProcessCode;
	}

	public void setBusinessProcessCode(String businessProcessCode) {
		this.businessProcessCode = businessProcessCode;
	}

	public String getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getItemVersion() {
		return itemVersion;
	}

	public void setItemVersion(String itemVersion) {
		this.itemVersion = itemVersion;
	}

	public String getImplementEntity() {
		return implementEntity;
	}

	public void setImplementEntity(String implementEntity) {
		this.implementEntity = implementEntity;
	}

	public String getImplementNature() {
		return implementNature;
	}

	public void setImplementNature(String implementNature) {
		this.implementNature = implementNature;
	}

	public String getColumn14() {
		return column14;
	}

	public void setColumn14(String column14) {
		this.column14 = column14;
	}

	public String getEntrusteDepart() {
		return entrusteDepart;
	}

	public void setEntrusteDepart(String entrusteDepart) {
		this.entrusteDepart = entrusteDepart;
	}

	public String getStatutoryTimeLimit() {
		return statutoryTimeLimit;
	}

	public void setStatutoryTimeLimit(String statutoryTimeLimit) {
		this.statutoryTimeLimit = statutoryTimeLimit;
	}

	public String getStatutoryTimeLimitUnit() {
		return statutoryTimeLimitUnit;
	}

	public void setStatutoryTimeLimitUnit(String statutoryTimeLimitUnit) {
		this.statutoryTimeLimitUnit = statutoryTimeLimitUnit;
	}

	public String getCommitmentTimeLimit() {
		return commitmentTimeLimit;
	}

	public void setCommitmentTimeLimit(String commitmentTimeLimit) {
		this.commitmentTimeLimit = commitmentTimeLimit;
	}

	public String getCommitmentTimeLimitUnit() {
		return commitmentTimeLimitUnit;
	}

	public void setCommitmentTimeLimitUnit(String commitmentTimeLimitUnit) {
		this.commitmentTimeLimitUnit = commitmentTimeLimitUnit;
	}

	public String getIsCharge() {
		return isCharge;
	}

	public void setIsCharge(String isCharge) {
		this.isCharge = isCharge;
	}

	public String getChargeBasis() {
		return chargeBasis;
	}

	public void setChargeBasis(String chargeBasis) {
		this.chargeBasis = chargeBasis;
	}

	public String getServiceObject() {
		return serviceObject;
	}

	public void setServiceObject(String serviceObject) {
		this.serviceObject = serviceObject;
	}

	public String getHandbookType() {
		return handbookType;
	}

	public void setHandbookType(String handbookType) {
		this.handbookType = handbookType;
	}

	public String getHandlForm() {
		return handlForm;
	}

	public void setHandlForm(String handlForm) {
		this.handlForm = handlForm;
	}

	public String getTripsSceneNum() {
		return tripsSceneNum;
	}

	public void setTripsSceneNum(String tripsSceneNum) {
		this.tripsSceneNum = tripsSceneNum;
	}

	public String getSpecialProcedure() {
		return specialProcedure;
	}

	public void setSpecialProcedure(String specialProcedure) {
		this.specialProcedure = specialProcedure;
	}

	public String getIsMobileSso() {
		return isMobileSso;
	}

	public void setIsMobileSso(String isMobileSso) {
		this.isMobileSso = isMobileSso;
	}

	public String getMobileAddress() {
		return mobileAddress;
	}

	public void setMobileAddress(String mobileAddress) {
		this.mobileAddress = mobileAddress;
	}

	public String getIsComputerSso() {
		return isComputerSso;
	}

	public void setIsComputerSso(String isComputerSso) {
		this.isComputerSso = isComputerSso;
	}

	public String getComputerOnlineUrl() {
		return computerOnlineUrl;
	}

	public void setComputerOnlineUrl(String computerOnlineUrl) {
		this.computerOnlineUrl = computerOnlineUrl;
	}

	public String getProcessAddress() {
		return processAddress;
	}

	public void setProcessAddress(String processAddress) {
		this.processAddress = processAddress;
	}

	public String getProcessTime() {
		return processTime;
	}

	public void setProcessTime(String processTime) {
		this.processTime = processTime;
	}

	public String getConsultMethod() {
		return consultMethod;
	}

	public void setConsultMethod(String consultMethod) {
		this.consultMethod = consultMethod;
	}

	public String getSuperviseComplaint() {
		return superviseComplaint;
	}

	public void setSuperviseComplaint(String superviseComplaint) {
		this.superviseComplaint = superviseComplaint;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getCancellationDate() {
		return cancellationDate;
	}

	public void setCancellationDate(String cancellationDate) {
		this.cancellationDate = cancellationDate;
	}

	public String getAffairid() {
		return affairid;
	}

	public void setAffairid(String affairid) {
		this.affairid = affairid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIsgz() {
		return isgz;
	}

	public void setIsgz(String isgz) {
		this.isgz = isgz;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public AffairsList() {
		super();
	}

	public AffairsList(String id) {
		super(id);
	}

	@Length(min = 0, max = 100, message = "事项名称长度必须介于 0 和 100 之间")
	public String getAffairname() {
		return affairname;
	}

	public void setAffairname(String affairname) {
		this.affairname = affairname;
	}

	public String getAffairserialno() {
		return affairserialno;
	}

	public void setAffairserialno(String affairserialno) {
		this.affairserialno = affairserialno;
	}

	@Length(min = 0, max = 100, message = "事项编号长度必须介于 0 和 100 之间")
	public String getAffairno() {
		return affairno;
	}

	public void setAffairno(String affairno) {
		this.affairno = affairno;
	}

	@Length(min = 0, max = 50, message = "事项图标长度必须介于 0 和 50 之间")
	public String getAffairicon() {
		return affairicon;
	}

	public void setAffairicon(String affairicon) {
		this.affairicon = affairicon;
	}

	@Length(min = 0, max = 200, message = "主管司局长度必须介于 0 和 200 之间")
	public String getAffairbureau() {
		return affairbureau;
	}

	public void setAffairbureau(String affairbureau) {
		this.affairbureau = affairbureau;
	}

	public String getAffaircondition() {
		return affaircondition;
	}

	public void setAffaircondition(String affaircondition) {
		this.affaircondition = affaircondition;
	}

	public String getAffairprocess() {
		return affairprocess;
	}

	public void setAffairprocess(String affairprocess) {
		this.affairprocess = affairprocess;
	}

	public String getAffairexplain() {
		return affairexplain;
	}

	public void setAffairexplain(String affairexplain) {
		this.affairexplain = affairexplain;
	}

	public String getAffairfiles() {
		return affairfiles;
	}

	public void setAffairfiles(String affairfiles) {
		this.affairfiles = affairfiles;
	}

	@Length(min = 0, max = 20, message = "按用户分类长度必须介于 0 和 20 之间")
	public String getAffairbyuser() {
		return affairbyuser;
	}

	public void setAffairbyuser(String affairbyuser) {
		this.affairbyuser = affairbyuser;
	}

	@Length(min = 0, max = 100, message = "按类别分类长度必须介于 0 和 100 之间")
	public String getAffairbycategory() {
		return affairbycategory;
	}

	public void setAffairbycategory(String affairbycategory) {
		this.affairbycategory = affairbycategory;
	}

	@Length(min = 0, max = 200, message = "按司局分类长度必须介于 0 和 200 之间")
	public String getAffairbybureau() {
		return affairbybureau;
	}

	public void setAffairbybureau(String affairbybureau) {
		this.affairbybureau = affairbybureau;
	}

	@Length(min = 0, max = 100, message = "按系统分类长度必须介于 0 和 100 之间")
	public String getAffairbysystem() {
		return affairbysystem;
	}

	public void setAffairbysystem(String affairbysystem) {
		this.affairbysystem = affairbysystem;
	}

	@Length(min = 0, max = 50, message = "事项图标1长度必须介于 0 和 50 之间")
	public String getAffairicon1() {
		return affairicon1;
	}

	public void setAffairicon1(String affairicon1) {
		this.affairicon1 = affairicon1;
	}

	public String getAffairUrl() {
		return affairUrl;
	}

	public void setAffairUrl(String affairUrl) {
		this.affairUrl = affairUrl;
	}

	public String getExtraUrls() {
		return extraUrls;
	}

	public void setExtraUrls(String extraUrls) {
		this.extraUrls = extraUrls;
	}

	public String getExtraUrls2() {
		return extraUrls2;
	}

	public void setExtraUrls2(String extraUrls2) {
		this.extraUrls2 = extraUrls2;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}

	public List<OAuthClient> getClientlist() {
		return clientlist;
	}

	public void setClientlist(List<OAuthClient> clientlist) {
		this.clientlist = clientlist;
	}
}