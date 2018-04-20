package com.xuwei.service.impl;

import java.io.File;
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

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.Attach;
import com.xuwei.bean.ConsumerCar;
import com.xuwei.mapper.AttachMapper;
import com.xuwei.mapper.ConsumerCarMapper;
import com.xuwei.service.IConsumerCarService;
import com.xuwei.util.CommonUtil;
import com.xuwei.util.DateUtil;
import com.xuwei.util.FileUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;

/**
 * @description: 客户_车产服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月04日 09:45:08
 * @author: caw
 * @version: 1.0
 */
@Service
public class ConsumerCarServiceImpl extends ServiceImpl<ConsumerCarMapper, ConsumerCar> implements IConsumerCarService {
	@Resource
	private ConsumerCarMapper consumerCarMapper;
	@Resource
	private AttachMapper attachMapper;

	/**
	 * 新增
	 */
	@Transactional
	@Override
	public ServiceResult createConsumerCar(MultipartFile file, HttpServletRequest request, Model model, ConsumerCar m) {
		ServiceResult result = new ServiceResult(false);
    	HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("filext", CommonUtil.getAppProParam("project_files.image.type"));
		try{
			if(m.getValuation()!=null){
				if(m.getValuation().intValue()<0){
					result.setMessage("估值不能为负数");
					return result;
				}
			}
			m.setCreatorId(OperateUtils.getCurrentUserId());
			m.setCreateTime(DateUtil.getNowTimestampStr());
			if(StringUtils.isEmpty(file.getOriginalFilename())){
				int deleteCount = consumerCarMapper.insert(m);
    			if(deleteCount>0){
					result.setIsSuccess(true);
					return result;
				}else{
					result.setMessage("没新增记录");
					return result;
				}
			}else{
				Long data = fileUpload(file, extMap, "customerCar/");
				if(data==null){
    				result.setMessage("只允许上传"+extMap.get("filext")+"格式的文件");
                    return result;
    			}else{
        			m.setAttachId(data);
        			int deleteCount = consumerCarMapper.insert(m);
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
 			result.setMessage("新增车产信息失败，请重新操作");
 			return result;
 		} 
	}
	
	/**
	 * 修改
	 */
	@Transactional
	@Override
	public ServiceResult updateConsumerCar(MultipartFile file, HttpServletRequest request, Model model, ConsumerCar m) {
		ServiceResult result = new ServiceResult(false);
    	HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("filext", CommonUtil.getAppProParam("project_files.image.type"));
		try{
			if(m.getValuation()!=null){
				if(m.getValuation().intValue()<0){
					result.setMessage("估值不能为负数");
					return result;
				}
			}
			m.setLastModifierId(OperateUtils.getCurrentUserId());
			m.setLastModifyTime(DateUtil.getNowTimestampStr());
			if(StringUtils.isEmpty(file.getOriginalFilename())){
				int deleteCount = consumerCarMapper.updateById(m);
    			if(deleteCount>0){
					result.setIsSuccess(true);
					return result;
				}else{
					result.setMessage("没修改记录");
					return result;
				}
			}else{
				if (m.getAttachId()==null) {
					Long data = fileUpload(file, extMap, "customerCar/");
					if(data==null){
	    				result.setMessage("只允许上传"+extMap.get("filext")+"格式的文件");
	                    return result;
	    			}else{
	        			m.setAttachId(data);
	        			int deleteCount = consumerCarMapper.updateById(m);
	        			if(deleteCount>0){
	    					result.setIsSuccess(true);
	    					return result;
	    				}else{
	    					result.setMessage("没修改记录");
	    					return result;
	    				}
	    			}
				}else{
					Long data = fileUploadMod(file, extMap, "customerCar/",m.getAttachId());
					if(data==null){
        				result.setMessage("只允许上传"+extMap.get("filext")+"格式的文件");
                        return result;
        			}else if(data==0){
        				Long addData = fileUpload(file, extMap, "customerCar/");
        				if(addData==null){
            				result.setMessage("只允许上传"+extMap.get("filext")+"格式的文件");
                            return result;
            			}else{
            				m.setAttachId(data);
            				int deleteCount = consumerCarMapper.updateById(m);
    	        			if(deleteCount>0){
    	    					result.setIsSuccess(true);
    	    					return result;
    	    				}else{
    	    					result.setMessage("没修改记录");
    	    					return result;
    	    				}
            			}
        			}else{
        				int deleteCount = consumerCarMapper.updateById(m);
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
 			result.setMessage("修改车产信息失败，请重新操作");
 			return result;
 		}
	}
	
	/**
	 * 删除车产信息（包括附件）
	 */
	@Transactional
	@Override
	public ServiceResult deleteConsumerCar(List<String> idArray) {
		ServiceResult result = new ServiceResult(false);
		Long[] idData = new Long[idArray.size()];
		int i = 0;
		try{
			for(String data:idArray){
				ConsumerCar consumerCar = consumerCarMapper.selectById(data);
				if(consumerCar.getAttachId()!=null){
					idData[i] = consumerCar.getAttachId();
					i++;
				}
			}
			consumerCarMapper.deleteBatchIds(idArray);
			attachMapper.deleteBatchIds(Arrays.asList(idData));
			result.setIsSuccess(true);
			return result;
		}catch (Exception e) {
 			result.setMessage("删除产权信息失败，请重新操作");
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
     * 获取车产信息（微信端）
     */
	@Override
	public ServiceResult getConsumerCarList(Page<ConsumerCar> page, Long userId, Long totalNum, Long customerid) {
		ServiceResult result = new ServiceResult(false);
		List<ConsumerCar> list = consumerCarMapper.getConsumerCarList(page, customerid);
		if(page.getTotal()==totalNum && totalNum!=0){
			List<ConsumerCar> lists = new ArrayList<ConsumerCar>();
			result.addData("consumerCarlist", lists);
			result.setIsSuccess(true);
			return result;
		}
		result.addData("consumerCarlist", list);
		result.setIsSuccess(true);
		return result;
	}

	/**
	 * 新增车产信息（微信端）
	 */
	@Override
	public ServiceResult addCar(ConsumerCar m, Long userId) {
		ServiceResult result = new ServiceResult(false);
		m.setCreatorId(userId);
		m.setCreateTime(DateUtil.getNowTimestampStr());
		m.setLastModifierId(userId);
		m.setLastModifyTime(DateUtil.getNowTimestampStr());
		int data = consumerCarMapper.insert(m);
		if(data>0){
			result.setIsSuccess(true);
			return result;
		}else{
			result.setMessage("没新增记录");
			return result;
		}
	}
	
	/**
	 * 修改车产信息（微信端）
	 */
	@Override
	public ServiceResult ModCar(ConsumerCar m, Long userId) {
		ServiceResult result = new ServiceResult(false);
		m.setLastModifierId(userId);
		m.setLastModifyTime(DateUtil.getNowTimestampStr());
		int data = consumerCarMapper.updateAllColumnById(m);
		if(data>0){
			result.setIsSuccess(true);
			return result;
		}else{
			result.setMessage("没修改记录");
			return result;
		}
	}
	
	/**
	 * 删除车产信息（微信端）
	 */
	@Transactional
	@Override
	public ServiceResult delCar(Long carId) {
		ServiceResult result = new ServiceResult(false);
		try{
			Long attachId = null;
			ConsumerCar consumerCar = consumerCarMapper.selectById(carId);
			if(consumerCar.getAttachId()!=null){
				attachId = consumerCar.getAttachId();
				consumerCar.deleteById(carId);
				attachMapper.deleteById(attachId);
				result.setIsSuccess(true);
				return result;
			}else{
				consumerCar.deleteById(carId);
				result.setIsSuccess(true);
				return result;
			}
		}catch (Exception e) {
 			result.setMessage("删除车产信息失败，请重新操作");
 			return result;
 		}
	}
	
	
}
