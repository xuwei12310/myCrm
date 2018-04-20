package com.xuwei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.User;

/**
 * @description: Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年07月12日 16:40:25
 * @author: hhd
 * @version: 1.0
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * @description: 通过主键查询对象
     * @createTime: 2016年8月12日 下午5:37:15
     * @author: lys
     * @param id
     * @return
     */
    User findById(long id);
    /**
     * @description: 根据用户名查找用户
     * @createTime: 2016年8月17日 下午5:21:38
     * @author: lys
     * @param username
     * @return
     */
    User findByUsername(String username);
    /**
     * @description: 查询用户userId下的授权
     * @createTime: 2016年8月22日 下午4:20:20
     * @author: lys
     * @param userId
     * @return
     */
    List<String> findAuthorities(Long userId);
    /**
     * @description: 修改密码
     * @createTime: 2016年8月23日 下午2:40:51
     * @author: lys
     * @param m
     * @return
     */
    int updatePWD(User m);
    /**
     * @description: 取得非管理员用户
     * @createTime: 2016年9月25日 下午12:27:05
     * @author: lys
     * @return
     */
    List<User> getNotAdminUsers();
    /**
     * @description: 查询用户userId下的角色
     * @createTime: 2016年9月26日 上午10:14:04
     * @author: lys
     * @param userId
     * @return
     */
    List<String> findRoles(Long userId);
    /**
     *
     * @description: 校验人事编码是否存在
     * @createTime: 2017年3月1日 上午11:52:30
     * @author: xlf
     * @param m
     * @return
     */
    User getUser(User m);
    /**
     *
     * @description: 除本身外校验人事编码是否存在
     * @createTime: 2017年3月1日 上午11:52:30
     * @author: xlf
     * @param m
     * @return
     */
    User getUserEc(User m);
	/**
	 * @description:
	 * @createTime 2017年8月29日 下午2:32:49
	 * @author xw
	 * @param m
	 * @param organizationId
	 * @param string
	 * @param page
	 * @return
	 */
	List<User> findListByPageMap(@Param("m")User m,@Param("organizationId")Long organizationId,@Param("parentIds")String parentIds,com.baomidou.mybatisplus.plugins.Page<User> page);
	/**
	 * @description:
	 * @createTime 2017年8月29日 下午5:18:43
	 * @author xw
	 * @param user
	 * @return 
	 */
	int update(User user);
	/**
	 * @description:
	 * @createTime 2017年8月29日 下午5:42:35
	 * @author xw
	 * @param user
	 */
	void updateStatus(User user);
	/**
	 * @description:
	 * @createTime 2017年8月30日 上午9:06:46
	 * @author xw
	 * @param idArray
	 */
	void deleteByIdArray(@Param("idArray")String[] idArray);
	
	/**
	 * 
	 * @description:获取转交用户列表（出去当前拥有人） 
	 * @createTime: 2017年9月6日 上午10:19:38
	 * @author: caw
	 * @param pageM
	 * @param userName
	 * @param transferUserId
	 * @return
	 */
	List<User> getTransferUserByList(com.baomidou.mybatisplus.plugins.Page<User> pageM, @Param("userName")String userName,
			@Param("transferUserId")Long transferUserId);

	/**
	 *
	 * @description:获取转交用户列表（出去当前拥有人）
	 * @createTime: 2017年9月6日 上午10:19:38
	 * @author: hhd
	 * @param userId
	 * @return
	 */
	List<User> getManagerByUserId(@Param("userId")Long userId);
	
	/**
	 * 
	 * @description: 获取所有用户（微信端）
	 * @createTime: 2017年9月12日 下午4:42:47
	 * @author: caw
	 * @return
	 */
	List<User> getAllUser();
	
	/**
	 * 
	 * @description: 获取我的信息（微信端）
	 * @createTime: 2017年9月15日 上午11:19:28
	 * @author: caw
	 * @param userId
	 * @return
	 */
	User getMyInfo(@Param("userId")Long userId);

	/**
	 *
	 * @description:获取公司总经理
	 * @createTime: 2017年9月6日 上午10:19:38
	 * @author: hhd
	 * @return
	 */
	List<User> getCompanyManager();
	/**
	 * @description:获取财务
	 * @createTime 2017年9月21日 下午3:55:38
	 * @author xw
	 * @return
	 */
	List<User> getFinance();
	/**
	 * 
	 * @description: 判断权限
	 * @createTime: 2017年9月21日 下午5:12:53
	 * @author: caw
	 * @param userId
	 * @param identity
	 * @return
	 */
	String getIdentityInfo(@Param("userId")Long userId, @Param("identity")String identity);


	/**
	 *
	 * @description: 获取我的信息（微信端）
	 * @createTime: 2017年9月15日 上午11:19:28
	 * @author: caw
	 * @param userId
	 * @return
	 */
	User getUserInfo(@Param("userId")Long userId);
	/**
	 * @description:查询排除管理员的用户
	 * @createTime 2017年10月12日 下午3:39:20
	 * @author xw
	 * @param m
	 * @param pageM
	 * @return
	 */
	List<User> findUserList(@Param("m")User m, Page<User> pageM);

}