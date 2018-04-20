package com.xuwei.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xuwei.bean.BrokerageRule;

/**
 * @description: 佣金规则Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月25日 19:04:18
 * @author: caw
 * @version: 1.0
 */
public interface BrokerageRuleMapper extends BaseMapper<BrokerageRule> {
	/**
	 * 
	 * @description: 获取佣金规则第一条数据
	 * @createTime: 2017年9月25日 下午7:25:04
	 * @author: caw
	 * @return
	 */
	BrokerageRule findByLIMITOne();
}