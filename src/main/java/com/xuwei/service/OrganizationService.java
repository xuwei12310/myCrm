/**
 * 
 */
package com.xuwei.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.Organization;
import com.xuwei.util.ServiceResult;

/**
 * @description:
 * @createTime 上午10:53:46
 * @author xw
 *
 */
public interface OrganizationService extends IService<Organization>{

	/**
	 * @description:获取所有机构
	 * @createTime 2017年8月29日 上午11:04:38
	 * @author xw
	 * @return
	 */
	List<Organization> findAll();
	
	/**
	 * @description:通过类型查询机构信息 
	 * @createTime 2017年8月29日 上午11:04:38
	 * @author xw
	 * @return
	 */
	List<Organization> findAllByOrgType(int orgType);

	/**
	 * @description:获取跟节点
	 * @createTime 2017年8月29日 上午11:42:58
	 * @author xw
	 * @return
	 */
	List<Organization> treeRoot();

	/**
	 * @description:获取子节点
	 * @createTime 2017年8月29日 上午11:43:03
	 * @author xw
	 * @param id
	 * @return
	 */
	List<Organization> treeChildren(Long id);

	/**
	 * @description:添加部门
	 * @createTime 2017年8月29日 下午3:21:56
	 * @author xw
	 * @param m
	 * @return
	 */
	ServiceResult create(Organization m);

	/**
	 * @description:更新部门
	 * @createTime 2017年8月31日 下午2:11:37
	 * @author xw
	 * @param m
	 * @return
	 */
	ServiceResult update(Organization m);

	/**
	 * @description:移动树
	 * @createTime 2017年8月31日 下午2:28:04
	 * @author xw
	 * @param sourceId
	 * @param targetId
	 * @param point
	 * @return
	 */
	Object move(long sourceId, long targetId, String point);
	
	/**
	 * 
	 * @description:通过id获取机构信息 
	 * @createTime: 2017年9月7日 上午11:03:00
	 * @author: zyd
	 * @param id
	 * @return
	 */
	Organization findById(Long id);
	
	/**
	 * 
	 * @description:查询公司下的机构信息 
	 * @createTime: 2017年9月7日 下午1:11:05
	 * @author: zyd
	 * @param parentIds
	 * @return
	 */
	List<Organization> findByCompany(Long company);

	/**
	 * @description:通过用户id获取部门
	 * @createTime 2017年9月30日 上午11:36:51
	 * @author xw
	 * @param currentUserId
	 */
	Organization selectByUserId(Long currentUserId);

}
