package com.huayanginfo.etl.model.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wangrd 北京华洋峻峰信息工程股份公司
 * https://www.huayanginfo.com ©2008-2021 huayanginfo.com
 * All Rights Reserved.
 * @since 2021年08月13日 星期五 10:19:23
 * 所有实体类的基类
 */
public class EntityBase implements Serializable {
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    /**
     * 创建者
     */
    @Column(name = "create_user")
    @ApiModelProperty(value = "创建者")
    private String createUser;

    /**
     * 修改者
     */
    @Column(name = "update_user")
    @ApiModelProperty(value = "修改者")
    private String updateUser;

    /**
     * 逻辑删除标识。仅且仅有0和1两个值，1表示已经被逻辑删除，0表示正常可用。
     */
    @Column(name = "deleted_flag")
    @ApiModelProperty(value = "逻辑删除标识。仅且仅有0和1两个值，1表示已经被逻辑删除，0表示正常可用。")
    private Boolean deletedFlag;
}
