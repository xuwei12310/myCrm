package com.xuwei.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.AssessmentCompany;
import com.xuwei.mapper.AssessmentCompanyMapper;
import com.xuwei.service.IAssessmentCompanyService;
import com.xuwei.util.ServiceResult;

/**
 * <p>
 * 评估公司 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2017-08-31
 */
@Service
public class AssessmentCompanyServiceImpl extends ServiceImpl<AssessmentCompanyMapper, AssessmentCompany> implements IAssessmentCompanyService {
	
	@Resource
	private AssessmentCompanyMapper assessmentCompanyDao;
	
	 /**
	    * 
	    * @description: 上移下移
	    * @createTime: 2017年8月30日 上午14:36:19
	    * @author: xw
	    * @param srcId
	    * @param destId
	    */
		@Override
		public ServiceResult changeArray(Long srcId, Long destId) {
			ServiceResult result = new ServiceResult(false);
			if(srcId==null){
				result.setMessage("请选中移动记录");
				return result;
			}
			if(destId==null){
				result.setMessage("请指定对换记录");
				return result;
			}
			assessmentCompanyDao.changeArray(srcId,destId);
			result.setIsSuccess(true);
			return result;
		}
	/**
	 * 
	 * @description: 取下个排序值
	 * @createTime:2017年8月30日 上午14:36:19
	 * @author: xw
	 * @param dictType
	 * @return
	 */
	@Override
	public Integer nextArray() {
		return assessmentCompanyDao.nextArray();
	}
	@Override
	public Page<AssessmentCompany> listAssessmentCompanyByOrder(Page<AssessmentCompany> pageM, Long orderId) {
		List<AssessmentCompany> list=assessmentCompanyDao.listAssessmentCompanyByOrder(pageM,orderId);
		pageM.setRecords(list);
		return pageM;
	}
	
}
