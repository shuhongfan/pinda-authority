package com.itheima.pinda.authority.biz.service.auth;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.pinda.authority.entity.auth.Menu;
/**
 * 业务接口
 * 菜单
 */
public interface MenuService extends IService<Menu> {
    /**
     * 根据ID删除
     */
    boolean removeByIds(List<Long> ids);

    /**
     * 查询用户可用菜单
     *
     * @param group
     * @param userId
     * @return
     */
    List<Menu> findVisibleMenu(String group, Long userId);
}