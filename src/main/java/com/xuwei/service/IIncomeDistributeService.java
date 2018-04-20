package com.xuwei.service;


import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.IncomeDistribute;
 
/**
 * @description: 收入分配服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月26日 09:36:12
 * @author: xw
 * @version: 1.0
 */
public interface IIncomeDistributeService extends IService<IncomeDistribute> {

	/**
	 * @description:分页查询收入分配
	 * @createTime 2017年9月26日 上午11:22:13
	 * @author xw
	 * @param pageM
	 * @param incomeId
	 * @return
	 */
	Page<IncomeDistribute> listByPage(Page<IncomeDistribute> pageM, Long incomeId);

	/**
	 * @description:修改分配
	 * @createTime 2017年9月26日 下午3:28:27
	 * @author xw
	 * @param m
	 * @param fromId
	 * @param pay
	 * @param result 
	 */
	void updateItem(IncomeDistribute m, Long fromId, BigDecimal pay, BigDecimal result);

	/**
	 * @description:通过分配id，收入id查询分配
	 * @createTime 2017年9月26日 下午8:08:19
	 * @author xw
	 * @param id
	 * @return
	 */
	List<IncomeDistribute> selectIncomeDistribute(IncomeDistribute id);
	
}
