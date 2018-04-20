package com.xuwei.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xuwei.bean.Product;


/**
 * @description: Mapper接口
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年12月13日 10:12:59
 * @author: xw
 * @version: 1.0
 */
public interface ProductMapper extends BaseMapper<Product> {

	/**
	 * @description:获取图标X轴
	 * @createTime 2017年12月21日 下午7:09:50
	 * @author xw
	 * @return
	 */
	List<String> getChartX();

	/**
	 * @description:获取图标Y轴
	 * @createTime 2017年12月21日 下午7:58:12
	 * @author xw
	 * @return
	 */
	List<String> getChartY();

}