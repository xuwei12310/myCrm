package com.xuwei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.xuwei.controller.BaseController;
import com.xuwei.bean.Dict;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.StringUtil;
import com.xuwei.util.ServiceResult;
import com.xuwei.service.IDictService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.Arrays;


/**
 * @description: 字典控制器
 * @copyright: 福建骏华信息有限公司 (c)2018
 * @createTime: 2018年01月16日 19:57:11
 * @author: xw
 * @version: 1.0
 */
@Controller
@RequestMapping("/admin/dict")
public class DictController extends BaseController<Dict> {
    @Resource
    private IDictService dictService;

    public DictController(){
        setResourceIdentity("sys:dict");
    }

    /**
    * @description: 转向模块主界面
    * @createTime: 2018年01月16日 19:57:11
    * @author: xw
    * @param model
    * @return
    */
    @RequestMapping(value = "main",method = RequestMethod.GET)
    public String main(Model model){
        return defaultViewPrefix();
    }

    /**
    * @description: 添加
    * @createTime: 2018年01月16日 19:57:11
    * @author: xw
    * @param m
    * @return
    */
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Object create(Dict m){
        ServiceResult result = new ServiceResult(false);
        if(!hasCreatePermission()){
            result.setMessage("没有添加权限");
        }else{
            boolean isSuccess = dictService.insert(m);
            result.setIsSuccess(isSuccess);
        }
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 修改
    * @createTime: 2018年01月16日 19:57:11
    * @author: xw
    * @param m
    * @return
    */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Object update(Dict m){
        ServiceResult result = new ServiceResult(false);
        if(!hasUpdatePermission()){
            result.setMessage("没有修改权限");
        }else{
            boolean isSuccess = dictService.updateAllColumnById(m);
            result.setIsSuccess(isSuccess);
        }
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 删除
    * @createTime: 2018年01月16日 19:57:11
    * @author: xw
    * @param ids
    * @return
    */
    @RequestMapping(value = "mulDelete",method = RequestMethod.POST)
    @ResponseBody
    public Object mulDelete(String ids){
        ServiceResult result = new ServiceResult(false);
        if(!hasUpdatePermission()){
            result.setMessage("没有删除权限");
        }else{
            try {
                String[] idArray = StringUtil.split(ids);
                if(idArray==null||idArray.length==0){
                    result.setMessage("请选择要删除的数据行");
                    return result;
                }
                boolean isSuccess = dictService.deleteBatchIds(Arrays.asList(idArray));
                result.setIsSuccess(isSuccess);
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

    /**
    * @description: 分页查询
    * @createTime: 2018年01月16日 19:57:11
    * @author: xw
    * @param m
    * @param rows
    * @param page
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "listByPage")
    public Object listByPage(Dict m, int rows, int page){
        if(!hasViewPermission()){
            return  JSONUtil.EMPTYJSON;
        }
        Page<Dict> pageM= new Page<>(page,rows);
        EntityWrapper<Dict> ew = new EntityWrapper<>(m);
        pageM = dictService.selectPage(pageM,ew);
        String[] properties = {"dictType","code","name","status","nameEn","isSys","id"};
        String jsonString = JSONUtil.toJson(pageM.getRecords(), properties,(long) pageM.getTotal());
        return jsonString;
    }

}
