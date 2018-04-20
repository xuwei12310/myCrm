package com.xuwei.service;

import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.BrokerageRule;
 
/**
 * @description: 佣金规则服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月25日 19:04:18
 * @author: caw
 * @version: 1.0
 */
public interface IBrokerageRuleService extends IService<BrokerageRule> {
	
	/**
	 * 
	 * @description: 获取佣金规则第一条数据
	 * @createTime: 2017年9月25日 下午7:23:41
	 * @author: caw
	 * @return
	 */
	public BrokerageRule findByLIMITOne();
}
