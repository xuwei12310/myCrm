package com.xuwei.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.Organization;
import com.xuwei.bean.User;
import com.xuwei.mapper.OrganizationMapper;
import com.xuwei.mapper.UserMapper;
import com.xuwei.service.IUserRoleService;
import com.xuwei.service.IUserService;
import com.xuwei.util.CommonUtil;
import com.xuwei.util.DateUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.PasswordUtil;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;

/**
 * @description: 用户实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年07月12日 16:40:25
 * @author: hhd
 * @version: 1.0
 */
@Service("UserServiceImpl")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    private UserMapper userDao;
    @Resource
    private OrganizationMapper organizationDao;
    @Resource
    private IUserRoleService userRoleService;
    @Override
    public ServiceResult create(User m) {
        ServiceResult result = new ServiceResult(false);
        String password = CommonUtil.getAppProParam("pwd.default");
        String pwdPrefix = PasswordUtil.getRandom(Integer.parseInt(CommonUtil.getAppProParam("pwd.from")), Integer.parseInt(CommonUtil.getAppProParam("pwd.distance")));
        String pwdSuffix = PasswordUtil.getRandom(Integer.parseInt(CommonUtil.getAppProParam("pwd.from")), Integer.parseInt(CommonUtil.getAppProParam("pwd.distance")));

        m.setPassword(PasswordUtil.getPwd(pwdPrefix , password, pwdSuffix));
        m.setPwdPrefix(pwdPrefix);
        m.setPwdSuffix(pwdSuffix);
        m.setIsLock(0);
        m.setStatus(1);
        m.setCreateTime(DateUtil.getNowTimestampStr());
        m.setCreatorId(OperateUtils.getCurrentUserId());
        m.setLastModifyTime(DateUtil.getNowTimestampStr());
        m.setLastModifierId(OperateUtils.getCurrentUserId());
        User u=userDao.getUser(m);
        if(u!=null){
            result.setMessage("该人事编码已存在，请重新填写");
            return result;
        }
        int insertCount = userDao.insert(m);
        if(insertCount>0){
        	User user = userDao.getUser(m);
        	if(!StringUtils.isEmpty(m.getRoleIds())){
				result = userRoleService.grantRole(user.getId(), m.getRoleIds().substring(0,m.getRoleIds().length()-1));
			}
            result.setIsSuccess(true);
            return result;
        }else{
            result.setMessage("未保存");
            return result;
        }
    }

    @Override
    public ServiceResult update(User m) {
        ServiceResult result = new ServiceResult(false);
        if(m==null||m.getId()==null){
            result.setMessage("请指定要修改记录");
            return result;
        }
        User oldm = userDao.findById(m.getId());
        if(oldm == null){
            result.setMessage("您要修改记录已经不存在，修改失败！");
            return result;
        }
        //m.setWechat(oldm.getWechat());
        //m.setOrganization(oldm.getOrganization());
        m.setPassword(oldm.getPassword());//密码
        m.setPwdPrefix(oldm.getPwdPrefix());
        m.setPwdSuffix(oldm.getPwdSuffix());
        m.setIsLock(oldm.getIsLock());
        m.setLockTime(oldm.getLockTime());
        m.setLoginCount(oldm.getLoginCount());
        m.setLoginFailureCount(oldm.getLoginFailureCount());
        m.setLoginTime(oldm.getLoginTime());
        m.setLastModifyTime(DateUtil.getNowTimestampStr());
        m.setLastModifierId(OperateUtils.getCurrentUserId());
        User u=userDao.getUserEc(m);
        if(u!=null){
            result.setMessage("用户名已存在，请重新填写");
            return result;
        }
        int insertCount = userDao.update(m);
        if(insertCount>0){
            result.setIsSuccess(true);
            //修改职务
            if(m.getRoleIds()!=null && m.getRoleIds()!=""){
            	result = userRoleService.grantRole(m.getId(),m.getRoleIds().substring(0,m.getRoleIds().length()-1));
            }
            return result;
        }else{
            result.setMessage("未保存");
            return result;
        }
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public List<String> findAuthorities(Long userId) {
        return userDao.findAuthorities(userId);
    }

    @Override
    public ServiceResult updatePWD(User user, String oldPassword, String newPassword) {
        ServiceResult result = new ServiceResult(false);
        if(user==null||user.getId()==null){
            result.setMessage("请指定修改的用户");
            return result;
        }
        if(StringUtils.isBlank(oldPassword)){
            result.setMessage("旧密码不能为空");
            return result;
        }
        if(StringUtils.isBlank(newPassword)){
            result.setMessage("新密码不能为空");
            return result;
        }
        User oldM = userDao.findById(user.getId());
        if(oldM==null||oldM.getId()==null){
            result.setMessage("请指定修改的用户");
            return result;
        }

        if(!StringUtils.equals(PasswordUtil.getPwd(oldM.getPwdPrefix() , oldPassword, oldM.getPwdSuffix()), oldM.getPassword())){
            result.setMessage("原密码错误");
            return result;
        }
        if(!CommonUtil.valdateUsepwd(newPassword)){
            result.setMessage("密码长度不小于6位，密码不能全为数字或字母，请检查新密码");
            return result;
        }
        String newPasswordMD5 = PasswordUtil.getPwd(oldM.getPwdPrefix() , newPassword, oldM.getPwdSuffix());
        oldM.setPassword(newPasswordMD5);
        oldM.setLastModifyTime(DateUtil.getNowTimestampStr());
        oldM.setLastModifierId(OperateUtils.getCurrentUserId());
        userDao.updatePWD(oldM);
        //User currentUser = this.selectById(OperateUtils.getCurrentUser().getId());
        result.setIsSuccess(true);
        return result;
    }
    
	@Override
	public ServiceResult mulUpdateStatus(String ids, Integer status) {
		ServiceResult result = new ServiceResult(false);
		String[] idArray = StringUtil.split(ids);
		int mulUpdateCount = 0;
		if (idArray == null || idArray.length == 0) {
			result.setMessage("请选择要修改的数据行！");
			return result;
		}
		if (status == null) {
			result.setMessage("请选择要修改的状态！");
			return result;
		}
		for (String id : idArray) {
			User user = userDao.findById(Long.parseLong(id));
			user.setStatus(status);
			user.setLastModifierId(OperateUtils.getCurrentUserId());
			user.setLastModifyTime(DateUtil.getNowTimestampStr());
			userDao.updateStatus(user);
			mulUpdateCount++;
		}
		if (mulUpdateCount > 0) {
			result.setIsSuccess(true);
			return result;
		} else {
			result.setMessage("没修改状态的记录！");
			return result;
		}
	}

	@Override
    public List<String> findRoles(Long userId) {
        return userDao.findRoles(userId);
    }

    @Override
    public List<User> getNotAdminUsers() {
        List<User> list = userDao.getNotAdminUsers();
        return list;
    }



    @Override
    public ServiceResult resetPwd(String ids) {
        ServiceResult result = new ServiceResult(false);
        String[] idArray = StringUtil.split(ids);
        if(idArray==null||idArray.length==0){
            result.setMessage("请选择要重置密码的数据行");
            return result;
        }
        for(String id:idArray){
            User oldM = userDao.findById(Long.parseLong(id));
            if(oldM==null||oldM.getId()==null){
                result.setMessage("请指定修改的用户");
                return result;
            }
            String password = CommonUtil.getAppProParam("pwd.default");

            oldM.setPassword(PasswordUtil.getPwd(oldM.getPwdPrefix() , password, oldM.getPwdSuffix()));
            oldM.setLastModifyTime(DateUtil.getNowTimestampStr());
            oldM.setLastModifierId(OperateUtils.getCurrentUserId());
            userDao.updatePWD(oldM);
        }
        result.setIsSuccess(true);
        //用户批量重置密码 日志添加
       // User currentUser = this.selectById(OperateUtils.getCurrentUser().getId());
        //LogUtils.logUser("用户模块","重置密码",currentUser.getId().toString(),"重置密码ids->"+ids,currentUser.getName()+"重置密码",currentUser.getId().toString());
        return result;
    }

	@Override
	public void findListByPageMap(User m, Long organizationId,com.baomidou.mybatisplus.plugins.Page<User> page) {
		if(organizationId!=null){
			Organization organization = organizationDao.findById(organizationId);
			List<User> list = userDao.findListByPageMap(m,organizationId,organization.getParentIds()+organizationId+"/",page);
			page.setRecords(list);
		}else{
			List<User> list = userDao.findListByPageMap(m,null,null,page);
			page.setRecords(list);
		}
	}
	
	@Override
	public void findUserList(User m, com.baomidou.mybatisplus.plugins.Page<User> pageM) {
			List<User> list = userDao.findUserList(m,pageM);
			pageM.setRecords(list);
	}

	@Transactional
	@Override
	public ServiceResult delete(String ids) {
		 ServiceResult result = new ServiceResult(false);
	     String[] idArray = StringUtil.split(ids);
	     if(idArray==null||idArray.length==0){
            result.setMessage("请选择要删除的数据行");
            return result;
	     }
        for(String id:idArray){
            User oldM = userDao.findById(Long.parseLong(id));
            if(oldM==null||oldM.getId()==null){
                result.setMessage("请指定删除的用户");
                return result;
            }
        }
        //删除用户权限
        userRoleService.deleteByUserId(idArray);
        //删除用户
        userDao.deleteByIdArray(idArray);
        result.setIsSuccess(true);
	    return result;
	}

	/**
	 * 获取当前用户信息
	 */
	@Override
	public User userInfo() {
		return userDao.findById(OperateUtils.getCurrentUserId());
	}

	/**
	 * 获取转交用户列表（出去当前拥有人）
	 */
	@Override
	public List<User> getTransferUserByList(com.baomidou.mybatisplus.plugins.Page<User> pageM, String userName,
			Long transferUserId) {
		return userDao.getTransferUserByList(pageM, userName, transferUserId);
	}

    @Override
    public List<User> getManagerByUserId(Long userId) {
        return userDao.getManagerByUserId(userId);
    }

    
	@Override
	public List<User> getFinance() {
		return userDao.getFinance();
	}

	/**
	 * 登录验证
	 */
	@Override
	public ServiceResult loginValidate(String account, String password, String host) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isBlank(account)){
			result.setMessage("请输入账号");
			return result;
		}
		if(StringUtils.isBlank(password)){
			result.setMessage("请输入密码");
			return result;
		}
		User user = userDao.findByUsername(account);
		if (user == null){
			result.setMessage("帐号不存在");
			return result;
		}
		if (new Integer(0).equals(user.getStatus())){
			result.setMessage("帐号已禁用");
			return result;
		}
		int sysAccountLockTime = CommonUtil.getAppProParam("sys.accountLockTime")==null?10:Integer.parseInt(CommonUtil.getAppProParam("sys.accountLockTime"));
		if (new Integer(1).equals(user.getIsLock())){
			Date lockTime = user.getLockTime();
			Date deblockingTime = DateUtils.addMinutes(lockTime, sysAccountLockTime);
			if (new Date().after(deblockingTime)) {
				user.setLoginFailureCount(Integer.valueOf(0));
				user.setIsLock(0);
				user.setLockTime(null);
				userDao.update(user);
			} else {
				result.setMessage("帐号已锁定");
				return result;
			}
		} else {
			user.setLoginFailureCount(Integer.valueOf(0));
			user.setIsLock(0);
			user.setLockTime(null);
			userDao.update(user);
		}
		if(!matches(user,password)){
			int sysAccountLockCount = CommonUtil.getAppProParam("sys.accountLockCount")==null?5:Integer.parseInt(CommonUtil.getAppProParam("sys.accountLockCount"));
			int loginFailureCount = user.getLoginFailureCount().intValue() + 1;
			if (loginFailureCount >= sysAccountLockCount) {
				user.setIsLock(1);
				user.setLoginTime(new Date());
				user.setLockTime(new Date());
			}
			user.setLoginFailureCount(loginFailureCount);
			userDao.update(user);
			result.setMessage("帐号密码不正确");
			return result;
		}
		user.setLoginTime(new Date());
		user.setLoginFailureCount(0);
		userDao.update(user);
		result.addData("userId", user.getId());
		result.setIsSuccess(true);
		return result;
	}

	public boolean matches(User user, String newPassword) {
        return user.getPassword().equals(PasswordUtil.getPwd(user.getPwdPrefix() , newPassword, user.getPwdSuffix()));
    }
	
	/**
	 * 获取用户信息
	 */
	@Override
	public User getUserInfo(Long userid) {
		return userDao.findById(userid);
	}

	/**
	 * 获取所有用户
	 */
	@Override
	public List<User> getAllUser() {
		return userDao.getAllUser();
	}

	/**
	 * 获取我的信息（微信端）
	 */
	@Override
	public User getMyInfo(Long userId) {
		return userDao.getMyInfo(userId);
	}

    @Override
    public List<User> getCompanyManager() {
        return userDao.getCompanyManager();
    }
    
    /**
     * 判断权限
     */
	@Override
	public boolean getIdentityInfo(Long userId, String identity) {
		String data = userDao.getIdentityInfo(userId, identity);
		if(StringUtils.isEmpty(data)){
			return false;
		}else{
			return true;
		}
	}

    @Override
    public User selectUserInfo(Long userId) {
        return userDao.getUserInfo(userId);
    }

/*	@Override
	public ServiceResult getSignatureInfo(String url) {
		ServiceResult result = new ServiceResult(true);
		String resultTs;
		try {
			String resultT = WXUtils.getToken();
			JSONObject json = JSONObject.parseObject(resultT);
			String token = json.get("access_token").toString();
		    String appId = CommonUtil.getAppProParam("wx.appId");
			if(StringUtils.isNotBlank(token)){
				resultTs = WXUtils.getTicket(token);
				JSONObject jsons = JSONObject.parseObject(resultTs);
				String ticket = jsons.get("ticket").toString();
				String noncestr = UUID.randomUUID().toString();
				noncestr = noncestr.replace("-", "");
				String timestamp =new Date().getTime()/1000+"";
			    String signature =  WXUtils.createSignature(ticket, noncestr, timestamp, url);
			    result.addData("appId", appId);
			    result.addData("ticket", ticket);
			    result.addData("noncestr", noncestr);
			    result.addData("timestamp", timestamp);
			    result.addData("signature", signature);
			    result.setIsSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}*/

}
