package com.xuwei.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.Attach;
 
/**
 * @description: 系统_附件服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年06月30日 11:46:47
 * @author: lys
 * @version: 1.0
 */
public interface IAttachService extends IService<Attach> {

	/**
	 * 
	 * @description: 根据id获取附件信息（微信端）
	 * @createTime: 2017年9月14日 上午10:53:11
	 * @author: caw
	 * @param attachId
	 * @return
	 */
	List<Attach> getAttachList(Long attachId);
	
	/**
	 * 
	 * @description: 生成SWF
	 * @createTime: 2017年10月9日 上午11:43:28
	 * @author: caw
	 * @param fileBasePath
	 * @param fileSaveName
	 * @return
	 */
	String createSWF(String fileBasePath, String fileSaveName);
}
