package cn.ac.ict.modules.oauth.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.security.shiro.session.SessionDAO;
import com.thinkgem.jeesite.common.web.BaseController;

import cn.ac.ict.modules.client.entity.OAuthClient;
import cn.ac.ict.modules.client.service.OAuthClientService;
import cn.ac.ict.modules.oauth.entity.AccessToken;
import cn.ac.ict.modules.oauth.handler.AuthorizationCodeTokenHandler;
import cn.ac.ict.modules.oauth.utils.MyOAuthUtils;
import cn.ac.ict.modules.sys.utils.Constants;
import cn.ac.ict.modules.sys.utils.LogUtils;

@Controller
@RequestMapping("${adminPath}/oauth/")
public class OAuthTokenController extends BaseController {

	@Autowired
	private OAuthClientService oAuthClientService;
	@Autowired
	private SessionDAO sessionDao;
	/**
	 * 处理的grant_type主要为： grant_type=authorization_code
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("token")
	public void token(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("=========================token");
		try {
			// 构建OAuth的token请求
			OAuthTokenRequest tokenRequest = new OAuthTokenRequest(request);

			if (MyOAuthUtils.isAuthorizationCode(tokenRequest)) {
				// 处理token请求
				logger.debug("Go to grant_type = 'authorization_code'");
				AuthorizationCodeTokenHandler tokenHandleDispatcher = new AuthorizationCodeTokenHandler(
						tokenRequest, response);
				AccessToken accessToken = tokenHandleDispatcher.handle();
				//新日志添加 20190802
				LogUtils.saveLog(accessToken.getClientId(), accessToken.getClientName(), accessToken.getTokenId(), Constants.SYSTEM_NAME, "R","1","======获取OAuth的Token========", accessToken.getLoginName(),null, request, null);
				Session session=  sessionDao.getSessionByID(accessToken.getSessionId());
//				UserAuth ui = userAuthService.getByLoginName(accessToken.getLoginName());
//				ui.setUserip(StringUtils.getRemoteAddr(request));
				OAuthClient oAuthClient = oAuthClientService.getClientByClientId(accessToken.getClientId());
				//新日志添加 20190802
				LogUtils.saveLog(oAuthClient.getClientId(),oAuthClient.getClientName(), accessToken.getTokenId(), Constants.SYSTEM_NAME, "R","1","获取OauthClient",accessToken.getLoginName(), null, request, null);
//				ui.setSystem(oAuthClient.getClientName());
//				session.setAttribute("onlineUser", ui);
				Map<String, String> loginOutMap = (Map<String, String>) session.getAttribute("loginOutMap");
				if (loginOutMap == null) {
					loginOutMap = new HashMap<>();
				}
				loginOutMap.put(accessToken.getClientId()+"_"+accessToken.getTokenId(),oAuthClient.getOutcomeUri());
				session.setAttribute("loginOutMap", loginOutMap);
				sessionDao.update(session);
			} else {
				unsupportGrantType(tokenRequest, response);
			}
			
		} catch (OAuthProblemException e) {
			e.printStackTrace();
			//新日志添加 20190802
			LogUtils.saveLog("R","2","======构建OAuth的token的请求时发生异常=======", request, e);
			// exception
			OAuthResponse oAuthResponse = OAuthASResponse
					.errorResponse(HttpServletResponse.SC_ACCEPTED)
					.location(e.getRedirectUri()).error(e).buildJSONMessage();
			MyOAuthUtils.writeOAuthJsonResponse(response, oAuthResponse);
		}

	}
	
	/**
	 * 不支持的grant_type
	 * 
	 * @param oauthRequest
	 * @param response
	 * @throws OAuthSystemException
	 */
	private void unsupportGrantType(OAuthTokenRequest tokenRequest,
			HttpServletResponse response) throws OAuthSystemException {
		String grantType = tokenRequest.getGrantType();
		logger.warn("Unsupport grant_type '{}' by client_id '{}'",
				grantType, tokenRequest.getClientId());

		OAuthResponse oAuthResponse = OAuthResponse
				.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
				.setError(OAuthError.TokenResponse.UNSUPPORTED_GRANT_TYPE)
				.setErrorDescription(
						"Unsupport grant_type '" + grantType + "'")
				.buildJSONMessage();
		MyOAuthUtils.writeOAuthJsonResponse(response, oAuthResponse);
	}
}
