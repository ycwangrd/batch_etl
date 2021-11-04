package com.huayanginfo.etl.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@ApiModel(value = "com-huayanginfo-etl-model-entity-JdpSysRoleMenu")
@Data
@Accessors(chain = true)
@Entity
@Table(name = "jdp_sys_role_menu")
public class JdpSysRoleMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 角色菜单主键id
     */
    @Id
    @Column(name = "role_menu_id", nullable = false)
    @ApiModelProperty(value = "角色菜单主键id")
    private String roleMenuId;
    /**
     * 角色id
     */
    @Column(name = "role_id")
    @ApiModelProperty(value = "角色id")
    private String roleId;
    /**
     * 菜单id
     */
    @Column(name = "menu_id")
    @ApiModelProperty(value = "菜单id")
    private String menuId;
}
