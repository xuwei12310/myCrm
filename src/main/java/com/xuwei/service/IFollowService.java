/**
 * 
 */
package com.xuwei.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.Follow;
import com.xuwei.bean.Todo;
import com.xuwei.util.ServiceResult;

/**
 * @description:
 * @createTime 2017年9月4日 上午10:53:54
 * @copyright 福建骏华有限公司（c）2017
 * @author xw
 *
 */
public interface IFollowService extends IService<Follow>{

	/**
	 * @description:分页查询
	 * @createTime 2017年9月4日 下午3:22:58
	 * @author xw
	 * @param m
	 * @param pageM
	 * @param ew 
	 */
	void listByPage(Follow m, Page<Follow> pageM, String search, EntityWrapper<Follow> ew);
	
	/**
	 * @description:通过id查找跟进
	 * @createTime 2017年10月11日 上午10:54:37
	 * @author xw
	 * @param id
	 * @return
	 */
	Follow selectById(long id);

	/**
	 * @description:跟进跟进id获取附件id
	 * @createTime 2017年9月5日 上午10:11:45
	 * @author xw
	 * @param followId
	 * @return
	 */
	String getAttachId(Long followId);

	/**
	 * @description:删除附件
	 * @createTime 2017年9月5日 上午11:54:41
	 * @author xw
	 * @param aId
	 * @return
	 */
	ServiceResult deleteAttach(String aId);

	/**
	 * @description:批量删除跟进
	 * @createTime 2017年9月5日 下午3:33:57
	 * @author xw
	 * @param idArray
	 * @return
	 */
	boolean deleteByIds(String[] idArray);

	/**
	 * @description:微信端保存跟进
	 * @createTime 2017年9月29日 上午11:02:12
	 * @author xw
	 * @param m
	 * @param userId 
	 * @param attachId 
	 * @param receiveTaskId 
	 * @param t 
	 * @param isActive 
	 * @return 
	 */
	ServiceResult save(Follow m, Long userId, Long attachId, Todo t, String receiveTaskId, Boolean isActive);

	/**
	 * @description:微信端更新跟进
	 * @createTime 2017年9月29日 上午11:02:12
	 * @author xw
	 * @param m
	 * @param userId
	 * @param attachId
	 * @return
	 */
	ServiceResult update(Follow m, Long userId, Long attachId);
	
	/**
	 * 
	 * @description: 根据跟进id修改附件信息
	 * @createTime: 2017年10月9日 下午3:50:55
	 * @author: caw
	 * @param followId
	 * @return
	 */
	boolean modAttach(Long followId, String attachIds);
	
	//ServiceResult downloadRecordWx(String mediaId,Long userId);
}
