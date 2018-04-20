/**
 * 
 */
package com.xuwei.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.xuwei.bean.OrgBankAccount;
import com.xuwei.bean.Organization;
import com.xuwei.bean.Role;
import com.xuwei.bean.User;
import com.xuwei.bean.UserRole;
import com.xuwei.service.IOrgBankAccountService;
import com.xuwei.service.IRoleService;
import com.xuwei.service.IUserRoleService;
import com.xuwei.service.IUserService;
import com.xuwei.service.OrganizationService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.EasyuiTree;
import com.xuwei.util.EasyuiTree.StateType;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;





/**
 * @description:部门控制器
 * @createTime 上午9:56:22
 * @author xw
 *
 */
@Controller
@RequestMapping("/sys/organization")
public class OrganizationController extends BaseTreeController<OrganizationService, Organization> {
	
	@Resource
	private OrganizationService organizationService;
	@Resource
	private IRoleService roleService;
	@Resource
	private IUserService userService;
	@Resource
	private IUserRoleService userRoleService;
	@Resource
	private IOrgBankAccountService orgBankAccountService;
	public OrganizationController() {
		setResourceIdentity("sys:organization");
	}
	/**
	 * 
	 * @description:转向主页面
	 * @createTime 2017年8月29日 上午10:40:41
	 * @author xw
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String main(Model model) {
		return defaultViewPrefix();
	}
	/**
	 * 
	 * @description:获取下拉树列表
	 * @createTime 2017年8月29日 上午10:53:01
	 * @author xw
	 * @return
	 */
	@RequestMapping(value = { "/getOrganization" })
	@ResponseBody
	public Object getOrganization(){
		String[] properties={"id","organizationName:text","parentId:pid","orgType"};
		List<Organization> organizationList = organizationService.findAll();
       	String jsonString = JSONUtil.toJson(organizationList, properties);
        return jsonString;
	}
	/**
	 * 
	 * @description:通过类型查询机构信息 
	 * @createTime: 2017年9月7日 下午12:51:01
	 * @author: zyd
	 * @param orgType
	 * @return
	 */
	@RequestMapping(value = { "/getOrganizationByOrgType" })
	@ResponseBody
	public Object getOrganizationByOrgType(int orgType){
		String[] properties={"id","organizationName:text","parentId:pid","orgType","parentIds"};
		List<Organization> organizationList = organizationService.findAllByOrgType(orgType);
       	String jsonString = JSONUtil.toJson(organizationList, properties);
        return jsonString;
	}
	@RequestMapping(value = { "/getOrganizationByCompany" })
	@ResponseBody
	public Object getOrganizationByCompany(Long companyId){
		if(companyId == null)
		{
			return null;
		}
		String[] properties={"id","organizationName:text","parentId:pid","orgType","parentIds"};
		List<Organization> organizationList = organizationService.findByCompany(companyId);
       	String jsonString = JSONUtil.toJson(organizationList, properties);
        return jsonString;
	}
	/**
	 * 
	 * @description:获取所有角色
	 * @createTime 2017年8月29日 上午11:30:06
	 * @author xw
	 * @return
	 */
	@RequestMapping(value = { "/getRole" })
	@ResponseBody
	public Object getRole(){
		String[] properties={"id","name"};
		List<Role> roleList = roleService.findAll();
       	String jsonString = JSONUtil.toJson(roleList, properties);
        return jsonString;
	}
	
    /**
     * 
     * @description:获取跟节点
     * @createTime 2017年10月11日 上午10:35:26
     * @author xw
     * @return
     */
    @RequestMapping(value = "/treeRoot")
	@ResponseBody
	public Object treeRoot() {
		List<Organization> list = organizationService.treeRoot();
		return convertToEasyuiTreeList(list);
	}
    
    /**
     * 
     * @description:获取子节点
     * @createTime 2017年10月11日 上午10:35:38
     * @author xw
     * @param id
     * @return
     */
	@RequestMapping(value = "/treeChildren")
	@ResponseBody
	public Object treeChildren(Long id) {
		List<Organization> list = organizationService.treeChildren(id);
		return convertToEasyuiTreeList(list);
	}
	
	public  List<EasyuiTree> convertToEasyuiTreeList(List<Organization> models) {
		List<EasyuiTree> trees = Lists.newArrayList();
		if (models == null || models.isEmpty()) {
			return trees;
		}
		for (Organization m : models) {
			EasyuiTree tree = convertToEasyuiTree(m);
			trees.add(tree);
		}
		return trees;
	}
	
	private EasyuiTree convertToEasyuiTree(Organization m) {
		EasyuiTree tree = new EasyuiTree();
		tree.setId(m.getId());
		tree.setText(m.getOrganizationName());
		tree.setIconCls(m.getIcon());
		tree.setPid(m.getParentId());
		tree.setChecked(false);
		if (m.isHasChildren()) {
			tree.setState(StateType.closed);
		}
		if(m instanceof Organization){
			Organization organization = m;
			tree.getAttributes().put("content", organization.getNote());
		}
		return tree;
	}
	/**
	 * 
	 * @description:获取部门下的员工
	 * @createTime 2017年8月29日 下午2:14:26
	 * @author xw
	 * @param model
	 * @param m
	 * @param organizationId
	 * @param page
	 * @return
	 */
	@RequestMapping(value = { "/findChildrenUser" })
	@ResponseBody
	public Object findChildrenUser(ModelMap model,User m,Long organizationId,int page,int rows) {
    	String[] properties={"id","username","name","companyName","company.id:companyId","organizations","roles","roleIds","status","phone","email","jobNumber","sex","organization.id:organizationId","wechat"};
    	 com.baomidou.mybatisplus.plugins.Page<User> pageM= new  com.baomidou.mybatisplus.plugins.Page<>(page,rows);
		Long userid =OperateUtils.getCurrentUserId();
		EntityWrapper<UserRole> ew= new EntityWrapper<UserRole>();
		ew.eq("user_id",userid);
		ew.eq("role_id",1);
		List<UserRole> userRoleList = userRoleService.selectList(ew);
		if(userRoleList.size()>0){
			m.setIsAdmin(1);
		}
    	userService.findListByPageMap(m,organizationId,pageM);
    	String jsonString = JSONUtil.toJson(pageM.getRecords(), properties,(long)pageM.getTotal());
        return jsonString;
	}
	/**
	 * 
	 * @description:添加树节点
	 * @createTime 2017年8月29日 下午4:21:06
	 * @author xw
	 * @param model
	 * @param m
	 * @return
	 */
	@RequestMapping(value="create",method=RequestMethod.POST)
	@ResponseBody
	public Object create(Model model,Organization m){
		ServiceResult result = new ServiceResult(false);
    	try {
    		m.setCreateTime(DateUtil.getNowTimestampStr());
    		m.setCreatorId(OperateUtils.getCurrentUserId());
    		m.setLastModifierId(OperateUtils.getCurrentUserId());
    		m.setLastModifyTime(DateUtil.getNowTimestampStr());
    		result = organizationService.create(m);
		} catch (Exception e) {
			result.setMessage("添加出错："+e.getMessage());
		}
    	String jsonString = result.toJSON();
        return jsonString;
	}
	/**
	 * 
	 * @description:修改
	 * @createTime 2017年8月31日 下午2:10:01
	 * @author xw
	 * @param model
	 * @param m
	 * @return
	 */
	@RequestMapping(value="update",method=RequestMethod.POST)
	@ResponseBody
	public Object update(Model model,Organization m){
		ServiceResult result = new ServiceResult(false);
    	try {
    		m.setLastModifierId(OperateUtils.getCurrentUserId());
    		m.setLastModifyTime(DateUtil.getNowTimestampStr());
    		result = organizationService.update(m);
		} catch (Exception e) {
			result.setMessage("修改出错："+e.getMessage());
		}
    	String jsonString = result.toJSON();
        return jsonString;
    }
	/**
	 * 
	 * @description:添加部门人员
	 * @createTime 2017年8月29日 下午4:21:22
	 * @author xw
	 * @param model
	 * @param m
	 * @return
	 */
	@RequestMapping(value = { "/addUser" }, method = RequestMethod.POST)
	@ResponseBody
	public Object addUser(ModelMap model,User m){
		ServiceResult result = new ServiceResult(false);
		if (!hasPermissionByOpt("addUser")) {
			result.setMessage("没有添加员工权限！");
			return result.toJSON();
		}
		m.setCreateTime(DateUtil.getNowTimestampStr());
		m.setCreatorId(OperateUtils.getCurrentUserId());
		m.setLastModifierId(OperateUtils.getCurrentUserId());
		m.setLastModifyTime(DateUtil.getNowTimestampStr());
		result = userService.create(m);
		return result.toJSON();
	}
	/**
	 * 
	 * @description:移动树
	 * @createTime 2017年8月31日 下午2:26:39
	 * @author xw
	 * @param request
	 * @param sourceId
	 * @param targetId
	 * @param point
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "move", method = RequestMethod.POST)
	@ResponseBody
    public Object move(
            HttpServletRequest request,
            @RequestParam("sourceId") long sourceId,
            @RequestParam("targetId") long targetId,
            @RequestParam("point") String point,
            Model model) {

        return organizationService.move(sourceId, targetId, point);

    }
	/**
	 * 
	 * @description:修改部门人员
	 * @createTime 2017年8月29日 下午5:08:06
	 * @author xw
	 * @param model
	 * @param m
	 * @return
	 */
	@RequestMapping(value = { "/updateUser" }, method = RequestMethod.POST)
	@ResponseBody
	public Object updateUser(ModelMap model,User m){
		ServiceResult result = new ServiceResult(false);
		if (!hasPermissionByOpt("updateUser")) {
			result.setMessage("没有修改员工权限！");
			return result.toJSON();
		}
		m.setLastModifierId(OperateUtils.getCurrentUserId());
		m.setLastModifyTime(DateUtil.getNowTimestampStr());
		result = userService.update(m);
		return result.toJSON();
	}
	/**
	 * 
	 * @description:批量修改员工状态
	 * @createTime 2017年8月29日 下午5:17:21
	 * @author xw
	 * @param model
	 * @param ids
	 * @param status
	 * @return
	 */
	@RequestMapping(value = { "/mulUserStatus" }, method = RequestMethod.POST)
	@ResponseBody
	public Object mulUserStatus(ModelMap model,String ids, Integer status){
		ServiceResult result = new ServiceResult(false);
    	if (!hasPermissionByOpt("mulUpdateStatus")) {
    		result.setMessage("没有修状态权限");
    		return result;
    	}
    	result = userService.mulUpdateStatus(ids,status);
    	return result;
	}
	/**
	 * 
	 * @description:重置密码
	 * @createTime 2017年8月30日 上午8:54:53
	 * @author xw
	 * @param model
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="resetPwd")
	@ResponseBody
	public Object resetPwd(ModelMap model,String ids){
		ServiceResult result = new ServiceResult(false);
		if (!hasPermissionByOpt("resetPwd")) {
    		result.setMessage("没有重置密码权限");
    		return result;
    	}
    	result = userService.resetPwd(ids);
		return result;
	}
	/**
	 * 
	 * @description:删除
	 * @createTime 2017年8月30日 上午8:56:04
	 * @author xw
	 * @param model
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="delete")
	@ResponseBody
	public Object delete(ModelMap model,String ids){
		ServiceResult result = new ServiceResult(false);
		if (!hasPermissionByOpt("delete")) {
    		result.setMessage("没有删除员工权限");
    		return result;
    	}
		try {
			result = userService.delete(ids);
		} catch (Exception e) {
			if(e instanceof org.springframework.dao.DataIntegrityViolationException){
				result.setMessage("数据已被引用不能删除");
			}else{
				result.setMessage("删除出错："+e.getMessage());
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @description: 添加 部门银行账号 
	 * @createTime: 2017年9月6日 下午3:43:26
	 * @author: zyd
	 * @param model
	 * @param m
	 * @return
	 */
	@RequestMapping(value = { "/addOrgBankAccount" }, method = RequestMethod.POST)
	@ResponseBody
	public Object addOrgBankAccount(ModelMap model,OrgBankAccount m){
		ServiceResult result = new ServiceResult(false);
		m.setLastModifierId(OperateUtils.getCurrentUserId());
		m.setLastModifyTime(DateUtil.getNowTimestampStr());
		boolean isSuccess = orgBankAccountService.insert(m);
        result.setIsSuccess(isSuccess);
		return result.toJSON();
	}
	/**
	 * 
	 * @description:通过ID 获取机构信息 
	 * @createTime: 2017年9月7日 下午5:22:30
	 * @author: zyd
	 * @param orgId
	 * @return
	 */
	@RequestMapping(value = {"/getOrganizationById"}, method = RequestMethod.POST)
	@ResponseBody
	public Object getOrganizationById(Long orgId) {
		Organization org = organizationService.findById(orgId);
		return org;
	}

	/**
	 *
	 * @description:通过ID 获取机构信息
	 * @createTime: 2017年9月7日 下午5:22:30
	 * @author: zyd
	 * @param orgId
	 * @return
	 */
	@RequestMapping(value = {"/deleteOrganizationById"}, method = RequestMethod.POST)
	@ResponseBody
	public Object deleteOrganizationById(Long orgId) {
		ServiceResult serviceResult=new ServiceResult(false);
		boolean isSuccess = organizationService.deleteById(orgId);
		serviceResult.setIsSuccess(isSuccess);
		return serviceResult;
	}

	
}
