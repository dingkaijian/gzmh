package cn.ac.ict.modules.sys.security.realm;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.Servlets;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm.Principal;
import cn.ac.ict.common.servlet.ValidateCodeServlet;
import cn.ac.ict.common.utils.Constants;
/*import cn.ac.ict.modules.sys.entity.PwUpdateRecord;*/
//import cn.ac.ict.modules.sys.security.principal.Principal;
import cn.ac.ict.modules.sys.security.token.UsernamePasswordToken;
/*import cn.ac.ict.modules.sys.service.PwUpdateRecordService;*/
import cn.ac.ict.modules.sys.utils.LogUtils;
import cn.ac.ict.modules.sys.utils.UserAuthUtils;
import cn.ac.ict.modules.user.entity.UserAuth;
import gov.zwfw.iam.comm.DoUtil;

/**
 * 自定义的系统安全认证实现类 用于进行认证和授权
 * @author changyanbo
 *
 */
@Service
public class IctSystemAuthorizingRealm extends AuthorizingRealm {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private SystemService systemService;
	
	/*private PwUpdateRecordService pwUpdateRecordService; */
	
	public IctSystemAuthorizingRealm() {
		this.setCachingEnabled(false);
	}

	/**
	 * 认证回调函数, 登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		if (logger.isDebugEnabled()){
			logger.debug("登录提交。进入认证方法：doGetAuthenticationInfo");
		}
		// 校验登录验证码
		Session session = UserAuthUtils.getSession();
		String code = (String)session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
		if (token.getValidateCode() == null || !token.getValidateCode().equalsIgnoreCase(code)){
			throw new AuthenticationException("msg:请输入正确的验证码");
		}
		//校验手机验证码
		if("1".equals(getSystemService().getSwitch("mobileCode"))){ //手机验证码开关
			String mobileCode = (String)session.getAttribute(Constants.MOBILE_VALIDATE_CODE);
			if (token.getMobileCode() == null || !token.getMobileCode().equalsIgnoreCase(mobileCode)) {
				throw new AuthenticationException("msg:请输入正确的手机验证码");
			}
		}
		
		User user = null;
		User queryUser = new User();
		queryUser.setLoginName(token.getUsername());
		user = getSystemService().getUserByLoginName(token.getUsername());
		if (user != null) {
			//校验客户端证书中的用户姓名，与账号对应的用户姓名是否匹配
			if("1".equals(getSystemService().getSwitch("certificate"))){//此处增加客户端证书校验开关
				if(!user.getName().equals(token.getCaUserName())){
					throw new AuthenticationException("msg:您的证书信息与当前用户不匹配,请重新登录.");
				}
			}
			
			// 由IctCredentialsMatcher进行密码验证处理
			return new SimpleAuthenticationInfo(new Principal(user,true), user.getPassword(), getName());
		} else {
			//新日志添加 20190731
			LogUtils.saveLog(null, null, null, null, "R", "2", "系统登录-用户名或密码错误",null , token.getUsername(), Servlets.getRequest(), null);
			return new SimpleAuthenticationInfo(new Principal(), "", getName());
		}
	}
	
	/**
	 * 获取权限授权信息，如果缓存中存在，则直接从缓存中获取，否则就重新获取， 登录成功后调用
	 * 注意：1.spring-mvc.xml中要配置Shiro对Controller的方法级AOP安全控制
	 * 2.何时调用该方法：当Controller的方法添加@RequiresPermissions("xxx")时，
	 * 或页面使用Shiro标签判断权限时调用该方法
	 */
	@Override
	protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
		if (logger.isDebugEnabled()){
			logger.debug("登录成功后，进入获取授权信息方法（从缓存获取）：getAuthorizationInfo");
		}
		if (principals == null) {
            return null;
        }
		// 从session中获取授权信息
        AuthorizationInfo info = null;
        // 认证不负责获取权限，由资源系统负责，因此此处无需从库查询和设置缓存
        //info = (AuthorizationInfo)UserUtils.getCache(UserUtils.CACHE_AUTH_INFO);
        if (info == null) {
            info = doGetAuthorizationInfo(principals);
            /*if (info != null) {
            	UserUtils.putCache(UserUtils.CACHE_AUTH_INFO, info);// 存在session中的
            }*/
        }
        return info;
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if (logger.isDebugEnabled()){
			logger.debug("缓存无授权信息，进入授权查询方法：doGetAuthorizationInfo");
		}
		try{
			// 处理同一账号多次登录问题
			Principal principal = (Principal) getAvailablePrincipal(principals);
//			if (!Global.TRUE.equals(Global.getConfig("user.multiAccountLogin"))){
//				// 获取当前账号已登录过的所有session集合
//				Collection<Session> sessions = getSystemService().getSessionDao().getActiveSessions(true, principal, UserAuthUtils.getSession());
//				if (sessions.size() > 0){
//					// 如果是登录进来的，则踢出已在线用户
//					if (UserAuthUtils.getSubject().isAuthenticated()){
//						for (Session session : sessions){
//							getSystemService().getSessionDao().delete(session);
//							// 让该账号对应的所有token失效，同时由资源系统挨个注销已登录的子系统
//						}
//					} else{
//						/*处理该情况：1.用户用A浏览器点记住我，且正常登录。2.用户关闭A浏览器。3.用户用B浏览器登录账号。
//						4.用户再用A浏览器登录，在地址栏输入系统地址（因记住我功能开启所以可随时输入地址进系统），此时出现下面的提示信息*/
//						UserAuthUtils.getSubject().logout();
//						throw new AuthenticationException("msg:账号已在其它地方登录，请重新登录。");
//					}
//				}
//			}
			
			//UserAuth userAuth = UserAuthUtils.getByCertNoOrLogin(principal.getCertificateno(), principal.getLoginName(),principal.getType());		
			if (principal != null) {
				SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
				// 认证不负责获取权限，由资源系统负责，因此此处无需从库查询和设置缓存
				/*List<Menu> list = UserUtils.getMenuList();
				for (Menu menu : list){
					if (StringUtils.isNotBlank(menu.getPermission())){
						// 添加基于Permission的权限信息
						for (String permission : StringUtils.split(menu.getPermission(),",")){
							info.addStringPermission(permission);
						}
					}
				}*/
				
				/*添加固定的用户权限，LoginController类的index方法添加了判断是否有user权限的注解，因此这里需要有。
				而LoginController类的index方法添加判断权限注解的目标看index方法的注释*/
				info.addStringPermission("user");			
				
				// 添加用户角色信息
				/*for (Role role : user.getRoleList()){
					info.addRole(role.getEnname());
				}			
				// 更新登录IP和时间
				getSystemService().updateUserLoginInfo(user);*/
				
				// 记录登录日志
				//新日志添加 20190731
				LogUtils.saveLog(null, null, null, null, "R", "1", "系统登录",principal.getLoginName() , principal.getName(), Servlets.getRequest(), null);
				return info;
			} else {
				return new SimpleAuthorizationInfo();
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new AuthenticationException("msg:系统错误");
		}
	}
	
	/**
	 * 获取系统业务对象
	 */
	public SystemService getSystemService() {
		if (systemService == null){
			systemService = SpringContextHolder.getBean(SystemService.class);
		}
		return systemService;
	}
	
	/*public PwUpdateRecordService getPwUpdateRecordService() {
		System.out.println("==============ICTRealm--getPwUpdateRecordService");
		if (pwUpdateRecordService == null){
			pwUpdateRecordService = SpringContextHolder.getBean(PwUpdateRecordService.class);
		}
		return pwUpdateRecordService;
	}*/
	
	@Override
	public boolean supports(AuthenticationToken token) {
		if(token instanceof UsernamePasswordToken){
			return true;
		}
		return false;
	}
}
