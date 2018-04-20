package com.xuwei.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.Resources;
import com.xuwei.bean.Role;
import com.xuwei.service.IResourcesService;
import com.xuwei.service.IRoleResourcesService;
import com.xuwei.service.IRoleService;
import com.xuwei.util.EasyuiUtilResources;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;

/**
 * @description: 角色控制器
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年07月12日 10:35:35
 * @author: hhd
 * @version: 1.0
 */
@Controller
@RequestMapping("/sys/role")
public class RoleController extends BaseController<Role> {

    @Resource
    private IRoleService roleService;

    @Resource
    private IRoleResourcesService roleResourcesService;

    @Resource
    private IResourcesService resourcesService;

    public RoleController(){
        setResourceIdentity("sys:role");
    }

    @RequestMapping(value = "main",method = RequestMethod.GET)
    public String main(Model model){
        return defaultViewPrefix();
    }

    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Object create(Model model,Role m){
        ServiceResult result = new ServiceResult(false);
        if(!hasCreatePermission()){
            result.setMessage("没有添加权限");
        }else{
            result = roleService.create(m);
        }
        String jsonString = result.toJSON();
        return jsonString;
    }

    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Object update(Model model,Role m){
        ServiceResult result = new ServiceResult(false);
        if(!hasUpdatePermission()){
            result.setMessage("没有修改权限");
        }else{
            result = roleService.update(m);
        }
        String jsonString = result.toJSON();
        return jsonString;
    }

    @RequestMapping(value = "mulDelete",method = RequestMethod.POST)
    @ResponseBody
    public Object mulDelete(Model model,String ids){
        ServiceResult result = new ServiceResult(false);
        if(!hasUpdatePermission()){
            result.setMessage("没有删除权限");
        }else{
            try {
                result = roleService.mulDelete(ids);
            } catch (Exception e) {
                if(e instanceof org.springframework.dao.DataIntegrityViolationException){
                    result.setMessage("数据已被引用不能删除");
                }else{
                    result.setMessage("删除出错："+e.getMessage());
                }
            }
        }
        String jsonString = result.toJSON();
        return jsonString;
    }

    @ResponseBody
    @RequestMapping(value = "listByPage")
    public Object listByPage(Model model, Role m, int rows, int page){
        if(!hasViewPermission()){
            return  JSONUtil.EMPTYJSON;
        }
        Page<Role> pageM =new Page<>(page,rows);
        EntityWrapper<Role> ew = new EntityWrapper<>();
        if(OperateUtils.getCurrentUserId()!=1){
            ew.ne("id",1);
        }
        if(!StringUtils.isEmpty(m.getName())){
            ew.like("name",m.getName());
        }
        if(!StringUtils.isEmpty(m.getRole())){
            ew.like("role",m.getRole());
        }
        pageM = roleService.selectPage(pageM,ew);
        String[] properties = { "id", "name", "role", "description","status", "note","isSys" };
        String jsonString = JSONUtil.toJson(pageM.getRecords(), properties,(long) pageM.getTotal());
        return jsonString;
    }

    @RequestMapping(value = "resources",method = RequestMethod.POST)
    @ResponseBody
    public Object resources(Model model){
        List<Resources> lists = resourcesService.tree();
        EasyuiUtilResources easyUIUtil = new EasyuiUtilResources();
        return easyUIUtil.convertToEasyuiTreeList(lists);
    }

    @RequestMapping(value = "getGrantResources",method = RequestMethod.POST)
    @ResponseBody
    public Object getGrantResources(Model model,Long roleId){
        List<Long> lists = roleResourcesService.getGrantResources(roleId);
        return lists;
    }


    @RequestMapping(value = "grantResources",method = RequestMethod.POST)
    @ResponseBody
    public Object grantResources(Model model,Long roleId,String ids){
        ServiceResult result =roleResourcesService.grantResources(roleId,ids);
        return result.toJSON();
    }
}
