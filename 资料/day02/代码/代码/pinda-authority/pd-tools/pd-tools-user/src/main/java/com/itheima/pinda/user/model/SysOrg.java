package com.itheima.pinda.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 * 实体类
 * </p>
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode
public class SysOrg {

    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     * 名称
     */
    private String name;

    /**
     * 简称
     */
    private String abbreviation;

    /**
     * 父ID
     */
    private Long parentId;

    /**
     * 排序
     */
    private Integer sortValue;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 描述
     */
    private String describe;


}
