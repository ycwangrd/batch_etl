package com.huayanginfo.etl.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@ApiModel(value = "com-huayanginfo-etl-model-entity-JdpSysOrg")
@Data
@Accessors(chain = true)
@Entity
@Table(name = "jdp_sys_org")
public class JdpSysOrg extends EntityBase {
    private static final long serialVersionUID = 1L;
    /**
     * 组织主键id
     */
    @Id
    @Column(name = "org_id")
    @ApiModelProperty(value = "组织主键id")
    private String orgId;
    /**
     * 父级组织id
     */
    @Column(name = "parent_id")
    @ApiModelProperty(value = "父级组织id")
    private String parentId;
    /**
     * 组织类型(1:机构,2:部门)
     */
    @Column(name = "org_type")
    @ApiModelProperty(value = "组织类型(1:机构,2:部门)")
    private Boolean orgType;
    /**
     * 组织代码
     */
    @Column(name = "org_code")
    @ApiModelProperty(value = "组织代码")
    private String orgCode;
    /**
     * 组织名称
     */
    @Column(name = "org_name")
    @ApiModelProperty(value = "组织名称")
    private String orgName;
    /**
     * 组织编号
     */
    @Column(name = "org_num")
    @ApiModelProperty(value = "组织编号")
    private String orgNum;
    /**
     * 组织负责人(与org_leader_id不一定一致,兼容选择和输入两种情况)
     */
    @Column(name = "org_leader")
    @ApiModelProperty(value = "组织负责人(与org_leader_id不一定一致,兼容选择和输入两种情况)")
    private String orgLeader;
    /**
     * 组织负责人主键
     */
    @Column(name = "org_leader_id")
    @ApiModelProperty(value = "组织负责人主键")
    private String orgLeaderId;
    /**
     * 电话
     */
    @Column(name = "org_phone")
    @ApiModelProperty(value = "电话")
    private String orgPhone;
    /**
     * 传真
     */
    @Column(name = "org_fax")
    @ApiModelProperty(value = "传真")
    private String orgFax;
    /**
     * 组织地址
     */
    @Column(name = "org_address")
    @ApiModelProperty(value = "组织地址")
    private String orgAddress;
    /**
     * 组织描述
     */
    @Column(name = "org_remark")
    @ApiModelProperty(value = "组织描述")
    private String orgRemark;
    /**
     * 是否虚拟组织
     */
    @Column(name = "is_virtual")
    @ApiModelProperty(value = "是否虚拟组织")
    private Boolean isVirtual;
    /**
     * 排序值
     */
    @Column(name = "order_num")
    @ApiModelProperty(value = "排序值")
    private Integer orderNum;
}
