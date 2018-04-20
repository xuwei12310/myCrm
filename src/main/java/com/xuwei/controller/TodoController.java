
package com.xuwei.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.Attach;
import com.xuwei.bean.Customer;
import com.xuwei.bean.CustomerCare;
import com.xuwei.bean.CustomerComplaint;
import com.xuwei.bean.Follow;
import com.xuwei.bean.Todo;
import com.xuwei.bean.TodoAttach;
import com.xuwei.bean.TodoComment;
import com.xuwei.bean.TodoCopyto;
import com.xuwei.bean.User;
import com.xuwei.service.IAttachService;
import com.xuwei.service.ICustomerCareService;
import com.xuwei.service.ICustomerComplaintService;
import com.xuwei.service.ICustomerService;
import com.xuwei.service.IFollowAttachService;
import com.xuwei.service.IFollowService;
import com.xuwei.service.IMessageService;
import com.xuwei.service.ITodoAttachService;
import com.xuwei.service.ITodoCommentService;
import com.xuwei.service.ITodoCopytoService;
import com.xuwei.service.ITodoService;
import com.xuwei.service.IUserService;
import com.xuwei.util.CommonUtil;
import com.xuwei.util.DateUtil;
import com.xuwei.util.FileUtil;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;

/**
 * @description: 待办任务控制器
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月07日 09:56:45
 * @author: zjy
 * @version: 1.0
 */
@Controller
@RequestMapping("/myWorkbench/todo")
public class TodoController extends BaseController<Todo> {
	@Resource
	private ITodoService todoService;
	@Resource
	private IUserService userService;
	@Resource
	private IAttachService attachService;
	@Resource
	private ITodoAttachService todoAttachService;
	@Resource
	private ITodoCopytoService todoCopytoService;
	@Resource
	private ITodoCommentService todoCommentService;
	@Resource
	private ICustomerService customerService;
	@Resource
	private ICustomerComplaintService customerComplaintService;
	@Resource
	private ICustomerCareService customercareService;
	@Resource
	private IFollowService followService;
	@Resource
	private IFollowAttachService followAttachService;
	@Resource
	private IMessageService messageService;

	public TodoController() {
		setResourceIdentity("myWorkbench:todo");
	}

	/**
	 * @description: 转向模块主界面
	 * @createTime: 2017年09月07日 09:56:45
	 * @author: zjy
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String main(Model model) {
		return defaultViewPrefix();
	}

	/**
	 * @description: 添加
	 * @createTime: 2017年09月07日 09:56:45
	 * @author: zjy
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public Object create(Model model, Todo m,String attachIds,String receiveId) {
		ServiceResult result = new ServiceResult(false);
		if (!hasCreatePermission()) {
			result.setMessage("没有添加权限");
		} else {
			if (StringUtils.isBlank(m.getDoTime())) {
				result.setMessage("执行时间不能为空");
				return result;
			}
			if (StringUtils.isBlank(m.getTaskContent())) {
				result.setMessage("任务内容不能为空");
				return result;
			}
			 result =todoService.addTodo(model,m,attachIds,receiveId);
			 result.setIsSuccess(true);
		}
		 String jsonString = result.toJSON();
			return jsonString;
	}

	/**
	 * 
	 * @description:完成
	 * @createTime 2017年8月30日 上午10:37:21
	 * @author zjy
	 * @param model
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "complete", method = RequestMethod.POST)
	@ResponseBody
	public Object complete(Model model, Follow m,
			Todo t,String attachIds,String receiveTaskId,Boolean todoisActive) {
		  ServiceResult result = new ServiceResult(false);
		  result =todoService.completeTodo(model,m,t,attachIds,receiveTaskId,todoisActive);
		  result.setIsSuccess(true);
		  result.addData("id", m.getId());
		  String jsonString = result.toJSON();
		  return jsonString;
	}
	/**
	 * @description: 修改
	 * @createTime: 2017年09月07日 09:56:45
	 * @author: zjy
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public Object update(Model model, Todo m,String attachIds,
			String receiveId) {
		ServiceResult result = new ServiceResult(false);
		if (!hasPermissionByOpt("modifCreate")) {
			result.setMessage("没有修改权限");
		} else {
			result=todoService.updateTodo(model,m,attachIds,receiveId);
			}
		String jsonString = result.toJSON();
	    return jsonString;
	}
	/**
	 *
	 * @description: 待办附件上传
	 * @createTime: 2017年10月9日 上午11:49:43
	 * @author: zjy
	 * @param request
	 * @param response
	 * @param file
	 * @param chunks
	 * @param tempFileName
	 * @param chunk
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="uploadFile", method=RequestMethod.POST)
    @ResponseBody
    public Object uploadFile(HttpServletRequest request, HttpServletResponse response,
 		   MultipartFile file,Integer chunks,@RequestParam("name")String tempFileName,Integer chunk) throws Exception{
		JSONObject jsonObject = new JSONObject();
		String newFileName = null; /* 最后合并后的新文件名 */
 		String fileName = null;
 		BufferedOutputStream outputStream = null;
 		String uploadPath1 = CommonUtil.getAppProParam("project_files.base.path")+CommonUtil.getAppProParam("project_files.attach.path");
 		File uploadFile1 = new File(uploadPath1);
 		if(!uploadFile1.exists()){
 			uploadFile1.mkdirs();
 		}
 		String uploadPath = CommonUtil.getAppProParam("project_files.base.path")+CommonUtil.getAppProParam("project_files.attach.path")+"todo/";
 		File uploadFile = new File(uploadPath);
 		if(!uploadFile.exists()){
 			uploadFile.mkdirs();
 		}
 		DiskFileItemFactory factory = new DiskFileItemFactory();
 		factory.setSizeThreshold(1024);
 		ServletFileUpload upload = new ServletFileUpload(factory);
 		upload.setHeaderEncoding("UTF-8");
 		fileName = file.getOriginalFilename();
 		if (tempFileName != null) {
 			String chunkName = tempFileName;
 			if (chunk != null) {
 				chunkName = chunk + "_" + tempFileName;
 			}
 			File savedFile = new File(uploadPath, chunkName);
 			FileUtil.saveFileNew(file.getInputStream(), savedFile);
 		}
 		newFileName = CommonUtil.getUUID().toString().replace("-", "").concat(".")
 				.concat(FilenameUtils.getExtension(tempFileName));
 		if ((chunk != null && chunk + 1 == chunks)||chunks==0) {
 			File attachFile = new File(uploadPath, newFileName);
 			outputStream = new BufferedOutputStream(new FileOutputStream(attachFile));
 			/* 遍历文件合并 */
 			for (int i = 0; i < chunks; i++) {
 				File tempFile = new File(uploadPath, i + "_" + tempFileName);
 				byte[] bytes = FileUtils.readFileToByteArray(tempFile);
 				outputStream.write(bytes);
 				outputStream.flush();
 				tempFile.delete();
 			}
 			outputStream.flush();
 			//保存到附件表
 			Attach attach = new Attach();
 			attach.setPath(CommonUtil.getAppProParam("project_files.attach.path")+"todo/"+newFileName);
 			attach.setExtention(FilenameUtils.getExtension(newFileName));
 			attach.setName(fileName);
 			boolean flag =  attachService.insert(attach);
 			if(flag){
 				 jsonObject.put("attachId", attach.getId());
 				 jsonObject.put("name", fileName);
 				 jsonObject.put("attachPath", CommonUtil.getAppProParam("project_files.attach.path")+"todo/"+newFileName);
 				 jsonObject.put("extention", FilenameUtils.getExtension(newFileName));
 				 //生成swf
// 				 attachService.createSWF(attach.getPath(), fileName);
 			}
 		}
 		try {
 			if (outputStream != null)
 				outputStream.close();
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
		jsonObject.put("status", true);
	 	return jsonObject;
	}
	/**
	 * @description: 修改评论
	 * @createTime: 2017年09月07日 09:56:45
	 * @author: zjy
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "updateComment", method = RequestMethod.POST)
	@ResponseBody
	public Object updateComment(Model model, TodoComment m) {
		ServiceResult result = new ServiceResult(false);
		TodoComment oldM = todoCommentService.selectById(m.getId());
		User user = userService.userInfo();
		if (user.getId() != oldM.getCommentUserId().getId()) {
			result.setMessage("只能修改自己的评论");
		} else {
			oldM.setComment(m.getComment());
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date now = Calendar.getInstance().getTime();
			oldM.setCommentTime(df.format(now));
			oldM.setLastModifyTime(DateUtil.getNowTimestampStr());
			oldM.setLastModifierId(OperateUtils.getCurrentUserId());
			boolean isSuccess = todoCommentService.updateAllColumnById(oldM);
			result.setIsSuccess(isSuccess);
		}

		String jsonString = result.toJSON();
		return jsonString;
	}

	/**
	 * @description: 删除
	 * @createTime: 2017年09月07日 09:56:45
	 * @author: zjy
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "mulDelete", method = RequestMethod.POST)
	@ResponseBody
	public Object mulDelete(String ids) {
		ServiceResult result = new ServiceResult(false);
		if (!hasPermissionByOpt("delCreate")) {
			result.setMessage("没有删除权限");
		} else {
			try {
				String[] idArray = StringUtil.split(ids);
				if (idArray == null || idArray.length == 0) {
					result.setMessage("请选择要删除的数据行");
					return result;
				}
				boolean isSuccess = todoService.deleteBatchIds(Arrays.asList(idArray));
				result.setIsSuccess(isSuccess);
			} catch (Exception e) {
				if (e instanceof org.springframework.dao.DataIntegrityViolationException) {
					result.setMessage("数据已被引用不能删除");
				} else {
					result.setMessage("删除出错：" + e.getMessage());
				}
			}
		}
		String jsonString = result.toJSON();
		return jsonString;
	}

	/**
	 * @description: 删除评论
	 * @createTime: 2017年09月07日 09:56:45
	 * @author: zjy
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "mulDeleteComment", method = RequestMethod.POST)
	@ResponseBody
	public Object muiDeleteComment(Long commentId) {
		ServiceResult result = new ServiceResult(false);
		try {
			TodoComment oldM = todoCommentService.selectById(commentId);
			User user = userService.userInfo();
			if (user.getId() != oldM.getCommentUserId().getId()) {
				result.setMessage("只能删除自己的评论");
			} else {
				boolean isSuccess = todoCommentService.deleteBatchIds(Arrays.asList(commentId));
				result.setIsSuccess(isSuccess);
			}

		} catch (Exception e) {
			if (e instanceof org.springframework.dao.DataIntegrityViolationException) {
				result.setMessage("数据已被引用不能删除");
			} else {
				result.setMessage("删除出错：" + e.getMessage());
			}
		}
		String jsonString = result.toJSON();
		return jsonString;
	}

	/**
	 * 
	 * @description: 获取当前客户拥有人
	 * @createTime: 2017年9月1日 下午3:40:42
	 * @author: zjy
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getownInfo")
	public Object getownInfo(Model model, Long data) {
		User list = todoService.getownInfo(data);
		String[] properties = { "id", "username", "name" };
		String jsonString = JSONUtil.toJson(list, properties);
		return jsonString;
	}

	/**
	 * 
	 * @description: 获取客户信息
	 * @createTime: 2017年9月4日 上午9:58:41
	 * @author: zjy
	 * @param rows
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "getCustomerByList", method = RequestMethod.POST)
	@ResponseBody
	public Object getCustomerByList(int rows, int page, String customerName) {
		Page<Customer> pageM = new Page<>(page, rows);
		String[] properties = { "id", "customerName" };
		List<Customer> list = todoService.getCustemerByList(pageM, customerName);
		return JSONUtil.toJson(list, properties, (long) pageM.getTotal());
	}
	/**
	 * 
	 * @description: 获取投诉信息
	 * @createTime: 2017年9月4日 上午9:58:41
	 * @author: zjy
	 * @param rows
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "getComplaintByList", method = RequestMethod.POST)
	@ResponseBody
	public Object getComplaintByList(int rows, int page, String customerId,String complaintContentId) {
		Page<CustomerComplaint> pageM = new Page<>(page, rows);
		String[] properties = { "id", "complaintContent", "complaintTime" };
		List<CustomerComplaint> list = todoService.getComplaintByList(pageM, customerId,complaintContentId);
		return JSONUtil.toJson(list, properties, (long) pageM.getTotal());
	}

	/**
	 * 
	 * @description:获取当前用户的信息
	 * @createTime: 2017年9月12日 上午11:48:24
	 * @author: zjy
	 * @param model
	 * @param data
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "userInfo")
	public Object userInfo(Model model, Long data) {
		User user = userService.userInfo();
		String[] properties = { "id", "username", "name" };
		String jsonString = JSONUtil.toJson(user, properties);
		return jsonString;
	}

	/**
	 * 
	 * @description: 关联关怀信息
	 * @createTime: 2017年9月4日 上午9:58:41
	 * @author: zjy
	 * @param rows
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "getCareByList", method = RequestMethod.POST)
	@ResponseBody
	public Object getCareByList(int rows, int page, String customerId,String customerCareContentId) {
		Page<CustomerCare> pageM = new Page<>(page, rows);
		String[] properties = { "id", "careContent" };
		List<CustomerCare> list = todoService.getCareByList(pageM, customerId,customerCareContentId);
		return JSONUtil.toJson(list, properties, (long) pageM.getTotal());
	}

	/**
	 * 
	 * @description: 获取拥有人信息
	 * @createTime: 2017年9月4日 上午9:58:41
	 * @author: zjy
	 * @param rows
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "getOwnerByList", method = RequestMethod.POST)
	@ResponseBody
	public Object getOwnerByList(int rows, int page, String userName) {
		Page<User> pageM = new Page<>(page, rows);
		String[] properties = { "id", "username", "name" };
		List<User> list = todoService.getOwnerByList(pageM, userName);
		return JSONUtil.toJson(list, properties, (long) pageM.getTotal());
	}

	/**
	 * @description: 获取抄送人名称
	 * @createTime: 2017年9月17日 上午12:01:20
	 * @author: zjy
	 * @param model
	 * @param data
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getReceiverName")
	public Object getReceiverName(Model model, Long data) {
		TodoCopyto list = todoCopytoService.getReceiverName(data);
		String[] properties = { "copyId", "copyName" };
		String jsonString = JSONUtil.toJson(list, properties);
		return jsonString;
	}

	/**
	 * @description: 查询抄送人
	 * @createTime: 2017年9月22日 下午2:44:20
	 * @author: zjy
	 * @param rows
	 * @param page
	 * @param personId
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "listPersonByPage", method = RequestMethod.POST)
	@ResponseBody
	public Object listPersonByPage(int rows, int page, String personId, User user) {
		Page<User> pageM = new Page<>(page, rows);
		String[] stringArr = null;
		if (personId != null) {
			stringArr = personId.split(",");
		}
		String[] properties = { "id", "name" };
		List<User> list = todoService.findNOTPageAll(user, stringArr, pageM);
		String jsonString = JSONUtil.toJson(list, properties, (long) pageM.getTotal());
		return jsonString;
	}

	/**
	 * @description: 查询已选抄送人
	 * @createTime: 2017年9月22日 下午2:44:29
	 * @author: zjy
	 * @param personId
	 * @return
	 */
	@RequestMapping(value = "listDelPersonByPage", method = RequestMethod.POST)
	@ResponseBody
	public Object listDelPersonByPage(String personId) {
		String[] stringArr = null;
		if (personId != null) {
			stringArr = personId.split(",");
		}
		String[] properties = { "id", "name" };
		List<User> list = todoService.findPageAll(stringArr);
		return JSONUtil.toJson(list, properties);
	}

	/**
	 * @description: 分页查询
	 * @createTime: 2017年09月07日 09:56:45
	 * @author: zjy
	 * @param m
	 * @param rows
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "listTodoByPage")
	public Object listByPage(Todo m, int rows, int page) {
		if (!hasViewPermission()) {
			return JSONUtil.EMPTYJSON;
		}
		Page<Todo> pageM = new Page<>(page, rows);
		User user = userService.userInfo();
		m.setDoUserId(user);
		String sysdate = DateUtil.getNowTimesminutStr();
		if (m.getDoUserTime() != null) {
			Date date = new Date();// 取时间
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, m.getDoUserTime());// 把日期往后增加一天.整数往后推,负数往前移动
			date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String querydate = formatter.format(date);
			m.setDoTime(querydate);
		}

		List<Todo> list = todoService.listTodoByPage(pageM, m, sysdate);
		String[] properties = { "taskType", "customerId.id:customerId", "customerId.customerName:customerName",
				"doTime", "taskContent", "followId.id:followId", "customerCareId.id:customerCareId",
				"customerCareId.careContent:careContent", "doUserId.name:name",
				"customerComplainId.id:customerComplainId", "customerComplainId.complaintContent:complaintContent",
				"doUserId.id:doUserId", "doUserId.username:doUserName", "remind", "remindUnit", "status", "reason",
				"id", "note", "date", "hour", "minute" };
		String jsonString = JSONUtil.toJson(list, properties, (long) pageM.getTotal());
		return jsonString;
	}

	/**
	 * @description: 我创建的分页查询
	 * @createTime: 2017年09月07日 09:56:45
	 * @author: zjy
	 * @param m
	 * @param rows
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "listTodoCreateByPage")
	public Object listTodoCreateByPage(Todo m, int rows, int page) {
		if (!hasViewPermission()) {
			return JSONUtil.EMPTYJSON;
		}
		Page<Todo> pageM = new Page<>(page, rows);
		User user = userService.userInfo();
		m.setCreatorId(user.getId());
		List<Todo> list = todoService.listTodoCreateByPage(pageM, m);
		String[] properties = { "taskType", "customerId.id:customerId", "customerId.customerName:customerName",
				"doTime", "taskContent", "followId.id:followId", "customerCareId.id:customerCareId",
				"customerCareId.careContent:careContent", "doUserId.name:name",
				"customerComplainId.id:customerComplainId", "customerComplainId.complaintContent:complaintContent",
				"doUserId.id:doUserId", "doUserId.username:doUserName", "remind", "remindUnit", "status", "reason",
				"creatorId", "id", "date", "hour", "minute"};
		String jsonString = JSONUtil.toJson(list, properties, (long) pageM.getTotal());
		return jsonString;
	}

	/**
	 * @description: 我抄送的分页查询
	 * @createTime: 2017年09月07日 09:56:45
	 * @author: zjy
	 * @param m
	 * @param rows
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "listTodoCopyByPage")
	public Object listTodoCopyByPage(Todo m, int rows, int page) {
		if (!hasViewPermission()) {
			return JSONUtil.EMPTYJSON;
		}
		Page<Todo> pageM = new Page<>(page, rows);
		User user = userService.userInfo();
		List<Todo> list = todoService.listTodoCopyByPage(pageM, m, user.getId());
		String[] properties = { "taskType", "customerId.id:customerId", "customerId.customerName:customerName",
				"doTime", "taskContent", "followId.id:followId", "customerCareId.id:customerCareId",
				"doUserId.name:name", "customerComplainId.id:customerComplainId", "doUserId.id:doUserId",
				"doUserId.username:doUserName", "remind", "remindUnit", "status", "reason", "creatorId", "id", "date",
				"hour", "minute" };
		String jsonString = JSONUtil.toJson(list, properties, (long) pageM.getTotal());
		return jsonString;
	}

	/**
	 * @description: 分页查询-评论
	 * @createTime: 2017年09月14日 08:47:17
	 * @author: zjy
	 * @param m
	 * @param rows
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "listTodoCommentByPage")
	public Object listCommentByPage(Model model, Long data) {
		List<TodoComment> list = todoCommentService.listCommentByPage(data);
		String[] properties = { "id", "todoId.id:todoId", "comment", "commentUserId.id:commentUserId",
				"commentUserId.username:commentUserName", "commentUserId.name:name", "commentTime" };
		String jsonString = JSONUtil.toJson(list, properties);
		return jsonString;
	}

	/**
	 * @description: 获取评论内容
	 * @createTime: 2017年09月14日 08:47:17
	 * @author: zjy
	 * @param m
	 * @param rows
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getCommentContent")
	public Object getCommentContent(Model model, Long comentId) {
		TodoComment list = todoCommentService.getCommentContent(comentId);
		String[] properties = { "comment" };
		String jsonString = JSONUtil.toJson(list, properties);
		return jsonString;
	}

	/**
	 * @description: 添加评论
	 * @createTime: 2017年09月14日 08:47:17
	 * @author: zjy
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "addComment", method = RequestMethod.POST)
	@ResponseBody
	public Object addComment(TodoComment m) {
		ServiceResult result = new ServiceResult(false);
		if (StringUtils.isBlank(m.getComment())) {
			result.setMessage("评论内容不能为空");
			return result;
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date now = Calendar.getInstance().getTime();
		m.setCommentTime(df.format(now));
		User user = userService.userInfo();
		m.setCommentUserId(user);
		m.setCreateTime(DateUtil.getNowTimestampStr());
		m.setCreatorId(OperateUtils.getCurrentUserId());
		m.setLastModifyTime(DateUtil.getNowTimestampStr());
		m.setLastModifierId(OperateUtils.getCurrentUserId());
		boolean isSuccess = todoCommentService.insert(m);
		result.setIsSuccess(isSuccess);
		String jsonString = result.toJSON();
		return jsonString;
	}

	/**
	 * @description: 添加未完成说明
	 * @createTime: 2017年09月14日 08:47:17
	 * @author: zjy
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "addNotComplect", method = RequestMethod.POST)
	@ResponseBody
	public Object addNotComplect(Todo m) {
		ServiceResult result = new ServiceResult(false);
		if (StringUtils.isBlank(m.getReason())) {
			result.setMessage("请填写未完成说明");
			return result;
		}
		Todo oldM = todoService.selectById(m.getId());
		oldM.setReason(m.getReason());
		oldM.setStatus(3);
		boolean isSuccess = todoService.updateById(oldM);
		result.setIsSuccess(isSuccess);
		String jsonString = result.toJSON();
		return jsonString;
	}

	/**
	 * @description: 添加完成说明
	 * @createTime: 2017年09月14日 08:47:17
	 * @author: zjy
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "otherComplect", method = RequestMethod.POST)
	@ResponseBody
	public Object otherComplect(Todo m) {
		ServiceResult result = new ServiceResult(false);
		if (StringUtils.isBlank(m.getReason())) {
			result.setMessage("请填写完成说明");
			return result;
		}
		result =todoService.updateOtherComplect(m);
		String jsonString = result.toJSON();
		return jsonString;
	}

	/**
	 * @description: 添加取消说明
	 * @createTime: 2017年09月14日 08:47:17
	 * @author: zjy
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "addCancel", method = RequestMethod.POST)
	@ResponseBody
	public Object addCancel(Todo m) {
		ServiceResult result = new ServiceResult(false);
		if (StringUtils.isBlank(m.getReason())) {
			result.setMessage("请填写取消说明");
			return result;
		}
		Todo oldM = todoService.selectById(m.getId());
		oldM.setReason(m.getReason());
		oldM.setStatus(4);
		boolean isSuccess = todoService.updateById(oldM);
		result.setIsSuccess(isSuccess);
		String jsonString = result.toJSON();
		return jsonString;
	}

	/**
	 * 
	 * @description:获取用户列表
	 * @createTime 2017年9月4日 下午5:59:22
	 * @author zjy
	 * @param model
	 * @param m
	 * @param rows
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getUserList")
	public Object getUserList(Model model, User m, int rows, int page) {
		if (!hasViewPermission()) {
			return JSONUtil.EMPTYJSON;
		}
		m.setStatus(1);
		Page<User> pageM = new Page<User>(page, rows);
		userService.findListByPageMap(m, null, pageM);
		String[] properties = { "id", "name", "username", "status", "isAdmin", "isLock", "organizations" };
		String jsonString = JSONUtil.toJson(pageM.getRecords(), properties, (long) pageM.getTotal());
		return jsonString;
	}
	/**
	 * @description: 附件下载
	 * @createTime: 2017年9月29日 上午9:27:02
	 * @author: ZJY
	 * @param response
	 * @param redirectAttributes
	 * @param attachId
	 */
    @RequestMapping(value = "downloadFile")
    public void downloadFile(HttpServletResponse response,RedirectAttributes redirectAttributes,Long attachId){
    	ServiceResult result = new ServiceResult(false);
    	Attach attach = attachService.selectById(attachId);
    	if(attach==null){
 			result.setMessage("项目附件不存在，下载失败！");
 		}
 		try {
 			FileUtil.downloadFile(response, CommonUtil.getAppProParam("project_files.base.path")+attach.getPath(), "附件_"+attach.getName());
 			result.setIsSuccess(true);
 		} catch (Exception e) {
 			result.setMessage("项目附件异常，下载失败！");
 		}
    }
    /**
	 * @description: 附件下载
	 * @createTime: 2017年9月29日 上午9:27:02
	 * @author: ZJY
	 * @param response
	 * @param redirectAttributes
	 * @param attachId
	 */
    @RequestMapping(value = "download")
    public void download(HttpServletResponse response,RedirectAttributes redirectAttributes,Long todoId){
    	ServiceResult result = new ServiceResult(false);
     	String attachId=todoService.getAttachId(todoId);
	   	if(attachId==null){
	   		result.setMessage("没有上传附件，下载失败！");
	   	}
	   	Attach attach = attachService.selectById(attachId);
		if(attach==null){
			result.setMessage("附件不存在，下载失败！");
		}
 		try {
 			FileUtil.downloadFile(response, CommonUtil.getAppProParam("project_files.base.path")+attach.getPath(), "附件_"+attach.getName());
 			result.setIsSuccess(true);
 		} catch (Exception e) {
 			result.setMessage("项目附件异常，下载失败！");
 		}
    }
    /**
     *
     * @description: 删除附件
     * @createTime: 2017年10月9日 下午2:39:44
     * @author: zjy
     * @param response
     * @param redirectAttributes
     * @param attachId
     */
    @RequestMapping(value = "deleteFile")
    public void deleteFile(HttpServletResponse response,RedirectAttributes redirectAttributes,Long attachId){
    	ServiceResult result = new ServiceResult(false);
 		try {
 			EntityWrapper<TodoAttach> ew = new EntityWrapper<TodoAttach>();
			ew.eq("attach_id", attachId);
			if (todoAttachService.selectCount(ew) > 0) {
				todoAttachService.delete(ew);
			}

 			attachService.deleteById(attachId);
 		} catch (Exception e) {
 			e.printStackTrace();
 			result.setMessage("项目附件异常，下载失败！");
 		}
    }
    /**
     *
     * @description: 获取待办附件
     * @createTime: 2017年10月9日 下午4:34:44
     * @author: zjy
     * @param model
     * @param followId
     * @return
     */
    @RequestMapping(value="todoAttach", method=RequestMethod.POST)
    @ResponseBody
    public Object todoAttach(Model model,Long todoId){
    	List<TodoAttach> list = todoAttachService.findListByTodoId(todoId);
    	String[] properties={"id","attachId.id:attachId","attachId.name:name","attachId.extention:extention","attachId.path:attachPath"};
    	String jsonString = JSONUtil.toJson(list, properties);
        return jsonString;
    }
    /**
	 * 
	 * @description:获取附件
	 * @createTime 2017年9月30日 下午12:03:22
	 * @author zjy
	 * @param followId
	 * @return
	 */
	@RequestMapping(value="getAttach")
	@ResponseBody
	public Object getAttach(Long todoId){
		ServiceResult result=new ServiceResult(false);
		String attachId=todoService.getAttachId(todoId);
	   	if(attachId==null){
	   		result.setMessage("没有上传附件，下载失败！");
	   		return result;
	   	}
	   	Attach attach = attachService.selectById(attachId);
		if(attach==null){
			result.setMessage("附件不存在，下载失败！");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
}
