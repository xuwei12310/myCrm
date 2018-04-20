package com.xuwei.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.Attach;
import com.xuwei.bean.Customer;
import com.xuwei.bean.CustomerCare;
import com.xuwei.bean.CustomerComplaint;
import com.xuwei.bean.Dict;
import com.xuwei.bean.Follow;
import com.xuwei.bean.FollowAttach;
import com.xuwei.bean.Todo;
import com.xuwei.bean.TodoAttach;
import com.xuwei.bean.TodoCopyto;
import com.xuwei.bean.User;
import com.xuwei.mapper.AttachMapper;
import com.xuwei.mapper.CustomerCareMapper;
import com.xuwei.mapper.CustomerComplaintMapper;
import com.xuwei.mapper.TodoAttachMapper;
import com.xuwei.mapper.TodoCopytoMapper;
import com.xuwei.mapper.TodoMapper;
import com.xuwei.service.IAttachService;
import com.xuwei.service.ICustomerService;
import com.xuwei.service.IFollowAttachService;
import com.xuwei.service.IFollowService;
import com.xuwei.service.IMessageService;
import com.xuwei.service.ITodoAttachService;
import com.xuwei.service.ITodoCopytoService;
import com.xuwei.service.ITodoService;
import com.xuwei.service.IUserService;
import com.xuwei.util.CommonUtil;
import com.xuwei.util.DateUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;

/**
 * @description: 待办任务服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月07日 09:56:45
 * @author: zjy
 * @version: 1.0
 */
@Service
public class TodoServiceImpl extends ServiceImpl<TodoMapper, Todo> implements ITodoService {
	@Resource
	private TodoMapper todoMapper;
	@Resource 
	private AttachMapper attachMapper;
	@Resource
	private TodoAttachMapper todoAttachMapper;
	@Resource
	private TodoCopytoMapper todoCopytoMapper;
	@Resource
	private  ITodoAttachService todoAttachService;
	@Resource
	private ICustomerService customerService;
	@Resource
	private IFollowAttachService followAttachService;
	@Resource
	private IFollowService followService;
	@Resource
	private ITodoCopytoService todoCopytoService;
	@Resource
	private IMessageService messageService;
	@Resource
	private IAttachService attachService;
	@Resource
	private IUserService userService;
	@Resource
	private CustomerComplaintMapper customerComplaintMapper;
	@Resource
	private CustomerCareMapper customerCareMapper;
	/**
	 * 
	 * @description: 获取客户信息
	 * @createTime: 2017年9月4日 上午10:16:45
	 * @author: zjy
	 * @param pageM
	 * @return
	 */
	@Override
	public List<Customer> getCustemerByList(Page<Customer> pageM,String customerName) {
		return todoMapper.getCustemerByList(pageM,customerName);
	}
	/**
	 * 
	 * @description: 获取用户信息
	 * @createTime: 2017年9月4日 上午10:16:45
	 * @author: zjy
	 * @param pageM
	 * @return
	 */
	@Override
	public List<User> getOwnerByList(Page<User> pageM, String userName) {
		return todoMapper.getOwnerByList(pageM, userName);
	}
	/**
	 * 
	 * @description: 获取列表
	 * @createTime: 2017年9月4日 上午10:16:45
	 * @author: zjy
	 * @param pageM
	 * @return
	 */
	@Override
	public List<Todo> listTodoByPage(Page<Todo> pageM, Todo m,String sysdate) {
		return todoMapper.listTodoByPage(pageM, m,sysdate);
	}
	/**
	 * 
	 * @description: 获取我创建的列表
	 * @createTime: 2017年9月4日 上午10:16:45
	 * @author: zjy
	 * @param pageM
	 * @return
	 */
	@Override
	public List<Todo> listTodoCreateByPage(Page<Todo> pageM, Todo m) {
		return todoMapper.listTodoCreateByPage(pageM, m);
	}
	/**
	 * 
	 * @description: 获取我创建的列表
	 * @createTime: 2017年9月4日 上午10:16:45
	 * @author: zjy
	 * @param pageM
	 * @return
	 */
	@Override
	public List<Todo> listTodoCopyByPage(Page<Todo> pageM, Todo m,Long userId) {
		return todoMapper.listTodoCopyByPage(pageM, m,userId);
	}
	
	@Override
	public User getownInfo(Long data) {
		return todoMapper.getownInfo(data);
	}
	@Override
	public List<CustomerComplaint> getComplaintByList(Page<CustomerComplaint> pageM, String customerId,String complaintContentId) {
		return todoMapper.getComplaintByList(pageM,customerId,complaintContentId);
	}
	@Override
	public List<CustomerCare> getCareByList(Page<CustomerCare> pageM, String customerId,String customerCareContentId) {
		return todoMapper.getCareByList(pageM,customerId,customerCareContentId);
	}
	@Override
	public List<Todo> listTodotoTimeByPage(Page<Todo> pageM, Todo m, String dateString) {
		return todoMapper.listTodotoTimeByPage(pageM, m,dateString);
	}
	@Override
	public String getAttachId(Long todoId) {
		return todoMapper.getAttachId(todoId);
	}
	@Transactional
	@Override
	public ServiceResult addTodo(Model model, Todo m, String attachIds, String receiveId) {
		ServiceResult result = new ServiceResult(false);
		m.setStatus(1);
		m.setCreateTime(DateUtil.getNowTimestampStr());
		m.setCreatorId(OperateUtils.getCurrentUserId());
		m.setLastModifyTime(DateUtil.getNowTimestampStr());
		m.setLastModifierId(OperateUtils.getCurrentUserId());
		insert(m);
		messageService.addTodoMessage(m.getId());
		TodoCopyto todoCopyto = new TodoCopyto();
		User user = new User();
		if (StringUtils.isNotEmpty(receiveId)) {
			String[] receive = receiveId.split(",");
			for (int i = 0; i < receive.length; i++) {
				user.setId(Long.valueOf(receive[i]));
				todoCopyto.setTodoId(m);
				todoCopyto.setCopytoUserId(user);
				todoCopyto.setCreateTime(DateUtil.getNowTimestampStr());
				todoCopyto.setCreatorId(OperateUtils.getCurrentUserId());
				todoCopyto.setLastModifyTime(DateUtil.getNowTimestampStr());
				todoCopyto.setLastModifierId(OperateUtils.getCurrentUserId());
				todoCopytoService.insert(todoCopyto);
			}
		}
		//保存附件
		if(!StringUtils.isEmpty(attachIds)){
			try {
				String[] attachIdArray = StringUtil.split(attachIds);
				if (attachIdArray.length > 0) {
					for (int i = 0; i < attachIdArray.length; i++) {
						TodoAttach todoAttach = new TodoAttach();
						Attach attach = attachService.selectById(Long.parseLong(attachIdArray[i]));
						if(attach == null){
							result.setMessage("无法找到附件信息,新增失败！");
						}
						Todo  todo=new Todo();
						todo.setId(m.getId());
						todoAttach.setTodoId(todo);
						todoAttach.setAttachId(attach);
						todoAttach.setCreateTime(DateUtil.getNowTimestampStr());
						todoAttach.setCreatorId(OperateUtils.getCurrentUserId());
						todoAttach.setLastModifyTime(DateUtil.getNowTimestampStr());
						todoAttach.setLastModifierId(OperateUtils.getCurrentUserId());
						todoAttachService.insert(todoAttach);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		result.setIsSuccess(true);
		return result;
	}
	@Transactional
	@Override
	public ServiceResult deleteAttach(String aId) {
		ServiceResult result=new ServiceResult();
		Long attachId=Long.valueOf(aId);
		Attach attach = attachMapper.selectById(attachId);
    	boolean deleteSuccess=deleteFile(CommonUtil.getAppProParam("project_files.base.path")+"/"+attach.getPath());
    	if(!deleteSuccess){
    		result.setIsSuccess(deleteSuccess);
    		result.setMessage("上传文件失败");
    		return result;
    	}
    	EntityWrapper<TodoAttach> wrapper=new EntityWrapper<TodoAttach>();
    	wrapper.eq("attach_id", attachId);
    	todoAttachMapper.delete(wrapper);
    	attachMapper.deleteById(attachId);
    	result.setIsSuccess(true);
		return result;
	}
	/**
	 * 
	 * @description:删除文件
	 * @createTime 2017年9月5日 上午11:39:53
	 * @author zjy
	 * @param fileName
	 * @return
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				System.out.println("删除单个文件" + fileName + "成功！");
				return true;
			} else {
				System.out.println("删除单个文件" + fileName + "失败！");
				return false;
			}
		} else {
			System.out.println("删除单个文件失败：" + fileName + "不存在！");
			return false;
		}
	}
	@Override
	public List<User> findNOTPageAll(User user, String[] stringArr, Page<User> pageM) {
		return todoMapper.findNOTPageAll(user,stringArr,pageM);
	}
	@Override
	public List<User> findPageAll(String[] stringArr) {
		return todoMapper.findPageAll(stringArr);
	}
	@Transactional
	@Override
	public ServiceResult completeTodo(Model model, Follow m, Todo t, String attachIds, String receiveTaskId,Boolean todoisActive) {
		ServiceResult result = new ServiceResult(false);
		m.setSource(2);
		Customer customer = customerService.selectCustomerById(m.getCustomer().getId());
		m.setFrontStatus(customer.getCustomerStatus());
		Dict customerStatus = new Dict();
		customerStatus.setId(m.getAfterStatus().getId());
		customer.setCustomerStatus(customerStatus);
		customerService.updateById(customer);
		m.setCreateTime(DateUtil.getNowTimestampStr());
		m.setCreatorId(OperateUtils.getCurrentUserId());
		m.setLastModifyTime(DateUtil.getNowTimestampStr());
		m.setLastModifierId(OperateUtils.getCurrentUserId());
	    followService.insert(m);
		// 更新完成状态
		Todo oldM = selectById(t.getTodoComplectId());
		oldM.setStatus(2);
		oldM.setFollowId(m);
		updateById(oldM);
		if (todoisActive) {
			// 创建下次任务
			t.setCustomerId(m.getCustomer());
			t.setTaskType(1);
			t.setStatus(1);
			t.setCreateTime(DateUtil.getNowTimestampStr());
			t.setCreatorId(OperateUtils.getCurrentUserId());
			t.setLastModifyTime(DateUtil.getNowTimestampStr());
			t.setLastModifierId(OperateUtils.getCurrentUserId());
			insert(t);
			messageService.addTodoMessage(t.getId());
			TodoCopyto todoCopyto = new TodoCopyto();
			User user = new User();
			if (StringUtils.isNotEmpty(receiveTaskId)) {
				String[] receive = receiveTaskId.split(",");
				for (int i = 0; i < receive.length; i++) {
					user.setId(Long.valueOf(receive[i]));
					todoCopyto.setTodoId(t);
					todoCopyto.setCopytoUserId(user);
					todoCopyto.setCreateTime(DateUtil.getNowTimestampStr());
					todoCopyto.setCreatorId(OperateUtils.getCurrentUserId());
					todoCopyto.setLastModifyTime(DateUtil.getNowTimestampStr());
					todoCopyto.setLastModifierId(OperateUtils.getCurrentUserId());
					todoCopytoService.insert(todoCopyto);
				}
			}
		}
		//保存跟进附件
		if(!StringUtils.isEmpty(attachIds)){
			try {
				String[] attachIdArray = StringUtil.split(attachIds);
				if (attachIdArray.length > 0) {
					for (int i = 0; i < attachIdArray.length; i++) {
						FollowAttach followAttach = new FollowAttach();
						Attach attach = attachService.selectById(Long.parseLong(attachIdArray[i]));
						if(attach == null){
							result.setMessage("无法找到附件信息,新增失败！");
						}
						followAttach.setFollow(m);
						followAttach.setAttach(attach);
						followAttach.setCreateTime(DateUtil.getNowTimestampStr());
						followAttach.setCreatorId(OperateUtils.getCurrentUserId());
						followAttach.setLastModifyTime(DateUtil.getNowTimestampStr());
						followAttach.setLastModifierId(OperateUtils.getCurrentUserId());
						followAttachService.insert(followAttach);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.setMessage("上传失败");
			}
		}
		return result;
	}
	@Transactional
	@Override
	public ServiceResult updateTodo(Model model, Todo m, String attachIds, String receiveId) {
		ServiceResult result = new ServiceResult(false);
		           // 新的id查找出对应旧的对象
					Todo oldM = selectById(m.getId());
					oldM.setTaskType(m.getTaskType());
					oldM.setCustomerId(m.getCustomerId());
					oldM.setDoTime(m.getDoTime());
					oldM.setTaskContent(m.getTaskContent());
					oldM.setDoUserId(m.getDoUserId());
					oldM.setRemind(m.getRemind());
					oldM.setRemindUnit(m.getRemindUnit());
					oldM.setCustomerComplainId(m.getCustomerComplainId());
					oldM.setCustomerCareId(m.getCustomerCareId());
					oldM.setLastModifyTime(DateUtil.getNowTimestampStr());
					oldM.setLastModifierId(OperateUtils.getCurrentUserId());
					if (StringUtils.isNotBlank(receiveId)) {
						String[] receiveArr = receiveId.split(",");
						List<String> receivelist = new ArrayList<String>();
						List<String> receiveDelList = new ArrayList<String>();
						for (int j = 0; j < receiveArr.length; j++) {
							receivelist.add(receiveArr[j]);
							receiveDelList.add(receiveArr[j]);
						}
						EntityWrapper<TodoCopyto> ew = new EntityWrapper<TodoCopyto>();
						ew.eq("todo_id", m.getId());
						List<TodoCopyto> list = todoCopytoService.selectList(ew);
		                
						//将需要新增的数放入list
						for (TodoCopyto tc : list) {
							for (int i = 0; i < receivelist.size(); i++) {
								if (tc.getCopytoUserId().getId().equals(Long.parseLong(receivelist.get(i)))) {
									receivelist.remove(i);
									i--;
								}
							}
						}
		                //将需要删除的数放入receiveDelList
						for (String ra : receiveDelList) {
							for (int i = 0; i < list.size(); i++) {
								if (list.get(i).getCopytoUserId().getId().equals(Long.parseLong(ra))) {
									list.remove(i);
									i--;
								}
							}
						}

						for (String ra : receivelist) {
							TodoCopyto todoCopyto = new TodoCopyto();
							todoCopyto.setTodoId(oldM);
							todoCopyto.setCopytoUserId(userService.selectById(ra));
							todoCopyto.setCreatorId(OperateUtils.getCurrentUserId());
							todoCopyto.setCreateTime(DateUtil.getNowTimestampStr());
							todoCopyto.setLastModifyTime(DateUtil.getNowTimestampStr());
							todoCopyto.setLastModifierId(OperateUtils.getCurrentUserId());
							todoCopytoService.insert(todoCopyto);
						}

						for (TodoCopyto tc : list) {
							todoCopytoService.deleteById(tc.getId());
						}
					} else {
						// 删除抄送人
						todoCopytoService.batchDeleteTodo(m.getId());
					}
					//修改附件
					String[] attachIdArray = StringUtil.split(attachIds);
					List<String> attachADDlist = new ArrayList<String>();
					Long todoId=m.getId();
//					try {
						if(!StringUtils.isEmpty(attachIds)){
							for(int i=0;i<attachIdArray.length;i++){
								attachADDlist.add(attachIdArray[i]);
							}
							EntityWrapper<TodoAttach> ew = new EntityWrapper<TodoAttach>();
							ew.eq("todo_id", todoId);
							List<TodoAttach> list = todoAttachMapper.selectList(ew);
							if(list.size()>0){
								for(int i=0;i<attachADDlist.size();i++){
									for(TodoAttach fa:list){
										TodoAttach todoAttach = todoAttachMapper.selectById(fa.getId());
										if(attachADDlist.get(i).equals(String.valueOf(todoAttach.getAttachId().getId()))){
											attachADDlist.remove(i);
										}
									}
								}
								if(attachADDlist.size()>0){
									for (int i = 0; i < attachADDlist.size(); i++) {
										TodoAttach todoAttach = new TodoAttach();
										Attach attach = attachMapper.selectById(Long.parseLong(attachADDlist.get(i)));
										todoAttach.setTodoId(todoMapper.selectById(todoId));
										todoAttach.setAttachId(attach);
										todoAttach.setCreateTime(DateUtil.getNowTimestampStr());
										todoAttach.setCreatorId(OperateUtils.getCurrentUserId());
										todoAttach.setLastModifyTime(DateUtil.getNowTimestampStr());
										todoAttach.setLastModifierId(OperateUtils.getCurrentUserId());
										todoAttachMapper.insert(todoAttach);
									}
								}
							}else{
								for(int i=0;i<attachIdArray.length;i++){
									TodoAttach todoAttach = new TodoAttach();
									Attach attach = attachMapper.selectById(Long.parseLong(attachIdArray[i]));
									todoAttach.setTodoId(todoMapper.selectById(todoId));
									todoAttach.setAttachId(attach);
									todoAttach.setCreateTime(DateUtil.getNowTimestampStr());
									todoAttach.setCreatorId(OperateUtils.getCurrentUserId());
									todoAttach.setLastModifyTime(DateUtil.getNowTimestampStr());
									todoAttach.setLastModifierId(OperateUtils.getCurrentUserId());
									todoAttachMapper.insert(todoAttach);
								}
							}
						}else{
							EntityWrapper<TodoAttach> ew = new EntityWrapper<TodoAttach>();
							ew.eq("todo_id", todoId);
							List<TodoAttach> list = todoAttachMapper.selectList(ew);
							for(TodoAttach fa:list){
								todoAttachMapper.deleteById(fa.getId());
								attachMapper.deleteById(fa.getAttachId().getId());
							}
						}
					boolean isSuccess = updateAllColumnById(oldM);
					result.setIsSuccess(isSuccess);
		      return result;
	}
	@Transactional
	@Override
	public ServiceResult updateOtherComplect(Todo m) {
        ServiceResult result = new ServiceResult(false);
		if(m.getCustomerComplainId()!=null && m.getCustomerComplainId().getId()!=null){
			CustomerComplaint complaintOldM=customerComplaintMapper.selectById(m.getCustomerComplainId().getId());
			complaintOldM.setStatus("2");
			customerComplaintMapper.updateById(complaintOldM);
		}
		if(m.getCustomerCareId()!=null && m.getCustomerCareId().getId()!=null){
			CustomerCare customerCareOldM=customerCareMapper.selectById(m.getCustomerCareId().getId());
			customerCareOldM.setStatus("2");
			customerCareMapper.updateById(customerCareOldM);
		}
		Todo oldM = selectById(m.getId());
		oldM.setReason(m.getReason());
		oldM.setStatus(2);
		boolean isSuccess = updateById(oldM);
		 result.setIsSuccess(isSuccess);
	 	return result;
	}
	
}
