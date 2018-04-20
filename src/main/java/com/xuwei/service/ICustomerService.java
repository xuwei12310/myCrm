package com.xuwei.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.Customer;
import com.xuwei.bean.User;
import com.xuwei.util.ServiceResult;
 
/**
 * @description: 客户服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年08月31日 10:49:27
 * @author: caw
 * @version: 1.0
 */
public interface ICustomerService extends IService<Customer> {
	/**
	 * 新增客户
	 * @createTime: 2017年8月31日 下午4:25:26
	 * @author: caw
	 * @param file
	 * @param request
	 * @param model
	 * @param m
	 * @return
	 */
	public ServiceResult createCustomer(MultipartFile file, HttpServletRequest request, Model model,Customer m);
	
	/**
	 * 获取图片
	 * @createTime: 2017年8月31日 下午4:25:26
	 * @author: caw
	 * @param attachId
	 * @param redirectAttributes
	 * @param request
	 * @param response
	 */
	public void getImg(Long attachId, RedirectAttributes redirectAttributes, HttpServletRequest request,
			HttpServletResponse response);
	/**
	 * 根据客户id获取照片的src
	 * @createTime: 2017年8月31日 下午4:25:26
	 * @author: caw
	 * @param model
	 * @param request
	 * @param customerid
	 * @return
	 */
	public ServiceResult imgSrc(Model model, HttpServletRequest request, Long customerid);
	
	/**
	 * 根据附件id获取照片的src
	 * @description: 
	 * @createTime: 2017年9月4日 下午5:07:09
	 * @author: caw
	 * @param model
	 * @param request
	 * @param attachid
	 * @return
	 */
	public ServiceResult imgSrcAttach(Model model, HttpServletRequest request, Long attachid);
	
	/**
	 * 根据id获取客户信息
	 * @createTime: 2017年9月1日 上午8:55:21
	 * @author: caw
	 * @param customerid
	 * @return
	 */
	Customer selectCustomerById(Long customerid);
	
	/**
	 * 获取客户信息
	 * @createTime: 2017年9月1日 上午8:55:21
	 * @author: caw
	 * @param pageM
	 * @param m
	 * @return
	 */
	List<Customer> listCustomerByPage(Page<Customer> pageM, Customer m, String todayTime, int viewType);
	
	/**
	 * @description: 获取用户信息
	 * @createTime 2017年8月31日 下午2:21:33
	 * @author caw
	 * @return
	 */
	List<User> getOwnerByList(Page<User> pageM, String userName);
	
	/**
	 * 
	 * @description: 修改
	 * @createTime: 2017年9月1日 上午10:46:05
	 * @author: caw
	 * @param file
	 * @param request
	 * @param model
	 * @param m
	 * @param attachId
	 * @return
	 */
	public ServiceResult updateCustomer(MultipartFile file, HttpServletRequest request, Model model,Customer m,Long attachId);
	
	/**
	 * 
	 * @description: 修改信用等级
	 * @createTime: 2017年9月5日 下午3:49:46
	 * @author: caw
	 * @param file
	 * @param request
	 * @param model
	 * @param m
	 * @return
	 */
	public ServiceResult modCustomerCredit(MultipartFile file, HttpServletRequest request, Model model,Customer m);
	
	/**
	 * 
	 * @description: 导出客户
	 * @createTime: 2017年9月6日 上午11:45:18
	 * @author: caw
	 * @param m
	 * @param titleExport
	 * @param fieldNamesExport
	 * @param fieldsExport
	 * @param response
	 */
	public void export(Customer m, String titleExport, String fieldNamesExport, String fieldsExport,
			HttpServletResponse response);
	
	/**
	 * 
	 * @description: 统计
	 * @createTime: 2017年9月7日 上午11:45:54
	 * @author: caw
	 * @return
	 */
	public ServiceResult countCustomer(int viewType, Customer m);
	
	/**
	 * 客户登陆认证（微信端）
	 * @description: 
	 * @createTime: 2017年9月8日 上午11:41:45
	 * @author: caw
	 * @param account
	 * @param password
	 * @param host
	 * @return
	 */
	public ServiceResult loginValidate(String account, String password, String host,String openid);
	
	/**
	 * 获取客户信息（微信端）
	 * @description: 
	 * @createTime: 2017年9月12日 上午8:31:43
	 * @author: caw
	 * @param searchValue
	 * @param page
	 * @param userId
	 * @param totalNum
	 * @param searchType
	 * @return
	 */
	public ServiceResult getCustomerList(String searchValue,Page<Customer> page, Long userId, Long totalNum, Long searchType, int viewType, Customer m);
	
	/**
	 * 新增客户（微信端）
	 * @description: 
	 * @createTime: 2017年9月12日 下午2:39:40
	 * @author: caw
	 * @param m
	 * @param userId
	 * @return
	 */
	public ServiceResult addCustomer(Customer m, Long userId);

	/**
	 * @description:跟进订单Id获取客户
	 * @createTime 2017年9月14日 上午10:51:46
	 * @author xw
	 * @param pageM
	 * @param orderId
	 * @return
	 */
	public Page<Customer> listCustomerByOrder(Page<Customer> pageM, Long orderId);
	
	/**
	 * 修改客户（微信端）
	 * @description: 
	 * @createTime: 2017年9月14日 下午2:09:16
	 * @author: caw
	 * @param m
	 * @param userId
	 * @return
	 */
	public ServiceResult modCustomer(Customer m, Long userId);
	
	/**
	 * 
	 * @description: 获取全部客户（微信端）
	 * @createTime: 2017年9月18日 上午11:46:17
	 * @author: caw
	 * @return
	 */
	public List<Customer> getAllCustomerByList();
}
