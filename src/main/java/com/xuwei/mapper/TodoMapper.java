package com.xuwei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.Customer;
import com.xuwei.bean.CustomerCare;
import com.xuwei.bean.CustomerComplaint;
import com.xuwei.bean.Todo;
import com.xuwei.bean.User;
import com.xuwei.util.ServiceResult;

/**
 * @description: 待办任务Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月07日 09:56:45
 * @author: zjy
 * @version: 1.0
 */
public interface TodoMapper extends BaseMapper<Todo> {
	/**
	 * @description: 获取客户信息
	 * @createTime: 2017年9月4日 上午10:19:39
	 * @author: zjy
	 * @param pageM
	 * @return
	 */
	List<Customer> getCustemerByList(Page<Customer> pageM,@Param("customerName")String customerName);
	/**
	 * 
	 * @description: 获取用户信息
	 * @createTime: 2017年9月4日 下午2:34:22
	 * @author: zjy
	 * @param pageM
	 * @return
	 */
	List<User> getOwnerByList(Page<User> pageM, @Param("userName")String userName);
	
	/**
	 * 
	 * @description: 获取列表
	 * @createTime: 2017年9月4日 上午10:19:53
	 * @author: zjy
	 * @param pageM
	 * @param m
	 * @return
	 */
	List<Todo> listTodoByPage(Page<Todo> pageM,@Param("m")Todo m,@Param("sysdate")String sysdate);
	/**
	 * 
	 * @description: 获取我创建的列表
	 * @createTime: 2017年9月4日 上午10:19:53
	 * @author: zjy
	 * @param pageM
	 * @param m
	 * @return
	 */
	List<Todo> listTodoCreateByPage(Page<Todo> pageM,@Param("m")Todo m);
	/**
	 * 
	 * @description: 获取我抄送的列表
	 * @createTime: 2017年9月4日 上午10:19:53
	 * @author: zjy
	 * @param pageM
	 * @param m
	 * @return
	 */
	List<Todo> listTodoCopyByPage(Page<Todo> pageM,@Param("m")Todo m,@Param("userId")Long userId);
	
	/**
	 * 
	 * @description: 获取今日执行列表
	 * @createTime: 2017年9月4日 上午10:19:53
	 * @author: zjy
	 * @param pageM
	 * @param m
	 * @return
	 */
	List<Todo> listTodotoTimeByPage(Page<Todo> pageM,@Param("m")Todo m,@Param("dateString")String dateString);
	/**
	 * 
	 * @description: 获取客户拥有人
	 * @createTime: 2017年9月4日 上午10:19:53
	 * @author: zjy
	 * @param pageM
	 * @param m
	 * @return
	 */
	User getownInfo(@Param("data")Long data);
	/**
	 * @description: 获取投诉信息
	 * @createTime: 2017年9月4日 上午10:19:39
	 * @author: zjy
	 * @param pageM
	 * @return
	 */
	List<CustomerComplaint> getComplaintByList(Page<CustomerComplaint> pageM,@Param("customerId")String customerId,@Param("complaintContentId")String complaintContentId);
	/**
	 * @description: 获取关怀信息
	 * @createTime: 2017年9月4日 上午10:19:39
	 * @author: zjy
	 * @param pageM
	 * @return
	 */
	List<CustomerCare> getCareByList(Page<CustomerCare> pageM,@Param("customerId")String customerId,@Param("customerCareContentId")String customerCareContentId);
	/**
	 * @description: 获取附件ID
	 * @createTime: 2017年9月26日 上午11:10:50
	 * @author: zjy
	 * @param todoId
	 * @return
	 */
	/**
	 * 
	 * @description: 根据id获取任务信息
	 * @createTime: 2017年9月25日 上午10:14:18
	 * @author: caw
	 * @param todoId
	 * @return
	 */
	Todo getTodoPageInfo( @Param("todoId")Long todoId);
	
	String getAttachId(@Param("todoId") Long todoId);
	/**
	 * @description: 删除附件
	 * @createTime: 2017年9月26日 上午11:11:02
	 * @author: zjy
	 * @return
	 */
	ServiceResult deleteAttach();
	/**
	 * @description: 查询不在列表的员工
	 * @createTime: 2017年9月26日 上午11:10:05
	 * @author: zjy
	 * @param user
	 * @param idArray
	 * @param pageM
	 * @return
	 */
	List<User> findNOTPageAll(@Param("m")User user, @Param("idArray")String[] idArray, Page<User> pageM);
	/**
	 * 
	 * @description: 查询已选员工
	 * @createTime: 2017年9月26日 上午11:10:08
	 * @author: zjy
	 * @param idArray
	 * @return
	 */
	List<User> findPageAll(@Param("idArray")String[] idArray);
}