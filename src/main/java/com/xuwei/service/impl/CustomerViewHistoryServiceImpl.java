package com.xuwei.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.CustomerViewHistory;
import com.xuwei.mapper.CustomerMapper;
import com.xuwei.mapper.CustomerViewHistoryMapper;
import com.xuwei.mapper.UserMapper;
import com.xuwei.service.ICustomerViewHistoryService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;

/**
 * @description: 客户_浏览历史服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月06日 09:12:17
 * @author: caw
 * @version: 1.0
 */
@Service
public class CustomerViewHistoryServiceImpl extends ServiceImpl<CustomerViewHistoryMapper, CustomerViewHistory> implements ICustomerViewHistoryService {
	@Resource
	private CustomerViewHistoryMapper customerViewHistoryMapper;
	@Resource
	private UserMapper userMapper;
	@Resource
	private CustomerMapper customerMapper;
	
	/**
	 * 查询客户浏览历史记录
	 */
	@Override
	public List<CustomerViewHistory> listCustomerViewHistoryByPage(Page<CustomerViewHistory> pageM,
			CustomerViewHistory m) {
		return customerViewHistoryMapper.listCustomerViewHistoryByPage(pageM,m);
	}

	/**
	 * 新增浏览历史
	 */
	@Override
	public ServiceResult createCustomerViewHistory(CustomerViewHistory m, Long customerId) {
		ServiceResult result = new ServiceResult(false);
		m.setCustomer(customerMapper.selectById(customerId));
		m.setViewUser(userMapper.findById(OperateUtils.getCurrentUserId()));
		m.setViewTime(DateUtil.getNowTimestampStr());
		m.setCreatorId(OperateUtils.getCurrentUserId());
		m.setCreateTime(DateUtil.getNowTimestampStr());
		m.setLastModifierId(OperateUtils.getCurrentUserId());
		m.setLastModifyTime(DateUtil.getNowTimestampStr());
		int deleteCount = customerViewHistoryMapper.insert(m);
		if(deleteCount>0){
			result.setIsSuccess(true);
			return result;
		}else{
			result.setMessage("没新增记录");
			return result;
		}
	}
	
}
