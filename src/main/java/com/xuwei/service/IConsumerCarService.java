package com.xuwei.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.ConsumerCar;
import com.xuwei.util.ServiceResult;
 
/**
 * @description: 客户_车产服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月04日 09:45:08
 * @author: caw
 * @version: 1.0
 */
public interface IConsumerCarService extends IService<ConsumerCar> {
	
	/**
	 * 
	 * @description: 新增
	 * @createTime: 2017年9月5日 上午9:21:23
	 * @author: caw
	 * @param file
	 * @param request
	 * @param model
	 * @param m
	 * @return
	 */
	ServiceResult createConsumerCar(MultipartFile file, HttpServletRequest request, Model model, ConsumerCar m);
	
	/**
	 * 
	 * @description: 修改
	 * @createTime: 2017年9月5日 上午9:22:00
	 * @author: caw
	 * @param file
	 * @param request
	 * @param model
	 * @param m
	 * @return
	 */
	ServiceResult updateConsumerCar(MultipartFile file, HttpServletRequest request, Model model, ConsumerCar m);
	
	/**
	 * 
	 * @description: 删除车产信息（包括附件）
	 * @createTime: 2017年9月5日 上午9:15:43
	 * @author: caw
	 * @param idArray
	 * @return
	 */
	ServiceResult deleteConsumerCar(List<String> idArray);
	
	/**
	 * 
	 * @description: 获取车产信息（微信端）
	 * @createTime: 2017年9月14日 下午4:43:13
	 * @author: caw
	 * @param page
	 * @param userId
	 * @param totalNum
	 * @param customerid
	 * @return
	 */
	ServiceResult getConsumerCarList(Page<ConsumerCar> page, Long userId, Long totalNum, Long customerid);
	
	/**
	 * 
	 * @description: 新增车产信息（微信端）
	 * @createTime: 2017年9月14日 下午5:01:52
	 * @author: caw
	 * @param m
	 * @param userId
	 * @return
	 */
	ServiceResult addCar(ConsumerCar m, Long userId);
	
	/**
	 * 
	 * @description: 修改车产信息（微信端）
	 * @createTime: 2017年9月14日 下午5:05:20
	 * @author: caw
	 * @param m
	 * @param userId
	 * @return
	 */
	ServiceResult ModCar(ConsumerCar m, Long userId);
	
	/**
	 * 
	 * @description: 删除车产信息（微信端）
	 * @createTime: 2017年9月14日 下午5:06:49
	 * @author: caw
	 * @param carId
	 * @return
	 */
	ServiceResult delCar(Long carId);
}
