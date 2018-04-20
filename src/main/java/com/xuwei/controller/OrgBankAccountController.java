package com.xuwei.controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.OrgBankAccount;
import com.xuwei.service.IOrgBankAccountService;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;


/**
 * @description: 机构_银行账号控制器
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月06日 10:26:17
 * @author: zyd
 * @version: 1.0
 */
@Controller
@RequestMapping("/sys/orgBankAccount")
public class OrgBankAccountController extends BaseController<OrgBankAccount> {
    @Resource
    private IOrgBankAccountService orgBankAccountService;

    public OrgBankAccountController(){
        setResourceIdentity("sys:orgBankAccount");
    }

    /**
    * @description: 转向模块主界面
    * @createTime: 2017年09月06日 10:26:17
    * @author: zyd
    * @param model
    * @return
    */
    @RequestMapping(value = "main",method = RequestMethod.GET)
    public String main(Model model){
        return defaultViewPrefix();
    }

    /**
    * @description: 添加
    * @createTime: 2017年09月06日 10:26:17
    * @author: zyd
    * @param model
    * @param m
    * @return
    */
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Object create(Model model,OrgBankAccount m){
        ServiceResult result = new ServiceResult(false);
      /*  if(!hasCreatePermission()){
            result.setMessage("没有添加权限");
        }else{
            boolean isSuccess = orgBankAccountService.insert(m);
            result.setIsSuccess(isSuccess);
        }*/
        boolean isSuccess = orgBankAccountService.insert(m);
        result.setIsSuccess(isSuccess);
        
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 修改
    * @createTime: 2017年09月06日 10:26:17
    * @author: zyd
    * @param model
    * @param m
    * @return
    */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Object update(Model model,OrgBankAccount m){
        ServiceResult result = new ServiceResult(false);
       /* if(!hasUpdatePermission()){
            result.setMessage("没有修改权限");
        }else{
            boolean isSuccess = orgBankAccountService.updateAllColumnById(m);
            result.setIsSuccess(isSuccess);
        }*/
        OrgBankAccount oldM = orgBankAccountService.selectById(m.getId());
        oldM.setAccountName(m.getAccountName());
        oldM.setAccountNumber(m.getAccountNumber());
        oldM.setBank(m.getBank());
        oldM.setNote(m.getNote());
        oldM.setStatus(m.getStatus());
        
        boolean isSuccess = orgBankAccountService.updateAllColumnById(oldM);
        result.setIsSuccess(isSuccess);
        
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 删除
    * @createTime: 2017年09月06日 10:26:17
    * @author: zyd
    * @param model
    * @param ids
    * @return
    */
    @RequestMapping(value = "mulDelete",method = RequestMethod.POST)
    @ResponseBody
    public Object mulDelete(Model model,String ids){
        ServiceResult result = new ServiceResult(false);
      /*  if(!hasUpdatePermission()){
            result.setMessage("没有删除权限");
        }else{
            try {
                String[] idArray = StringUtil.split(ids);
                if(idArray==null||idArray.length==0){
                    result.setMessage("请选择要删除的数据行");
                    return result;
                }
                boolean isSuccess = orgBankAccountService.deleteBatchIds(Arrays.asList(idArray));
                result.setIsSuccess(isSuccess);
            } catch (Exception e) {
                if(e instanceof org.springframework.dao.DataIntegrityViolationException){
                    result.setMessage("数据已被引用不能删除");
                }else{
                    result.setMessage("删除出错："+e.getMessage());
                }
            }
        }*/
        try {
            String[] idArray = StringUtil.split(ids);
            if(idArray==null||idArray.length==0){
                result.setMessage("请选择要删除的数据行");
                return result;
            }
            boolean isSuccess = orgBankAccountService.deleteBatchIds(Arrays.asList(idArray));
            result.setIsSuccess(isSuccess);
        } catch (Exception e) {
            if(e instanceof org.springframework.dao.DataIntegrityViolationException){
                result.setMessage("数据已被引用不能删除");
            }else{
                result.setMessage("删除出错："+e.getMessage());
            }
        }
        
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 分页查询
    * @createTime: 2017年09月06日 10:26:17
    * @author: zyd
    * @param model
    * @param m
    * @param rows
    * @param page
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "listByPage")
    public Object listByPage(Model model, OrgBankAccount m, int rows, int page){
        if(!hasViewPermission()){
            return  JSONUtil.EMPTYJSON;
        }
        Page<OrgBankAccount> pageM= new Page<>(page,rows);
        EntityWrapper<OrgBankAccount> ew = new EntityWrapper<>(m);
        pageM = orgBankAccountService.selectPage(pageM,ew);
        String[] properties = {"orgId","accountName","bank","accountNumber","status","id","note"};
        String jsonString = JSONUtil.toJson(pageM.getRecords(), properties,(long) pageM.getTotal());
        return jsonString;
    }
    
    /**
     * @description: 根据部门ID分页查询（部门与员工模块使用到）
     * @createTime: 2017年09月06日 10:26:17
     * @author: zyd
     * @param model
     * @param m
     * @param rows
     * @param page
     * @return
     */
     @RequestMapping(value = "getOrgBankAccountByList",method = RequestMethod.POST)
     @ResponseBody
     public Object OrgBankAccount(int rows, int page ,Long orgId){
     	Page<OrgBankAccount> pageM= new Page<>(page,rows);
     	 String[] properties = {"orgId","accountName","bank","accountNumber","status","id","note"};
         List<OrgBankAccount> list = orgBankAccountService.getBankAccountByList(pageM,orgId);
     	return JSONUtil.toJson(list, properties,(long) pageM.getTotal());
     }
     /**
      * 
      * @description:获取公司账户下拉列表  账户名+账号
      * @createTime 2017年9月13日 下午3:11:53
      * @author xw
      * @param m
      * @return
      */
     @RequestMapping(value="payBankAccountCombobox")
     @ResponseBody
     public Object payBankAccountCombobox(OrgBankAccount m){
         Page<OrgBankAccount> pageM= new Page<>(1,30);
         pageM = orgBankAccountService.payBankAccountCombobox(pageM,m);
         String[] properties = {"accountName","id"};
         String jsonString = JSONUtil.toJson(pageM.getRecords(), properties);
         return jsonString;
     }

}
