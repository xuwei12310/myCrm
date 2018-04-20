package com.xuwei.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.Province;
import com.xuwei.mapper.ProvinceMapper;
import com.xuwei.service.IProvinceService;
import com.xuwei.util.ServiceResult;

/**
 * @description: 省服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年08月29日 10:49:14
 * @author: wwh
 * @version: 1.0
 */
@Service
public class ProvinceServiceImpl extends ServiceImpl<ProvinceMapper, Province> implements IProvinceService {

    @Resource
    private ProvinceMapper provinceMapper;

    public Integer maxArrayIndex() {
        return provinceMapper.maxArrayIndex() == null ? 0 : provinceMapper.maxArrayIndex();
    }

    public ServiceResult changeArray(Long srcId, Long destId) {
        ServiceResult result = new ServiceResult(false);
        if (srcId == null) {
            result.setMessage("请选中移动记录");
            return result;
        }
        if (destId == null) {
            result.setMessage("请指定对换记录");
            return result;
        }
        provinceMapper.changeArray(srcId, destId);
        result.setIsSuccess(true);
        return result;

    }

    public List<Province> getAllProvince() {
        return provinceMapper.getAllProvince();
    }
}
