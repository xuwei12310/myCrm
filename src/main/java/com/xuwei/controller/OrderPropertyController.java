package com.xuwei.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.CustomerProperty;
import com.xuwei.bean.Order;
import com.xuwei.bean.OrderProperty;
import com.xuwei.service.ICustomerPropertyService;
import com.xuwei.service.IOrderPropertyService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;


/**
 * @description: 订单_抵押房产控制器
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月15日 09:41:57
 * @author: hhd
 * @version: 1.0
 */
@Controller
@RequestMapping("/myWorkbench/orderProperty")
public class OrderPropertyController extends BaseController<OrderProperty> {
    @Resource
    private IOrderPropertyService orderPropertyService;
    @Resource
    private ICustomerPropertyService customerPropertyService;

    public OrderPropertyController(){
        setResourceIdentity("sys:orderProperty");
    }

    /**
    * @description: 添加
    * @createTime: 2017年09月15日 09:41:57
    * @author: hhd
    * @param ids
    * @return
    */
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Object create(String ids,Long orderId){
        ServiceResult result = new ServiceResult(false);
        String[] idArray = StringUtil.split(ids);
        if(idArray==null||idArray.length==0){
            result.setMessage("请选择要添加的数据行");
            return result;
        }
        EntityWrapper<OrderProperty> ew=new EntityWrapper<>();
        ew.eq("order_id",orderId);
        List<OrderProperty> his = orderPropertyService.selectList(ew);
        for (OrderProperty item:his){
            for (String id:idArray){
                if(id.equals(item.getCustomerPropertyId().toString())){
                    result.setMessage("不能添加重复的数据");
                    return result;
                }
            }
        }
        List<OrderProperty> list=new ArrayList<>();
        for (String id:idArray){
            OrderProperty orderProperty = new OrderProperty();
            Order order=new Order();
            order.setId(orderId);
            orderProperty.setOrder(order);
            orderProperty.setCustomerPropertyId(Long.valueOf(id));
            orderProperty.setCreatorId(OperateUtils.getCurrentUserId());
            orderProperty.setCreateTime(DateUtil.getNowTimestampStr());
            orderProperty.setLastModifierId(OperateUtils.getCurrentUserId().toString());
            orderProperty.setLastModifyTime(DateUtil.getNowTimestampStr());
            list.add(orderProperty);
        }
        boolean isSuccess = orderPropertyService.insertBatch(list);
        result.setIsSuccess(isSuccess);
        String jsonString = result.toJSON();
        return jsonString;
    }


    /**
    * @description: 删除
    * @createTime: 2017年09月15日 09:41:57
    * @author: hhd
    * @param ids
    * @return
    */
    @RequestMapping(value = "mulDelete",method = RequestMethod.POST)
    @ResponseBody
    public Object mulDelete(String ids,Long orderId){
        ServiceResult result = new ServiceResult(false);
        try {
            String[] idArray = StringUtil.split(ids);
            if(idArray==null||idArray.length==0){
                result.setMessage("请选择要删除的数据行");
                return result;
            }

            int isSuccess = orderPropertyService.deletePropertyByOrder(Arrays.asList(idArray),orderId);
            if(isSuccess>0){
                result.setIsSuccess(true);
            }else {
                result.setIsSuccess(false);
            }

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
    * @createTime: 2017年09月15日 09:41:57
    * @author: hhd
    * @param m
    * @param rows
    * @param page
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "listByPage")
    public Object listByPage(OrderProperty m, int rows, int page){
        Page<OrderProperty> pageM= new Page<>(page,rows);
        EntityWrapper<OrderProperty> ew = new EntityWrapper<>(m);
        pageM = orderPropertyService.selectPage(pageM,ew);
        List<OrderProperty> list = pageM.getRecords();
        if(list.size()==0){
            return JSONUtil.EMPTYJSON;
        }
        List<Long> array=new ArrayList<>();
        for (OrderProperty orderProperty:list){
            array.add(orderProperty.getCustomerPropertyId());
        }

        EntityWrapper<CustomerProperty> ewp = new EntityWrapper<>();
        ewp.in("a.id",array);
        Page<CustomerProperty> pageC= new Page<>(page,rows);
        pageC = customerPropertyService.selectPageByEw(pageC,ewp);
        String[] properties = {"customerId","certificate","owner","isCommon","area","housingNature.id:housingNatureid",
                "housingNature.name:housingNatureName","areaId.id:areaId","areaId.showName:areaShowName","areaId.areaName",
                "plotId.id:plotId","plotId.plotName:plotPlotName","houseAddress","havaLandCertificate","landCertificateNumber",
                "landNature.id:landNatureid","landNature.name:landNatureName","propertyValue","attachId","id","note"};
        String jsonString = JSONUtil.toJson(pageC.getRecords(), properties,(long) pageM.getTotal());

        return jsonString;
    }

}
