package com.xuwei.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xuwei.bean.OrderBank;

/**
 * <p>
  * 订单_合作银行 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2017-09-14
 */
public interface OrderBankMapper extends BaseMapper<OrderBank> {
    /**
     * 查询订单合作银行信息  显示支行信息
     * @param orderId
     * @return
     */
    List<Map<String,Object>> queryByPage(Pagination page, @Param("orderId")Long orderId );

	/**
	 * @description:
	 * @createTime 2017年9月18日 下午4:37:25
	 * @author xw
	 * @param id
	 * @return
	 */
	List<OrderBank> selectByOrderId(@Param("id")Long id);
	/**
	 * 查询订单合作银行信息  显示支行信息
	 * @param orderId
	 * @return
	 */
	List<OrderBank> selectListByPage(Pagination page, @Param("orderId")Long orderId );
	/**
	 * 根据id查询银行详细信息
	 * @param id
	 * @return
	 */
	OrderBank getBankById(@Param("id")Long id);
}