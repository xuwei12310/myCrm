package com.xuwei.service;

import java.util.List;

import org.springframework.ui.Model;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.Customer;
import com.xuwei.bean.CustomerCare;
import com.xuwei.bean.CustomerComplaint;
import com.xuwei.bean.Follow;
import com.xuwei.bean.Todo;
import com.xuwei.bean.User;
import com.xuwei.util.ServiceResult;
 
/**
 * @description: 待办任务服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月07日 09:56:45
 * @author: zjy
 * @version: 1.0
 */
public interface ITodoService extends IService<Todo> {
	/**
	 * 
	 * @description: 获取客户信息
	 * @createTime: 2017年9月4日 上午10:14:53
	 * @author: zjy
	 * @param pageM
	 * @return
	 */
	List<Customer> getCustemerByList(Page<Customer> pageM,String customerName);
	/**
	 * 
	 * @description: 获取用户信息
	 * @createTime: 2017年9月4日 下午2:30:50
	 * @author: zjy
	 * @param pageM
	 * @return
	 */
	List<User> getOwnerByList(Page<User> pageM, String userName);
	/**
	 * 
	 * @description:  查询任务列表
	 * @createTime: 2017年9月3日 下午2:34:59
	 * @author: zjy
	 * @param pageM
	 * @param m
	 * @return
	 */
	List<Todo> listTodoByPage(Page<Todo> pageM, Todo m,String sysdate);
	/**
	 * 
	 * @description:  查询我创建的任务列表
	 * @createTime: 2017年9月3日 下午2:34:59
	 * @author: zjy
	 * @param pageM
	 * @param m
	 * @return
	 */
	List<Todo> listTodoCreateByPage(Page<Todo> pageM, Todo m);
	/**
	 * 
	 * @description:  查询我抄送的任务列表
	 * @createTime: 2017年9月3日 下午2:34:59
	 * @author: zjy
	 * @param pageM
	 * @param m
	 * @return
	 */
	List<Todo> listTodoCopyByPage(Page<Todo> pageM, Todo m,Long userId);
	
	/**
	 * 
	 * @description:  查询今日执行列表
	 * @createTime: 2017年9月3日 下午2:34:59
	 * @author: zjy
	 * @param pageM
	 * @param m
	 * @return
	 */
	List<Todo> listTodotoTimeByPage(Page<Todo> pageM, Todo m,String dateString);
	/**
	 * 
	 * @description:  当前客户拥有人
	 * @createTime: 2017年9月3日 下午2:34:59
	 * @author: zjy
	 * @param pageM
	 * @param m
	 * @return
	 */
	User getownInfo(Long data);
	/**
	 * 
	 * @description: 查询不在列表中的抄送人
	 * @createTime: 2017年9月17日 上午12:00:11
	 * @author: zjy
	 * @param data
	 * @return
	 */
	List<User> findNOTPageAll(User user,String [] stringArr,Page<User> pageM);
	
	/**
	 * @description: 查询已选抄送人
	 * @createTime: 2017年9月22日 下午2:45:30
	 * @author: zjy
	 * @param stringArr
	 * @return
	 */
	List<User> findPageAll(String [] stringArr);
	
	/**
	 * 
	 * @description: 获取投诉信息
	 * @createTime: 2017年9月4日 上午10:14:53
	 * @author: zjy
	 * @param pageM
	 * @return
	 */
	List<CustomerComplaint> getComplaintByList(Page<CustomerComplaint> pageM,String customerId,String complaintContentId);
	/**
	 * 
	 * @description: 获取关怀信息
	 * @createTime: 2017年9月4日 上午10:14:53
	 * @author: zjy
	 * @param pageM
	 * @return
	 */
	List<CustomerCare> getCareByList(Page<CustomerCare> pageM,String customerId,String customerCareContentId);
	/**
	 * 
	 * @description: 任务Id查询任务附件表对象
	 * @createTime: 2017年9月13日 下午2:51:26
	 * @author: zjy
	 * @param followId
	 * @return
	 */
	String getAttachId(Long todoId);
	/**
	 * 
	 * @description: 删除待办附件
	 * @createTime: 2017年9月13日 下午4:15:08
	 * @author: zjy
	 * @param aId
	 * @return
	 */
	ServiceResult deleteAttach(String aId);
	/**
	 * @description: 完成待办
	 * @createTime: 2017年10月15日 下午9:47:43
	 * @author: zjy
	 * @param model
	 * @param m
	 * @param t
	 * @param attachIds
	 * @param receiveTaskId
	 * @return
	 */
	ServiceResult completeTodo(Model model, Follow m,Todo t,String attachIds,String receiveTaskId,Boolean todoisActive);
	/**
	 * @description: 新增待办
	 * @createTime: 2017年10月15日 下午9:48:36
	 * @author: zjy
	 * @return
	 */
	ServiceResult addTodo(Model model,Todo m,String attachIds,String receiveId);
	/**
	 * @description: 修改待办
	 * @createTime: 2017年10月15日 下午9:48:36
	 * @author: zjy
	 * @return
	 */
	ServiceResult updateTodo(Model model,Todo m,String attachIds,String receiveId);
	
	ServiceResult updateOtherComplect(Todo m);
}
