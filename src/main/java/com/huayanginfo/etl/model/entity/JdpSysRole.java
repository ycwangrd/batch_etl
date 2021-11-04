package com.huayanginfo.etl.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@ApiModel(value = "com-huayanginfo-etl-model-entity-JdpSysRole")
@Data
@Accessors(chain = true)
@Entity
@Table(name = "jdp_sys_role")
public class JdpSysRole extends EntityBase {
    private static final long serialVersionUID = 1L;
    /**
     * 角色主键id
     */
    @Id
    @Column(name = "role_id")
    @ApiModelProperty(value = "角色主键id")
    private String roleId;
    /**
     * 组织id
     */
    @Column(name = "org_id")
    @ApiModelProperty(value = "组织id")
    private String orgId;
    /**
     * 角色编码
     */
    @Column(name = "role_code")
    @ApiModelProperty(value = "角色编码")
    private String roleCode;
    /**
     * 角色名称
     */
    @Column(name = "role_name")
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    /**
     * 描述
     */
    @Column(name = "role_desc")
    @ApiModelProperty(value = "描述")
    private String roleDesc;
}
