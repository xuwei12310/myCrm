package com.xuwei.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.OrgBankAccount;
import com.xuwei.mapper.OrgBankAccountMapper;
import com.xuwei.service.IOrgBankAccountService;

/**
 * @description: 机构_银行账号服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月06日 10:26:17
 * @author: zyd
 * @version: 1.0
 */
@Service
public class OrgBankAccountServiceImpl extends ServiceImpl<OrgBankAccountMapper, OrgBankAccount> implements IOrgBankAccountService {

	@Resource
	private OrgBankAccountMapper orgBankAccountMapper;
	@Override
	public List<OrgBankAccount> getBankAccountByList(Page<OrgBankAccount> pageM, Long orgId) {
		return orgBankAccountMapper.getBankAccountByList(pageM,orgId);
	}
	@Override
	public Page<OrgBankAccount> payBankAccountCombobox(Page<OrgBankAccount> pageM,OrgBankAccount m) {
		
		List<OrgBankAccount> list= orgBankAccountMapper.payBankAccountCombobox(m);
		pageM.setRecords(list);
		return pageM;
	}
	
}
