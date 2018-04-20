/**
 * 
 */
package com.xuwei.controller;


import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.CoreUser;
import com.xuwei.fun.PagerItem;
import com.xuwei.fun.SysFun;
import com.xuwei.service.CoreUserIService;
import com.xuwei.util.JSONUtil;


/**
 * @description:用户控制层
 * @createTime 2017年12月8日 下午3:20:57
 * @copyright 福建骏华有限公司（c）2017
 * @author xw
 *
 */
@RequestMapping("/coreUser")
@Controller
public class CoreUserController extends BaseController<CoreUser>{
	
	@Resource
	private CoreUserIService coreUserService;
	/**
	 * 
	 * @description:分页显示用户列表
	 * @createTime 2017年12月8日 下午4:39:25
	 * @author xw
	 * @param pageSize 每页数据条数
	 * @param pageIndex页码
	 * @param user 用于查询的bean
	 * @return
	 */
	@RequestMapping("listView")
	public String listView(Integer pageSize,Integer pageIndex,CoreUser user,HttpServletRequest request){
		EntityWrapper<CoreUser> ew=new EntityWrapper<CoreUser>();
		ew.like("username", user.getUsername());
		PagerItem pagerItem=new PagerItem();
		pagerItem.setPageIndex(pageIndex);
		pagerItem.setPageSize(pageSize);
		Page<CoreUser> page=new Page<CoreUser>(pagerItem.getPageIndex(), pagerItem.getPageSize());
		page = coreUserService.selectPage(page, ew);
		pagerItem.changeRowCount(page.getTotal());
		pagerItem.changeUrl(SysFun.generalUrl(request.getRequestURI(), request.getQueryString()));
		request.setAttribute("DataList", page.getRecords());
		request.setAttribute("pagerItem", pagerItem);
		request.setAttribute("username", user.getUsername());
		return "coreuser_list";
	}
	@RequestMapping("toUpdate")
	public String toUpdate(HttpServletRequest request,CoreUser user){
		CoreUser getUser = coreUserService.selectById(user.getUserid());
		request.setAttribute("user",getUser);
		return "coreuser_update";
	}
	@RequestMapping("update")
	public void update(HttpServletRequest request,HttpServletResponse response,CoreUser user) throws IOException{
		CoreUser oldM = coreUserService.selectById(user.getUserid());
		oldM.setIsadmin(user.getIsadmin());
		oldM.setNickname(user.getNickname());
		oldM.setUserpwd(user.getUserpwd());
		oldM.setUsername(user.getUsername());
		oldM.setTelno(user.getTelno());
		coreUserService.updateById(oldM);
		PrintWriter writer = response.getWriter();
		writer.println("<script>");
		writer.println("parent.window.location.reload()");
		writer.println("</script>");
	}
	@RequestMapping("detail")
	public String detail(HttpServletRequest request,CoreUser user){
		CoreUser getUser = coreUserService.selectById(user.getUserid());
		request.setAttribute("user",getUser);
		return "coreuser_detail";
	}
	@RequestMapping("toAdd")
	public String toAdd(HttpServletRequest request){
		return "coreuser_insert";
	}
	@RequestMapping("add")
	public void Add(HttpServletRequest request,HttpServletResponse response,CoreUser user) throws IOException{
		coreUserService.insert(user);
		PrintWriter writer = response.getWriter();
		writer.println("<script>");
		writer.println("parent.window.location.reload()");
		writer.println("</script>");
	}
	@RequestMapping("delete")
	@ResponseBody
	public String delete(HttpServletRequest request,Long id){
		coreUserService.deleteById(id);
		return "ok";
	}
	@RequestMapping("manager")
	@ResponseBody
	public String manager(HttpServletRequest request,Long id){
		CoreUser user = coreUserService.selectById(id);
		user.setIsadmin("1");
		coreUserService.updateById(user);
		return "ok";
	}
	
    /**
    * @description: 分页查询
    * @createTime: 2017年12月13日 10:12:59
    * @author: xw
    * @param m
    * @param rows
    * @param page
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "listByPage",produces = "application/json; charset=utf-8")
    public Object listByPage(CoreUser m, int rows, int page){
        Page<CoreUser> pageM= new Page<>(page,rows);
        EntityWrapper<CoreUser> ew = new EntityWrapper<CoreUser>();
        if(m.getUsername()!=null){
        	ew.like("username", m.getUsername());
        }
        pageM = coreUserService.selectPage(pageM,ew);
        String[] properties = {"userid","username","nickname","telno"};
        String jsonString = JSONUtil.toJson(pageM.getRecords(), properties,(long) pageM.getTotal());
        return jsonString;
    }
}
