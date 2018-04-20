package com.xuwei.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.Role;
import com.xuwei.mapper.RoleMapper;
import com.xuwei.service.IRoleService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;

/**
 * @description: 角色服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年07月12日 11:07:39
 * @author: hhd
 * @version: 1.0
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    RoleMapper roleDao;

    //@CacheEvict(value={"admin-authorization","admin-menus","admin-tree-menus"},allEntries=true)
    @Override
    public ServiceResult create(Role m) {
        ServiceResult result = new ServiceResult(false);
        EntityWrapper<Role> ew = new EntityWrapper<Role>();
        ew.eq("name",m.getName());
        List<Role> roleList = roleDao.selectList(ew);
        if(roleList!=null && roleList.size()>0){
            result.setMessage("角色名字存在");
            return result;
        }
        ew = new EntityWrapper<Role>();
        ew.eq("role",m.getRole());
        roleList = roleDao.selectList(ew);
        if(roleList!=null && roleList.size()>0){
            result.setMessage("角色标识存在");
            return result;
        }
        m.setIsSys("否");
        m.setCreateTime(DateUtil.getNowTimestampStr());
        m.setCreatorId(OperateUtils.getCurrentUserId());
        m.setLastModifyTime(DateUtil.getNowTimestampStr());
        m.setLastModifierId(OperateUtils.getCurrentUserId());
        int insertCount = roleDao.insert(m);
        if(insertCount>0){
            result.setIsSuccess(true);
            return result;
        }else{
            result.setMessage("未保存");
            return result;
        }
    }
   // @CacheEvict(value={"admin-authorization","admin-menus","admin-tree-menus"},allEntries=true)
    @Override
    public ServiceResult update(Role m) {
        ServiceResult result = new ServiceResult(false);
        if(m==null||m.getId()==null){
            result.setMessage("请指定要修改记录");
            return result;
        }

        Role roleOld = roleDao.selectById(m.getId());
        EntityWrapper<Role> ew = new EntityWrapper<Role>();
        ew.eq("name",m.getName());
        List<Role> roleList = roleDao.selectList(ew);
        if(roleList!=null && roleList.size()>0){
            if(!roleOld.getName().equals(roleList.get(0).getName())) {
                result.setMessage("角色名字存在");
                return result;
            }
        }
        ew = new EntityWrapper<Role>();
        ew.eq("role",m.getRole());
        roleList = roleDao.selectList(ew);
        if(roleList!=null && roleList.size()>0){
            if(!roleOld.getRole().equals(roleList.get(0).getRole())) {
                result.setMessage("角色标识存在");
                return result;
            }
        }
        m.setIsSys(roleOld.getIsSys());
        m.setLastModifyTime(DateUtil.getNowTimestampStr());
        m.setLastModifierId(OperateUtils.getCurrentUserId());
        int insertCount = roleDao.updateAllColumnById(m);
        if(insertCount>0){
            result.setIsSuccess(true);
            return result;
        }else{
            result.setMessage("未保存");
            return result;
        }
    }
   // @CacheEvict(value={"admin-authorization","admin-menus","admin-tree-menus"},allEntries=true)
    @Override
    public ServiceResult mulDelete(String ids) {
        ServiceResult result = new ServiceResult(false);
        String[] idArray = StringUtil.split(ids);
        if(idArray==null||idArray.length==0){
            result.setMessage("请选择要删除的数据行");
            return result;
        }
        int deleteCount = roleDao.mulDelete(idArray);
        if(deleteCount>0){
            result.setIsSuccess(true);
            return result;
        }else{
            result.setMessage("没删除记录");
            return result;
        }
    }
	/* (non-Javadoc)
	 * @see com.fjlzit.service.IRoleService#findAll()
	 */
	@Override
	public List<Role> findAll() {
		return roleDao.findAll();
	}
    
}
