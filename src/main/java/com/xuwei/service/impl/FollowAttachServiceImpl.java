package com.xuwei.service.impl;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.FollowAttach;
import com.xuwei.mapper.FollowAttachMapper;
import com.xuwei.service.IFollowAttachService;

/**
 * <p>
 * 跟进_附件 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2017-09-04
 */
@Service
public class FollowAttachServiceImpl extends ServiceImpl<FollowAttachMapper, FollowAttach> implements IFollowAttachService {

    @Resource
    private FollowAttachMapper followAttachMapper;

    @Override
    public List<FollowAttach> selectByFollowId(Long followid) {
        List<FollowAttach> list = followAttachMapper.selectByFollowId(followid);
        return list;
    }

    /**
     * 根据附件id删除跟进附件
     */
	@Override
	public boolean delAttachIdPage(Long attachId) {
		followAttachMapper.delAttachIdPage(attachId);
		return true;
	}

	/**
	 * 获取跟进附件
	 */
	@Override
	public List<FollowAttach> findListByFollowId(Long followId) {
		return followAttachMapper.findListByFollowId(followId);
	}
}
