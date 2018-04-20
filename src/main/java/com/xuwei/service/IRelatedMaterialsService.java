package com.xuwei.service;

import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.RelatedMaterials;
import com.xuwei.util.ServiceResult;

/**
 * <p>
 * 评估公司 服务类
 * </p>
 *
 * @author ${author}
 * @since 2017-08-31
 */
public interface IRelatedMaterialsService extends IService<RelatedMaterials> {

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
	
}
