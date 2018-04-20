package com.xuwei.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.CoreUser;
import com.xuwei.util.ServiceResult;

/**
 * <p>
 * 用户管理 服务类
 * </p>
 *
 * @author xw
 * @since 2017-12-08
 */
public interface CoreUserIService extends IService<CoreUser> {

	/**
	 * @description:分页查询用户
	 * @createTime 2017年12月8日 下午4:33:47
	 * @author xw
	 * @param page
	 * @param user
	 * @return
	 */
	Page<CoreUser> selectByPage(Page<CoreUser> page, CoreUser user);

	/**
	 * @description:根据部门查找用户
	 * @createTime 2017年12月21日 上午9:33:07
	 * @author xw
	 * @param m
	 * @param organizationId
	 * @param pageM
	 */
	void findListByPageMap(CoreUser m, Long organizationId, Page<CoreUser> pageM);

	/**
	 * @description:批量删除
	 * @createTime 2017年12月21日 下午3:05:38
	 * @author xw
	 * @param ids
	 * @return
	 */
	ServiceResult delete(String ids);

	/**
	 * @description:重置密码
	 * @createTime 2017年12月21日 下午3:06:08
	 * @author xw
	 * @param ids
	 * @return
	 */
	ServiceResult resetPwd(String ids);
	
}
