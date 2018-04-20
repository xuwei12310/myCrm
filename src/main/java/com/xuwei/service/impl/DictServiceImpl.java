package com.xuwei.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.Dict;
import com.xuwei.bean.DictType;
import com.xuwei.mapper.DictMapper;
import com.xuwei.service.IDictService;
import com.xuwei.util.ServiceResult;


/**
 * @description: 字典服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年07月19日 16:31:12
 * @author: lxb
 * @version: 1.0
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

	
	@Resource
	private DictMapper dictMapper;
	
	  /**
	    * 
	    * @description: 上移下移
	    * @createTime: 2017年7月20日 上午11:20:59
	    * @author: lxb
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
		dictMapper.changeArray(srcId,destId);
		result.setIsSuccess(true);
		return result;
	}
	/**
	 * 
	 * @description: 取下个排序值
	 * @createTime: 2017年8月9日 下午3:54:23
	 * @author: lxb
	 * @param dictType
	 * @return
	 */
	@Override
	public Integer nextArray(DictType dictType) {
		return dictMapper.nextArray(dictType);
	}
	
	/**
	 * 根据id获取字典数据（微信端）
	 */
	@Override
	public List<Dict> getDictList(Long id) {
		return dictMapper.getDictList(id);
	}


}
