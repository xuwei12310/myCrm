package com.xuwei.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.Pay;
import com.xuwei.mapper.PayMapper;
import com.xuwei.service.IPayService;

/**
 * @description: 支出登记服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月12日 16:48:34
 * @author: xw
 * @version: 1.0
 */
@Service
public class PayServiceImpl extends ServiceImpl<PayMapper, Pay> implements IPayService {
	@Resource
	private PayMapper payMapper;
	
	@Override
	public Page<Pay> listByPage(Page<Pay> pageM,Pay m,EntityWrapper<Pay> ew) {
		List<Pay> list=payMapper.listByPage(pageM,m,ew);
		pageM.setRecords(list);
		return pageM;
	}
	
	@Override
	public Page<Pay> listByBanlancePage(Page<Pay> pageM, Pay m) {
		List<Pay> list=payMapper.listByBanlancePage(pageM,m);
		pageM.setRecords(list);
		return pageM;
	}

	@Override
	public boolean verifyByIds(List<String> asList) {
		
		int count=payMapper.verifyByIds(asList);
        return count > 0;
    }
	
}
