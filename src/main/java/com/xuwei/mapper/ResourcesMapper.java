package com.xuwei.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.Resources;

import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * @description: 资源dao
 * @copyright: 福建骏华信息有限公司 (c)2016
 * @createTime: 2016年8月15日下午4:20:35
 * @author：lys
 * @version：1.0
 */
public interface ResourcesMapper extends BaseMapper<Resources> {

	List<Resources> menuTreeChildren(@Param("parentId")Long parentId);
	/**
	 * @description: 查询树节点
	 * @createTime: 2016年8月15日 下午5:12:08
	 * @author: lys
	 * @return
	 */
	List<Resources> treeRoot();
	/**
	 * @description: 查找pk下的树子节点
	 * @createTime: 2016年8月15日 下午5:31:06
	 * @author: lys
	 * @param id
	 * @return
	 */
	List<Resources> treeChildren(Long id);
	/**
	 * @description: 查询m.id下的子节点
	 * @createTime: 2016年8月15日 下午5:44:00
	 * @author: lys
	 * @param m
	 * @return
	 */
	List<Resources> findChildren(@Param("m")Resources m);
	/**
	 * @description: 分页查询m.id下的子节点
	 * @createTime: 2016年8月15日 下午5:44:00
	 * @author: lys
	 * @param m
	 * @param page
	 * @return
	 */
	List<Resources> findChildrenByPage(@Param("m")Resources m,Page<Resources> page);
	/**
	 * @description: 更新对象
	 * @createTime: 2016年8月12日 下午5:36:12
	 * @author: lys
	 * @param m
	 * @return
	 */
	int update(Resources m);
	/**
	 * @description: 根据主键id有值更新
	 * @createTime: 2016年9月25日 下午6:15:08
	 * @author: lys
	 * @param m
	 * @return
	 */
	int updateByIdSelective(Resources m);
	/**
	 * @description: 通过主见数组删除对象
	 * @createTime: 2016年8月12日 下午5:39:40
	 * @author: lys
	 * @param idArray
	 * @return
	 */
	int mulDelete(String[] idArray);
	/**
	 * @description: 取得下个权重值
	 * @createTime: 2016年8月16日 下午12:00:45
	 * @author: lys
	 * @param id
	 * @return
	 */
	int nextWeight(Long id);
	/**
	 * @description: 通过主键查询对象
	 * @createTime: 2016年8月12日 下午5:37:15
	 * @author: lys
	 * @param id
	 * @return
	 */
	Resources findById(Long id);
	/**
	 * @description: 查询整棵树节点
	 * @createTime: 2016年8月24日 下午3:04:43
	 * @author: lys
	 * @return
	 */
	List<Resources> tree();
	/**
	 * @description: 取得排序大于等于currentWeight的兄弟节点（包括自己）
	 * @createTime: 2016年9月30日 下午3:05:03
	 * @author: lys
	 * @param parentIds
	 * @param weight
	 * @return
	 */
	List<Resources> findSelfAndNextSiblings(@Param("parentIds")String parentIds, @Param("weight")Integer weight);

	void updateSelftAndChild(@Param("source")Resources source, @Param("parentId")Long parentId, @Param("parentIds")String parentIds, @Param("weight")Integer weight);

	void updateChildrenParentIds(@Param("newParentIds")String newParentIds, @Param("oldParentIds")String oldParentIds);

}