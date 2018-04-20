package com.xuwei.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.Attach;
import com.xuwei.bean.CustomerProperty;
import com.xuwei.mapper.AttachMapper;
import com.xuwei.mapper.CustomerPropertyMapper;
import com.xuwei.service.ICustomerPropertyService;
import com.xuwei.util.CommonUtil;
import com.xuwei.util.DateUtil;
import com.xuwei.util.FileUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;

/**
 * @description: 客户_产权服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月04日 09:42:59
 * @author: caw
 * @version: 1.0
 */
@Service
public class CustomerPropertyServiceImpl extends ServiceImpl<CustomerPropertyMapper, CustomerProperty> implements ICustomerPropertyService {
	@Resource
	private CustomerPropertyMapper customerPropertyMapper;
	@Resource
	private AttachMapper attachMapper;
	
	/**
	 * 分页查询客户产权
	 */
	@Override
	public List<CustomerProperty> listPropertyByPage(Page<CustomerProperty> pageM,Long customerid) {
		return customerPropertyMapper.listPropertyByPage(pageM, customerid);
	}
	
	/**
	 * 新增
	 */
	@Transactional
	@Override
	public ServiceResult createCustomerProperty(MultipartFile file, HttpServletRequest request, Model model,
			CustomerProperty m) {
		ServiceResult result = new ServiceResult(false);
    	HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("filext", CommonUtil.getAppProParam("project_files.image.type"));
		try{
			if(m.getPropertyValue()!=null){
				if(m.getPropertyValue().intValue()<0){
					result.setMessage("房产价值不能为负数");
					return result;
				}
			}
			if(StringUtils.isNotEmpty(m.getArea())){
				if((new BigDecimal(m.getArea())).intValue()<0){
					result.setMessage("面积不能为负数");
					return result;
				}
			}
			m.setCreatorId(OperateUtils.getCurrentUserId());
			m.setCreateTime(DateUtil.getNowTimestampStr());
			if(StringUtils.isEmpty(file.getOriginalFilename())){
				int deleteCount = customerPropertyMapper.insert(m);
    			if(deleteCount>0){
					result.setIsSuccess(true);
					return result;
				}else{
					result.setMessage("没新增记录");
					return result;
				}
			}else{
				Long data = fileUpload(file, extMap, "customerProperty/");
				if(data==null){
    				result.setMessage("只允许上传"+extMap.get("filext")+"格式的文件");
                    return result;
    			}else{
        			m.setAttachId(data);
        			int deleteCount = customerPropertyMapper.insert(m);
        			if(deleteCount>0){
    					result.setIsSuccess(true);
    					return result;
    				}else{
    					result.setMessage("没新增记录");
    					return result;
    				}
    			}
			}
		}catch (Exception e) {
			e.printStackTrace();
 			result.setMessage("新增产权信息失败，请重新操作");
 			return result;
 		} 
	}

	/**
	 * 修改
	 */
	@Transactional
	@Override
	public ServiceResult updateCustomerProperty(MultipartFile file, HttpServletRequest request, Model model,
			CustomerProperty m) {
		ServiceResult result = new ServiceResult(false);
    	HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("filext", CommonUtil.getAppProParam("project_files.image.type"));
		try{
			if(m.getPropertyValue()!=null){
				if(m.getPropertyValue().intValue()<0){
					result.setMessage("房产价值不能为负数");
					return result;
				}
			}
			if(StringUtils.isNotEmpty(m.getArea())){
				if((new BigDecimal(m.getArea())).intValue()<0){
					result.setMessage("面积不能为负数");
					return result;
				}
			}
			m.setLastModifierId(OperateUtils.getCurrentUserId());
			m.setLastModifyTime(DateUtil.getNowTimestampStr());
			if(StringUtils.isEmpty(file.getOriginalFilename())){
				int deleteCount = customerPropertyMapper.updateById(m);
    			if(deleteCount>0){
					result.setIsSuccess(true);
					return result;
				}else{
					result.setMessage("没修改记录");
					return result;
				}
			}else{
				if (m.getAttachId()==null) {
					Long data = fileUpload(file, extMap, "customerProperty/");
					if(data==null){
	    				result.setMessage("只允许上传"+extMap.get("filext")+"格式的文件");
	                    return result;
	    			}else{
	        			m.setAttachId(data);
	        			int deleteCount = customerPropertyMapper.updateById(m);
	        			if(deleteCount>0){
	    					result.setIsSuccess(true);
	    					return result;
	    				}else{
	    					result.setMessage("没修改记录");
	    					return result;
	    				}
	    			}
				}else{
					Long data = fileUploadMod(file, extMap, "customerProperty/",m.getAttachId());
					if(data==null){
        				result.setMessage("只允许上传"+extMap.get("filext")+"格式的文件");
                        return result;
        			}else if(data==0){
        				Long addData = fileUpload(file, extMap, "customerProperty/");
        				if(addData==null){
            				result.setMessage("只允许上传"+extMap.get("filext")+"格式的文件");
                            return result;
            			}else{
            				m.setAttachId(data);
            				int deleteCount = customerPropertyMapper.updateById(m);
    	        			if(deleteCount>0){
    	    					result.setIsSuccess(true);
    	    					return result;
    	    				}else{
    	    					result.setMessage("没修改记录");
    	    					return result;
    	    				}
            			}
        			}else{
        				int deleteCount = customerPropertyMapper.updateById(m);
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
 			result.setMessage("修改产权信息失败，请重新操作");
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
     * 删除产权信息（包括附件）
     */
    @Transactional
	@Override
	public ServiceResult deleteCustomerProperty(List<String> idArray) {
		ServiceResult result = new ServiceResult(false);
		Long[] idData = new Long[idArray.size()];
		int i = 0;
		try{
			for(String data:idArray){
				CustomerProperty customerProperty = customerPropertyMapper.selectById(Long.parseLong(data));
				if(customerProperty.getAttachId()!=null){
					idData[i] = customerProperty.getAttachId();
					i++;
				}
			}
			customerPropertyMapper.deleteBatchIds(idArray);
			attachMapper.deleteBatchIds(Arrays.asList(idData));
			result.setIsSuccess(true);
			return result;
		}catch (Exception e) {
 			result.setMessage("删除产权信息失败，请重新操作");
 			return result;
 		}
	}

    /**
     * 获取产权信息（微信端）
     */
	@Override
	public ServiceResult getCustomerPropertyList(Page<CustomerProperty> page, Long userId,
			Long totalNum, Long customerid) {
		ServiceResult result = new ServiceResult(false);
		List<CustomerProperty> list = customerPropertyMapper.getCustomerPropertyList(page, customerid);
		if(page.getTotal()==totalNum && totalNum!=0){
			List<CustomerProperty> lists = new ArrayList<CustomerProperty>();
			result.addData("customerPropertylist", lists);
			result.setIsSuccess(true);
			return result;
		}
		result.addData("customerPropertylist", list);
		result.setIsSuccess(true);
		return result;
	}

	/**
	 * 查看产权信息（微信端）
	 */
	@Override
	public CustomerProperty getCustomerPropertyInfo(Long id) {
		return customerPropertyMapper.getCustomerPropertyInfo(id);
	}

	/**
	 * 添加产权信息（微信端）
	 */
	@Override
	public ServiceResult addProperty(CustomerProperty m, Long userId) {
		ServiceResult result = new ServiceResult(false);
		if(m.getAreaId().getId()==null){
			result.setMessage("居住地区不能为空");
			return result;
		}
		if(m.getPlotId().getId()==null){
			result.setMessage("小区不能为空");
			return result;
		}
		m.setCreatorId(userId);
		m.setCreateTime(DateUtil.getNowTimestampStr());
		m.setLastModifierId(userId);
		m.setLastModifyTime(DateUtil.getNowTimestampStr());
		int data = customerPropertyMapper.insert(m);
		if(data>0){
			result.setIsSuccess(true);
			return result;
		}else{
			result.setMessage("没新增记录");
			return result;
		}
	}

	/**
	 * 修改产权信息（微信端）
	 */
	@Override
	public ServiceResult modProperty(CustomerProperty m, Long userId) {
		ServiceResult result = new ServiceResult(false);
		if(m.getAreaId().getId()==null){
			result.setMessage("居住地区不能为空");
			return result;
		}
		if(m.getPlotId().getId()==null){
			result.setMessage("小区不能为空");
			return result;
		}
		m.setLastModifierId(userId);
		m.setLastModifyTime(DateUtil.getNowTimestampStr());
		int data = customerPropertyMapper.updateAllColumnById(m);
		if(data>0){
			result.setIsSuccess(true);
			return result;
		}else{
			result.setMessage("没修改记录");
			return result;
		}
	}

	/**
	 * 删除产权信息（微信端）
	 */
	@Transactional
	@Override
	public ServiceResult delCustomerProperty(Long customerPropertyId) {
		ServiceResult result = new ServiceResult(false);
		try{
			Long attachId = null;
			CustomerProperty customerProperty = customerPropertyMapper.selectById(customerPropertyId);
			if(customerProperty.getAttachId()!=null){
				attachId = customerProperty.getAttachId();
				customerPropertyMapper.deleteById(customerPropertyId);
				attachMapper.deleteById(attachId);
				result.setIsSuccess(true);
				return result;
			}else{
				customerPropertyMapper.deleteById(customerPropertyId);
				result.setIsSuccess(true);
				return result;
			}
		}catch (Exception e) {
 			result.setMessage("删除产权信息失败，请重新操作");
 			return result;
 		}
	}

	@Override
	public Page<CustomerProperty> selectPageByEw(Page<CustomerProperty> pageM, EntityWrapper ew) {
		List<CustomerProperty> list = customerPropertyMapper.selectPageByEw(pageM,ew);
		pageM.setRecords(list);
		return pageM;
	}
}
