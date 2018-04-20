package com.xuwei.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.BankSubbranch;
import com.xuwei.mapper.BankSubbranchMapper;
import com.xuwei.service.IBankSubbranchService;
import com.xuwei.util.ServiceResult;


/**
 * @description: 银行服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年07月19日 16:31:12
 * @author: xw
 * @version: 1.0
 */
@Service
public class BankSubbranchServiceImpl extends ServiceImpl<BankSubbranchMapper, BankSubbranch> implements IBankSubbranchService {

	@Resource
	private BankSubbranchMapper bankSubbranchMapper;
	
  /**
    * 
    * @description: 上移下移
    * @createTime: 2017年8月30日 上午14:36:19
    * @author: xw
    * @param srcId
    * @param destId
    */
	@Override
	public ServiceResult changeArray(Long srcId, Long destId) {
		ServiceResult result = new ServiceResult(false);
		if(srcId==null){
			result.setMessage("请选中移动记录");
			return result;
		}
		if(destId==null){
			result.setMessage("请指定对换记录");
			return result;
		}
		bankSubbranchMapper.changeArray(srcId,destId);
		result.setIsSuccess(true);
		return result;
	}
	/**
	 * 
	 * @description: 取下个排序值
	 * @createTime:2017年8月30日 上午14:36:19
	 * @author: xw
	 * @param dictType
	 * @return
	 */
	@Override
	public Integer nextArray() {
		return bankSubbranchMapper.nextArray();
	}
	@Override
	public Page<BankSubbranch> listBankByOrder(Page<BankSubbranch> pageM, Long orderId) {
		List<BankSubbranch> list=bankSubbranchMapper.listBankByOrder(pageM,orderId);
		pageM.setRecords(list);
		return pageM;
	}

}
