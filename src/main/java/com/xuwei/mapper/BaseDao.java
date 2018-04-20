package com.xuwei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.BaseEntity;




/**
 * @description: dao基类
 * @copyright: 福建骏华信息有限公司 (c)2016
 * @createTime: 2016年8月12日下午5:37:41
 * @author：lys
 * @version：1.0
 */
public interface BaseDao<M extends BaseEntity<ID>, ID> {
    /**
     * @description: 插入对象
     * @createTime: 2016年8月12日 下午5:36:04
     * @author: lys
     * @param m
     * @return
     */
    int insert(M m);
    /**
     * @description: 批量插入对象
     * @createTime: 2016年8月16日 下午5:44:46
     * @author: lys
     * @param m
     * @return
     */
    int insertBatch(List<M> list);
    /**
     * @description: 更新对象
     * @createTime: 2016年8月12日 下午5:36:12
     * @author: lys
     * @param m
     * @return
     */
    int update(M m);
    /**
     * @description: 根据主键id有值更新
     * @createTime: 2016年9月25日 下午6:15:08
     * @author: lys
     * @param m
     * @return
     */
    int updateByIdSelective(M m);
    /**
     * @description: 通过主键删除对象
     * @createTime: 2016年8月12日 下午5:36:21
     * @author: lys
     * @param id
     * @return
     */
    int deleteById(ID id);
    /**
     * @description: 通过主键查询对象
     * @createTime: 2016年8月12日 下午5:37:15
     * @author: lys
     * @param id
     * @return
     */
    M findById(ID id);
    /**
     * @description: 通过主见数组删除对象
     * @createTime: 2016年8月12日 下午5:39:40
     * @author: lys
     * @param idArray
     * @return
     */
    int mulDelete(String[] idArray);
    /**
     * @description: 统计
     * @createTime: 2016年8月12日 下午10:25:59
     * @author: lys
     * @param m
     * @return
     */
	Long count(@Param("m") M m);
	/**
	 * @description: 分页查询
	 * @createTime: 2016年8月12日 下午10:26:09
	 * @author: lys
	 * @param m
	 * @param page
	 * @return
	 */
	List<M> findListByPage(@Param("m") M m, Page<M> page);
	/**
	 * @description: 查询所有
	 * @createTime: 2016年8月17日 上午9:22:20
	 * @author: lys
	 * @return
	 */
	List<M> findAll();
	/**
	 * @description: 下一个权重值
	 * @createTime: 2016年10月10日 下午1:39:29
	 * @author: lys
	 * @param id
	 * @return
	 */
	int nextWeight(ID id);
	/**
	 * @description: 下一个权重值
	 * @createTime: 2016年10月10日 下午1:39:58
	 * @author: lys
	 * @return
	 */
	int nextWeight();
	/**
	 * @description: 下一个排序值
	 * @createTime: 2017年2月10日 下午4:10:39
	 * @author: lys
	 * @return
	 */
	int nextArray();
	/**
	 * @description: 对换权限
	 * @createTime: 2016年10月10日 上午10:06:44
	 * @author: lys
	 * @param srcId
	 * @param destId
	 */
    void changeArray(@Param("srcId") ID srcId, @Param("destId") ID destId);
	/**
	 * 
	 * @description: 唯一性检查
	 * @createTime: 2017年6月29日 下午5:46:10
	 * @author: hhd
	 * @param m
	 * @return
	 */
	M findByUnique(@Param("m") M m);
}
