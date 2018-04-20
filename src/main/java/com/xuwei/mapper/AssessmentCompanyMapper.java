package com.xuwei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.AssessmentCompany;

/**
 * <p>
  * 评估公司 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2017-08-31
 */
public interface AssessmentCompanyMapper extends BaseMapper<AssessmentCompany> {

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

	/**
	 * @description:
	 * @createTime 2017年9月14日 上午10:17:56
	 * @author xw
	 * @param pageM 
	 * @param orderId
	 * @return
	 */
	List<AssessmentCompany> listAssessmentCompanyByOrder(Page<AssessmentCompany> pageM, @Param("id")Long orderId);

}