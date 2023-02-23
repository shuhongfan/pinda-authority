package com.itheima.pinda.zuul.filter;

import cn.hutool.core.util.StrUtil;
import com.itheima.pinda.authority.dto.auth.ResourceQueryDTO;
import com.itheima.pinda.authority.entity.auth.Resource;
import com.itheima.pinda.base.R;
import com.itheima.pinda.common.constant.CacheKey;
import com.itheima.pinda.context.BaseContextConstants;
import com.itheima.pinda.exception.code.ExceptionCode;
import com.itheima.pinda.zuul.api.ResourceApi;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 鉴权处理过滤器
 */
@Component
@Slf4j
public class AccessFilter extends BaseFilter {
    @Autowired
    private ResourceApi resourceApi;
    @Autowired
    private CacheChannel cacheChannel;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    //鉴权处理逻辑
    @Override
    public Object run() throws ZuulException {
        //第1步：判断当前请求uri是否需要忽略
        if(isIgnoreToken()){
            //当前请求需要忽略，直接放行
            return null;
        }

        //第2步：获取当前请求的请求方式和uri，拼接成GET/user/page这种形式，称为权限标识符
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        String method = request.getMethod();//GET POST PUT
        String uri = request.getRequestURI();
        uri = StrUtil.subSuf(uri,zuulPrefix.length());
        uri = StrUtil.subSuf(uri,uri.indexOf("/",1));
        String permission = method + uri;//GET/user/page

        //第3步：从缓存中获取所有需要进行鉴权的资源(同样是由资源表的method字段值+url字段值拼接成)，如果没有获取到则通过Feign调用权限服务获取并放入缓存中
        CacheObject cacheObject = cacheChannel.get(CacheKey.RESOURCE, CacheKey.RESOURCE_NEED_TO_CHECK);
        List<String> list = (List<String>) cacheObject.getValue();
        if(list == null){
            //缓存中没有相应数据
            list = resourceApi.list().getData();
            //放入缓存中
            if(list != null && list.size() > 0){
                cacheChannel.set(CacheKey.RESOURCE,CacheKey.RESOURCE_NEED_TO_CHECK,list);
            }
        }

        //第4步：判断这些资源是否包含当前请求的权限标识符，如果不包含当前请求的权限标识符，则返回未经授权错误提示
        long count = list.stream().filter((resource) -> {
            return permission.startsWith(resource);
        }).count();

        if(count == 0){
            //当前请求是一个未知请求，直接返回未授权异常信息
            errorResponse(ExceptionCode.UNAUTHORIZED.getMsg(),ExceptionCode.UNAUTHORIZED.getCode(),200);
            return null;
        }

        //第5步：如果包含当前的权限标识符，则从zuul header中取出用户id，根据用户id取出缓存中的用户拥有的权限，如果没有取到则通过Feign调用权限服务获取并放入缓存，判断用户拥有的权限是否包含当前请求的权限标识符
        String userId = RequestContext.getCurrentContext().getZuulRequestHeaders().get(BaseContextConstants.JWT_KEY_USER_ID);
        List<String> visibleResource = (List<String>) cacheChannel.get(CacheKey.USER_RESOURCE, userId).getValue();
        if(visibleResource == null){
            //缓存中不存在，需要通过接口远程调用权限服务来获取
            ResourceQueryDTO resourceQueryDTO = ResourceQueryDTO.builder().userId(new Long(userId)).build();
            List<Resource> resourceList = resourceApi.visible(resourceQueryDTO).getData();
            if(resourceList != null && resourceList.size() > 0){
                visibleResource = resourceList.stream().map((resource -> {
                    return resource.getMethod() + resource.getUrl();
                })).collect(Collectors.toList());
                //将当前用户拥有的权限载入缓存
                cacheChannel.set(CacheKey.USER_RESOURCE,userId,visibleResource);
            }
        }

        //第6步：如果用户拥有的权限包含当前请求的权限标识符则说明当前用户拥有权限，直接放行
        count = visibleResource.stream().filter((resource) -> {
            return permission.startsWith(resource);
        }).count();

        if(count > 0){
            //当前用户拥有访问权限，直接放行
            return null;
        }else{
            //第7步：如果用户拥有的权限不包含当前请求的权限标识符则说明当前用户没有权限，返回未经授权错误提示
            errorResponse(ExceptionCode.UNAUTHORIZED.getMsg(),ExceptionCode.UNAUTHORIZED.getCode(),200);
            return null;
        }
    }
}
