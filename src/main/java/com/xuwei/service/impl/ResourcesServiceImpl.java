package com.xuwei.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xuwei.bean.Resources;
import com.xuwei.mapper.ResourcesMapper;
import com.xuwei.service.IResourcesService;
import com.xuwei.util.DateUtil;
import com.xuwei.util.OperateUtils;
import com.xuwei.util.ServiceResult;
import com.xuwei.util.StringUtil;

/**
 * @description: 服务实现类
 * @copyright: 福建骏华信息有限公司 (c)2017
 * @createTime: 2017年07月14日 16:17:01
 * @author: hhd
 * @version: 1.0
 */
@Service
public class ResourcesServiceImpl extends ServiceImpl<ResourcesMapper, Resources> implements IResourcesService {

    @Resource
    private ResourcesMapper resourcesDao;


    @Override
    public List<Resources> treeRoot() {
        return resourcesDao.treeRoot();
    }

    @Override
    public List<Resources> treeChildren(Long id) {
        return resourcesDao.treeChildren(id);
    }

    @Override
    public void findChildrenByPage(Resources m, Page<Resources> page) {
        List<Resources> list = resourcesDao.findChildrenByPage(m,page);
        page.setRecords(list);
    }

    //@CacheEvict(value={"admin-authorization","admin-menus","admin-tree-menus"},allEntries=true)
    @Override
    public ServiceResult create(Resources m) {
        ServiceResult result = new ServiceResult(false);
        //标识不能重复
        int nextWeight = resourcesDao.nextWeight(m.getParentId());
        m.setWeight(nextWeight);

        Resources parent = resourcesDao.findById(m.getParentId());
        m.setParentIds(parent.makeSelfAsNewParentIds());
        m.setCreateTime(DateUtil.getNowTimestampStr());
        m.setCreatorId(OperateUtils.getCurrentUserId());
        m.setLastModifyTime(DateUtil.getNowTimestampStr());
        m.setLastModifierId(OperateUtils.getCurrentUserId());
        int insertCount = resourcesDao.insert(m);
        if(insertCount>0){
            String isCRUD = m.getIsCRUD();
            if("是".equals(isCRUD)){
                String baseIdentity = findActualIdentity(m);
                for (int j = 1; j < 5; j++) {
                    Resources child = new Resources();
                    child.setParentId(m.getId());
                    if(j == 1){
                        child.setName("查看");
                        child.setIdentity(baseIdentity+":view");
                    }else if(j == 2){
                        child.setName("添加");
                        child.setIdentity(baseIdentity+":create");
                    }else if(j == 3){
                        child.setName("修改");
                        child.setIdentity(baseIdentity+":update");
                    }else if(j == 4){
                        child.setName("删除");
                        child.setIdentity(baseIdentity+":delete");
                    }
                    child.setParentIds(m.makeSelfAsNewParentIds());
                    child.setWeight(j);
                    child.setStatus("启用");
                    child.setResourcesType("权限");
                    child.setCreateTime(DateUtil.getNowTimestampStr());
                    child.setCreatorId(OperateUtils.getCurrentUserId());
                    child.setLastModifyTime(DateUtil.getNowTimestampStr());
                    child.setLastModifierId(OperateUtils.getCurrentUserId());
                    resourcesDao.insert(child);
                }
            }

            result.addData("id", m.getId());
            result.setIsSuccess(true);
            return result;
        }else{
            result.setMessage("未保存");
            return result;
        }
    }


    /**
     * 得到真实的资源标识  即 父亲:儿子
     * @param resources
     * @return
     */
    public String findActualIdentity(Resources resources) {
        if(resources == null) {
            return null;
        }
        StringBuilder s = new StringBuilder("");

        boolean hasResourceIdentity = !StringUtils.isEmpty(resources.getIdentity());

        Resources parent = resources;
        while(parent != null) {
            if(!StringUtils.isEmpty(parent.getIdentity())) {
                s.insert(0, parent.getIdentity() + ":");
                hasResourceIdentity = true;
            }
            parent = resourcesDao.findById(parent.getParentId());
        }

        //如果用户没有声明 资源标识  且父也没有，那么就为空
        if(!hasResourceIdentity) {
            return "";
        }
        //如果最后一个字符是: 因为不需要，所以删除之
        int length = s.length();
        if(length > 0 && s.lastIndexOf(":") == length - 1) {
            s.deleteCharAt(length - 1);
        }
        return s.toString();
    }
    //@CacheEvict(value={"admin-authorization","admin-menus","admin-tree-menus"},allEntries=true)
    @Override
    public ServiceResult update(Resources m) {
        ServiceResult result = new ServiceResult(false);
        if(m==null||m.getId()==null){
            result.setMessage("请指定要修改记录");
            return result;
        }
        m.setLastModifyTime(DateUtil.getNowTimestampStr());
        m.setLastModifierId(OperateUtils.getCurrentUserId());
        int insertCount = resourcesDao.update(m);
        if(insertCount>0){
            result.setIsSuccess(true);
            return result;
        }else{
            result.setMessage("未保存");
            return result;
        }
    }
   // @CacheEvict(value={"admin-authorization","admin-menus","admin-tree-menus"},allEntries=true)
    @Override
    public ServiceResult mulDelete(String ids) {
        ServiceResult result = new ServiceResult(false);
        String[] idArray = StringUtil.split(ids);
        if(idArray==null||idArray.length==0){
            result.setMessage("请选择要删除的数据行");
            return result;
        }
        int deleteCount = resourcesDao.mulDelete(idArray);
        if(deleteCount>0){
            result.setIsSuccess(true);
            return result;
        }else{
            result.setMessage("没删除记录");
            return result;
        }
    }

    @Override
    public List<Resources> tree() {
        return resourcesDao.tree();
    }

    //@CacheEvict(value={"admin-authorization","admin-menus","admin-tree-menus"},allEntries=true)
    @Override
    public ServiceResult move(Long sourceId, Long targetId, String point) {
        ServiceResult result = new ServiceResult(false);

        Resources source = resourcesDao.findById(sourceId);
        Resources target = resourcesDao.findById(targetId);
        if (source == null || target == null || source.isRoot()) { //根节点不能移动
            result.setMessage("请选中要移动的树节点");
            return result;
        }
        //append
        boolean isSibling = source.getParentId().equals(target.getParentId());
        boolean isBottomOrTop = "bottom".equals(point) || "top".equals(point);
        //如果是相邻的兄弟交换位置 直接交换weight即可
        if (isSibling && isBottomOrTop && Math.abs(source.getWeight() - target.getWeight()) == 1) {
            //无需移动
            if ("bottom".equals(point) && source.getWeight() > target.getWeight()) {
                result.setMessage("无需移动");
                return result;
            }
            if ("top".equals(point) && source.getWeight() < target.getWeight()) {
                result.setMessage("无需移动");
                return result;
            }
            int sourceWeight = source.getWeight();
            source.setWeight(target.getWeight());
            target.setWeight(sourceWeight);
            result.setIsSuccess(true);
            return result;
        }
        //移动到目标节点之后
        if ("bottom".equals(point)) {
            List<Resources> siblings = resourcesDao.findSelfAndNextSiblings(target.getParentIds(), target.getWeight());
            siblings.remove(0);//把自己移除

            if (siblings.size() == 0) { //如果目标节点下方没有兄弟了 则直接把源的设置为目标即可
                int nextWeight = nextWeight(target.getParentId());
                resourcesDao.updateSelftAndChild(source, target.getParentId(), target.getParentIds(), nextWeight);
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
            List<Resources> siblings = resourcesDao.findSelfAndNextSiblings(target.getParentIds(), target.getWeight());
            //兄弟节点中包含源节点   1 2 3 [4 source] 5： 4放到2
            if (siblings.contains(source)) {//siblings:2 3 [4 source] 5
                siblings = siblings.subList(0, siblings.indexOf(source) + 1);//siblings:2 3 4
                int firstWeight = siblings.get(0).getWeight();
                for (int i = 0; i < siblings.size() - 1; i++) {//2 3从后向前移
                    siblings.get(i).setWeight(siblings.get(i + 1).getWeight());
                    resourcesDao.updateByIdSelective(siblings.get(i));
                }
                siblings.get(siblings.size() - 1).setWeight(firstWeight);//4变为首位
                resourcesDao.updateByIdSelective( siblings.get(siblings.size() - 1));
            } else {
                // 1 2 3 4 5 [new]
                int nextWeight = nextWeight(target.getParentId());
                int firstWeight = siblings.get(0).getWeight();
                for (int i = 0; i < siblings.size() - 1; i++) {
                    siblings.get(i).setWeight(siblings.get(i + 1).getWeight());
                    resourcesDao.updateByIdSelective(siblings.get(i));
                }
                siblings.get(siblings.size() - 1).setWeight(nextWeight);
                resourcesDao.updateByIdSelective( siblings.get(siblings.size() - 1));
                source.setWeight(firstWeight);
                updateSelftAndChild(source, target.getParentId(), target.getParentIds(), source.getWeight());
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

    @Override
    public int nextWeight(Long id) {
        return resourcesDao.nextWeight(id);
    }

    @Override
    public void updateSelftAndChild(Resources source, Long parentId, String parentIds, Integer weight) {
        String oldParentIds = source.makeSelfAsNewParentIds();
        source.setParentId(parentId);
        source.setParentIds(parentIds);
        source.setWeight(weight);
        resourcesDao.updateByIdSelective(source);

        String newParentIds = source.makeSelfAsNewParentIds();
        resourcesDao.updateChildrenParentIds(newParentIds,oldParentIds);
    }
}
