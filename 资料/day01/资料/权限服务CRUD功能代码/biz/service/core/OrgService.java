package com.itheima.pinda.authority.biz.service.core;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.pinda.authority.entity.core.Org;
/**
 * 业务接口
 * 组织
 */
public interface OrgService extends IService<Org> {
    /**
     * 查询指定id集合下的所有子集
     */
    List<Org> findChildren(List<Long> ids);

    /**
     * 批量删除以及删除其子节点
     */
    boolean remove(List<Long> ids);
}
