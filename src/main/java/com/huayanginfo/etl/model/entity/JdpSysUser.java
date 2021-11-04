package com.huayanginfo.etl.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@ApiModel(value = "com-huayanginfo-etl-model-entity-JdpSysUser")
@Data
@Accessors(chain = true)
@Entity
@Table(name = "jdp_sys_user")
public class JdpSysUser extends EntityBase {
    private static final long serialVersionUID = 1L;
    /**
     * 用户主键id
     */
    @Id
    @Column(name = "user_id", nullable = false)
    @ApiModelProperty(value = "用户主键id")
    private String userId;
    /**
     * 是否管理员(1:管理员,2:注册用户)
     */
    @Column(name = "is_admin")
    @ApiModelProperty(value = "是否管理员(1:管理员,2:注册用户)")
    private Boolean isAdmin;
    /**
     * 组织id(is_admin字段为1时,此字段为必填)
     */
    @Column(name = "org_id")
    @ApiModelProperty(value = "组织id(is_admin字段为1时,此字段为必填)")
    private String orgId;
    /**
     * 用户名
     */
    @Column(name = "user_name")
    @ApiModelProperty(value = "用户名")
    private String userName;
    /**
     * 用户昵称
     */
    @Column(name = "user_alias")
    @ApiModelProperty(value = "用户昵称")
    private String userAlias;
    /**
     * 邮箱
     */
    @Column(name = "user_email")
    @ApiModelProperty(value = "邮箱")
    private String userEmail;
    /**
     * 是否启用 true是   false否
     */
    @Column(name = "is_enabled")
    @ApiModelProperty(value = "是否启用 true是   false否")
    private Boolean isEnabled;
    /**
     * 最后登录IP
     */
    @Column(name = "last_login_ip")
    @ApiModelProperty(value = "最后登录IP")
    private String lastLoginIp;
    /**
     * 密码(MD5加密密码)
     */
    @Column(name = "user_passwd")
    @ApiModelProperty(value = "密码(MD5加密密码)")
    private String userPasswd;
    /**
     * 登录错误次数
     */
    @Column(name = "login_error_count")
    @ApiModelProperty(value = "登录错误次数")
    private Integer loginErrorCount;
    /**
     * 检查周期内登陆错误开始时间（登陆成功后会清空）
     */
    @Column(name = "first_login_error_time")
    @ApiModelProperty(value = "检查周期内登陆错误开始时间（登陆成功后会清空）")
    private LocalDateTime firstLoginErrorTime;
    /**
     * 加密混淆码(盐)
     */
    @Column(name = "obfuscation_code")
    @ApiModelProperty(value = "加密混淆码(盐)")
    private String obfuscationCode;
    /**
     * 登录次数
     */
    @Column(name = "login_count")
    @ApiModelProperty(value = "登录次数")
    private Integer loginCount;
    /**
     * 最后登录时间
     */
    @Column(name = "last_login_time")
    @ApiModelProperty(value = "最后登录时间")
    private LocalDateTime lastLoginTime;
    /**
     * 用户组
     */
    @Column(name = "group_id")
    @ApiModelProperty(value = "用户组")
    private Integer groupId;
    /**
     * 用户等级
     */
    @Column(name = "level_id")
    @ApiModelProperty(value = "用户等级")
    private Integer levelId;
    /**
     * 积分
     */
    @Column(name = "integral")
    @ApiModelProperty(value = "积分")
    private Integer integral;
    /**
     * 手机号
     */
    @Column(name = "user_phone")
    @ApiModelProperty(value = "手机号")
    private String userPhone;
    /**
     * 密码最后被改变时间
     */
    @Column(name = "last_password_change")
    @ApiModelProperty(value = "密码最后被改变时间")
    private LocalDateTime lastPasswordChange;
    /**
     * 用户审核状态(1审核通过、2审核不通过 0待审核 )
     */
    @Column(name = "check_status")
    @ApiModelProperty(value = "用户审核状态(1审核通过、2审核不通过 0待审核 )")
    private Short checkStatus;
    /**
     * 禁止登陆结束时间
     */
    @Column(name = "login_limit_end")
    @ApiModelProperty(value = "禁止登陆结束时间")
    private LocalDateTime loginLimitEnd;
    /**
     * 是否已重置密码
     */
    @Column(name = "is_reset_password")
    @ApiModelProperty(value = "是否已重置密码")
    private Boolean isResetPassword;
    /**
     * 提醒修改密码消息是否已发
     */
    @Column(name = "pass_msg_has_send")
    @ApiModelProperty(value = "提醒修改密码消息是否已发")
    private Boolean passMsgHasSend;
}
