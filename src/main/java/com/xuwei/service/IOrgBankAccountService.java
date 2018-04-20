package com.xuwei.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.OrgBankAccount;
 
/**
 * @description: 机构_银行账号服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月06日 10:26:17
 * @author: zyd
 * @version: 1.0
 */
public interface IOrgBankAccountService extends IService<OrgBankAccount> {
	
	/**
	 * 
	 * @description:  根据部门ID 查询银行账户信息
	 * @createTime: 2017年9月6日 下午12:02:17
	 * @author: zyd
	 * @return
	 */
	List<OrgBankAccount> getBankAccountByList(Page<OrgBankAccount> pageM, Long orgId);

	/**
	 * @description:获取公司银行账户下拉列表
	 * @createTime 2017年9月13日 下午3:13:12
	 * @author xw
	 * @param pageM
	 * @param m
	 * @return
	 */
	Page<OrgBankAccount> payBankAccountCombobox(Page<OrgBankAccount> pageM,
			OrgBankAccount m);
}
