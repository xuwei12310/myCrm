package com.xuwei.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.CustomerProperty;
import com.xuwei.util.ServiceResult;
 
/**
 * @description: 客户_产权服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月04日 09:42:59
 * @author: caw
 * @version: 1.0
 */
public interface ICustomerPropertyService extends IService<CustomerProperty> {
	
	/**
	 * 分页查询客户产权
	 * @description: 
	 * @createTime: 2017年9月4日 上午11:18:26
	 * @author: caw
	 * @param pageM
	 * @return
	 */
	List<CustomerProperty> listPropertyByPage(Page<CustomerProperty> pageM, Long customerid);
	
	/**
	 * 
	 * @description: 新增
	 * @createTime: 2017年9月4日 下午5:17:10
	 * @author: caw
	 * @param file
	 * @param request
	 * @param model
	 * @param m
	 * @return
	 */
	ServiceResult createCustomerProperty(MultipartFile file, HttpServletRequest request, Model model,CustomerProperty m);
	
	/**
	 * 
	 * @description: 修改
	 * @createTime: 2017年9月4日 下午5:18:30
	 * @author: caw
	 * @param file
	 * @param request
	 * @param model
	 * @param m
	 * @return
	 */
	ServiceResult updateCustomerProperty(MultipartFile file, HttpServletRequest request, Model model,CustomerProperty m);
	
	/**
	 * 
	 * @description: 删除产权信息（包括附件）
	 * @createTime: 2017年9月4日 下午5:54:07
	 * @author: caw
	 * @param idArray
	 * @return
	 */
	ServiceResult deleteCustomerProperty(List<String> idArray);
	
	/**
	 * 
	 * @description: 获取产权信息（微信端）
	 * @createTime: 2017年9月13日 下午3:43:51
	 * @author: caw
	 * @param page
	 * @param userId
	 * @param totalNum
	 * @return
	 */
	ServiceResult getCustomerPropertyList(Page<CustomerProperty> page, Long userId, Long totalNum, Long customerid);
	
	/**
	 * 
	 * @description: 查看产权信息（微信端）
	 * @createTime: 2017年9月13日 下午5:19:23
	 * @author: caw
	 * @param id
	 * @return
	 */
	CustomerProperty getCustomerPropertyInfo(Long id);
	
	/**
	 * 
	 * @description: 添加产权信息（微信端）
	 * @createTime: 2017年9月14日 下午3:30:26
	 * @author: caw
	 * @param m
	 * @param userId
	 * @return
	 */
	ServiceResult addProperty(CustomerProperty m, Long userId);
	
	/**
	 * 
	 * @description: 修改产权信息（微信端）
	 * @createTime: 2017年9月14日 下午3:35:00
	 * @author: caw
	 * @param m
	 * @param userId
	 * @return
	 */
	ServiceResult modProperty(CustomerProperty m, Long userId);
	
	/**
	 * 
	 * @description: 删除产权信息（微信端）
	 * @createTime: 2017年9月14日 下午4:19:50
	 * @author: caw
	 * @param customerPropertyId
	 * @return
	 */
	ServiceResult delCustomerProperty(Long customerPropertyId);

	/**
	 * 分页查询产权
	 * @description:
	 * @createTime: 2017年10月18日 上午11:18:26
	 * @author: hhd
	 * @param pageM
	 * @return
	 */
	Page<CustomerProperty> selectPageByEw(Page<CustomerProperty> pageM, EntityWrapper ew);
}
