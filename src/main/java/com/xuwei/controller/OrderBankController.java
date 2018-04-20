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
import com.xuwei.bean.OrderBank;
import com.xuwei.service.IOrderBankService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;


/**
 * @description: 订单_合作银行控制器
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月18日 15:00:36
 * @author: hhd
 * @version: 1.0
 */
@Controller
@RequestMapping("/myWorkbench/orderBank")
public class OrderBankController extends BaseController<OrderBank> {
    @Resource
    private IOrderBankService orderBankService;

    public OrderBankController(){
        setResourceIdentity("sys:orderBank");
    }

    /**
    * @description: 添加
    * @createTime: 2017年09月18日 15:00:36
    * @author: hhd
    * @param m
    * @return
    */
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Object create(OrderBank m){
        ServiceResult result = orderBankService.addOrderBank(m);
        return result.toJSON();
    }

    /**
    * @description: 修改
    * @createTime: 2017年09月18日 15:00:36
    * @author: hhd
    * @param m
    * @return
    */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Object update(OrderBank m){
        ServiceResult result = new ServiceResult(false);
        OrderBank orderBank = orderBankService.selectById(m.getId());
        orderBank.setBank(m.getBank());
        orderBank.setSubbranch(m.getSubbranch());
        orderBank.setNote(m.getNote());
        orderBank.setLastModifierId(OperateUtils.getCurrentUserId());
        orderBank.setLastModifyTime(DateUtil.getNowTimestampStr());
        boolean isSuccess = orderBankService.updateAllColumnById(orderBank);
        result.setIsSuccess(isSuccess);
        String jsonString = result.toJSON();
        return jsonString;
    }

    /**
    * @description: 删除
    * @createTime: 2017年09月18日 15:00:36
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
            boolean isSuccess = orderBankService.deleteBatchIds(Arrays.asList(idArray));
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
    * @createTime: 2017年09月18日 15:00:36
    * @author: hhd
    * @param m
    * @param rows
    * @param page
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "listByPage")
    public Object listByPage(OrderBank m, int rows, int page){
        if(m.getOrder()==null||m.getOrder().getId()==null){
            return  JSONUtil.EMPTYJSON;
        }
        Page<List<Map<String,Object>>> pageM= new Page<>(page,rows);
        List<Map<String,Object>> list = orderBankService.queryByPage(pageM,m.getOrder().getId());
        String[] properties = {"bankId","subbranchId","isSign","singTime","note","id","address","contacts","bank_name","subbranch_name"};
        String jsonString = JSONUtil.toJson(list, properties,(long) pageM.getTotal());
        return jsonString;
    }

    /**
     * @description: 指定签约银行
     * @createTime: 2017年09月18日 10:18:12
     * @author: hhd
     * @param id
     * @return
     */
    @RequestMapping(value = "updateStatus",method = RequestMethod.POST)
    @ResponseBody
    public Object mulUpdateStatus(Long id,String time){
        ServiceResult result = orderBankService.mulUpdateStatus(id,time);
        return result.toJSON();
    }

}
