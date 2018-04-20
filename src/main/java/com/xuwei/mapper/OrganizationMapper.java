/**
 * 
 */
package com.xuwei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xuwei.bean.Organization;

/**
 * @description:
 * @createTime 上午10:56:15
 * @author xw
 *
 */
public interface OrganizationMapper extends BaseMapper<Organization>{


	/**
	 * @description:获取所有组织机构
	 * @createTime 2017年8月29日 上午11:25:27
	 * @author xw
	 * @return
	 */
	List<Organization> findAll();
	
	/**
	 * 
	 * @description:通过类型查询机构信息 
	 * @createTime: 2017年9月7日 下午12:47:14
	 * @author: zyd
	 * @param orgType
	 * @return
	 */
	List<Organization> findAllByOrgType(int orgType);

	/**
	 * @description:获取树的跟节点
	 * @createTime 2017年8月29日 上午11:46:35
	 * @author xw
	 * @return
	 */
	List<Organization> treeRoot();

	/**
	 * @description:获取子节点
	 * @createTime 2017年8月29日 上午11:46:39
	 * @author xw
	 * @param id
	 * @return
	 */
	List<Organization> treeChildren(@Param("id")Long id);

	/**
	 * 
	 * @description:查询公司下的机构信息 
	 * @createTime: 2017年9月7日 下午1:11:05
	 * @author: zyd
	 * @param parentIds
	 * @return
	 */
	List<Organization> findByCompany(@Param("parentIds")String parentIds);
	/**
	 * @description:通过Id获取组织机构
	 * @createTime 2017年8月29日 下午2:32:31
	 * @author xw
	 * @param organizationId
	 * @return
	 */
	Organization findById(@Param("id")Long organizationId);

	/**
	 * @description:获取排序的下一个值
	 * @createTime 2017年8月29日 下午3:24:40
	 * @author xw
	 * @param parentId
	 * @return
	 */
	int nextArray(@Param("pid")Long parentId);

	/**
	 * @description:更新组织机构
	 * @createTime 2017年8月31日 下午2:17:23
	 * @author xw
	 * @param olM
	 * @return
	 */
	int update(Organization olM);

	/**
	 * @description:修改移动的组织机构
	 * @createTime 2017年8月31日 下午2:34:27
	 * @author xw
	 * @param source
	 */
	void updateByIdSelective(Organization source);

	/**
	 * @description:修改子节点的parentId
	 * @createTime 2017年8月31日 下午2:34:33
	 * @author xw
	 * @param newParentIds
	 * @param oldParentIds
	 */
	void updateChildrenParentIds(@Param("newParentIds")String newParentIds, @Param("oldParentIds")String oldParentIds);

	/**
	 * @description:查找子节点
	 * @createTime 2017年8月31日 下午2:34:41
	 * @author xw
	 * @param parentIds
	 * @param weight
	 * @return
	 */
	List<Organization> findSelfAndNextSiblings(@Param("parentIds")String parentIds, @Param("weight")Integer weight);

	/**
	 * @description:通过用户id获取部门
	 * @createTime 2017年9月30日 上午11:40:32
	 * @author xw
	 * @param currentUserId
	 * @return
	 */
	Organization selectByUserId(@Param("id")Long currentUserId);


}
