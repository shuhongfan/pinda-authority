package com.itheima.pinda.authority.biz.dao.auth;

import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.itheima.pinda.authority.entity.auth.User;
//import com.itheima.pinda.database.mybatis.auth.DataScope;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 账号
 * </p>
 *
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据角色id，查询已关联用户
     *
     * @param roleId
     * @param keyword
     * @return
     */
    List<User> findUserByRoleId(@Param("roleId") Long roleId, @Param("keyword") String keyword);

    /**
     * 递增 密码错误次数
     *
     * @param id
     * @return
     */
    int incrPasswordErrorNumById(@Param("id") Long id);

    /**
     * 带数据权限的分页查询
     *
     * @param page
     * @param wrapper
     * @return
     */
    IPage<User> findPage(IPage<User> page, @Param(Constants.WRAPPER) Wrapper<User> wrapper);

    /**
     * 重置 密码错误次数
     *
     * @param id
     * @return
     */
    int resetPassErrorNum(@Param("id") Long id);

    /**
     * 修改用户最后登录时间
     *
     * @param account
     * @param now
     * @return
     */
    int updateLastLoginTime(@Param("account") String account, @Param("now") LocalDateTime now);
}
