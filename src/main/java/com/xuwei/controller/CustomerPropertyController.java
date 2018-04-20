package com.xuwei.controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.Attach;
import com.xuwei.bean.CustomerProperty;
import com.xuwei.service.IAttachService;
import com.xuwei.service.ICustomerPropertyService;
import com.xuwei.util.CommonUtil;
import com.xuwei.util.FileUtil;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;


/**
 * @description: 客户_产权控制器
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月04日 09:42:59
 * @author: caw
 * @version: 1.0
 */
@Controller
@RequestMapping("/myWorkbench/customerProperty")
public class CustomerPropertyController extends BaseController<CustomerProperty> {
    @Resource
    private ICustomerPropertyService customerPropertyService;
    @Resource
    private IAttachService attachService;

    public CustomerPropertyController(){
        setResourceIdentity("myWorkbench:customerProperty");
    }

    /**
    * @description: 转向模块主界面
    * @createTime: 2017年09月04日 09:42:59
    * @author: caw
    * @param model
    * @return
    */
    @RequestMapping(value = "main",method = RequestMethod.GET)
    public String main(Model model){
        return defaultViewPrefix();
    }

    /**
    * @description: 添加
    * @createTime: 2017年09月04日 09:42:59
    * @author: caw
    * @param model
    * @param m
    * @return
    */
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Object create(@RequestParam(value="attachName", required=false) MultipartFile file,HttpServletRequest request,Model model,CustomerProperty m){
        ServiceResult result = new ServiceResult(false);
    	result = customerPropertyService.createCustomerProperty(file, request, model, m);
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 修改
    * @createTime: 2017年09月04日 09:42:59
    * @author: caw
    * @param model
    * @param m
    * @return
    */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Object update(@RequestParam(value="attachName", required=false) MultipartFile file,HttpServletRequest request,Model model,CustomerProperty m){
        ServiceResult result = new ServiceResult(false);
    	result = customerPropertyService.updateCustomerProperty(file, request, model, m);
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 删除
    * @createTime: 2017年09月04日 09:42:59
    * @author: caw
    * @param model
    * @param ids
    * @return
    */
    @RequestMapping(value = "mulDelete",method = RequestMethod.POST)
    @ResponseBody
    public Object mulDelete(Model model,String ids){
        ServiceResult result = new ServiceResult(false);
        try {
            String[] idArray = StringUtil.split(ids);
            if(idArray==null||idArray.length==0){
                result.setMessage("请选择要删除的数据行");
                return result;
            }
            result = customerPropertyService.deleteCustomerProperty(Arrays.asList(idArray));
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
     * @description: 附件下载
     * @createTime: 2017年9月4日 下午5:50:19
     * @author: caw
     * @param response
     * @param redirectAttributes
     * @param attachId
     */
    @RequestMapping(value = "downloadFile")
    public void downloadFile(HttpServletResponse response,RedirectAttributes redirectAttributes,Long attachId){
    	ServiceResult result = new ServiceResult(false);
    	Attach attach = attachService.selectById(attachId);
    	if(attach==null){
 			result.setMessage("项目附件不存在，下载失败！");
 		}
 		try {
 			FileUtil.downloadFile(response, CommonUtil.getAppProParam("project_files.base.path")+attach.getPath(), "附件_"+attach.getName());
 			result.setIsSuccess(true);
 		} catch (Exception e) {
 			result.setMessage("项目附件异常，下载失败！");
 		}
    }
    
    /**
    * @description: 分页查询
    * @createTime: 2017年09月04日 09:42:59
    * @author: caw
    * @param model
    * @param m
    * @param rows
    * @param page
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "listByPage")
    public Object listByPage(Model model, CustomerProperty m, int rows, int page){
        Page<CustomerProperty> pageM= new Page<>(page,rows);
        List<CustomerProperty> list = customerPropertyService.listPropertyByPage(pageM,m.getCustomerId());
        String[] properties = {"customerId","certificate","owner","isCommon","area","housingNature.id:housingNatureid","housingNature.name:housingNatureName","areaId.id:areaId","areaId.showName:areaShowName","areaId.areaName","plotId.id:plotId","plotId.plotName:plotPlotName","houseAddress","havaLandCertificate","landCertificateNumber","landNature.id:landNatureid","landNature.name:landNatureName","propertyValue","attachId","id","note"};
        String jsonString = JSONUtil.toJson(list, properties,(long) pageM.getTotal());
        return jsonString;
    }

}
