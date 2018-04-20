package com.xuwei.realm;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.xuwei.service.RSAService;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author lsx
 * @Description: 身份验证拦截器
 * @Copyright: 福州骏华信息有限公司 (c)2015
 * @Created Date : 2015-4-29
 * @vesion 1.0
 */
public class AuthenticationFilter extends FormAuthenticationFilter {
    private static final String ENPASSWORDPARAM = "enPassword";
    private static final String CAPTCHAIDPARAM = "captchaId";
    private static final String CAPTCHAPARAM = "captcha";
    private String enPasswordParam = ENPASSWORDPARAM;
    private String captchaIdParam = CAPTCHAIDPARAM;
    private String captchaParam = CAPTCHAPARAM;

    @Resource(name = "rsaServiceImpl")
    private RSAService rsaService;

    protected org.apache.shiro.authc.AuthenticationToken createToken(
            ServletRequest servletRequest, ServletResponse servletResponse) {
        String str1 = getUsername(servletRequest);
        String str2 = getPassword(servletRequest);
        String str3 = getCaptchaId(servletRequest);
        String str4 = getCaptcha(servletRequest);
        boolean bool = isRememberMe(servletRequest);
        String str5 = getHost(servletRequest);
        return new AuthenticationToken(str1, str2, str3, str4, bool, str5);
    }

    protected boolean onAccessDenied(ServletRequest servletRequest,
                                     ServletResponse servletResponse) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String str = request.getHeader("X-Requested-With");
        if ((str != null) && (str.equalsIgnoreCase("XMLHttpRequest"))) {
            response.addHeader("loginStatus", "accessDenied");
            try {
                response.sendError(403);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        try {
            return super.onAccessDenied(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected boolean onLoginSuccess(org.apache.shiro.authc.AuthenticationToken token, Subject subject,
                                     ServletRequest servletRequest, ServletResponse servletResponse) {
        Session session = subject.getSession();
        HashMap hashMap = new HashMap();
        Collection localCollection = session.getAttributeKeys();
        Iterator localIterator = localCollection.iterator();
        Object localObject;
        while (localIterator.hasNext()) {
            localObject = localIterator.next();
            hashMap.put(localObject,
                    session.getAttribute(localObject));
        }
        session.stop();
        session = subject.getSession();
        localIterator = hashMap.entrySet().iterator();
        while (localIterator.hasNext()) {
            localObject = localIterator.next();
            session.setAttribute(((Map.Entry) localObject).getKey(),
                    ((Map.Entry) localObject).getValue());
        }

        try {
            return super.onLoginSuccess(token, subject, servletRequest, servletResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    protected String getPassword(ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String str = this.rsaService.decryptParameter(this.enPasswordParam, request);
        this.rsaService.removePrivateKey(request);
        return str;
    }

    protected String getCaptchaId(ServletRequest paramServletRequest) {
        String str = WebUtils.getCleanParam(paramServletRequest,
                this.captchaIdParam);
        if (str == null)
            str = ((HttpServletRequest) paramServletRequest).getSession()
                    .getId();
        return str;
    }

    protected String getCaptcha(ServletRequest paramServletRequest) {
        return WebUtils.getCleanParam(paramServletRequest, this.captchaParam);
    }

    public String getEnPasswordParam() {
        return this.enPasswordParam;
    }

    public void setEnPasswordParam(String enPasswordParam) {
        this.enPasswordParam = enPasswordParam;
    }

    public String getCaptchaIdParam() {
        return this.captchaIdParam;
    }

    public void setCaptchaIdParam(String captchaIdParam) {
        this.captchaIdParam = captchaIdParam;
    }

    public String getCaptchaParam() {
        return this.captchaParam;
    }

    public void setCaptchaParam(String captchaParam) {
        this.captchaParam = captchaParam;
    }
}
