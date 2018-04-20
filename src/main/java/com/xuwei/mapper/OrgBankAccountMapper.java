package com.xuwei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.OrgBankAccount;

/**
 * @description: 机构_银行账号Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月06日 10:26:17
 * @author: zyd
 * @version: 1.0
 */
public interface OrgBankAccountMapper extends BaseMapper<OrgBankAccount> {

	/**
	 * 
	 * @description:通过部门ID 获取银行账户 
	 * @createTime: 2017年9月6日 下午12:29:26
	 * @author: zyd
	 * @param pageM
	 * @param orgId
	 * @return
	 */
	List<OrgBankAccount> getBankAccountByList(Page<OrgBankAccount> pageM, @Param("orgId")Long orgId);

	/**
	 * @description:
	 * @createTime 2017年9月13日 下午3:15:44
	 * @author xw
	 * @param m
	 * @return
	 */
	List<OrgBankAccount> payBankAccountCombobox(@Param("m")OrgBankAccount m);
}