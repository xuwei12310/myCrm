package com.xuwei.controller;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.xuwei.activiti.service.IActivitiProcessService;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;


/**
 * @description: 工作流控制器
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月4日 14:52:26
 * @author: hhd
 * @version: 1.0
 */
@Controller
@RequestMapping("/sys/activiti")
public class ActivitiController extends BaseController {

    @Resource
    RepositoryService repositoryService;
    @Resource
    private IActivitiProcessService activitiProcessService;


    public ActivitiController(){
        setResourceIdentity("sys:activiti");
    }

    /**
    * @description: 转向模块主界面
    * @createTime: 2017年08月29日 10:52:26
    * @author: wwh
    * @param model
    * @return
    */
    @RequestMapping(value = "main",method = RequestMethod.GET)
    public String main(Model model){
        return "activiti/activiti";
    }

    /**
    * @description: 添加
    * @createTime: 2017年09月4日 10:52:26
    * @author: hhd
    * @param file
    * @return
    */
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Object create(@RequestParam(value = "file", required = false) MultipartFile file){
        ServiceResult result = new ServiceResult(false);
        if(!hasCreatePermission()){
            result.setMessage("没有添加权限");
        }else{
            String fileName = file.getOriginalFilename();
            try {
                InputStream fileInputStream = file.getInputStream();
                //Deployment deployment = null;

                String extension = FilenameUtils.getExtension(fileName);
                if (extension.equals("zip") || extension.equals("bar")) {
                    ZipInputStream zip = new ZipInputStream(fileInputStream);
                    repositoryService.createDeployment().addZipInputStream(zip).deploy();
                } else {
                    repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
                }
                result.setIsSuccess(true);
            } catch (Exception e) {
                //e.printStackTrace();
                result.setMessage("上传格式文件出错");
            }
        }
        String jsonString = result.toJSON();
        return jsonString;
    }


    /**
    * @description: 删除
    * @createTime: 2017年08月29日 10:52:26
    * @author: wwh
    * @param model
    * @param ids
    * @return
    */
    @RequestMapping(value = "mulDelete",method = RequestMethod.POST)
    @ResponseBody
    public Object mulDelete(Model model,String ids){
        ServiceResult result = new ServiceResult(false);
        if(!hasDeletePermission()){
            result.setMessage("没有删除权限");
        }else{
            try {
                String[] idArray = StringUtil.split(ids);
                if(idArray==null||idArray.length==0){
                    result.setMessage("请选择要删除的数据行");
                    return result;
                }
                for (String id : idArray){
                    repositoryService.deleteDeployment(id, true);
                }
                result.setIsSuccess(true);
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
    * @createTime: 2017年08月29日 10:52:26
    * @author: hhd
    * @param model
    * @param rows
    * @param page
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "listByPage")
    public Object listByPage(Model model, int rows, int page, String name){
        if(!hasViewPermission()){
            ServiceResult result = new ServiceResult(false);
            result.setMessage("没有权限");
            return  result;
        }
        if(StringUtils.isEmpty(name)){
            name="%%";
        }else {
            name="%"+name+"%";
        }
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().processDefinitionNameLike(name).orderByDeploymentId().desc();
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage((page-1)*rows,rows);
        String[] properties = {"deploymentId","name","key","version","resourceName","diagramResourceName","suspended","id"};
        String rowsdata = JSONUtil.toJson(processDefinitionList,properties);
        JSONObject object = new JSONObject();
        object.put("rows", JSONObject.parse(rowsdata));
        object.put("total", processDefinitionQuery.count());
        return object.toString();
    }


    /**
     * @description  读取资源，通过部署ID
     * @createTime 2017-9-4 下午16:21:00
     * @author hhd
     * @param processDefinitionId
     * @param resourceType
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/resource/read")
    public void loadByDeployment(@RequestParam("processDefinitionId") String processDefinitionId, @RequestParam("resourceType") String resourceType,
                                 HttpServletResponse response) throws Exception {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        String resourceName = "";
        if (resourceType.equals("image")) {
            resourceName = processDefinition.getDiagramResourceName();
        } else if (resourceType.equals("xml")) {
            resourceName = processDefinition.getResourceName();
        }
        InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    /**
     * 获取节点信息
     * @param processInstanceId
     * @return
     */
    @RequestMapping(value = "/activityInfo")
    @ResponseBody
    public Map<String, Object> activityInfo(String processInstanceId) {
        Map<String, Object> activityInfo = activitiProcessService.getActivityInfo(processInstanceId);
        return activityInfo;
    }
}
