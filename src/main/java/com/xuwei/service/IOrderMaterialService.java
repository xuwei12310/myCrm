package com.xuwei.service;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.OrderMaterial;
import com.xuwei.util.ServiceResult;

/**
 * @description: 订单_相关材料服务类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月18日 10:18:12
 * @author: hhd
 * @version: 1.0
 */
public interface IOrderMaterialService extends IService<OrderMaterial> {
    /**
     * 新增相关材料
     * @param m
     * @param file
     * @return
     */
    ServiceResult add(OrderMaterial m, MultipartFile file);
    /**
     * 修改相关材料
     * @param m
     * @param file
     * @return
     */
    ServiceResult edit(OrderMaterial m, MultipartFile file);

    /**
     *
     * @description: 获取相关材料（微信端）
     * @createTime: 2017年9月25日 下午4:43:13
     * @author: hhd
     * @param page
     * @param totalNum
     * @param orderid
     * @return
     */
    ServiceResult getMaterialByPage(Page<OrderMaterial> page, Long totalNum, Long orderid);
}
