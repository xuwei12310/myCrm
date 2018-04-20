package com.xuwei.realm;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author lys
 * @Description:自定义用户名密码Token
 * @Copyright: 福州骏华信息有限公司 (c)2015
 * @Created Date : 2015-3-31
 * @vesion 1.0
 */
public class AuthenticationToken extends UsernamePasswordToken {
    private static final long serialVersionUID = 5898441540965086534L;
    private String captchaId;
    private String captcha;

    public AuthenticationToken(String username, String password,
                               String captchaId, String captcha, boolean rememberMe, String host) {
        super(username, password, rememberMe, host);
        this.captchaId = captchaId;
        this.captcha = captcha;
    }

    public String getCaptchaId() {
        return this.captchaId;
    }

    public void setCaptchaId(String captchaId) {
        this.captchaId = captchaId;
    }

    public String getCaptcha() {
        return this.captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
