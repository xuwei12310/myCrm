package com.xuwei.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xuwei.bean.OtherPartners;

/**
 * <p>
  * 评估公司 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2017-08-31
 */
public interface OtherPartnersMapper extends BaseMapper<OtherPartners> {

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