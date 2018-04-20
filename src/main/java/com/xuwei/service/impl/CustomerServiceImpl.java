package com.xuwei.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.Attach;
import com.xuwei.bean.Customer;
import com.xuwei.bean.Dict;
import com.xuwei.bean.User;
import com.xuwei.mapper.AttachMapper;
import com.xuwei.mapper.CustomerMapper;
import com.xuwei.mapper.DictMapper;
import com.xuwei.mapper.UserMapper;
import com.xuwei.service.ICustomerService;
import com.xuwei.util.CommonUtil;
import com.xuwei.util.DateUtil;
import com.xuwei.util.FileUtil;
import com.xuwei.util.JXLUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;

/**
 * @description: 客户服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年08月31日 10:49:27
 * @author: caw
 * @version: 1.0
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {
	@Resource
	private AttachMapper attachMapper;
	@Resource
	private CustomerMapper customerMapper;
	@Resource
	private UserMapper userMapper;
	@Resource
	private DictMapper dictMapper;

	/**
     * 获取图片
     */
	@Override
	public void getImg(Long attachId, RedirectAttributes redirectAttributes, HttpServletRequest request,
			HttpServletResponse response) {
		// 文件保存目录路径
		String basePath = CommonUtil.getAppProParam("project_files.base.path");
		String path = null;
		if (attachId != null) {
			Attach attach = attachMapper.selectById(attachId);
			path = attach.getPath();
		} else {
			throw new RuntimeException("没有找到相应的图片");
		}
		String filePath = basePath + path;
		File file = new File(filePath);
		try {
			if (file.exists()) {
				ServletOutputStream outputStream = response.getOutputStream();
				InputStream in = new FileInputStream(filePath);
				BufferedOutputStream bout = new BufferedOutputStream(outputStream);
				byte b[] = new byte[1024];
				int len = in.read(b);
				while (len > 0) {
					bout.write(b, 0, len);
					len = in.read(b);
				}
				bout.close();
				in.close();
				outputStream.flush();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据客户id获取logo的src
	 */
	@Override
	public ServiceResult imgSrc(Model model, HttpServletRequest request, Long customerid) {
		ServiceResult result = new ServiceResult(false);
		Customer customer = customerMapper.selectById(customerid);
		//当前url的访问路径
    	String path = request.getContextPath();
    	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    	String src = null;
    	if(customer.getPhotoId()!=null){
    		src = basePath+"admin/myWorkbench/totalCustomer/getImg.jhtml?attachId="+customer.getPhotoId();
    	}
    	result.addData("src", src);
    	return result;
	}
	
	/**
	 * 根据附件id获取logo的src
	 */
	@Override
	public ServiceResult imgSrcAttach(Model model, HttpServletRequest request, Long attachid) {
		ServiceResult result = new ServiceResult(false);
		//当前url的访问路径
		
    	String path = request.getContextPath();
    	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    	String src = null;
    	if(attachid!=null){
    		src = basePath+"admin/myWorkbench/totalCustomer/getImg.jhtml?attachId="+attachid;
    	}
    	result.addData("src", src);
    	return result;
	}
	
	/**
	 * 新增
	 */
	@Override
	public ServiceResult createCustomer(MultipartFile file, HttpServletRequest request, Model model, Customer m) {
		ServiceResult result = new ServiceResult(false);
    	HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("filext", CommonUtil.getAppProParam("project_files.image.type"));
		try{
			if(StringUtils.isEmpty(m.getCustomerName())){
				result.setMessage("客户名称不能为空");
				return result;
			}
			if(m.getCustomerStatus()==null){
				result.setMessage("客户状态不能为空");
				return result;
			}
			if(StringUtils.isEmpty(m.getMobilePhone())){
				result.setMessage("手机号不能为空");
				return result;
			}
			int numOne = customerMapper.mobilePhoneNum(m.getMobilePhone(),null);
			if(numOne!=0){
				result.setMessage("手机号已存在");
				return result;
			}
			if(StringUtils.isEmpty(m.getIdNumber())){
				result.setMessage("身份证号不能为空");
				return result;
			}
			int numTwo = customerMapper.idNumberNum(m.getIdNumber(),null);
			if(numTwo!=0){
				result.setMessage("身份证号已存在");
				return result;
			}
			if(m.getCustomerSource().getId()==null){
				result.setMessage("客户来源不能为空");
				return result;
			}
			if(m.getLiveArea().getId()==null){
				result.setMessage("居住地区不能为空");
				return result;
			}
			m.setCustomerType(1);
			m.setCreatorId(OperateUtils.getCurrentUserId());
			m.setCreateTime(DateUtil.getNowTimestampStr());
			m.setLastModifierId(OperateUtils.getCurrentUserId());
			m.setLastModifyTime(DateUtil.getNowTimestampStr());
			if(StringUtils.isEmpty(file.getOriginalFilename())){
				int deleteCount = customerMapper.insert(m);
    			if(deleteCount>0){
					result.setIsSuccess(true);
					result.addData("cid", m.getId());
					return result;
				}else{
					result.setMessage("没新增记录");
					return result;
				}
			}else{
				Long data = fileUpload(file, extMap, "customer/");
				if(data==null){
    				result.setMessage("只允许上传"+extMap.get("filext")+"格式的文件");
                    return result;
    			}else{
        			m.setPhotoId(data);
        			int deleteCount = customerMapper.insert(m);
        			if(deleteCount>0){
        				result.addData("cid", m.getId());
    					result.setIsSuccess(true);
    					return result;
    				}else{
    					result.setMessage("没新增记录");
    					return result;
    				}
    			}
			}
		}catch (Exception e) {
 			result.setMessage("新增客户失败，请重新操作");
 			return result;
 		} 
	}
	
	/**
     * 文件上传（新增）
     */
    public Long fileUpload(MultipartFile file,HashMap<String, String> extMap,String functionName)throws Exception{
	   	   String downloadWritePath = CommonUtil.getAppProParam("project_files.base.path")+ CommonUtil.getAppProParam("project_files.attach.path") + functionName;
		   String downloadDirPath = downloadWritePath+"/";
		   String fileExt = FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase();
		   if(!StringUtils.isEmpty(file.getOriginalFilename())){
			   if(!Arrays.asList(extMap.get("filext").split(",")).contains(fileExt)){
	            return null;
			   }
		   }
		   
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
		   attach.setPath(CommonUtil.getAppProParam("project_files.attach.path")+functionName+subFileName);
		   attach.setExtention(fileExt);
		   attach.setName(file.getOriginalFilename());
		   attachMapper.insert(attach);
		   return attach.getId();
    }

    /**
     * 文件上传（修改）
     */
    public Long fileUploadMod(MultipartFile file,HashMap<String, String> extMap,String functionName,Long fileid)throws Exception{
 	   	   String downloadWritePath = CommonUtil.getAppProParam("project_files.base.path")+ CommonUtil.getAppProParam("project_files.attach.path") + functionName;
		   String downloadDirPath = downloadWritePath+"/";
		   String fileExt = FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase();
		   if(!StringUtils.isEmpty(file.getOriginalFilename())){
			   if(!Arrays.asList(extMap.get("filext").split(",")).contains(fileExt)){
				   return null;
			   }
		   }
		   
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
		   Attach attach = attachMapper.selectById(fileid);
		   if(attach==null){
			   return 0L;
		   }
		   attach.setLastModifierId(OperateUtils.getCurrentUserId());
		   attach.setLastModifyTime(DateUtil.getNowTimestampStr());
		   attach.setPath(CommonUtil.getAppProParam("project_files.attach.path")+functionName+subFileName);
		   attach.setExtention(fileExt);
		   attach.setName(file.getOriginalFilename());
		   attachMapper.updateById(attach);
		   return 1L;
    }
    
    /**
     * 根据id获取客户信息
     */
	@Override
	public Customer selectCustomerById(Long customerid) {
		return customerMapper.selectCustomerById(customerid);
	}

	/**
     * 获取客户信息
     */
	@Override
	public List<Customer> listCustomerByPage(Page<Customer> pageM, Customer m, String todayTime, int viewType) {
		if(!StringUtils.isEmpty(m.getCreateTimeMin()) && !StringUtils.isEmpty(m.getCreateTimeMax())){
			if(m.getCreateTimeMin().equals(m.getCreateTimeMax())){
				try {
					String date = DateUtil.dateToString(DateUtil.addDay(DateUtil.toDate(m.getCreateTimeMax()), 1));
					m.setCreateTimeMax(date);
				} catch (ParseException e) {}
			}
		}
		if(!StringUtils.isEmpty(m.getLastTrackTimeMin()) && !StringUtils.isEmpty(m.getLastTrackTimeMax())){
			if(m.getLastTrackTimeMin().equals(m.getLastTrackTimeMax())){
				try {
					String date = DateUtil.dateToString(DateUtil.addDay(DateUtil.toDate(m.getLastTrackTimeMax()), 1));
					m.setLastTrackTimeMax(date);
				} catch (ParseException e) {}
			}
		}
		return customerMapper.listCustomerByPage(pageM, m, todayTime, viewType);
	}
	
	/**
	 * @description: 获取用户信息
	 */
	@Override
	public List<User> getOwnerByList(Page<User> pageM, String userName) {
		return customerMapper.getOwnerByList(pageM,userName);
	}

	/**
	 * 修改
	 */
	@Override
	public ServiceResult updateCustomer(MultipartFile file, HttpServletRequest request, Model model, Customer m,
			Long attachId) {
		ServiceResult result = new ServiceResult(false);
    	HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("filext", CommonUtil.getAppProParam("project_files.image.type"));
		try{
			if(StringUtils.isEmpty(m.getCustomerName())){
				result.setMessage("客户名称不能为空");
				return result;
			}
			if(m.getCustomerStatus()==null){
				result.setMessage("客户状态不能为空");
				return result;
			}
			if(StringUtils.isEmpty(m.getMobilePhone())){
				result.setMessage("手机号不能为空");
				return result;
			}
			int numOne = customerMapper.mobilePhoneNum(m.getMobilePhone(),m.getId());
			if(numOne!=0){
				result.setMessage("手机号已存在");
				return result;
			}
			if(StringUtils.isEmpty(m.getIdNumber())){
				result.setMessage("身份证号不能为空");
				return result;
			}
			int numTwo = customerMapper.idNumberNum(m.getIdNumber(),m.getId());
			if(numTwo!=0){
				result.setMessage("身份证号已存在");
				return result;
			}
			if(m.getCustomerSource().getId()==null){
				result.setMessage("客户来源不能为空");
				return result;
			}
			if(m.getLiveArea().getId()==null){
				result.setMessage("居住地区不能为空");
				return result;
			}
			m.setLastModifierId(OperateUtils.getCurrentUserId());
			m.setLastModifyTime(DateUtil.getNowTimestampStr());
			if(StringUtils.isEmpty(file.getOriginalFilename())){
				int deleteCount = customerMapper.updateById(m);
    			if(deleteCount>0){
					result.setIsSuccess(true);
					return result;
				}else{
					result.setMessage("没修改记录");
					return result;
				}
			}else{
				if(attachId==null){
					Long data = fileUpload(file, extMap, "customer/");
					if(data==null){
	    				result.setMessage("只允许上传"+extMap.get("filext")+"格式的文件");
	                    return result;
	    			}else{
	        			m.setPhotoId(data);
	        			int deleteCount = customerMapper.updateById(m);
	        			if(deleteCount>0){
	    					result.setIsSuccess(true);
	    					return result;
	    				}else{
	    					result.setMessage("没修改记录");
	    					return result;
	    				}
	    			}
				}else{
					Long data = fileUploadMod(file, extMap, "customer/",attachId);
					if(data==null){
        				result.setMessage("只允许上传"+extMap.get("filext")+"格式的文件");
                        return result;
        			}else if(data==0){
        				Long addData = fileUpload(file, extMap, "customer/");
        				if(addData==null){
            				result.setMessage("只允许上传"+extMap.get("filext")+"格式的文件");
                            return result;
            			}else{
            				m.setPhotoId(data);
            				int deleteCount = customerMapper.updateById(m);
    	        			if(deleteCount>0){
    	    					result.setIsSuccess(true);
    	    					return result;
    	    				}else{
    	    					result.setMessage("没修改记录");
    	    					return result;
    	    				}
            			}
        			}else{
        				int deleteCount = customerMapper.updateById(m);
            			if(deleteCount>0){
        					result.setIsSuccess(true);
        					return result;
        				}else{
        					result.setMessage("没修改记录");
        					return result;
        				}
        			}
				}
			}
		}catch (Exception e) {
 			result.setMessage("修改客户失败，请重新操作");
 			return result;
 		} 
	}
	
	/**
	 * 修改信用等级
	 */
	@Override
	public ServiceResult modCustomerCredit(MultipartFile file, HttpServletRequest request, Model model, Customer m) {
		ServiceResult result = new ServiceResult(false);
    	HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("filext", CommonUtil.getAppProParam("project_files.image.type"));
		try{
			Customer customer = customerMapper.selectById(m.getId());
			customer.setLastModifierId(OperateUtils.getCurrentUserId());
			customer.setLastModifyTime(DateUtil.getNowTimestampStr());
			customer.setCreditRatingId(m.getCreditRatingId());
			if(StringUtils.isEmpty(file.getOriginalFilename())){
				int deleteCount = customerMapper.updateById(customer);
    			if(deleteCount>0){
					result.setIsSuccess(true);
					return result;
				}else{
					result.setMessage("没修改记录");
					return result;
				}
			}else{
				if(customer.getCreditRatingAttachId()==null){
					Long data = fileUpload(file, extMap, "customer/");
					if(data==null){
	    				result.setMessage("只允许上传"+extMap.get("filext")+"格式的文件");
	                    return result;
	    			}else{
	    				customer.setCreditRatingAttachId(data);
	        			int deleteCount = customerMapper.updateById(customer);
	        			if(deleteCount>0){
	    					result.setIsSuccess(true);
	    					return result;
	    				}else{
	    					result.setMessage("没修改记录");
	    					return result;
	    				}
	    			}
				}else{
					Long data = fileUploadMod(file, extMap, "customer/",customer.getCreditRatingAttachId());
					if(data==null){
        				result.setMessage("只允许上传"+extMap.get("filext")+"格式的文件");
                        return result;
        			}else if(data==0){
        				Long addData = fileUpload(file, extMap, "customer/");
        				if(addData==null){
            				result.setMessage("只允许上传"+extMap.get("filext")+"格式的文件");
                            return result;
            			}else{
            				customer.setCreditRatingAttachId(data);
            				int deleteCount = customerMapper.updateById(customer);
    	        			if(deleteCount>0){
    	    					result.setIsSuccess(true);
    	    					return result;
    	    				}else{
    	    					result.setMessage("没修改记录");
    	    					return result;
    	    				}
            			}
        			}else{
        				int deleteCount = customerMapper.updateById(customer);
            			if(deleteCount>0){
        					result.setIsSuccess(true);
        					return result;
        				}else{
        					result.setMessage("没修改记录");
        					return result;
        				}
        			}
				}
			}
		}catch (Exception e) {
 			result.setMessage("修改信用等级信息失败，请重新操作");
 			return result;
 		} 
	}

	/**
	 * 导出客户
	 */
	@Override
	public void export(Customer m, String titleExport, String fieldNamesExport, String fieldsExport,
			HttpServletResponse response) {
		List<Map<String, Object>> listMap = customerMapper.findBySearch(m);
		
		try {
			Map<String, Map<String, String>> proConvertMap = new HashMap<String, Map<String, String>>();
			//客户类型
			Map<String, String> customerTypeMap = new HashMap<String, String>();
			customerTypeMap.put("1", "个人");
			customerTypeMap.put("2", "企业");
			proConvertMap.put("customerType", customerTypeMap);
			//性别
			Map<String, String> sexMap = new HashMap<String, String>();
			sexMap.put("1", "男");
			sexMap.put("0", "女");
			proConvertMap.put("sex", sexMap);
			//智能评级
			Map<String, String> gradeMap = new HashMap<String, String>();
			EntityWrapper<Dict> ewCustomer = new EntityWrapper<Dict>();
	        ewCustomer.eq("dict_type","grade");
	        List<Dict> list = dictMapper.selectList(ewCustomer);
	        for(int i=0;i<list.size();i++){
	        	gradeMap.put(String.valueOf(list.get(i).getId()), list.get(i).getName());
	        }
			proConvertMap.put("gradeId", gradeMap);
			//信用等级
			Map<String, String> creditRatingMap = new HashMap<String, String>();
			EntityWrapper<Dict> creditRating = new EntityWrapper<Dict>();
			creditRating.eq("dict_type","creditRating");
	        List<Dict> creditRatinglist = dictMapper.selectList(creditRating);
	        for(int i=0;i<creditRatinglist.size();i++){
	        	creditRatingMap.put(String.valueOf(creditRatinglist.get(i).getId()), creditRatinglist.get(i).getName());
	        }
			proConvertMap.put("creditRatingId", creditRatingMap);
			JXLUtil.exportExcelMap(listMap, titleExport, fieldNamesExport,
					fieldsExport, response, proConvertMap);
		} catch (Exception e) {
			throw new RuntimeException("导出失败");
		}
	}

	/**
	 * 统计
	 */
	@Override
	public ServiceResult countCustomer(int viewType, Customer m) {
		ServiceResult result = new ServiceResult(false);
		String todayTime = DateUtil.dateToString(DateUtil.getNowTimestamp());
		result.addData("wholeCustomerNum", customerMapper.wholeCustomerNum(viewType,m));
		result.addData("personalCustomerNum", customerMapper.personalCustomerNum(viewType,m));
		result.addData("enterpriseCustomerNum", customerMapper.enterpriseCustomerNum(viewType,m));
		result.addData("todayUpdateNum", customerMapper.todayUpdateNum(todayTime,viewType,m));
		return result;
	}

	/**
	 * 客户登陆认证（微信端）
	 */
	@Transactional
	@Override
	public ServiceResult loginValidate(String account, String password, String host,String openid) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isBlank(account)){
			result.setMessage("请输入账号");
			return result;
		}
		if(StringUtils.isBlank(password)){
			result.setMessage("请输入密码");
			return result;
		}
		Customer customer = customerMapper.findByIdNumber(account);
		if (customer == null){
			result.setMessage("帐号不存在");
			return result;
		}
		String idNumber = customer.getIdNumber().substring(customer.getIdNumber().length()-6,customer.getIdNumber().length());
		if(!password.equals(idNumber)){
			result.setMessage("帐号密码不正确");
			return result;
		}
		customer.setWxOpenId(openid);
		customer.updateById();
		result.addData("userId", customer.getId());
		result.setIsSuccess(true);
		return result;
	}
	
	/**
	 * 获取客户信息（微信端）
	 */
	@Override
	public ServiceResult getCustomerList(String searchValue, Page<Customer> page, Long userId,
			Long totalNum, Long searchType, int viewType, Customer m) {
		ServiceResult result = new ServiceResult(false);
		List<Customer> list = new ArrayList<Customer>();
		Date date = DateUtil.getNowTimestamp();
		String currentTime = DateUtil.dateToString(date);
		if(searchType==1){            //查询全部
			list = customerMapper.getCustomerList(searchValue, page, viewType, m);
		}else if(searchType==2){      //查询今日更新
			list = customerMapper.getCustomerTodayUpdateList(searchValue, page, currentTime, viewType, m);
		}else if(searchType==3){      //查询近一周
			list = customerMapper.getCustomerNearlyAWeekList(searchValue, page, currentTime, viewType, m);
		}else if(searchType==4){      //查询近一个月
			list = customerMapper.getCustomerNearlyAMonthList(searchValue, page, currentTime, viewType, m);
		}
		if(page.getTotal()==totalNum && totalNum!=0){
			List<Customer> lists = new ArrayList<Customer>();
			result.addData("customerlist", lists);
			result.setIsSuccess(true);
			return result;
		}
		result.addData("customerlist", list);
		result.setIsSuccess(true);
		return result;
	}

	/**
	 * 新增客户（微信端）
	 */
	@Override
	public ServiceResult addCustomer(Customer m, Long userId) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(m.getCustomerName())){
			result.setMessage("客户名称不能为空");
			return result;
		}
		if(StringUtils.isEmpty(m.getMobilePhone())){
			result.setMessage("手机号不能为空");
			return result;
		}
		int numOne = customerMapper.mobilePhoneNum(m.getMobilePhone(),m.getId());
		if(numOne!=0){
			result.setMessage("手机号已存在");
			return result;
		}
		if(StringUtils.isEmpty(m.getIdNumber())){
			result.setMessage("身份证号不能为空");
			return result;
		}
		int numTwo = customerMapper.idNumberNum(m.getIdNumber(),m.getId());
		if(numTwo!=0){
			result.setMessage("身份证号已存在");
			return result;
		}
		if(m.getCustomerSource().getId()==null){
			result.setMessage("客户来源不能为空");
			return result;
		}
		if(m.getCustomerStatus().getId()==null){
			result.setMessage("客户状态不能为空");
			return result;
		}
		if(m.getLiveArea().getId()==null){
			result.setMessage("居住地区不能为空");
			return result;
		}
		m.setCustomerType(1);
		m.setCreatorId(userId);
		m.setCreateTime(DateUtil.getNowTimestampStr());
		m.setLastModifierId(userId);
		m.setLastModifyTime(DateUtil.getNowTimestampStr());
		m.setOwner(userMapper.findById(userId));
		int data = customerMapper.insert(m);
		if(data>0){
			result.addData("customerid", m.getId());
			result.setIsSuccess(true);
			return result;
		}else{
			result.setMessage("没新增记录");
			return result;
		}
	}

	@Override
	public Page<Customer> listCustomerByOrder(Page<Customer> pageM, Long orderId) {
		List<Customer> list=customerMapper.listCustomerByOrder(pageM,orderId);
		pageM.setRecords(list);
		return pageM;
	}
	
	
	/**
	 * 修改客户（微信端）
	 */
	@Override
	public ServiceResult modCustomer(Customer m, Long userId) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(m.getCustomerName())){
			result.setMessage("客户名称不能为空");
			return result;
		}
		if(StringUtils.isEmpty(m.getMobilePhone())){
			result.setMessage("手机号不能为空");
			return result;
		}
		int numOne = customerMapper.mobilePhoneNum(m.getMobilePhone(),m.getId());
		if(numOne!=0){
			result.setMessage("手机号已存在");
			return result;
		}
		if(StringUtils.isEmpty(m.getIdNumber())){
			result.setMessage("身份证号不能为空");
			return result;
		}
		int numTwo = customerMapper.idNumberNum(m.getIdNumber(),m.getId());
		if(numTwo!=0){
			result.setMessage("身份证号已存在");
			return result;
		}
		if(m.getCustomerSource().getId()==null){
			result.setMessage("客户来源不能为空");
			return result;
		}
		if(m.getCustomerStatus().getId()==null){
			result.setMessage("客户状态不能为空");
			return result;
		}
		if(m.getLiveArea().getId()==null){
			result.setMessage("居住地区不能为空");
			return result;
		}
		m.setLastModifierId(userId);
		m.setLastModifyTime(DateUtil.getNowTimestampStr());
		int data = customerMapper.updateAllColumnById(m);
		if(data>0){
			result.addData("customerid", m.getId());
			result.setIsSuccess(true);
			return result;
		}else{
			result.setMessage("没修改记录");
			return result;
		}
	}

	/**
	 * 获取全部客户（微信端）
	 */
	@Override
	public List<Customer> getAllCustomerByList() {
		return customerMapper.getAllCustomerByList();
	}
}
