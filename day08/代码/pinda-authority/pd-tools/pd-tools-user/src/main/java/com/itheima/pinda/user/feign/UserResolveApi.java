package com.itheima.pinda.user.feign;
import com.itheima.pinda.base.R;
import com.itheima.pinda.user.feign.fallback.UserResolveApiFallback;
import com.itheima.pinda.user.model.SysUser;

import com.itheima.pinda.base.R;
import com.itheima.pinda.user.feign.fallback.UserResolveApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
/**
 * 用户操作API
 */
@FeignClient(name = "${pinda.feign.authority-server:pinda-auth-server}", fallbackFactory = UserResolveApiFallback.class)
public interface UserResolveApi {
    /**
     * 根据id 查询用户详情
     */
    @PostMapping(value = "/user/anno/id/{id}")
    R<SysUser> getById(@PathVariable("id") Long id, @RequestBody UserQuery userQuery);
}