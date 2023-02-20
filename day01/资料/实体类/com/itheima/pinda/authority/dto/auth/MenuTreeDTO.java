package com.itheima.pinda.authority.dto.auth;

import java.util.List;

import com.itheima.pinda.authority.entity.auth.Menu;
import com.itheima.pinda.model.ITreeNode;

import io.swagger.annotations.ApiModel;
import lombok.ToString;

/**
 * 树形菜单 DTO
 *
 */
@ToString(callSuper = true)
@ApiModel(value = "ResourceTreeDTO", description = "资源树")
public class MenuTreeDTO extends Menu implements ITreeNode<MenuTreeDTO, Long> {
    private List<MenuTreeDTO> children;

    private String label;

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public Long getCreateUser() {
        return super.getCreateUser();
    }

    @Override
    public Long getUpdateUser() {
        return super.getUpdateUser();
    }

    @Override
    public List<MenuTreeDTO> getChildren() {
        return this.children;
    }

    @Override
    public void setChildren(List<MenuTreeDTO> children) {
        this.children = children;
    }

    public String getLabel() {
        return this.getName();
    }

    //    @Override
//    public boolean equals(Object o) {
//        return super.equals(o);
//    }
//
//    @Override
//    public int hashCode() {
//        return super.hashCode();
//    }

}
