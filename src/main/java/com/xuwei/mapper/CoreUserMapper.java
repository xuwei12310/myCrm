package com.xuwei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xuwei.bean.CoreUser;


/**
 * <p>
  * ${table.comment} Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2017-12-08
 */
public interface CoreUserMapper extends BaseMapper<CoreUser> {

	/**
	 * @description:
	 * @createTime 2017年12月21日 上午9:37:51
	 * @author xw
	 * @param m
	 * @param organizationId
	 * @param string
	 * @param pageM
	 * @return
	 */
	List<CoreUser> findListByPageMap(@Param("m")CoreUser m,@Param("organizationId")Long organizationId,@Param("parentIds")String parentIds,Page<CoreUser> page);

	/**
	 * @description:批量删除
	 * @createTime 2017年12月21日 下午3:07:29
	 * @author xw
	 * @param split
	 * @return
	 */
	int delete(@Param("ids")String[] split);

	/**
	 * @description:重置密码
	 * @createTime 2017年12月21日 下午3:10:18
	 * @author xw
	 * @param split
	 * @return
	 */
	int resetPwd(@Param("ids")String[] split);

}