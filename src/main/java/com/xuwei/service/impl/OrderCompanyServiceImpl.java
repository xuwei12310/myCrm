package com.xuwei.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.OrderCompany;
import com.xuwei.mapper.OrderCompanyMapper;
import com.xuwei.service.IOrderCompanyService;
import com.xuwei.util.ServiceResult;

/**
 * @description: 订单_评估公司服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月18日 17:46:41
 * @author: hhd
 * @version: 1.0
 */
@Service
public class OrderCompanyServiceImpl extends ServiceImpl<OrderCompanyMapper, OrderCompany> implements IOrderCompanyService {

    @Resource
    private OrderCompanyMapper orderCompanyMapper;

    @Override
    public List<Map<String, Object>> queryByPage(Pagination page, Long orderId) {
        return orderCompanyMapper.queryByPage(page,orderId);
    }

    @Override
    public ServiceResult getCompanyByPage(Page<OrderCompany> page, Long totalNum, Long orderid) {
        ServiceResult result = new ServiceResult(false);
        List<OrderCompany> list = orderCompanyMapper.selectListByPage(page, orderid);
        if(page.getTotal()==totalNum && totalNum!=0){
            List<OrderCompany> lists = new ArrayList<>();
            result.addData("orderCompanyList", lists);
            result.setIsSuccess(true);
            return result;
        }
        result.addData("orderCompanyList", list);
        result.setIsSuccess(true);
        return result;
    }

    @Override
    public OrderCompany getCompanyById(Long id) {
        return orderCompanyMapper.getCompanyById(id);
    }
}
