/**
 * 
 */
package com.xuwei.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.xuwei.bean.CoreUser;
import com.xuwei.bean.Resources;
import com.xuwei.bean.User;
import com.xuwei.service.CoreUserIService;
import com.xuwei.service.IMainService;
import com.xuwei.service.IUserService;
import com.xuwei.util.CryptographyUtil;
import com.xuwei.util.OperateUtils;


/**
 * @description:
 * @createTime 2017年12月11日 上午10:43:14
 * @copyright 福建骏华有限公司（c）2017
 * @author xw
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController<CoreUser>{
	
	@Resource
	private CoreUserIService coreUserService;
	@Resource
	private IMainService mainService;
	@Resource
	private IUserService userService;
	@RequestMapping(value="toLogin")
	public String toLogin(){
		return "login";
	}
	@RequestMapping("login")
	public String login(CoreUser user,HttpServletRequest request,Model model){
		String vUserName=request.getParameter("username");
		String vUserPwd=request.getParameter("password");
		if(StringUtils.isEmpty(vUserName) || StringUtils.isEmpty(vUserPwd)){
			request.setAttribute("errorMsg", "用户名或密码不能为空！");
			return "login";
		}
		vUserPwd=CryptographyUtil.md5(vUserPwd, "xuwei");
		Subject subject =SecurityUtils.getSubject();
		UsernamePasswordToken token=new UsernamePasswordToken(vUserName, vUserPwd);
		
		try{
			subject.login(token);
			User currentUser = OperateUtils.getCurrentUser();
			List<Resources> resourcesList=mainService.findRootResourcesByUser(currentUser);
			model.addAttribute("resourcesList", resourcesList);
			User info = userService.selectUserInfo(currentUser.getId());
			model.addAttribute("info",info);
			return "main";
		}catch(UnknownAccountException e1){
			request.setAttribute("errorMsg", "用户不存在！");
			return "login";
		}catch(LockedAccountException e2){
			request.setAttribute("errorMsg", "账号已被锁定，请联系管理员！");
			return "login";
		}catch(DisabledAccountException e3){
			request.setAttribute("errorMsg", "账号已被禁用！");
			return "login";
		}catch (IncorrectCredentialsException e4) {
			request.setAttribute("errorMsg", "用户名或密码错误！");
			return "login";
		}
	/*	String vmsg="";
		request.setAttribute("username", vUserName);
		if(SysFun.isNullOrEmpty(vUserName)){
			vmsg+="账号不能为空!  ";
		}
		if(SysFun.isNullOrEmpty(vUserPwd)){
			vmsg+="密码不能为空! ";
		}
		//如果验证失败，则将失败内容放到作用域变量，并转发到页面
		if(!SysFun.isNullOrEmpty(vmsg)){
			request.setAttribute("msg", vmsg);
			System.out.println("msg:"+vmsg);
			return "login"; 
		}
		EntityWrapper<CoreUser> ew=new EntityWrapper<>();
		ew.eq("username", user.getUsername());
		List<CoreUser> selectList = coreUserService.selectList(ew);
		if(selectList.size()<1){
			request.setAttribute("msg", "账号不存在");
			
			return "login"; 
		}
		CoreUser bean=selectList.get(0);
		if(!bean.getUserpwd().equals(vUserPwd)){
			request.setAttribute("msg", "密码错误");
			return "login"; 
		}
		request.getSession().setAttribute("user", bean);
		return "main";*/
		
	}
	@RequestMapping("logout")
	public String logout(HttpServletRequest request){
		request.getSession().removeAttribute("user");
		return "login";
	}
}
