package cn.ac.ict.modules.sys.utils;

import com.thinkgem.jeesite.common.config.Global;

/**
 * 定义常量的工具类
 * @author changyanbo
 *
 */
public class ConstantUtils {
	
	/**
	 * 登录用户信息
	 */
	public static final String USER_LOGININFO = "user_logininfo";
	/**
	 * UserFilter类需要验证的sessionId
	 */
	public static final String VALIDATION_SESSIONID = "validation_sessionId";
	/**
	 * 需要反向认证的sessionId
	 */
	public static final String CERTIFICATION_SESSIONID = "certification_sessionId";
	/**
	 * 反向认证的认证地址addr（未加密，带有http:// 或 https://）
	 */
	public static final String CERTIFICATION_ADDR_DEC = "certification_addr_dec";
	/**
	 * 反向认证的认证地址addr（加密的，没有http:// 和 https://）
	 */
	public static final String CERTIFICATION_ADDR_ENC = "certification_addr_enc";
	/**
	 * 短信发送成功
	 */
	public static final int SMS_SUCCESS = 0;
	/**
	 * 短信发送失败
	 */
	public static final int SMS_FAIL = -1;
  	/**
	 * 系统标识，用于记录日志
	 */
	public static final String SYSTEM_NAME = Global.getConfig("system.name");
	
}
