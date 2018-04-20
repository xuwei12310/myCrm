package com.xuwei.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.Attach;
import com.xuwei.bean.Customer;
import com.xuwei.bean.Dict;
import com.xuwei.bean.Follow;
import com.xuwei.bean.FollowAttach;
import com.xuwei.bean.Organization;
import com.xuwei.bean.Todo;
import com.xuwei.bean.TodoCopyto;
import com.xuwei.bean.User;
import com.xuwei.service.IAttachService;
import com.xuwei.service.ICustomerService;
import com.xuwei.service.IFollowAttachService;
import com.xuwei.service.IFollowService;
import com.xuwei.service.IMessageService;
import com.xuwei.service.ITodoCopytoService;
import com.xuwei.service.ITodoService;
import com.xuwei.service.IUserService;
import com.xuwei.service.OrganizationService;
import com.xuwei.util.CommonUtil;
import com.xuwei.util.DateUtil;
import com.xuwei.util.FileUtil;
import com.xuwei.util.JSONUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;

/**
 * @description:客户跟进控制层
 * @createTime 上午10:26:45
 * @author xw
 *
 */
@Controller
@RequestMapping(value="/myWorkbench/follow")
public class FollowController extends BaseController<Follow>{
	
	@Resource 
	private IFollowService followService;
	@Resource
	private IAttachService attachService;
	@Resource
	private IFollowAttachService followAttachService;
	@Resource
	private IUserService userService;
	@Resource
	private ICustomerService customerService;
	@Resource
	private ITodoService todoService;
	@Resource
	private IMessageService messageService;
	@Resource
	private ITodoCopytoService todoCopytoService;
	@Resource
	private OrganizationService organizationService;
	public FollowController(){
		setResourceIdentity("myWorkbench:follow");
	}
	/**
	 * 
	 * @description:主页面
	 * @createTime 2017年8月30日 上午10:36:19
	 * @author xw
	 * @param mode
	 * @return
	 */
	@RequestMapping(value="main")
	public String Main(Model mode){
		return defaultViewPrefix();
	}
	/**
	 * 
	 * @description:添加
	 * @createTime 2017年8月30日 上午10:37:21
	 * @author xw
	 * @param model
	 * @param m
	 * @param t
	 * @param receiveTaskId 抄送人
	 * @param attachIds 
	 * @param isActive 是否创建任务
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public Object create(Model model, Follow m,Todo t,String receiveTaskId,String attachIds,Boolean isActive) {
		ServiceResult result = new ServiceResult(false);
		if (!hasPermissionByOpt("create")) {
			result.setMessage("没有添加权限");
		} else {
			m.setSource(1);
			Customer customer = customerService.selectCustomerById(m.getCustomer().getId());
			m.setFrontStatus(customer.getCustomerStatus());
			Dict customerStatus=new Dict();
			customerStatus.setId(m.getAfterStatus().getId());
			customer.setLastTrackTime(DateUtil.getNowTimesminutStr());
			customer.setCustomerStatus(customerStatus);
			customerService.updateById(customer);
			m.setCreateTime(DateUtil.getNowTimestampStr());
			m.setCreatorId(OperateUtils.getCurrentUserId());
			m.setLastModifyTime(DateUtil.getNowTimestampStr());
			m.setLastModifierId(OperateUtils.getCurrentUserId());
			boolean isSuccess = followService.insert(m);
			//创建任务
			if(isActive){
				t.setStatus(1);
				t.setCustomerId(customer);
				t.setTaskType(1);
				t.setFollowId(m);
				t.setCreateTime(DateUtil.getNowTimestampStr());
				t.setCreatorId(OperateUtils.getCurrentUserId());
				t.setLastModifyTime(DateUtil.getNowTimestampStr());
				t.setLastModifierId(OperateUtils.getCurrentUserId());
				todoService.insert(t);
				messageService.addTodoMessage(t.getId());
				TodoCopyto todoCopyto = new TodoCopyto();
				User user = new User();
				if (!StringUtils.isEmpty(receiveTaskId)) {
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
								return result;
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
					result.setIsSuccess(false);
					result.setMessage("上传失败");
					return result;
				}
			}
			result.setIsSuccess(isSuccess);
			result.addData("id", m.getId());
		}
		String jsonString = result.toJSON();
		return jsonString;
	}
	/**
	 * 
	 * @description:文件上传
	 * @createTime 2017年9月4日 下午4:33:54
	 * @author xw
	 * @param file
	 * @param extMap
	 * @param functionName
	 * @return
	 * @throws Exception
	 */
	 public Long fileUpload(MultipartFile file,HashMap<String, String> extMap,String functionName)throws Exception{
   	   String downloadWritePath = CommonUtil.getAppProParam("project_files.base.path")+ "crm/" + functionName;
  		   String downloadDirPath = downloadWritePath+"/";
  		   System.out.println(downloadWritePath);
  		   String fileExt = FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase();
  		/*   if(!StringUtils.isEmpty(file.getOriginalFilename())){
  			   if(!Arrays.<String>asList(extMap.get("filext").split(",")).contains(fileExt)){
                  return null;
  			   }
  		   }*/
  		   //检查MailWrite目录
  		   File subjectWriteDir = new File(downloadDirPath);
  		   if(!subjectWriteDir.isDirectory()){
  			   subjectWriteDir.mkdirs();
  		   }
  		   //邮件临时文件夹
  		   File subDir = new File(downloadDirPath);
  		   if (!subDir.exists()) {
  			   subDir.mkdirs();
  		   }
  		   String subFileName =  CommonUtil.getUUID()+"."+fileExt;	
  		   String subFilePath = downloadDirPath +subFileName;
  		   File dest = new File(subFilePath);
  		   FileUtil.saveFileNew(file.getInputStream(), dest);
  		   
  		   Attach attach = new Attach();
  		   attach.setCreatorId(OperateUtils.getCurrentUserId());
  		   attach.setCreateTime(DateUtil.getNowTimestampStr());
  		   attach.setPath("crm/"+functionName+subFileName);
  		   attach.setExtention(fileExt);
  		   attach.setName(file.getOriginalFilename());
  		   if(attachService.insert(attach)){
  			   return attach.getId();
  		   }
   	   return 0L;
      }
	/**
	 * 
	 * @description:修改
	 * @createTime 2017年8月30日 上午10:37:33
	 * @author xw
	 * @param model
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	 public Object update(Model model,Follow m,String attachIds){
        ServiceResult result = new ServiceResult(false);
        if(!hasUpdatePermission()){
            result.setMessage("没有修改权限");
        }else{
        	Follow oldM = followService.selectById(m.getId());
            oldM.setCustomer(m.getCustomer());
            Customer customer=customerService.selectCustomerById(m.getCustomer().getId());
            if(!customer.getCustomerStatus().getId().equals(m.getAfterStatus().getId())){
            	oldM.setFrontStatus(oldM.getAfterStatus());
            	oldM.setAfterStatus(m.getAfterStatus());
            	Dict customerStatus=new Dict();
    			customerStatus.setId(m.getAfterStatus().getId());
    			customer.setLastTrackTime(DateUtil.getNowTimesminutStr());
    			customer.setCustomerStatus(customerStatus);
    			customerService.updateById(customer);
            }
            oldM.setFollowDetails(m.getFollowDetails());
            Dict followStage=new Dict();
            followStage.setId(m.getFollowStage().getId());
            oldM.setFollowStage(followStage);
            Dict followType=new Dict();
            followType.setId(m.getFollowType().getId());
            oldM.setFollowType(followType);
            User user =new User();
            user.setId(m.getFollowPersonnel().getId());
            oldM.setFollowPersonnel(user);
            oldM.setNote(m.getNote());
            oldM.setLastModifyTime(DateUtil.getNowTimestampStr());
            oldM.setLastModifierId(OperateUtils.getCurrentUserId());
            //修改跟进附件
            followService.modAttach(m.getId(),attachIds);
            boolean isSuccess = followService.updateAllColumnById(oldM);
            result.setIsSuccess(isSuccess);
        }
        
        String jsonString = result.toJSON();
        return jsonString;
    }
	/**
	 * 
	 * @description:删除
	 * @createTime 2017年8月30日 上午10:37:44
	 * @author xw
	 * @param model
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "mulDelete", method = RequestMethod.POST)
	@ResponseBody
	public Object mulDelete(Model model, String ids) {
		ServiceResult result = new ServiceResult(false);
		if (!hasUpdatePermission()) {
			result.setMessage("没有删除权限");
		} else {
			try {
				String[] idArray = StringUtil.split(ids);
				if (idArray == null || idArray.length == 0) {
					result.setMessage("请选择要删除的数据行");
					return result;
				}
				boolean isSuccess = followService.deleteByIds(idArray);
				result.setIsSuccess(isSuccess);
				if(!isSuccess){
					result.setMessage("客户状态已经改变，不能删除");
				}
			} catch (Exception e) {
				if (e instanceof org.springframework.dao.DataIntegrityViolationException) {
					e.printStackTrace();
					result.setMessage("数据已被引用不能删除");
				} else {
					result.setMessage("删除出错：" + e.getMessage());
					e.printStackTrace();
				}
			}
		}
		String jsonString = result.toJSON();
		return jsonString;
	}
	/**
	 * 
	 * @description:列表
	 * @createTime 2017年8月30日 上午10:37:58
	 * @author xw
	 * @param model
	 * @param m
	 * @param rows
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "listByPage")
	public Object listByPage(Model model, Follow m, int rows, int page) {
		EntityWrapper<Follow> ew=new EntityWrapper<Follow>();
		Long userId=OperateUtils.getCurrentUserId();
		if(hasPermissionByOpt("viewAll")){
		}else if(hasPermissionByOpt("viewDept")){
			Organization organization=organizationService.selectByUserId(userId);
			ew.isWhere(false).and("i.id={0}",organization.getId());
		}else if(hasViewPermission()){
			ew.isWhere(false).and("a.follow_personnel_id={0}",userId);
		}else{
			return JSONUtil.EMPTYJSON;
		}
		Page<Follow> pageM = new Page<>(page, rows);
		followService.listByPage(m,pageM,null,ew);
		String[] properties = {"customer.customerName:customerName","source","note","id","customer.id:customerId","followType.id:followType.id",
				"followType.name:followTypeName","followStage.id:followStage.id","followStage.name:followStageName","customer.customerStatus.id:customerStatus.id",
				"customer.customerStatus.name:customerStatusName","followTime","followDetails","followPersonnel.id:followPersonnelId",
				"followPersonnel.name:followPersonnelName","frontStatus.id:frontStatus.id","frontStatus.name:frontStatusName",
				"afterStatus.id:afterStatus.id","afterStatus.name:afterStatusName","customer.mobilePhone:telephone"};
		String jsonString = JSONUtil.toJson(pageM.getRecords(), properties, (long) pageM.getTotal());
		return jsonString;
	}
	/**
	 * 
	 * @description:获取用户列表
	 * @createTime 2017年9月4日 下午5:59:22
	 * @author xw
	 * @param model
	 * @param m
	 * @param rows
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getUserList")
    public Object getUserList(Model model, User m, int rows, int page){
        if(!hasViewPermission()){
            return  JSONUtil.EMPTYJSON;
        }
        m.setStatus(1);
        Page<User> pageM=new Page<User>(page,rows);
        userService.findUserList(m,pageM);
        String[] properties = {"id","name","username","status","isAdmin","isLock","organizations"};
        String jsonString = JSONUtil.toJson(pageM.getRecords(), properties,(long)pageM.getTotal());
        return jsonString;
    }
	/**
	 * 
	 * @description:下载附件
	 * @createTime 2017年9月5日 上午10:07:15
	 * @author xw
	 * @param response
	 * @param redirectAttributes
	 * @param followId
	 */
	@RequestMapping(value = { "/download" })
	public void down(HttpServletResponse response,RedirectAttributes redirectAttributes,Long followId){
		   ServiceResult result = new ServiceResult(false);
		   if(!hasPermissionByOpt("download")){
			   result.setMessage("没有下载权限！");
			}
		   	String attachId=followService.getAttachId(followId);
		   	if(attachId==null){
		   		result.setMessage("没有上传附件，下载失败！");
		   	}
			Attach attach = attachService.selectById(attachId);
			if(attach==null){
				result.setMessage("附件不存在，下载失败！");
			}
			try {
				FileUtil.downloadFile(response, CommonUtil.getAppProParam("project_files.base.path")+"/"+attach.getPath(), attach.getName());
				result.setIsSuccess(true);
			} catch (Exception e) {
				e.printStackTrace();
				result.setMessage("附件异常，下载失败！");
			}
	   }
	/**
	 * 
	 * @description:获取附件
	 * @createTime 2017年9月30日 下午12:03:22
	 * @author xw
	 * @param followId
	 * @return
	 */
	@RequestMapping(value="getAttach")
	@ResponseBody
	public Object getAttach(Long followId){
		ServiceResult result=new ServiceResult(false);
		String attachId=followService.getAttachId(followId);
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
	
	/**
	 * 
	 * @description: 跟进附件上传
	 * @createTime: 2017年10月9日 上午11:49:43
	 * @author: caw
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
 		String uploadPath = CommonUtil.getAppProParam("project_files.base.path")+CommonUtil.getAppProParam("project_files.attach.path")+"follow/";
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
 			attach.setPath(CommonUtil.getAppProParam("project_files.attach.path")+"follow/"+newFileName);
 			attach.setExtention(FilenameUtils.getExtension(newFileName));
 			attach.setName(fileName);
 			boolean flag =  attachService.insert(attach);
 			if(flag){
 				 jsonObject.put("attachId", attach.getId());
 				 jsonObject.put("name", fileName);
 				 jsonObject.put("attachPath", CommonUtil.getAppProParam("project_files.attach.path")+"follow/"+newFileName);
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
	 * 
	 * @description: 附件下载
	 * @createTime: 2017年10月9日 下午2:37:00
	 * @author: caw
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
 			FileUtil.downloadFile(response, CommonUtil.getAppProParam("project_files.base.path")+attach.getPath(), "项目附件_"+attach.getName());
 			result.setIsSuccess(true);
 		} catch (Exception e) {
 			e.printStackTrace();
 			result.setMessage("项目附件异常，下载失败！");
 		}
    }
    
    /**
     * 
     * @description: 删除附件
     * @createTime: 2017年10月9日 下午2:39:44
     * @author: caw
     * @param response
     * @param redirectAttributes
     * @param attachId
     */
    @RequestMapping(value = "deleteFile")
    public void deleteFile(HttpServletResponse response,RedirectAttributes redirectAttributes,Long attachId){
    	ServiceResult result = new ServiceResult(false);
 		try {
 			followAttachService.delAttachIdPage(attachId);
 			attachService.deleteById(attachId);
 		} catch (Exception e) {
 			e.printStackTrace();
 			result.setMessage("项目附件异常，下载失败！");
 		}
    }
    
    /**
     * 
     * @description: 获取跟进附件
     * @createTime: 2017年10月9日 下午4:34:44
     * @author: caw
     * @param model
     * @param followId
     * @return
     */
    @RequestMapping(value="followAttach", method=RequestMethod.POST)
    @ResponseBody
    public Object followAttach(Model model,Long followId){
    	List<FollowAttach> list = followAttachService.findListByFollowId(followId);
    	String[] properties={"id","attach.id:attachId","attach.name:name","attach.extention:extention","attach.path:attachPath"};
    	String jsonString = JSONUtil.toJson(list, properties);
        return jsonString;
    }
    @RequestMapping(value="getCurrentUser", method=RequestMethod.POST)
    @ResponseBody
    public Object getCurrentUser(){
    	User user = OperateUtils.getCurrentUser();
    	String[] properties={"id","username"};
    	String jsonString = JSONUtil.toJson(user, properties);
        return jsonString;
    }
}
