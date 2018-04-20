package com.xuwei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.BankSubbranch;

/**
 * @description: 银行支行Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年07月19日 16:31:12
 * @author: xw
 * @version: 1.0
 */
public interface BankSubbranchMapper extends BaseMapper<BankSubbranch> {
   /**
    * 
    * @description: 上移下移
    * @createTime: 2017年8月30日 上午14:36:19
    * @author: xw
    * @param srcId
    * @param destId
    */
	void changeArray(@Param("srcId")Long srcId, @Param("destId")Long destId);
	/**
	 * 
	 * @description: 取下个排序值
	 * @createTime: 2017年8月30日 上午14:36:19
	 * @author: xw
	 * @param dictType
	 * @return
	 */
	Integer nextArray();
	/**
	 * @description:
	 * @createTime 2017年9月14日 上午10:10:50
	 * @author xw
	 * @param pageM 
	 * @param orderId
	 * @return
	 */
	List<BankSubbranch> listBankByOrder(Page<BankSubbranch> pageM, @Param("id")Long orderId);



}