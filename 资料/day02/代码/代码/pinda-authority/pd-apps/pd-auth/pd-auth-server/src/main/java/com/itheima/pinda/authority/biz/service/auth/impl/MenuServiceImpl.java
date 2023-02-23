package com.itheima.pinda.authority.biz.service.auth.impl;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.pinda.authority.biz.dao.auth.MenuMapper;
import com.itheima.pinda.authority.biz.service.auth.MenuService;
import com.itheima.pinda.authority.biz.service.auth.ResourceService;
import com.itheima.pinda.authority.entity.auth.Menu;
import com.itheima.pinda.utils.NumberHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.itheima.pinda.utils.StrPool.DEF_PARENT_ID;
/**
 * 业务实现类
 * 菜单
 */
@Slf4j
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Autowired
    private ResourceService resourceService;

    @Override
    public List<Menu> findVisibleMenu(String group, Long userId) {
        List<Menu> visibleMenu = baseMapper.findVisibleMenu(group, userId);
        return visibleMenu;
    }

    @Override
    public boolean removeByIds(List<Long> ids) {
        if (ids.isEmpty()) {
            return true;
        }
        boolean result = super.removeByIds(ids);
        if (result) {
            resourceService.removeByMenuId(ids);
        }
        return result;
    }

    @Override
    public boolean save(Menu menu) {
        menu.setIsEnable(NumberHelper.getOrDef(menu.getIsEnable(), true));
        menu.setIsPublic(NumberHelper.getOrDef(menu.getIsPublic(), false));
        menu.setParentId(NumberHelper.getOrDef(menu.getParentId(), DEF_PARENT_ID));

        super.save(menu);
        return true;
    }
}