/**
 * 
 */
package com.xuwei.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.Organization;
import com.xuwei.mapper.OrganizationMapper;
import com.xuwei.service.OrganizationService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;


/**
 * @description:
 * @createTime 上午10:55:11
 * @author xw
 *
 */
@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization> implements OrganizationService{
	
	@Resource
	private OrganizationMapper oranizationMapper;
	
	@Override
	public List<Organization> findAll() {
		
		return oranizationMapper.findAll();
	}

	@Override
	public List<Organization> findAllByOrgType(int orgType) {
		
		return oranizationMapper.findAllByOrgType(orgType);
	}
	@Override
	public List<Organization> treeRoot() {
		
		return oranizationMapper.treeRoot();
	}

	@Override
	public List<Organization> treeChildren(Long id) {
		
		return oranizationMapper.treeChildren(id);
	}
	@Override
	public ServiceResult create(Organization m) {
		ServiceResult result = new ServiceResult(false);
		//标识不能重复
		int nextArray = oranizationMapper.nextArray(m.getParentId());
		m.setArray(nextArray);
		Organization parent = oranizationMapper.findById(m.getParentId());
		m.setParentIds(parent.makeSelfAsNewParentIds());
		m.setCreateTime(DateUtil.getNowTimestampStr());
		m.setCreatorId(OperateUtils.getCurrentUserId());
		m.setLastModifyTime(DateUtil.getNowTimestampStr());
		m.setLastModifierId(OperateUtils.getCurrentUserId());
		int insertCount = oranizationMapper.insert(m);
		if(insertCount>0){
			result.addData("id", m.getId());
			result.setIsSuccess(true);
			return result;
		}else{
			result.setMessage("未保存");
			return result;
		}
	}

	@Override
	public ServiceResult update(Organization m) {
		ServiceResult result = new ServiceResult(false);
		if(m==null||m.getId()==null){
			result.setMessage("请指定要修改的部门");
			return result;
		}
		Organization olM = oranizationMapper.findById(m.getId());
		if(m.getParentId().intValue() != olM.getParentId().intValue()){
			olM.setParentId(m.getParentId());
			int nextArray = oranizationMapper.nextArray(m.getParentId());
			olM.setArray(nextArray);
			Organization parent = oranizationMapper.findById(m.getParentId());
			olM.setParentIds(parent.makeSelfAsNewParentIds());
		}
		olM.setOrganizationName(m.getOrganizationName());
		//olM.setContent(m.getContent());
		olM.setOrgType(m.getOrgType());
		olM.setNote(m.getNote());
		olM.setLastModifyTime(DateUtil.getNowTimestampStr());
		olM.setLastModifierId(OperateUtils.getCurrentUserId());
		int insertCount = oranizationMapper.update(olM);
		if(insertCount>0){
			result.setIsSuccess(true);
			return result;
		}else{
			result.setMessage("未保存");
			return result;
		}
	}
	@Override
	public Object move(long sourceId, long targetId, String point) {
		ServiceResult result = new ServiceResult(false);
		
		Organization source = oranizationMapper.findById(sourceId);
		Organization target = oranizationMapper.findById(targetId);
		if (source == null || target == null || source.isRoot()) { //根节点不能移动
			result.setMessage("请选中要移动的树节点");
			return result;
	    }
		//append
		boolean isSibling = source.getParentId().equals(target.getParentId());
        boolean isBottomOrTop = "bottom".equals(point) || "top".equals(point);
        //如果是相邻的兄弟交换位置 直接交换weight即可
        if (isSibling && isBottomOrTop && Math.abs(source.getArray() - target.getArray()) == 1) {
            //无需移动
            if ("bottom".equals(point) && source.getArray() > target.getArray()) {
            	result.setMessage("无需移动");
    			return result;
            }
            if ("top".equals(point) && source.getArray() < target.getArray()) {
            	result.setMessage("无需移动");
    			return result;
            }
            int sourceWeight = source.getArray();
            source.setArray(target.getArray());
            target.setArray(sourceWeight);
            oranizationMapper.update(source);
            oranizationMapper.update(target);
            result.setIsSuccess(true);
            return result;
        }
        //移动到目标节点之后
        if ("bottom".equals(point)) {
            List<Organization> siblings = oranizationMapper.findSelfAndNextSiblings(target.getParentIds(), target.getArray());
            siblings.remove(0);//把自己移除

            if (siblings.size() == 0) { //如果目标节点下方没有兄弟了 则直接把源的设置为目标即可
                int nextWeight = nextWeight(target.getParentId());
                updateSelftAndChild(source, target.getParentId(), target.getParentIds(), nextWeight);
                result.setIsSuccess(true);
                return result;
            } 
            else {
            	point = "top";
                target = siblings.get(0); //否则，相当于插入到实际目标节点下一个节点之前
            }
        }
        //移动到目标节点之前
        if ("top".equals(point)) {
            List<Organization> siblings = oranizationMapper.findSelfAndNextSiblings(target.getParentIds(), target.getArray());
            //兄弟节点中包含源节点   1 2 3 [4 source] 5： 4放到2
            if (siblings.contains(source)) {//siblings:2 3 [4 source] 5
                siblings = siblings.subList(0, siblings.indexOf(source) + 1);//siblings:2 3 4
                int firstWeight = siblings.get(0).getArray();
                for (int i = 0; i < siblings.size() - 1; i++) {//2 3从后向前移
                    siblings.get(i).setArray(siblings.get(i + 1).getArray());
                    oranizationMapper.updateByIdSelective(siblings.get(i));
                }
                siblings.get(siblings.size() - 1).setArray(firstWeight);//4变为首位
                oranizationMapper.updateByIdSelective( siblings.get(siblings.size() - 1));
            } else {
                // 1 2 3 4 5 [new]
                int nextWeight = nextWeight(target.getParentId());
                int firstWeight = siblings.get(0).getArray();
                for (int i = 0; i < siblings.size() - 1; i++) {
                    siblings.get(i).setArray(siblings.get(i + 1).getArray());
                    oranizationMapper.updateByIdSelective(siblings.get(i));
                }
                siblings.get(siblings.size() - 1).setArray(nextWeight);
                oranizationMapper.updateByIdSelective( siblings.get(siblings.size() - 1));
                source.setArray(firstWeight);
                updateSelftAndChild(source, target.getParentId(), target.getParentIds(), source.getArray());
            }
            result.setIsSuccess(true);
            return result;
        }
        //否则作为最后孩子节点
        int nextWeight = nextWeight(target.getId());
        updateSelftAndChild(source, target.getId(), target.makeSelfAsNewParentIds(), nextWeight);
        result.setIsSuccess(true);
        return result;
	}
	
	public int nextWeight(long id) {
		return oranizationMapper.nextArray(id);
	}
	public void updateSelftAndChild(Organization source, long parentId, String parentIds, Integer weight) {
		String oldParentIds = source.makeSelfAsNewParentIds();
		source.setParentId(parentId);
		source.setParentIds(parentIds);
		source.setArray(weight);
		oranizationMapper.updateByIdSelective(source);
		
		String newParentIds = source.makeSelfAsNewParentIds();
		oranizationMapper.updateChildrenParentIds(newParentIds,oldParentIds);
	}

	@Override
	public Organization findById(Long id) {
		
		return oranizationMapper.findById(id);
	}

	@Override
	public List<Organization> findByCompany(Long companyId) {
		if(companyId == null)
		{
			return null;
		}
		Organization org = oranizationMapper.findById(companyId);
		String parentIds = org.getParentIds()+""+companyId+"/";
		return oranizationMapper.findByCompany(parentIds);
	}

	@Override
	public Organization selectByUserId(Long currentUserId) {
		return oranizationMapper.selectByUserId(currentUserId);
	}
	
	
}
