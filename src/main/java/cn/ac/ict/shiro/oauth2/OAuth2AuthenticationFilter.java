package cn.ac.ict.shiro.oauth2;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;

import com.thinkgem.jeesite.common.utils.StringUtils;

import cn.ac.ict.common.UserLoginInformation;
import cn.ac.ict.modules.sys.utils.ConstantUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;
public class OAuth2AuthenticationFilter extends AuthenticatingFilter {

    //oauth2 authc code参数名
    private String authcCodeParam = "code";
    //客户端id
    private String clientId;
    //服务器端登录成功/失败后重定向到的客户端地址
    private String redirectUrl;
    //oauth2服务器响应类型
    private String responseType = "code";

    private String failureUrl;

    public void setAuthcCodeParam(String authcCodeParam) {
        this.authcCodeParam = authcCodeParam;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public void setFailureUrl(String failureUrl) {
        this.failureUrl = failureUrl;
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        System.out.println("==============OAuth2FiltercreateToken==============");
        String code = httpRequest.getParameter(authcCodeParam);
        System.out.println("code1:" + code);
        return new OAuth2Token(code);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

    	System.out.println("==============OAuth2FilteronAccessDenied==============");
        String error = request.getParameter("error");
        String errorDescription = request.getParameter("error_description");
        if(!StringUtils.isEmpty(error)) {//如果服务端返回了错误
            WebUtils.issueRedirect(request, response, failureUrl + "?error=" + error + "error_description=" + errorDescription);
            return false;
        }

        Subject subject = getSubject(request, response);
        if(!subject.isAuthenticated()) {
            if(StringUtils.isEmpty(request.getParameter(authcCodeParam))) {
                //如果用户没有身份验证，且没有auth code，则重定向到服务端授权
                saveRequestAndRedirectToLogin(request, response);
                return false;
            }
        }

        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
    	System.out.println("==============OAuth2FilteronLoginSuccess==============");
    	System.out.println("code2:" );
    	OAuth2Token oAuth2Token = (OAuth2Token) token;
    	HttpServletRequest rq = (HttpServletRequest)request;
	    HttpSession session = rq.getSession();
	    UserLoginInformation ui = (UserLoginInformation) oAuth2Token.getUi();
	    ui.setUserid(ui.getId());
	    //用户真实IP地址
	    ui.setUserip(StringUtils.getRemoteAddr(rq));
	    session.setAttribute(ConstantUtils.USER_LOGININFO, ui);
	    session.setAttribute("key",oAuth2Token.getDynamickey());
 		String sessionId = session.getId();
 		//CryptionData.setEncCode(sessionId);
		//session.setAttribute(ConstantUtils.VALIDATION_SESSIONID, sessionId);
		session.setAttribute(ConstantUtils.CERTIFICATION_SESSIONID, sessionId);
        issueSuccessRedirect(request, response);
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ae, ServletRequest request,
                                     ServletResponse response) {
    	System.out.println("==============OAuth2FilteronLoginFailure==============");
        Subject subject = getSubject(request, response);
        if (subject.isAuthenticated() || subject.isRemembered()) {
            try {
                issueSuccessRedirect(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                WebUtils.issueRedirect(request, response, failureUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
