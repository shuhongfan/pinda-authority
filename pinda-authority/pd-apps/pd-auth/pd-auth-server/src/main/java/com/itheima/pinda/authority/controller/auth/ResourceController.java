package com.itheima.pinda.authority.controller.auth;
import java.util.List;
import java.util.stream.Collectors;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itheima.pinda.authority.dto.auth.ResourceQueryDTO;
import com.itheima.pinda.authority.dto.auth.ResourceSaveDTO;
import com.itheima.pinda.authority.dto.auth.ResourceUpdateDTO;
import com.itheima.pinda.authority.entity.auth.Resource;
import com.itheima.pinda.authority.biz.service.auth.ResourceService;
import com.itheima.pinda.base.BaseController;
import com.itheima.pinda.base.R;
import com.itheima.pinda.base.entity.SuperEntity;
import com.itheima.pinda.database.mybatis.conditions.Wraps;
import com.itheima.pinda.database.mybatis.conditions.query.LbqWrapper;
import com.itheima.pinda.dozer.DozerUtils;
import com.itheima.pinda.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * 前端控制器
 * 资源
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/resource")
@Api(value = "Resource", tags = "资源")
public class ResourceController extends BaseController {
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private DozerUtils dozer;
    /**
     * 分页查询资源
     */
    @ApiOperation(value = "分页查询资源", notes = "分页查询资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "long", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页显示几条", dataType = "long", paramType = "query", defaultValue = "10"),
    })
    @GetMapping("/page")
    @SysLog("分页查询资源")
    public R<IPage<Resource>> page(Resource data) {
        IPage<Resource> page = getPage();
        // 构建值不为null的查询条件
        LbqWrapper<Resource> query = Wraps.lbQ(data);
        resourceService.page(page, query);
        return success(page);
    }

    /**
     * 查询资源
     */
    @ApiOperation(value = "查询资源", notes = "查询资源")
    @GetMapping("/{id}")
    @SysLog("查询资源")
    public R<Resource> get(@PathVariable Long id) {
        return success(resourceService.getById(id));
    }

    /**
     * 新增资源
     */
    @ApiOperation(value = "新增资源", notes = "新增资源不为空的字段")
    @PostMapping
    @SysLog("新增资源")
    public R<Resource> save(@RequestBody @Validated ResourceSaveDTO data) {
        Resource resource = dozer.map(data, Resource.class);
        resourceService.save(resource);
        return success(resource);
    }

    /**
     * 修改资源
     */
    @ApiOperation(value = "修改资源", notes = "修改资源不为空的字段")
    @PutMapping
    @SysLog("修改资源")
    public R<Resource> update(@RequestBody @Validated(SuperEntity.Update.class) ResourceUpdateDTO data) {
        Resource resource = dozer.map(data, Resource.class);
        resourceService.updateById(resource);
        return success(resource);
    }

    /**
     * 删除资源
     * 链接类型的资源 只清空 menu_id
     * 按钮和数据列 则物理删除
     */
    @ApiOperation(value = "删除资源", notes = "根据id物理删除资源")
    @DeleteMapping
    @SysLog("删除资源")
    public R<Boolean> delete(@RequestParam("ids[]") List<Long> ids) {
        return success(resourceService.removeByIds(ids));
    }

    /**
     * 查询用户可用的所有资源
     */
    @ApiOperation(value = "查询用户可用的所有资源", notes = "查询用户可用的所有资源")
    @GetMapping
    @SysLog("查询用户可用的所有资源")
    public R<List<Resource>> visible(ResourceQueryDTO resource) {
        if (resource == null) {
            resource = new ResourceQueryDTO();
        }

        if (resource.getUserId() == null) {
            resource.setUserId(getUserId());
        }
        return success(resourceService.findVisibleResource(resource));
    }

    /**
     * 查询所有资源
     */
    @ApiOperation(value = "查询所有资源", notes = "查询所有资源")
    @GetMapping("/list")
    @SysLog("查询所有资源")
    public R<List> list() {
        List<Resource> list = resourceService.list();
        List<String> resourceList = list.stream().map((Resource r) -> {
            return r.getMethod() + r.getUrl();
        }).collect(Collectors.toList());
        return success(resourceList);
    }
}
