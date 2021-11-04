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

@ApiModel(value = "com-huayanginfo-etl-model-entity-JdpSysUserRole")
@Data
@Accessors(chain = true)
@Entity
@Table(name = "jdp_sys_user_role")
public class JdpSysUserRole implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户角色主键
     */
    @Id
    @Column(name = "user_role_id", nullable = false)
    @ApiModelProperty(value = "用户角色主键")
    private String userRoleId;
    /**
     * 用户id
     */
    @Column(name = "user_id", nullable = false)
    @ApiModelProperty(value = "用户id")
    private String userId;
    /**
     * 角色id
     */
    @Column(name = "role_id", nullable = false)
    @ApiModelProperty(value = "角色id")
    private String roleId;
}
