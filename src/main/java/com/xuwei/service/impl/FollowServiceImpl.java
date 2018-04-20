package com.xuwei.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.Attach;
import com.xuwei.bean.Customer;
import com.xuwei.bean.Dict;
import com.xuwei.bean.Follow;
import com.xuwei.bean.FollowAttach;
import com.xuwei.bean.Todo;
import com.xuwei.bean.TodoCopyto;
import com.xuwei.bean.User;
import com.xuwei.mapper.AttachMapper;
import com.xuwei.mapper.CustomerMapper;
import com.xuwei.mapper.FollowAttachMapper;
import com.xuwei.mapper.FollowMapper;
import com.xuwei.service.IFollowService;
import com.xuwei.service.IMessageService;
import com.xuwei.service.ITodoCopytoService;
import com.xuwei.service.ITodoService;
import com.xuwei.util.CommonUtil;
import com.xuwei.util.DateUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;


/**
 * @description: 客户跟进服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年07月19日 16:31:12
 * @author: xw
 * @version: 1.0
 */
@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements IFollowService {

	
	@Resource
	private FollowMapper followMapper;
	@Resource 
	private AttachMapper attachMapper;
	@Resource
	private FollowAttachMapper followAttachMapper;
	@Resource
	private CustomerMapper customerMapper;
	@Resource
	private ITodoService todoService;
	@Resource
	private IMessageService messageService;
	@Resource
	private ITodoCopytoService todoCopytoService;
	@Override
	public void listByPage(Follow m, Page<Follow> pageM,String search,EntityWrapper<Follow> ew) {
		List<Follow> list=followMapper.listByPage(m,pageM,search,ew);
		pageM.setRecords(list);
	}

	@Override
	public Follow selectById(long id) {
		return followMapper.selectById(id);
	}

	@Override
	public String getAttachId(Long followId) {
		return followMapper.getAttachId(followId);
	}

	@Override
	@Transactional
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
    	EntityWrapper<FollowAttach> wrapper=new EntityWrapper<FollowAttach>();
    	wrapper.eq("attach_id", attachId);
    	followAttachMapper.delete(wrapper);
    	attachMapper.deleteById(attachId);
    	result.setIsSuccess(true);
		return result;
	}
	/**
	 * 
	 * @description:删除文件
	 * @createTime 2017年9月5日 上午11:39:53
	 * @author xw
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
	public boolean deleteByIds(String[] idArray) {
		int count=0;
		for (String string : idArray) {
			Long id=Long.valueOf(string);
			Follow follow = followMapper.selectById(id);
			if(follow.getCustomer().getCustomerStatus().getId().equals(follow.getAfterStatus().getId())){
				EntityWrapper<FollowAttach> ew=new EntityWrapper<FollowAttach>();
				ew.eq("follow_id", id);
				List<FollowAttach> list = followAttachMapper.selectByFollowId(id);
				followAttachMapper.delete(ew);
				if(list.size()>0){
					long attachId=list.get(0).getAttach().getId();
					attachMapper.deleteById(attachId);
				}
				Dict frontStatus=follow.getFrontStatus();
				Customer customer=follow.getCustomer();
				customer.setCustomerStatus(frontStatus);
				customerMapper.updateById(customer);
				followMapper.deleteById(id);
				count++;
			}
		}
        return count > 0;
    }

	@Override
	@Transactional
	public ServiceResult save(Follow m,Long userId,Long attachId,Todo t,String receiveTaskId,Boolean isActive) {
		ServiceResult result=new ServiceResult(false);
		if(m.getCustomer()==null){
			result.setMessage("客户不能为空");
			return result;
		}
		if(m.getFollowPersonnel()==null){
			result.setMessage("跟进人不能为空");
			return result;
		}
		if(m.getFollowTime()==null){
			result.setMessage("跟进时间不能为空");
			return result;
		}
		if(m.getFollowType()==null){
			result.setMessage("跟进方式不能为空");
			return result;
		}
		if(m.getFollowStage()==null){
			result.setMessage("跟进阶段不能为空");
			return result;
		}
		if(m.getAfterStatus()==null){
			result.setMessage("更新客户状态不能为空");
			return result;
		}
		Customer customer=customerMapper.selectById(m.getCustomer().getId());
		m.setFrontStatus(customer.getCustomerStatus());
		m.setSource(1);
		customer.setCustomerStatus(m.getAfterStatus());
		customer.setLastTrackTime(DateUtil.getNowTimesminutStr());
		customer.updateById();
		m.setCreateTime(DateUtil.getNowTimesminutStr());
		m.setCreatorId(userId);
		m.setLastModifierId(userId);
		m.setLastModifyTime(DateUtil.getNowTimesminutStr());
		m.insertAllColumn();
		if(attachId!=null){
			FollowAttach followAttach=new FollowAttach();
			Attach attach=new Attach();
			attach.setId(attachId);
			followAttach.setAttach(attach);
			followAttach.setFollow(m);
			followAttach.setCreateTime(DateUtil.getNowTimesminutStr());
			followAttach.setCreatorId(userId);
			followAttach.setLastModifierId(userId);
			followAttach.setCreateTime(DateUtil.getNowTimesminutStr());
			followAttach.insertAllColumn();
		}
		//添加任务
		if(isActive&&t!=null && t.getTaskContent()!=null){
			t.setStatus(1);
			t.setTaskType(1);
			t.setFollowId(m);
			t.setCustomerId(m.getCustomer());
			t.setCreateTime(DateUtil.getNowTimestampStr());
			t.setCreatorId(userId);
			t.setLastModifyTime(DateUtil.getNowTimestampStr());
			t.setLastModifierId(userId);
			todoService.insert(t);
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
					todoCopyto.setCreatorId(userId);
					todoCopyto.setLastModifyTime(DateUtil.getNowTimestampStr());
					todoCopyto.setLastModifierId(userId);
					todoCopytoService.insert(todoCopyto);
				}
			}
		}
		result.setIsSuccess(true);
		return result;
	}

	@Transactional
	@Override
	public ServiceResult update(Follow m, Long userId, Long attachId) {
		ServiceResult result=new ServiceResult(false);
		if(m.getFollowPersonnel()==null){
			result.setMessage("跟进人不能为空");
			return result;
		}
		if(m.getFollowTime()==null){
			result.setMessage("跟进时间不能为空");
			return result;
		}
		if(m.getFollowType()==null){
			result.setMessage("跟进方式不能为空");
			return result;
		}
		if(m.getFollowStage()==null){
			result.setMessage("跟进阶段不能为空");
			return result;
		}
		if(m.getAfterStatus()==null){
			result.setMessage("更新客户状态不能为空");
			return result;
		}
		Follow follow = followMapper.selectById(m.getId());
		Customer customer=customerMapper.selectById(follow.getCustomer().getId());
		follow.setFrontStatus(customer.getCustomerStatus());
		customer.setCustomerStatus(m.getAfterStatus());
		customer.setLastTrackTime(DateUtil.getNowTimesminutStr());
		customer.updateById();
		follow.setFollowPersonnel(m.getFollowPersonnel());
		follow.setFollowTime(m.getFollowTime());
		follow.setFollowType(m.getFollowType());
		follow.setFollowStage(m.getFollowStage());
		follow.setAfterStatus(m.getAfterStatus());
		follow.setFollowDetails(m.getFollowDetails());
		follow.setLastModifierId(userId);
		follow.setLastModifyTime(DateUtil.getNowTimesminutStr());
		followMapper.updateAllColumnById(follow);
		List<FollowAttach> listLF=followAttachMapper.selectByFollowId(follow.getId());
		for (FollowAttach fa:listLF){
			followAttachMapper.deleteById(fa.getId());
			attachMapper.deleteById(fa.getAttach().getId());
		}
		FollowAttach followAttach=new FollowAttach();
		Attach attach=new Attach();
		attach.setId(attachId);
		followAttach.setAttach(attach);
		followAttach.setFollow(follow);
		followAttach.setCreateTime(DateUtil.getNowTimesminutStr());
		followAttach.setCreatorId(userId);
		followAttach.setLastModifierId(userId);
		followAttach.setCreateTime(DateUtil.getNowTimesminutStr());
		followAttach.insertAllColumn();
		result.setIsSuccess(true);
		return result;
	}

	/**
	 * 根据跟进id修改附件信息
	 */
	@Override
	public boolean modAttach(Long followId, String attachIds) {
		String[] attachIdArray = StringUtil.split(attachIds);
		List<String> attachADDlist = new ArrayList<String>();
		
//		try {
			if(!StringUtils.isEmpty(attachIds)){
				for(int i=0;i<attachIdArray.length;i++){
					attachADDlist.add(attachIdArray[i]);
				}
				EntityWrapper<FollowAttach> ew = new EntityWrapper<FollowAttach>();
				ew.eq("follow_id", followId);
				List<FollowAttach> list = followAttachMapper.selectList(ew);
				if(list.size()>0){
					for(int i=0;i<attachADDlist.size();i++){
						for(FollowAttach fa:list){
							FollowAttach followAttach = followAttachMapper.selectByFAId(fa.getId());
							if(attachADDlist.get(i).equals(String.valueOf(followAttach.getAttach().getId()))){
								attachADDlist.remove(i);
							}
						}
					}
					if(attachADDlist.size()>0){
						for (int i = 0; i < attachADDlist.size(); i++) {
							FollowAttach followAttach = new FollowAttach();
							Attach attach = attachMapper.selectById(Long.parseLong(attachADDlist.get(i)));
							followAttach.setFollow(followMapper.selectById(followId));
							followAttach.setAttach(attach);
							followAttach.setCreateTime(DateUtil.getNowTimestampStr());
							followAttach.setCreatorId(OperateUtils.getCurrentUserId());
							followAttach.setLastModifyTime(DateUtil.getNowTimestampStr());
							followAttach.setLastModifierId(OperateUtils.getCurrentUserId());
							followAttachMapper.insert(followAttach);
						}
					}
				}else{
					for(int i=0;i<attachIdArray.length;i++){
						FollowAttach followAttach = new FollowAttach();
						Attach attach = attachMapper.selectById(Long.parseLong(attachIdArray[i]));
						followAttach.setFollow(followMapper.selectById(followId));
						followAttach.setAttach(attach);
						followAttach.setCreateTime(DateUtil.getNowTimestampStr());
						followAttach.setCreatorId(OperateUtils.getCurrentUserId());
						followAttach.setLastModifyTime(DateUtil.getNowTimestampStr());
						followAttach.setLastModifierId(OperateUtils.getCurrentUserId());
						followAttachMapper.insert(followAttach);
					}
				}
			}else{
				EntityWrapper<FollowAttach> ew = new EntityWrapper<FollowAttach>();
				ew.eq("follow_id", followId);
				List<FollowAttach> list = followAttachMapper.selectList(ew);
				for(FollowAttach fa:list){
					followAttachMapper.deleteById(fa.getId());
					attachMapper.deleteById(fa.getAttach().getId());
				}
			}
		
		return true;
	}

	
	/*@Override
	public ServiceResult downloadRecordWx(String mediaId,Long userId) {
		*//**
		 * 前台把照片上传微信服务器,后台通过接口把图片下载到自己服务器上
		 *//*
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isBlank(mediaId)){
			result.setMessage("图片ID为空");
			return result;
		}
		try {
			String resultT = WXUtils.getToken();
			JSONObject json = JSONObject.parseObject(resultT);
			String token = json.get("access_token").toString();
			// 服务器存图路径
			String basePath = CommonUtil.getAppProParam("project_files.base.path");
			String attachPath = CommonUtil.getAppProParam("project_files.attach.path");
			String receivePath = CommonUtil.getAppProParam("project_record.path");
	
			String savePath = basePath + attachPath + receivePath;
			String ext = WXUtils.downloadMediaFromWx(token,mediaId, savePath);
			Attach attach = new Attach();
			attach.setPath(attachPath + receivePath + mediaId +"."+ext);
			attach.setExtention(ext);
			attach.setName(mediaId+"."+ext);
			attach.setCreateTime(DateUtil.getNowTimesminutStr());
			attach.setCreatorId(userId);
			attach.setLastModifierId(userId);
			attach.setLastModifyTime(DateUtil.getNowTimesminutStr());
			attachMapper.insert(attach);
			result.addData("recordId", attach.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.setIsSuccess(true);
		return result;
	}*/

}
