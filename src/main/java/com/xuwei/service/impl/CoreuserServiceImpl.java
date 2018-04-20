package com.xuwei.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.CoreUser;
import com.xuwei.bean.Organization;
import com.xuwei.mapper.CoreUserMapper;
import com.xuwei.mapper.OrganizationMapper;
import com.xuwei.service.CoreUserIService;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;


/**
 * <p>
 * ${table.comment} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2017-12-08
 */
@Service
public class CoreuserServiceImpl extends ServiceImpl<CoreUserMapper, CoreUser> implements CoreUserIService {
	
	@Resource(name="coreUserMapper")
	private CoreUserMapper userMapper;
	@Resource
	private OrganizationMapper organizationMapper;
	
	@Override
	public Page<CoreUser> selectByPage(Page<CoreUser> page, CoreUser user) {
		return null;
	}

	@Override
	public void findListByPageMap(CoreUser m, Long organizationId, Page<CoreUser> pageM) {
		if(organizationId!=null){
			Organization organization = organizationMapper.findById(organizationId);
			List<CoreUser> list = userMapper.findListByPageMap(m,organizationId,organization.getParentIds()+organizationId+"/",pageM);
			pageM.setRecords(list);
		}else{
			List<CoreUser> list = userMapper.findListByPageMap(m,null,null,pageM);
			pageM.setRecords(list);
		}
	}

	@Override
	public ServiceResult delete(String ids) {
		ServiceResult result=new ServiceResult(false);
		String[] split = StringUtil.split(ids);
		int count=userMapper.delete(split);
		if(count>0){
			result.setIsSuccess(true);
		}
		return result;
	}

	@Override
	public ServiceResult resetPwd(String ids) {
		ServiceResult result=new ServiceResult(false);
		String[] split = StringUtil.split(ids);
		int count=userMapper.resetPwd(split);
		if(count>0){
			result.setIsSuccess(true);
		}
		return result;
	}
	
}
