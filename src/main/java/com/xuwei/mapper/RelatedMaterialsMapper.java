package com.xuwei.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xuwei.bean.RelatedMaterials;

/**
 * <p>
  * 相关材料 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2017-08-31
 */
public interface RelatedMaterialsMapper extends BaseMapper<RelatedMaterials> {

	/**
	 * @description:
	 * @createTime 2017年8月31日 下午5:52:04
	 * @author xw
	 * @return
	 */
	Integer nextArray();

	/**
	 * @description:
	 * @createTime 2017年8月31日 下午5:52:45
	 * @author xw
	 * @param srcId
	 * @param destId
	 */
	void changeArray(@Param("srcId")Long srcId, @Param("destId")Long destId);

}