package cn.ac.ict.modules.ws.entity;  
 
/** 
 * @ClassName: Stream
 * @description: 
 * @author: LiGuoqiang
 * @Date: 2020年2月18日 下午1:19:36
 */
public class Stream {

	private String systemName;  //系统名称
	private String systemNameEng;//系统英文名称
	private String systemOperationStatus;//系统运行状态
	private String onlineUser;//系统当前在线用户数
	private String registerUserAllNum;//系统用户注册总数
	private String registerUserNum;//系统当前用户注册数
	private String dailyVisits;//系统当日访问量
	private String urlAdress;//URL地址
	private String urlRespTime;//URL 响应时长
	private String urlRespStatusCode;////URL 响应状态码
	private String sysRespTime;//请求响应时间
	private String businessVolume;//业务量
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public String getSystemNameEng() {
		return systemNameEng;
	}
	public void setSystemNameEng(String systemNameEng) {
		this.systemNameEng = systemNameEng;
	}
	public String getSystemOperationStatus() {
		return systemOperationStatus;
	}
	public void setSystemOperationStatus(String systemOperationStatus) {
		this.systemOperationStatus = systemOperationStatus;
	}
	public String getOnlineUser() {
		return onlineUser;
	}
	public void setOnlineUser(String onlineUser) {
		this.onlineUser = onlineUser;
	}
	public String getRegisterUserAllNum() {
		return registerUserAllNum;
	}
	public void setRegisterUserAllNum(String registerUserAllNum) {
		this.registerUserAllNum = registerUserAllNum;
	}
	public String getRegisterUserNum() {
		return registerUserNum;
	}
	public void setRegisterUserNum(String registerUserNum) {
		this.registerUserNum = registerUserNum;
	}
	public String getDailyVisits() {
		return dailyVisits;
	}
	public void setDailyVisits(String dailyVisits) {
		this.dailyVisits = dailyVisits;
	}
	public String getUrlAdress() {
		return urlAdress;
	}
	public void setUrlAdress(String urlAdress) {
		this.urlAdress = urlAdress;
	}
	public String getUrlRespTime() {
		return urlRespTime;
	}
	public void setUrlRespTime(String urlRespTime) {
		this.urlRespTime = urlRespTime;
	}
	public String getUrlRespStatusCode() {
		return urlRespStatusCode;
	}
	public void setUrlRespStatusCode(String urlRespStatusCode) {
		this.urlRespStatusCode = urlRespStatusCode;
	}
	public String getSysRespTime() {
		return sysRespTime;
	}
	public void setSysRespTime(String sysRespTime) {
		this.sysRespTime = sysRespTime;
	}
	public String getBusinessVolume() {
		return businessVolume;
	}
	public void setBusinessVolume(String businessVolume) {
		this.businessVolume = businessVolume;
	}
	@Override
	public String toString() {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("<?xml version=\"1.0\" encoding=\" UTF-8\"?><stream>");
		sBuffer.append("<systemName>"+systemName+"</systemName>");
		sBuffer.append("<systemNameEng>"+systemNameEng+"</systemNameEng>");
		sBuffer.append("<systemOperationStatus>"+systemOperationStatus+"</systemOperationStatus>");
		sBuffer.append("<onlineUser>"+onlineUser+"</onlineUser>");
		sBuffer.append("<registerUserAllNum>"+registerUserAllNum+"</registerUserAllNum>");
		sBuffer.append("<registerUserNum>"+registerUserNum+"</registerUserNum>");
		sBuffer.append("<dailyVisits>"+dailyVisits+"</dailyVisits>");
		sBuffer.append("<urlAdress>"+urlAdress+"</urlAdress>");
		sBuffer.append("<urlRespTime>"+urlRespTime+"</urlRespTime>");
		sBuffer.append("<urlRespTime>"+urlRespTime+"</urlRespTime>");
		sBuffer.append("<urlRespStatusCode>"+urlRespStatusCode+"</urlRespStatusCode>");
		sBuffer.append("<sysRespTime>"+sysRespTime+"</sysRespTime>");
		sBuffer.append("<businessVolume>"+businessVolume+"</businessVolume>");
		sBuffer.append("</stream>");
		return sBuffer.toString();
	}
	
	
}
 

