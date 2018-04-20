package com.xuwei.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.OrderCompany;
import com.xuwei.service.IOrderCompanyService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;


/**
 * @description: 订单_评估公司控制器
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月18日 17:46:41
 * @author: hhd
 * @version: 1.0
 */
@Controller
@RequestMapping("/myWorkbench/orderCompany")
public class OrderCompanyController extends BaseController<OrderCompany> {
    @Resource
    private IOrderCompanyService orderCompanyService;

    /**
    * @description: 添加
    * @createTime: 2017年09月18日 17:46:41
    * @author: hhd
    * @param m
    * @return
    */
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Object create(OrderCompany m){
        ServiceResult result = new ServiceResult(false);
        m.setCreatorId(OperateUtils.getCurrentUserId());
        m.setCreateTime(DateUtil.getNowTimestampStr());
        m.setLastModifierId(OperateUtils.getCurrentUserId());
        m.setLastModifyTime(DateUtil.getNowTimestampStr());
        boolean isSuccess = orderCompanyService.insert(m);
        result.setIsSuccess(isSuccess);
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 修改
    * @createTime: 2017年09月18日 17:46:41
    * @author: hhd
    * @param m
    * @return
    */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Object update(OrderCompany m){
        ServiceResult result = new ServiceResult(false);
        OrderCompany orderCompany = orderCompanyService.selectById(m.getId());
        orderCompany.setAssessmentCompany(m.getAssessmentCompany());
        orderCompany.setAccount(m.getAccount());
        orderCompany.setIsAssessment(m.getIsAssessment());
        orderCompany.setBank(m.getBank());
        orderCompany.setCardNumber(m.getCardNumber());
        orderCompany.setFee(m.getFee());
        orderCompany.setAssessmenFee(m.getAssessmenFee());
        orderCompany.setNote(m.getNote());
        orderCompany.setLastModifierId(OperateUtils.getCurrentUserId());
        orderCompany.setLastModifyTime(DateUtil.getNowTimestampStr());
        boolean isSuccess = orderCompanyService.updateAllColumnById(orderCompany);
        result.setIsSuccess(isSuccess);
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 删除
    * @createTime: 2017年09月18日 17:46:41
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
            boolean isSuccess = orderCompanyService.deleteBatchIds(Arrays.asList(idArray));
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
    * @createTime: 2017年09月18日 17:46:41
    * @author: hhd
    * @param m
    * @param rows
    * @param page
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "listByPage")
    public Object listByPage(OrderCompany m, int rows, int page){
        if(m.getOrder()==null||m.getOrder().getId()==null){
            return  JSONUtil.EMPTYJSON;
        }
        Page<OrderCompany> pageM= new Page<>(page,rows);
        List<Map<String,Object>> list= orderCompanyService.queryByPage(pageM,m.getOrder().getId());
        String[] properties = {"assessmentCompanyId","bank","account","cardNumber","isAssessment","assessmenFee",
                "fee","isMakeBills","id","name","address","contacts","telephone","note"};
        String jsonString = JSONUtil.toJson(list, properties,(long) pageM.getTotal());
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
    public Object mulUpdateStatus(String ids,Integer isAssessment){
        ServiceResult result = new ServiceResult(false);
        String[] idArray = StringUtil.split(ids);
        if(idArray==null||idArray.length==0){
            result.setMessage("请选择要修改的数据行");
            return result;
        }
        List<OrderCompany> list = orderCompanyService.selectBatchIds(Arrays.asList(idArray));
        for (OrderCompany item:list){
            item.setIsAssessment(isAssessment);
            item.setLastModifierId(OperateUtils.getCurrentUserId());
            item.setLastModifyTime(DateUtil.getNowTimestampStr());
        }
        boolean isSuccess = orderCompanyService.updateBatchById(list);
        result.setIsSuccess(isSuccess);
        String jsonString = result.toJSON();
        return jsonString;
    }

}
