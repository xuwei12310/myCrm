package com.xuwei.controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.Attach;
import com.xuwei.bean.OrderMaterial;
import com.xuwei.service.IAttachService;
import com.xuwei.service.IOrderMaterialService;
import com.xuwei.util.CommonUtil;
import com.xuwei.util.DateUtil;
import com.xuwei.util.FileUtil;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;


/**
 * @description: 订单_相关材料控制器
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月18日 10:18:12
 * @author: hhd
 * @version: 1.0
 */
@Controller
@RequestMapping("/myWorkbench/orderMaterial")
public class OrderMaterialController extends BaseController<OrderMaterial> {
    @Resource
    private IOrderMaterialService orderMaterialService;
    @Resource
    private IAttachService attachService;

    public OrderMaterialController(){
        setResourceIdentity("sys:orderMaterial");
    }

    /**
    * @description: 添加
    * @createTime: 2017年09月18日 10:18:12
    * @author: hhd
    * @param m
    * @return
    */
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Object create(OrderMaterial m, @RequestParam(value="file", required=false) MultipartFile file){
        ServiceResult result = orderMaterialService.add(m,file);
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 修改
    * @createTime: 2017年09月18日 10:18:12
    * @author: hhd
    * @param m
    * @return
    */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Object update(OrderMaterial m,@RequestParam(value="file", required=false) MultipartFile file){
        ServiceResult result = orderMaterialService.edit(m,file);
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 删除
    * @createTime: 2017年09月18日 10:18:12
    * @author: hhd
    * @param ids
    * @return
    */
    @RequestMapping(value = "mulDelete",method = RequestMethod.POST)
    @ResponseBody
    public Object mulDelete(String ids){
        ServiceResult result = new ServiceResult(false);
        try {
            String[] idArray = StringUtil.split(ids);
            if(idArray==null||idArray.length==0){
                result.setMessage("请选择要删除的数据行");
                return result;
            }
            boolean isSuccess = orderMaterialService.deleteBatchIds(Arrays.asList(idArray));
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
    * @createTime: 2017年09月18日 10:18:12
    * @author: hhd
    * @param m
    * @param rows
    * @param page
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "listByPage")
    public Object listByPage(OrderMaterial m, int rows, int page){
        if(m==null || m.getOrder()==null ||m.getOrder().getId()==null){
            return JSONUtil.EMPTYJSON;
        }
        Page<OrderMaterial> pageM= new Page<>(page,rows);
        EntityWrapper<OrderMaterial> ew = new EntityWrapper<>(m);
        pageM = orderMaterialService.selectPage(pageM,ew);
        String[] properties = {"materialId","notice","number","isFinish","attachId","name","id"};
        String jsonString = JSONUtil.toJson(pageM.getRecords(), properties,(long) pageM.getTotal());
        return jsonString;
    }

    /**
     * @description: 批量修改状态
     * @createTime: 2017年09月18日 10:18:12
     * @author: hhd
     * @param ids
     * @return
     */
    @RequestMapping(value = "updateStatus",method = RequestMethod.POST)
    @ResponseBody
    public Object mulUpdateStatus(String ids,Integer isFinish){
        ServiceResult result = new ServiceResult(false);
        String[] idArray = StringUtil.split(ids);
        if(idArray==null||idArray.length==0){
            result.setMessage("请选择要修改的数据行");
            return result;
        }
        List<OrderMaterial> list = orderMaterialService.selectBatchIds(Arrays.asList(idArray));
        for (OrderMaterial item:list){
            item.setIsFinish(isFinish);
            item.setLastModifierId(OperateUtils.getCurrentUserId());
            item.setLastModifyTime(DateUtil.getNowTimestampStr());
        }
        boolean isSuccess = orderMaterialService.updateBatchById(list);
        result.setIsSuccess(isSuccess);
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
     * @description: 附件下载
     * @createTime: 2017年9月18日 下午2:50:19
     * @author: hhd
     * @param response
     * @param attachId
     */
    @RequestMapping(value = "downloadFile")
    public void downloadFile(HttpServletResponse response, Long attachId){
        ServiceResult result = new ServiceResult(false);
        Attach attach = attachService.selectById(attachId);
        if(attach==null){
            result.setMessage("附件不存在，下载失败！");
        }
        try {
            FileUtil.downloadFile(response, CommonUtil.getAppProParam("project_files.base.path")+attach.getPath(), attach.getName());
            result.setIsSuccess(true);
        } catch (Exception e) {
            result.setMessage("附件异常，下载失败！");
        }
    }

}
