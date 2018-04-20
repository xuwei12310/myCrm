package com.xuwei.realm;

import org.apache.commons.lang.time.DateUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.xuwei.bean.User;
import com.xuwei.service.IUserService;
import com.xuwei.util.Md5Utils;
import com.xuwei.util.SpringMVCUtils;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

/**
 * @author lys
 * @Description:shiro认证授权
 * @Copyright: 福州骏华信息有限公司 (c)2015
 * @Created Date : 2015-3-31
 * @vesion 1.0
 */
public class AuthenticationRealm extends AuthorizingRealm {

	/*
	 * @Resource(name = "captchaServiceImpl") private CaptchaService
	 * captchaService;
	 */
	@Resource
	private IUserService userService;

	/**
     * 根据Token获取认证信息
     */
    protected AuthenticationInfo doGetAuthenticationInfo(
            org.apache.shiro.authc.AuthenticationToken token) {
       /* AuthenticationToken authenticationToken = (AuthenticationToken) token;*/
    	UsernamePasswordToken userNamePasswordtoken=(UsernamePasswordToken) token;
        String username = userNamePasswordtoken.getUsername();
        String password=String.valueOf(userNamePasswordtoken.getPassword());
       // String password = new String(authenticationToken.getPassword());
        User user = userService.findByUsername(username);
        
        /*String captchaId = authenticationToken.getCaptchaId();
        String captcha = authenticationToken.getCaptcha();
        String host = authenticationToken.getHost();*/

        //验证码校验 开发中去除
       /* if (!this.captchaService.isValid(captchaId,captcha)){
			throw new UnsupportedTokenException();
		}
*/
        if(StringUtils.isEmpty(username) ||StringUtils.isEmpty(password)){
        	throw new UnknownAccountException();
        }
        if(user==null){
        	throw new UnknownAccountException();
        }
        if(user.getStatus()==0){
        	throw new DisabledAccountException();
        }
        int sysAccountLockTime=10;
        if(user.getIsLock()==1){
        	 Date lockTime = user.getLockTime();
             Date deblockingTime = DateUtils.addMinutes(lockTime, sysAccountLockTime);
             if (new Date().after(deblockingTime)) {
                 user.setLoginFailureCount(Integer.valueOf(0));
                 user.setIsLock(0);
                 user.setLockTime(null);
                 this.userService.update(user);
             } else {
                 throw new LockedAccountException();
             }
         }else{
             user.setLoginFailureCount(Integer.valueOf(0));
             user.setIsLock(0);
             user.setLockTime(null);
             userService.update(user);
         }
        int sysAccountLockCount=5;
        if(!matches(user, password)){
        	  int loginFailureCount = user.getLoginFailureCount().intValue() + 1;
              if (loginFailureCount >= sysAccountLockCount) {
                  user.setIsLock(1);
                 // user.setLoginTime(new Date());
                  user.setLockTime(new Date());
              }
              user.setLoginFailureCount(loginFailureCount);
              this.userService.update(user);
              throw new IncorrectCredentialsException();
          }
        
        SpringMVCUtils.getSession().setAttribute("user",user);
        AuthenticationInfo info = new SimpleAuthenticationInfo(new Principal(user.getId(), username,user.getName()), password, getName());
        return info;
       /* if (user!=null) {
            if (user == null) {
                throw new UnknownAccountException();
            }
            if ("禁用".equals(user.getStatus())) {
                throw new DisabledAccountException();
            }
            int sysAccountLockTime = CommonUtil.getAppProParam("sys.accountLockTime") == null ? 10 : Integer.parseInt(CommonUtil.getAppProParam("sys.accountLockTime"));
            if ("是".equals(user.getIsLock())) {
                Date lockTime = user.getLockTime();
                Date deblockingTime = DateUtils.addMinutes(lockTime, sysAccountLockTime);
                if (new Date().after(deblockingTime)) {
                    user.setLoginFailureCount(Integer.valueOf(0));
                    user.setIsLock(0);
                    user.setLockTime(null);
                    this.userService.update(user);
                } else {
                    throw new LockedAccountException();
                }
            } else {
                user.setLoginFailureCount(Integer.valueOf(0));
                user.setIsLock(0);
                user.setLockTime(null);
                userService.update(user);
            }
            int sysAccountLockCount = CommonUtil.getAppProParam("sys.accountLockCount") == null ? 5 : Integer.parseInt(CommonUtil.getAppProParam("sys.accountLockCount"));

            if (!matches(user, password)) {
                int loginFailureCount = user.getLoginFailureCount().intValue() + 1;
                if (loginFailureCount >= sysAccountLockCount) {
                    user.setIsLock(1);
                   // user.setLoginTime(new Date());
                    user.setLockTime(new Date());
                }
                user.setLoginFailureCount(loginFailureCount);
                this.userService.update(user);
                throw new IncorrectCredentialsException();
            }
           // user.setLoginTime(new Date());
           // user.setLoginFailureCount(0);
            this.userService.update(user);

            SpringMVCUtils.getSession().setAttribute("loginName", user.getName());

          //  LogUtils.logUserByLogin(user.getId().toString(), username, "登录退出模块", "登录", "登录成功");
            // 如果身份认证验证成功，返回一个AuthenticationInfo实现；
            return new SimpleAuthenticationInfo(new Principal(user.getId(), username,user.getName()), password, getName());
        }
        throw new UnknownAccountException();*/
    }

	public boolean matches(User user, String newPassword) {
		return user.getPassword().equals(newPassword);
	}

	public String encryptPassword(String username, String password, String salt) {
		return Md5Utils.hash(username + password + salt);
	}

	/**
	 * 从数据库中取出用户的授权信息并放在AuthorizationInfo中
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) principals.fromRealm(getName()).iterator().next();
		if (principal != null) {
			List<String> authoritiesList = this.userService.findAuthorities(principal.getId());
			SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
			if (authoritiesList != null) {
				authorizationInfo.addStringPermissions(authoritiesList);
			}
			List<String> roles = this.userService.findRoles(principal.getId());
			authorizationInfo.addRoles(roles);
			return authorizationInfo;
		}
		return null;
	}
}
