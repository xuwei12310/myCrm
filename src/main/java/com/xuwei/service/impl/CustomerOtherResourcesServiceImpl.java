package com.xuwei.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.CustomerOtherResources;
import com.xuwei.mapper.CustomerOtherResourcesMapper;
import com.xuwei.service.ICustomerOtherResourcesService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.ServiceResult;

/**
 * @description: 客户_其他财力服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月04日 09:43:35
 * @author: caw
 * @version: 1.0
 */
@Service
public class CustomerOtherResourcesServiceImpl extends ServiceImpl<CustomerOtherResourcesMapper, CustomerOtherResources> implements ICustomerOtherResourcesService {
	@Resource
	private CustomerOtherResourcesMapper customerOtherResourcesMapper;
	
	/**
	 * 获取其他财产（微信端）
	 */
	@Override
	public ServiceResult getOtherResourcesList(Page<CustomerOtherResources> page, Long userId, Long totalNum,
			Long customerid) {
		ServiceResult result = new ServiceResult(false);
		List<CustomerOtherResources> list = customerOtherResourcesMapper.getOtherResourcesList(page, customerid);
		if(page.getTotal()==totalNum && totalNum!=0){
			List<CustomerOtherResources> lists = new ArrayList<CustomerOtherResources>();
			result.addData("otherResourceslist", lists);
			result.setIsSuccess(true);
			return result;
		}
		result.addData("otherResourceslist", list);
		result.setIsSuccess(true);
		return result;
	}

	/**
	 *  新增其他财产（微信端）
	 */
	@Override
	public ServiceResult addOtherResources(CustomerOtherResources m, Long userId) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(m.getName())){
			result.setMessage("财产名称不能为空");
			return result;
		}
		m.setCreatorId(userId);
		m.setCreateTime(DateUtil.getNowTimestampStr());
		m.setLastModifierId(userId);
		m.setLastModifyTime(DateUtil.getNowTimestampStr());
		int data = customerOtherResourcesMapper.insert(m);
		if(data>0){
			result.setIsSuccess(true);
			return result;
		}else{
			result.setMessage("没新增记录");
			return result;
		}
	}

	/**
	 * 修改其他财产（微信端）
	 */
	@Override
	public ServiceResult modOtherResources(CustomerOtherResources m, Long userId) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(m.getName())){
			result.setMessage("财产名称不能为空");
			return result;
		}
		m.setLastModifierId(userId);
		m.setLastModifyTime(DateUtil.getNowTimestampStr());
		int data = customerOtherResourcesMapper.updateAllColumnById(m);
		if(data>0){
			result.setIsSuccess(true);
			return result;
		}else{
			result.setMessage("没修改记录");
			return result;
		}
	}	
}
