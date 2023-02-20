package com.itheima.pinda.authority.controller.core;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itheima.pinda.authority.biz.service.core.OrgService;
import com.itheima.pinda.authority.dto.core.OrgSaveDTO;
import com.itheima.pinda.authority.dto.core.OrgTreeDTO;
import com.itheima.pinda.authority.dto.core.OrgUpdateDTO;
import com.itheima.pinda.authority.entity.core.Org;
import com.itheima.pinda.base.BaseController;
import com.itheima.pinda.base.R;
import com.itheima.pinda.base.entity.SuperEntity;
import com.itheima.pinda.database.mybatis.conditions.Wraps;
import com.itheima.pinda.database.mybatis.conditions.query.LbqWrapper;
import com.itheima.pinda.dozer.DozerUtils;
import com.itheima.pinda.log.annotation.SysLog;
import com.itheima.pinda.utils.BizAssert;
import com.itheima.pinda.utils.TreeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static com.itheima.pinda.utils.StrPool.DEF_PARENT_ID;
import static com.itheima.pinda.utils.StrPool.DEF_ROOT_PATH;
/**
 * 前端控制器
 * 组织
 */
@Slf4j
@RestController
@RequestMapping("/org")
@Api(value = "Org", tags = "组织")
public class OrgController extends BaseController {
    @Autowired
    private OrgService orgService;
    @Autowired
    private DozerUtils dozer;
    /**
     * 分页查询组织
     */
    @ApiOperation(value = "分页查询组织", notes = "分页查询组织")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "long", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页显示几条", dataType = "long", paramType = "query", defaultValue = "10"),
    })
    @GetMapping("/page")
    @SysLog("分页查询组织")
    public R<IPage<Org>> page(Org data) {
        IPage<Org> page = this.getPage();
        // 构建值不为null的查询条件
        LbqWrapper<Org> query = Wraps.lbQ(data);
        this.orgService.page(page, query);
        return this.success(page);
    }

    /**
     * 查询组织
     */
    @ApiOperation(value = "查询组织", notes = "查询组织")
    @GetMapping("/{id}")
    @SysLog("查询组织")
    public R<Org> get(@PathVariable Long id) {
        return this.success(this.orgService.getById(id));
    }

    /**
     * 新增组织
     */
    @ApiOperation(value = "新增组织", notes = "新增组织不为空的字段")
    @PostMapping
    @SysLog("新增组织")
    public R<Org> save(@RequestBody @Validated OrgSaveDTO data) {
        Org org = this.dozer.map(data, Org.class);
        if (org.getParentId() == null || org.getParentId() <= 0) {
            org.setParentId(DEF_PARENT_ID);
            org.setTreePath(DEF_ROOT_PATH);
        } else {
            Org parent = this.orgService.getById(org.getParentId());
            BizAssert.notNull(parent, "父组织不能为空");

            org.setTreePath(StringUtils.join(parent.getTreePath(), parent.getId(), DEF_ROOT_PATH));
        }
        this.orgService.save(org);
        return this.success(org);
    }

    /**
     * 修改组织
     */
    @ApiOperation(value = "修改组织", notes = "修改组织不为空的字段")
    @PutMapping
    @SysLog("修改组织")
    public R<Org> update(@RequestBody @Validated(SuperEntity.Update.class) OrgUpdateDTO data) {
        Org org = this.dozer.map(data, Org.class);
        this.orgService.updateById(org);
        return this.success(org);
    }

    /**
     * 删除组织
     */
    @ApiOperation(value = "删除组织", notes = "根据id物理删除组织")
    @SysLog("删除组织")
    @DeleteMapping
    public R<Boolean> delete(@RequestParam("ids[]") List<Long> ids) {
        this.orgService.remove(ids);
        return this.success(true);
    }

    /**
     * 查询系统所有的组织树
     */
    @ApiOperation(value = "查询系统所有的组织树", notes = "查询系统所有的组织树")
    @GetMapping("/tree")
    @SysLog("查询系统所有的组织树")
    public R<List<OrgTreeDTO>> tree(@RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "status", required = false) Boolean status) {
        List<Org> list = this.orgService.list(Wraps.<Org>lbQ().like(Org::getName, name)
                .eq(Org::getStatus, status).orderByAsc(Org::getSortValue));
        List<OrgTreeDTO> treeList = this.dozer.mapList(list, OrgTreeDTO.class);
        return this.success(TreeUtil.build(treeList));
    }
}