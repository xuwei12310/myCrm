package com.xuwei.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.Attach;
import com.xuwei.bean.OrderMaterial;
import com.xuwei.mapper.AttachMapper;
import com.xuwei.mapper.OrderMaterialMapper;
import com.xuwei.service.IOrderMaterialService;
import com.xuwei.util.CommonUtil;
import com.xuwei.util.DateUtil;
import com.xuwei.util.FileUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;

/**
 * @description: 订单_相关材料服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年09月18日 10:18:12
 * @author: hhd
 * @version: 1.0
 */
@Service
public class OrderMaterialServiceImpl extends ServiceImpl<OrderMaterialMapper, OrderMaterial> implements IOrderMaterialService {

    @Resource
    private OrderMaterialMapper orderMaterialMapper;
    @Resource
    private AttachMapper attachMapper;

    @Transactional
    @Override
    public ServiceResult add(OrderMaterial m, MultipartFile file) {
        ServiceResult result = new ServiceResult(false);
        HashMap<String, String> extMap = new HashMap<String, String>();
        extMap.put("filext", CommonUtil.getAppProParam("project_files.image.type"));
        try{
            m.setCreatorId(OperateUtils.getCurrentUserId());
            m.setCreateTime(DateUtil.getNowTimestampStr());
            m.setLastModifierId(OperateUtils.getCurrentUserId());
            m.setLastModifyTime(DateUtil.getNowTimestampStr());
            if(StringUtils.isEmpty(file.getOriginalFilename())){
                int addNumber = orderMaterialMapper.insertAllColumn(m);
                if(addNumber>0){
                    result.setIsSuccess(true);
                    return result;
                }
            }else{
                Long data = fileUpload(file, extMap, "order/",null);
                if(data==null){
                    result.setMessage("只允许上传"+extMap.get("filext")+"格式的文件");
                    return result;
                }else{
                    m.setAttachId(data);
                    int addNumber = orderMaterialMapper.insertAllColumn(m);
                    if(addNumber>0){
                        result.setIsSuccess(true);
                        return result;
                    }else{
                        result.setMessage("没修改记录");
                        return result;
                    }
                }
            }
        }catch (Exception e) {
            result.setMessage("新增相关资料失败，请重新操作");
            return result;
        }
        return result;
    }

    @Transactional
    @Override
    public ServiceResult edit(OrderMaterial m, MultipartFile file) {
        ServiceResult result = new ServiceResult(false);
        HashMap<String, String> extMap = new HashMap<>();
        extMap.put("filext", CommonUtil.getAppProParam("project_files.image.type"));
        try{
            OrderMaterial orderMaterial = orderMaterialMapper.selectById(m.getId());
            orderMaterial.setName(m.getName());
            orderMaterial.setNotice(m.getNotice());
            orderMaterial.setNumber(m.getNumber());
            orderMaterial.setIsFinish(m.getIsFinish());
            orderMaterial.setLastModifierId(OperateUtils.getCurrentUserId());
            orderMaterial.setLastModifyTime(DateUtil.getNowTimestampStr());
            if(StringUtils.isEmpty(file.getOriginalFilename())){
                int addNumber = orderMaterialMapper.updateAllColumnById(orderMaterial);
                if(addNumber>0){
                    result.setIsSuccess(true);
                    return result;
                }
            }else{
                Long data = fileUpload(file, extMap, "order/",orderMaterial.getAttachId());
                if(data==null){
                    result.setMessage("只允许上传"+extMap.get("filext")+"格式的文件");
                    return result;
                }else{
                    orderMaterial.setAttachId(data);
                    int addNumber = orderMaterialMapper.updateAllColumnById(orderMaterial);
                    if(addNumber>0){
                        result.setIsSuccess(true);
                        return result;
                    }else{
                        result.setMessage("没修改记录");
                        return result;
                    }
                }
            }
        }catch (Exception e) {
            result.setMessage("新增相关资料失败，请重新操作");
            return result;
        }
        return result;
    }

    @Override
    public ServiceResult getMaterialByPage(Page<OrderMaterial> page, Long totalNum, Long orderid) {
        ServiceResult result = new ServiceResult(false);
        EntityWrapper<OrderMaterial> ew= new EntityWrapper<>();
        ew.eq("order_id",orderid);
        List<OrderMaterial> list = orderMaterialMapper.selectPage(page, ew);
        if(page.getTotal()==totalNum && totalNum!=0){
            List<OrderMaterial> lists = new ArrayList<>();
            result.addData("orderMaterialList", lists);
            result.setIsSuccess(true);
            return result;
        }
        result.addData("orderMaterialList", list);
        result.setIsSuccess(true);
        return result;
    }

    /**
     * 文件上传
     */
    public Long fileUpload(MultipartFile file,HashMap<String, String> extMap,String functionName,Long attachId)throws Exception{
        String downloadWritePath = CommonUtil.getAppProParam("project_files.base.path")+ CommonUtil.getAppProParam("project_files.attach.path") + functionName;
        String downloadDirPath = downloadWritePath+"/";
        String fileExt = FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase();
        if(!StringUtils.isEmpty(file.getOriginalFilename())){
            if(!Arrays.asList(extMap.get("filext").split(",")).contains(fileExt)){
                return null;
            }
        }
        //文件夹不存在则创建文件夹
        File subjectWriteDir = new File(downloadDirPath);
        if(!subjectWriteDir.isDirectory()){
            subjectWriteDir.mkdirs();
        }
        File subDir = new File(downloadDirPath);
        if (!subDir.exists()) {
            subDir.mkdirs();
        }
        String subFileName =  CommonUtil.getUUID()+"."+fileExt;
        String subFilePath = downloadDirPath +subFileName;
        File dest = new File(subFilePath);
        FileUtil.saveFileNew(file.getInputStream(), dest);
        if(attachId==null){
            Attach attach = new Attach();
            attach.setCreatorId(OperateUtils.getCurrentUserId());
            attach.setCreateTime(DateUtil.getNowTimestampStr());
            attach.setPath(CommonUtil.getAppProParam("project_files.attach.path")+functionName+subFileName);
            attach.setExtention(fileExt);
            attach.setName(file.getOriginalFilename());
            attachMapper.insert(attach);
            return attach.getId();
        }else {
            Attach attach = attachMapper.selectById(attachId);
            if(attach==null){
                return 0L;
            }
            attach.setLastModifierId(OperateUtils.getCurrentUserId());
            attach.setLastModifyTime(DateUtil.getNowTimestampStr());
            attach.setPath(CommonUtil.getAppProParam("project_files.attach.path")+functionName+subFileName);
            attach.setExtention(fileExt);
            attach.setName(file.getOriginalFilename());
            attachMapper.updateAllColumnById(attach);
            return attach.getId();
        }
    }
}
