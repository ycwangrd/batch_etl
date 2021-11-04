package com.huayanginfo.etl.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@ApiModel(value = "com-huayanginfo-etl-model-entity-JdpSysMenu")
@Data
@Accessors(chain = true)
@Entity
@Table(name = "jdp_sys_menu")
public class JdpSysMenu extends EntityBase {
    /**
     * 菜单主键id
     */
    @Id
    @Column(name = "menu_id", nullable = false)
    @ApiModelProperty(value = "菜单主键id")
    private String menuId;

    /**
     * 上级菜单id
     */
    @Column(name = "parent_menu_id")
    @ApiModelProperty(value = "上级菜单id")
    private String parentMenuId;

    /**
     * 菜单名称
     */
    @Column(name = "menu_name")
    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    /**
     * 路由标识(path)
     */
    @Column(name = "route_path")
    @ApiModelProperty(value = "路由标识(path)")
    private String routePath;

    /**
     * 页面路径
     */
    @Column(name = "page_path")
    @ApiModelProperty(value = "页面路径")
    private String pagePath;

    /**
     * 权限标识
     */
    @Column(name = "perm_identity")
    @ApiModelProperty(value = "权限标识")
    private String permIdentity;

    /**
     * 菜单图标
     */
    @Column(name = "icon_class")
    @ApiModelProperty(value = "菜单图标")
    private String iconClass;

    /**
     * 重定向路由标识
     */
    @Column(name = "redirect_route")
    @ApiModelProperty(value = "重定向路由标识")
    private String redirectRoute;

    /**
     * 排序值
     */
    @Column(name = "order_num")
    @ApiModelProperty(value = "排序值")
    private Integer orderNum;

    /**
     * 菜单类型(1:菜单,2:功能)
     */
    @Column(name = "menu_type")
    @ApiModelProperty(value = "菜单类型(1:菜单,2:功能)")
    private Short menuType;

    /**
     * 是否可见 0-否  1-是
     */
    @Column(name = "is_show")
    @ApiModelProperty(value = "是否可见 0-否  1-是")
    private Boolean isShow;

    /**
     * 是否参与权限分配
     */
    @Column(name = "is_auth")
    @ApiModelProperty(value = "是否参与权限分配")
    private Boolean isAuth;
    /**
     * 功能目标区域(_blank,rightDown)
     */
    @Column(name = "menu_target")
    @ApiModelProperty(value = "功能目标区域(_blank,rightDown)")
    private String menuTarget;
}

