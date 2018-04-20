package com.xuwei.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.IncomeDistribute;

/**
 * @description: 收入分配Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月26日 09:36:12
 * @author: xw
 * @version: 1.0
 */
public interface IncomeDistributeMapper extends BaseMapper<IncomeDistribute> {

	/**
	 * @description:分页查询收入分配
	 * @createTime 2017年9月26日 上午11:24:43
	 * @author xw
	 * @param pageM
	 * @param incomeId
	 * @return
	 */
	List<IncomeDistribute> listByPage(Page<IncomeDistribute> pageM,@Param("incomeId")Long incomeId);

	/**
	 * @description:通过分配id，收入id查询分配
	 * @createTime 2017年9月26日 下午8:09:17
	 * @author xw
	 * @param id
	 * @return
	 */
	List<IncomeDistribute> selectIncomeDistribute(@Param("m")IncomeDistribute id);

}