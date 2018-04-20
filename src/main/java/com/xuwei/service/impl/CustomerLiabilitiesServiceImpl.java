package com.xuwei.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.CustomerLiabilities;
import com.xuwei.mapper.CustomerLiabilitiesMapper;
import com.xuwei.service.ICustomerLiabilitiesService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.ServiceResult;

/**
 * @description: 客户_负债服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月04日 09:44:17
 * @author: caw
 * @version: 1.0
 */
@Service
public class CustomerLiabilitiesServiceImpl extends ServiceImpl<CustomerLiabilitiesMapper, CustomerLiabilities> implements ICustomerLiabilitiesService {

	@Resource
	private CustomerLiabilitiesMapper customerLiabilitiesMapper;
	
	/**
	 * 获取负债（微信端）
	 */
	@Override
	public ServiceResult getLiabilitiesList(Page<CustomerLiabilities> page, Long userId, Long totalNum,
			Long customerid) {
		ServiceResult result = new ServiceResult(false);
		List<CustomerLiabilities> list = customerLiabilitiesMapper.getLiabilitiesList(page, customerid);
		if(page.getTotal()==totalNum && totalNum!=0){
			List<CustomerLiabilities> lists = new ArrayList<CustomerLiabilities>();
			result.addData("liabilitieslist", lists);
			result.setIsSuccess(true);
			return result;
		}
		result.addData("liabilitieslist", list);
		result.setIsSuccess(true);
		return result;
	}

	/**
	 * 新增负债（微信端）
	 */
	@Override
	public ServiceResult addLiabilities(CustomerLiabilities m, Long userId) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(m.getName())){
			result.setMessage("负债名称不能为空");
			return result;
		}
		m.setCreatorId(userId);
		m.setCreateTime(DateUtil.getNowTimestampStr());
		m.setLastModifierId(userId);
		m.setLastModifyTime(DateUtil.getNowTimestampStr());
		int data = customerLiabilitiesMapper.insert(m);
		if(data>0){
			result.setIsSuccess(true);
			return result;
		}else{
			result.setMessage("没新增记录");
			return result;
		}
	}

	/**
	 * 修改负债（微信端）
	 */
	@Override
	public ServiceResult modLiabilities(CustomerLiabilities m, Long userId) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(m.getName())){
			result.setMessage("负债名称不能为空");
			return result;
		}
		m.setLastModifierId(userId);
		m.setLastModifyTime(DateUtil.getNowTimestampStr());
		int data = customerLiabilitiesMapper.updateAllColumnById(m);
		if(data>0){
			result.setIsSuccess(true);
			return result;
		}else{
			result.setMessage("没修改记录");
			return result;
		}
	}
	
}
