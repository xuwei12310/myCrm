package com.xuwei.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.BrokerageRule;
import com.xuwei.mapper.BrokerageRuleMapper;
import com.xuwei.service.IBrokerageRuleService;

/**
 * @description: 佣金规则服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月25日 19:04:18
 * @author: caw
 * @version: 1.0
 */
@Service
public class BrokerageRuleServiceImpl extends ServiceImpl<BrokerageRuleMapper, BrokerageRule> implements IBrokerageRuleService {
    @Resource
	private BrokerageRuleMapper brokerageRuleMapper;
	
	/**
	 * 获取佣金规则第一条数据
	 */
	@Override
	public BrokerageRule findByLIMITOne() {
		return brokerageRuleMapper.findByLIMITOne();
	}
	
}
