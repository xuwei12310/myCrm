/**
 * 
 */
package com.xuwei.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xuwei.bean.Resources;
import com.xuwei.bean.User;
import com.xuwei.service.IMainService;
import com.xuwei.service.IUserService;
import com.xuwei.util.OperateUtils;


/**
 * @description:
 * @createTime 2017年12月11日 上午11:41:36
 * @copyright 福建骏华有限公司（c）2017
 * @author xw
 *
 */
@Controller
@RequestMapping("main")
public class MainController extends BaseController<User>{
	
	@Resource
	private IMainService mainService;
	@Resource IUserService userService;
	@RequestMapping("welcome")
	public String welcome(){
		return "welcome";
	}
	@RequestMapping("main")
	public String main(Model model){
		User user = OperateUtils.getCurrentUser();
		List<Resources> resourcesList=mainService.findRootResourcesByUser(user);
		model.addAttribute("resourcesList", resourcesList);
		User info = userService.selectUserInfo(user.getId());
		model.addAttribute("info",info);
		return "main";
	}
	/**
	 * 
	 * @description:获取菜单子节点
	 * @createTime 2018年2月9日 下午6:53:21
	 * @author xw
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value = "/menuTree")
    @ResponseBody
    public Object menuTree(@RequestParam(value = "id", required = true) Long parentId) {
		User user = OperateUtils.getCurrentUser();
		List<Resources> resourcesList = mainService.findMenuTreetResources(parentId,user);
		return resourcesList;
    }
}
