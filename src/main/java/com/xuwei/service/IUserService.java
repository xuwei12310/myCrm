package com.xuwei.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.User;
import com.xuwei.util.ServiceResult;

/**
 * @description: 用户模块
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年07月12日 16:40:25
 * @author: hhd
 * @version: 1.0
 */
public interface IUserService extends IService<User> {
    /**
     * @description: 根据用户名查找用户
     * @createTime: 2016年8月17日 下午5:20:37
     * @author: lys
     * @param username
     * @return
     */
    User findByUsername(String username);
    /**
     * @description: 取得用户的授权权限
     * @createTime: 2016年8月22日 下午3:53:31
     * @author: lys
     * @param userId
     * @return
     */
    List<String> findAuthorities(Long userId);
    /**
     * @description: 修改密码
     * @createTime: 2016年8月23日 下午2:25:05
     * @author: lys
     * @param user
     * @param oldPassword
     * @param newPassword
     * @return
     */
    ServiceResult updatePWD(User user, String oldPassword, String newPassword);
    /**
     * @description: 取得非管理员用户
     * @createTime: 2016年9月25日 下午12:25:34
     * @author: lys
     * @return
     */
    List<User> getNotAdminUsers();
    /**
     * @description: 取得用户拥有的角色
     * @createTime: 2016年9月26日 上午10:12:58
     * @author: lys
     * @param userId
     * @return
     */
    List<String> findRoles(Long userId);

    /**
     * @description: 重置用户密码
     * @createTime: 2016年9月29日 下午1:12:32
     * @author: wsh
     * @param ids
     * @return
     */
    ServiceResult resetPwd(String ids);
    /**
     * @description: 插入对象
     * @createTime: 2016年8月12日 下午5:43:40
     * @author: lys
     * @param m
     * @return
     */
    ServiceResult create(User m);
    /**
     * @description: 更新对象
     * @createTime: 2016年8月12日 下午5:44:07
     * @author: lys
     * @param m
     * @return
     */
    ServiceResult update(User m);
	/**
	 * @description:查询所有用户
	 * @createTime 2017年8月29日 下午2:30:25
	 * @author xw
	 * @param m
	 * @param organizationId
	 * @param page
	 */
	void findListByPageMap(User m, Long organizationId, com.baomidou.mybatisplus.plugins.Page<User> page);
	/**
	 * @description:更新用户
	 * @createTime 2017年8月29日 下午5:17:37
	 * @author xw
	 * @param ids
	 * @param status
	 * @return
	 */
	ServiceResult mulUpdateStatus(String ids, Integer status);
	/**
	 * @description:批量删除用户名
	 * @createTime 2017年8月30日 上午8:56:15
	 * @author xw
	 * @param ids
	 * @return
	 */
	ServiceResult delete(String ids);
	
	/**
	 * 
	 * @description: 获取当前用户信息
	 * @createTime: 2017年9月1日 下午3:50:36
	 * @author: caw
	 * @return
	 */
	User userInfo();
	
	/**
	 * 
	 * @description: 获取转交用户列表（出去当前拥有人）
	 * @createTime: 2017年9月6日 上午10:16:34
	 * @author: caw
	 * @param pageM
	 * @param userName
	 * @param transferUserId
	 * @return
	 */
	List<User> getTransferUserByList(com.baomidou.mybatisplus.plugins.Page<User> pageM, String userName, Long transferUserId);


	/**
	 *
	 * @description: 获取用户的经理
	 * @createTime: 2017年9月7日 上午16:16:34
	 * @author: hhd
	 * @param userId
	 * @return
	 */
	List<User> getManagerByUserId(@Param("userId")Long userId);

	/**
	 * 登录验证（微信端）
	 * @description:
	 * @createTime: 2017年9月8日 上午11:21:21
	 * @author: caw
	 * @param account
	 * @param password
	 * @param host
	 * @return
	 */
	ServiceResult loginValidate(String account, String password, String host);
	
	/**
	 * 
	 * @description: 获取用户信息
	 * @createTime: 2017年9月12日 下午2:27:11
	 * @author: caw
	 * @param userid
	 * @return
	 */
	User getUserInfo(Long userid);
	
	/**
	 * 
	 * @description: 获取所有用户
	 * @createTime: 2017年9月12日 下午4:40:38
	 * @author: caw
	 * @return
	 */
	List<User> getAllUser();
	
	/**
	 * 
	 * @description: 获取我的信息（微信端）
	 * @createTime: 2017年9月15日 上午11:20:08
	 * @author: caw
	 * @param userId
	 * @return
	 */
	User getMyInfo(Long userId);

	/**
	 *
	 * @description: 获取公司总经理
	 * @createTime: 2017年9月7日 上午16:16:34
	 * @author: hhd
	 * @return
	 */
	List<User> getCompanyManager();
	/**
	 * @description:获取财务
	 * @createTime 2017年9月21日 下午3:54:52
	 * @author xw
	 * @return
	 */
	List<User> getFinance();
	
	/**
	 * 
	 * @description: 判断权限
	 * @createTime: 2017年9月21日 下午5:08:46
	 * @author: caw
	 * @param userId
	 * @param identity
	 * @return
	 */
	boolean getIdentityInfo(Long userId,String identity);
	/**
	 *
	 * @description: 获取我的信息 包括公司 职务等
	 * @createTime: 2017年10月9日 下午6:20:08
	 * @author: hhd
	 * @param userId
	 * @return
	 */
	User selectUserInfo(Long userId);
	/**
	 * @description:查询用户列表排除管理员
	 * @createTime 2017年10月12日 下午3:34:39
	 * @author xw
	 * @param m
	 * @param object
	 * @param pageM
	 */
	void findUserList(User m,Page<User> pageM);
	
	/**
	 * 
	 * @description: 获取微信签名
	 * @createTime: 2017年10月23日 下午3:13:26
	 * @author: caw
	 * @param url
	 * @return
	 */
	/*ServiceResult getSignatureInfo(String url);*/
}
