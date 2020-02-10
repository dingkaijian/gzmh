package cn.ac.ict.modules.sys.security.matcher;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm.Principal;
import cn.ac.ict.common.security.encryption.sm.SM2Util;
import cn.ac.ict.modules.sys.entity.LoginLimit;
//import cn.ac.ict.modules.sys.security.principal.Principal;
import cn.ac.ict.modules.sys.security.token.UsernamePasswordToken;
import cn.ac.ict.modules.sys.service.LoginLimitService;
/*import cn.ac.ict.modules.sys.service.LoginLimitService;
import cn.ac.ict.modules.sys.service.PwUpdateRecordService;*/
import cn.ac.ict.modules.user.service.UserAuthService;

/**
 * 密码匹配器
 * 
 * @author changyanbo
 *
 */
public class IctCredentialsMatcher extends SimpleCredentialsMatcher {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private LoginLimitService loginLimitService;

	@Autowired
	private UserAuthService userAuthService;
/*	@Autowired
	private PwUpdateRecordService pwUpdateRecordService;
	@Autowired
	private LoginLimitService loginLimitService;*/

	/**
	 * 用户名密码的匹配逻辑，使用原门户的匹配逻辑
	 */
	@SuppressWarnings("unchecked")
	public boolean doCredentialsMatch(AuthenticationToken authctoken, AuthenticationInfo info) {
		System.out.println("================ICTMatcher--doCredentialsMatch");
		UsernamePasswordToken token = (UsernamePasswordToken) authctoken;
		Object accountCredentials = getCredentials(info);
		// 通过用户登录名从数据库中获取用户
		List<Principal> principals = info.getPrincipals().asList();
		System.out.println("==============principals:"+principals);
		Principal principal = principals.get(0);
		System.out.println("==============principal:"+principal);
		// 用户输入的密码，已经使用js进行SM2加密过的
		String userInputPassword = String.valueOf(token.getPassword());

		// 将用户传进来的密码，使用SM2进行解密，解密后为明文
		// 从数据库根据用户登录名获取，用户密码的加密方式
		// 如果加密方式为MD5，将明文进行MD5加密，与数据库密码进行匹配
		// 如果加密方式为SM3，将明文进行SM3加密，与数据库密码进行匹配
		userInputPassword = SM2Util.doPassword(userInputPassword, principal.getEncMode());
		// UserAuth user =
		// userAuthService.getByLoginName(principal.getLoginName());
		// 随机key
		String key = token.getKey();
		// 数据库中加密的密码
		// String dbSavedPassword =
		// PasswordSecurity.processPassword(accountCredentials.toString(), key);
		String dbSavedPassword = accountCredentials.toString();
		boolean flag = equals(userInputPassword, dbSavedPassword);
		System.out.println("==================dbSavedPassword:"+dbSavedPassword);
		System.out.println("==================userInputPassword:"+userInputPassword);
		System.out.println("===================flag:"+flag);
		if (principal.getLoginName() == null) {
			//LoginLimitmethod(token.getUsername(), token.getHost(), token, flag);
			//token.setMessage("用户名或密码错误");
			System.out.println("=================principal.getLoginName():"+principal.getLoginName());
			return LoginLimitmethod(token.getUsername(), token.getHost(), token, flag);
		}
		flag = LoginLimitmethod(token.getUsername(), token.getHost(), token, flag);
		
		if (!flag) {
			System.out.println("===============token.getMessage():"+token.getMessage());
			throw new AuthenticationException("msg:"+token.getMessage());
		}
		/*if (principal.getLoginName() == null) {
			//LoginLimitmethod(token.getUsername(), token.getHost(), token, flag);
			//token.setMessage("用户名或密码错误");
			return LoginLimitmethod(token.getUsername(), token.getHost(), token, flag);
		}
		flag = LoginLimitmethod(token.getUsername(), token.getHost(), token, flag);
		String usertype = principal.getType();
		if(usertype!=null && "1".equals(usertype) && "natural".equals(token.getUserType())){
			//新日志添加 20190731
			throw new AuthenticationException("msg:该用户为法人用户，请选择法人登录");
		}else if(usertype!=null && "0".equals(usertype) && "legal".equals(token.getUserType())){
			//新日志添加 20190731
			throw new AuthenticationException("msg:该用户为自然人用户，请选择自然人登录");
		}
		
		// 密码日期的校验（多长时间没有修改密码了，固定时间修改密码）
		if (Global.getConfig("password.update.days.function").equals("true")) {
			List<PwUpdateRecord> pwUpdateRecordList = pwUpdateRecordService.getByUserid(principal.getId());
			if (pwUpdateRecordList.size() != 0) {
				PwUpdateRecord pwUpdateRecord = pwUpdateRecordList.get(0);
				Date endDate = new Date();
				Date startDate = pwUpdateRecord.getUpdateDate();
				long days = (endDate.getTime() - startDate.getTime()) / (1000 * 3600 * 24);
				if (days >= Integer.parseInt(Global.getConfig("password.update.days"))) {
					
					 * token.setMessage("密码过期，请修改密码"); return false;
					 
					throw new AuthenticationException("msg:密码过期，请修改密码");
				}
			}
			
		}*/
		
		System.out.println("===================flag:"+flag);
		return flag;
	}

	public boolean LoginLimitmethod(String username, String ip, UsernamePasswordToken token, boolean flag) {
		System.out.println("================ICTMatcher--LoginLimitmethod");
		LoginLimit loginLimitusername = loginLimitService.get(username);
		LoginLimit loginLimitip = loginLimitService.get(ip);
		if (loginLimitip == null) {
			loginLimitip = new LoginLimit();
			loginLimitip.setId(ip);
			loginLimitip.setLocked(0);
			loginLimitip.setLoginCount(0);
			loginLimitip.setIsNewRecord(true);
			
		}
		if (loginLimitusername == null) {
			loginLimitusername = new LoginLimit();
			loginLimitusername.setId(username);
			loginLimitusername.setLocked(0);
			loginLimitusername.setLoginCount(0);
			loginLimitusername.setIsNewRecord(true);
		}
		Date thisErrorLoginTime = null; // 修改的本次登陆错误时间
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datestr = format.format(date);
		try {
			thisErrorLoginTime = format.parse(datestr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String message="";
		if (!flag) {
			// 用户名密码错误
			message="登录名或密码错误！";
			if (loginLimitip.getLocked() == 1) {
				// ip锁定
				Date lastLoginErrorTime = null; // 最后一次登陆错误时间
				Long timeSlot = 0L;
				if (loginLimitip.getLoginTime() == null) {
					lastLoginErrorTime = thisErrorLoginTime;
				} else {
					lastLoginErrorTime = loginLimitip.getLoginTime();
					timeSlot = thisErrorLoginTime.getTime() - lastLoginErrorTime.getTime();
				}
				if (timeSlot < 1800000) { // 判断最后锁定时间,30分钟之内继续锁定
					message+="本机IP已连续登录失败20次，为了保障系统安全，系统进行强制锁定，请" + (int)(30-Math.ceil((double)timeSlot/60000)) + "分钟后再试！";
					token.setMessage(message);
				} else { // 判断最后锁定时间,30分钟之后仍是错误，继续锁定30分钟
					loginLimitip.setLoginCount(1);
					loginLimitip.setLocked(0);
					loginLimitip.setLoginTime(thisErrorLoginTime);
					loginLimitService.updateLoginLimit(loginLimitip);
					if (loginLimitusername.getLocked() == 1) {
						// username锁定
						if (loginLimitusername.getLoginTime() == null) {
							lastLoginErrorTime = thisErrorLoginTime;
						} else {
							lastLoginErrorTime = loginLimitusername.getLoginTime();
							timeSlot = thisErrorLoginTime.getTime() - lastLoginErrorTime.getTime();
						}
						if (timeSlot < 1800000) { // 判断最后锁定时间,30分钟之内继续锁定
							message+="该账号已连续登录失败6次，为了保障账号安全，系统进行强制锁定，请" + (int)(30-Math.ceil((double)timeSlot/60000)) + "分钟后再试！";
							token.setMessage(message);
						} else { // 判断最后锁定时间,30分钟之后仍是错误，继续锁定30分钟
							loginLimitusername.setLoginCount(1);
							loginLimitusername.setLocked(0);
							loginLimitusername.setLoginTime(thisErrorLoginTime);
							loginLimitService.updateLoginLimit(loginLimitusername);
							token.setMessage(message);
						}
					} else {
						// username没有锁定
						if (loginLimitusername.getLoginCount() == 5) { // 账户第六次登陆失败																// ，此时登陆错误次数增加至5，以后错误仍是5，不再递增
							loginLimitusername.setLoginCount(6);
							loginLimitusername.setLocked(1);
							loginLimitusername.setLoginTime(thisErrorLoginTime);
							loginLimitService.updateLoginLimit(loginLimitusername); // 修改用户
							message+="该账号已连续登录失败6次，为了保障账号安全，系统进行强制锁定，请30分钟后再试！";
							token.setMessage(message);
						} else { // 账户前五次登陆失败
							if (loginLimitusername.getLoginCount() == 2) {
								message+="该账号已连续登录失败3次，为了保障账号安全，再错3次，系统将进行强制锁定，请谨慎操作！如忘记账号密码，请点击“忘记密码”链接进行操作。";
							}
							loginLimitusername.setLoginCount(loginLimitusername.getLoginCount() + 1);
							loginLimitusername.setLoginTime(thisErrorLoginTime);
							loginLimitService.updateLoginLimit(loginLimitusername); // 修改用户
							token.setMessage(message);
						}
					}
				}
			} else {
				// ip没有锁定
				if (loginLimitip.getLoginCount() == 19) {													// ，此时登陆错误次数增加至5，以后错误仍是5，不再递增
					loginLimitip.setLoginCount(20);
					loginLimitip.setLocked(1);
					loginLimitip.setLoginTime(thisErrorLoginTime);
					loginLimitService.updateLoginLimit(loginLimitip); // 修改用户
					message += "本机IP已连续登录失败20次，为了保障系统安全，系统进行强制锁定！请30分钟后再试！";
				} else { 
					if (loginLimitip.getLoginCount() == 9) {
						message += "本机IP已连续登录失败10次，为了保障系统安全，再错10次，系统将针对该IP进行强制锁定！";
					}
					loginLimitip.setLoginCount(loginLimitip.getLoginCount() + 1);
					loginLimitip.setLoginTime(thisErrorLoginTime);
					loginLimitService.updateLoginLimit(loginLimitip); // 修改用户
				}
				if (loginLimitusername.getLocked() == 1) {
					// username锁定
					Date lastLoginErrorTime = null; // 最后一次登陆错误时间
					Long timeSlot = 0L;
					if (loginLimitusername.getLoginTime() == null) {
						lastLoginErrorTime = thisErrorLoginTime;
					} else {
						lastLoginErrorTime = loginLimitusername.getLoginTime();
						timeSlot = thisErrorLoginTime.getTime() - lastLoginErrorTime.getTime();
					}
					if (timeSlot < 1800000) { // 判断最后锁定时间,30分钟之内继续锁定
						message += "该账号已连续登录失败6次，为了保障账号安全，系统进行强制锁定，请" + (int)(30-Math.ceil((double)timeSlot/60000)) + "分钟后再试！";
						token.setMessage(message);
					} else { 
						loginLimitusername.setLoginCount(1);
						loginLimitusername.setLocked(0);
						loginLimitusername.setLoginTime(thisErrorLoginTime);
						loginLimitService.updateLoginLimit(loginLimitusername);
						token.setMessage(message);
					}
				} else {
					// username没有锁定
					if (loginLimitusername.getLoginCount() == 5) { 														// ，此时登陆错误次数增加至5，以后错误仍是5，不再递增
						loginLimitusername.setLoginCount(6);
						loginLimitusername.setLocked(1);
						loginLimitusername.setLoginTime(thisErrorLoginTime);
						loginLimitService.updateLoginLimit(loginLimitusername); // 修改用户
						message += "该账号已连续登录失败6次，为了保障账号安全，系统进行强制锁定，请30分钟后再试！";
						token.setMessage(message);
					} else {
						if (loginLimitusername.getLoginCount() == 2) {
							message += "该账号已连续登录失败3次，为了保障账号安全，再错3次，系统将进行强制锁定，请谨慎操作！如忘记账号密码，请点击“忘记密码”链接进行操作。";
						}
						loginLimitusername.setLoginCount(loginLimitusername.getLoginCount() + 1);
						loginLimitusername.setLoginTime(thisErrorLoginTime);
						loginLimitService.updateLoginLimit(loginLimitusername); // 修改用户
						token.setMessage(message);
					}
				}
			}
		} else {
			if (loginLimitip.getLocked()==1) {
				Date lastLoginErrorTime = null; // 最后一次登陆错误时间
				Long timeSlot = 0L;
				if (loginLimitip.getLoginTime() == null) {
					lastLoginErrorTime = new Date();
				} else {
					lastLoginErrorTime = loginLimitip.getLoginTime();
					timeSlot = new Date().getTime() - lastLoginErrorTime.getTime();
				}
				if (timeSlot < 1800000) { // 判断最后锁定时间,30分钟之内继续锁定
					message = "本机IP已连续登录失败20次，为了保障系统安全，系统进行强制锁定，请" + (int)(30-Math.ceil((double)timeSlot/60000)) + "分钟后再试！";
					token.setMessage(message);
					flag = false;
				} else { // 判断最后锁定时间,30分钟之后登陆账户
					if (loginLimitusername.getLocked()==1) {
						if (loginLimitusername.getLoginTime() == null) {
							lastLoginErrorTime = new Date();
						} else {
							lastLoginErrorTime = loginLimitusername.getLoginTime();
							timeSlot = new Date().getTime() - lastLoginErrorTime.getTime();
						}
						if (timeSlot < 1800000) { // 判断最后锁定时间,30分钟之内继续锁定
							message = "该账号已连续登录失败6次，为了保障账号安全，系统进行强制锁定，请" + (int)(30-Math.ceil((double)timeSlot/60000)) + "分钟后再试！";
							token.setMessage(message);
							flag = false;
						} 
					}
				}
			}else{
				if (loginLimitusername.getLocked()==1) {
					Date lastLoginErrorTime = null; // 最后一次登陆错误时间
					Long timeSlot = 0L;
					if (loginLimitusername.getLoginTime() == null) {
						lastLoginErrorTime = new Date();
					} else {
						lastLoginErrorTime = loginLimitusername.getLoginTime();
						timeSlot = new Date().getTime() - lastLoginErrorTime.getTime();
					}
					if (timeSlot < 1800000) { // 判断最后锁定时间,30分钟之内继续锁定
						message = "该账号已连续登录失败6次，为了保障账号安全，系统进行强制锁定，请" + (int)(30-Math.ceil((double)timeSlot/60000)) + "分钟后再试！";
						token.setMessage(message);
						flag = false;
					} 
				}
			}
		}
		return flag;
	}
 
}
