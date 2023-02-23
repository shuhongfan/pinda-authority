package com.itheima.pinda.authority.biz.service.core.impl;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.pinda.authority.biz.dao.core.OrgMapper;
import com.itheima.pinda.authority.entity.core.Org;
import com.itheima.pinda.database.mybatis.conditions.Wraps;
import com.itheima.pinda.authority.biz.service.core.OrgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
/**
 * 业务实现类
 * 组织
 */
@Slf4j
@Service
public class OrgServiceImpl extends ServiceImpl<OrgMapper, Org> implements OrgService {
    @Override
    public List<Org> findChildren(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        // MySQL 全文索引
        String applySql = String.format(" MATCH(tree_path) AGAINST('%s' IN BOOLEAN MODE) ", StringUtils.join(ids, " "));
        return super.list(Wraps.<Org>lbQ().in(Org::getId, ids).or(query -> query.apply(applySql)));
    }

    @Override
    public boolean remove(List<Long> ids) {
        List<Org> list = this.findChildren(ids);
        List<Long> idList = list.stream().mapToLong(Org::getId).boxed().collect(Collectors.toList());
        return !idList.isEmpty() ? super.removeByIds(idList) : true;
    }
}