package com.xuwei.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xuwei.bean.FollowAttach;

/**
 * <p>
 * 跟进_附件 服务类
 * </p>
 *
 * @author ${author}
 * @since 2017-09-04
 */
public interface IFollowAttachService extends IService<FollowAttach> {

    /**
     * 根据跟进id查询 附件
     * @param followid
     * @return
     */
    List<FollowAttach> selectByFollowId(Long followid);
    
    /**
     * 
     * @description: 根据附件id删除跟进附件
     * @createTime: 2017年10月9日 下午2:44:07
     * @author: caw
     * @param attachId
     * @return
     */
    boolean delAttachIdPage(Long attachId);
    
    /**
     * 
     * @description: 获取跟进附件
     * @createTime: 2017年10月9日 下午4:30:36
     * @author: caw
     * @param followId
     * @return
     */
    List<FollowAttach> findListByFollowId(Long followId);
}
