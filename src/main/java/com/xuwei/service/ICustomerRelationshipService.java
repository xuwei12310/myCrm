package com.xuwei.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.CustomerRelationship;
import com.xuwei.util.ServiceResult;
 
/**
 * @description: 客户_联系人服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月04日 09:41:34
 * @author: caw
 * @version: 1.0
 */
public interface ICustomerRelationshipService extends IService<CustomerRelationship> {
	
	/**
	 * 
	 * @description: 新增
	 * @createTime: 2017年9月5日 下午2:29:24
	 * @author: caw
	 * @param file
	 * @param request
	 * @param model
	 * @param m
	 * @return
	 */
	ServiceResult createCustomerRelationship(MultipartFile file, HttpServletRequest request, Model model, CustomerRelationship m);
	
	/**
	 * 
	 * @description: 修改
	 * @createTime: 2017年9月5日 下午2:29:48
	 * @author: caw
	 * @param file
	 * @param request
	 * @param model
	 * @param m
	 * @return
	 */
	ServiceResult updateCustomerRelationship(MultipartFile file, HttpServletRequest request, Model model, CustomerRelationship m);
	
	/**
	 * 
	 * @description: 删除联系人（包括附件）
	 * @createTime: 2017年9月5日 下午2:30:28
	 * @author: caw
	 * @param idArray
	 * @return
	 */
	ServiceResult deleteCustomerRelationship(List<String> idArray);
	
	/**
	 * @description: 获取联系人（微信端）
     * @createTime: 2017年9月14日 下午5:16:39
     * @author: caw  
	 * @param page
	 * @param userId
	 * @param totalNum
	 * @param customerid
	 * @return
	 */
	ServiceResult getRelationshiList(Page<CustomerRelationship> page, Long userId, Long totalNum, Long customerid);
	
	/**
	 * @description: 新增联系人（微信端）
     * @createTime: 2017年9月14日 下午5:16:39
     * @author: caw 
	 * @param m
	 * @param userId
	 * @return
	 */
	ServiceResult addRelationshi(CustomerRelationship m, Long userId);
	
	/**
	 * @description: 修改联系人（微信端）
     * @createTime: 2017年9月14日 下午5:16:39
     * @author: caw 
	 * @param m
	 * @param userId
	 * @return
	 */
	ServiceResult modRelationshi(CustomerRelationship m, Long userId);
	
	/**
	 * @description: 删除联系人（微信端）
     * @createTime: 2017年9月14日 下午5:16:39
     * @author: caw 
	 * @param relationshiId
	 * @return
	 */
	ServiceResult delRelationshi(Long relationshiId);
}
