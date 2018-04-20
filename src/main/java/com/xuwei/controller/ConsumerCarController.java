package com.xuwei.controller;

import java.util.Arrays;

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

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.Attach;
import com.xuwei.bean.ConsumerCar;
import com.xuwei.service.IAttachService;
import com.xuwei.service.IConsumerCarService;
import com.xuwei.util.CommonUtil;
import com.xuwei.util.FileUtil;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;


/**
 * @description: 客户_车产控制器
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月04日 09:45:08
 * @author: caw
 * @version: 1.0
 */
@Controller
@RequestMapping("/myWorkbench/consumerCar")
public class ConsumerCarController extends BaseController<ConsumerCar> {
    @Resource
    private IConsumerCarService consumerCarService;
    @Resource
    private IAttachService attachService;

    public ConsumerCarController(){
        setResourceIdentity("myWorkbench:consumerCar");
    }

    /**
    * @description: 转向模块主界面
    * @createTime: 2017年09月04日 09:45:08
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
    * @createTime: 2017年09月04日 09:45:08
    * @author: caw
    * @param model
    * @param m
    * @return
    */
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Object create(@RequestParam(value="attachCarName", required=false) MultipartFile file,HttpServletRequest request,Model model,ConsumerCar m){
        ServiceResult result = new ServiceResult(false);
        result = consumerCarService.createConsumerCar(file, request, model, m);
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 修改
    * @createTime: 2017年09月04日 09:45:08
    * @author: caw
    * @param model
    * @param m
    * @return
    */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Object update(@RequestParam(value="attachCarName", required=false) MultipartFile file,HttpServletRequest request,Model model,ConsumerCar m){
        ServiceResult result = new ServiceResult(false);
    	result = consumerCarService.updateConsumerCar(file, request, model, m);
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 删除
    * @createTime: 2017年09月04日 09:45:08
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
            result = consumerCarService.deleteConsumerCar(Arrays.asList(idArray));
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
    * @createTime: 2017年09月04日 09:45:08
    * @author: caw
    * @param model
    * @param m
    * @param rows
    * @param page
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "listByPage")
    public Object listByPage(Model model, ConsumerCar m, int rows, int page){
        Page<ConsumerCar> pageM= new Page<>(page,rows);
        EntityWrapper<ConsumerCar> ew = new EntityWrapper<>(m);
        pageM = consumerCarService.selectPage(pageM,ew);
        String[] properties = {"customerId","brand","model","years","carStatus","valuation","attachId","id"};
        String jsonString = JSONUtil.toJson(pageM.getRecords(), properties,(long) pageM.getTotal());
        return jsonString;
    }

    /**
     * 
     * @description: 附件下载
     * @createTime: 2017年9月5日 上午9:12:30
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
}
