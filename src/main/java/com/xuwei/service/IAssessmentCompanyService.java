package com.xuwei.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.AssessmentCompany;
import com.xuwei.util.ServiceResult;

/**
 * <p>
 * 评估公司 服务类
 * </p>
 *
 * @author ${author}
 * @since 2017-08-31
 */
public interface IAssessmentCompanyService extends IService<AssessmentCompany> {

	/**
	 * @description:
	 * @createTime 2017年8月31日 下午5:51:15
	 * @author xw
	 * @return
	 */
	Integer nextArray();

	/**
	 * @description:
	 * @createTime 2017年8月31日 下午5:52:37
	 * @author xw
	 * @param srcId
	 * @param destId
	 * @return
	 */
	ServiceResult changeArray(Long srcId, Long destId);

	/**
	 * @description:
	 * @createTime 2017年9月14日 上午10:16:44
	 * @author xw
	 * @param pageM
	 * @param orderId
	 * @return
	 */
	Page<AssessmentCompany> listAssessmentCompanyByOrder(Page<AssessmentCompany> pageM, Long orderId);
	
}
