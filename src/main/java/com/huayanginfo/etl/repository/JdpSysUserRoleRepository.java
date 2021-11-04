package com.huayanginfo.etl.repository;

import com.huayanginfo.etl.model.entity.JdpSysMenu;
import com.huayanginfo.etl.model.entity.JdpSysRole;
import com.huayanginfo.etl.model.entity.JdpSysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wangrd 北京华洋峻峰信息工程股份公司
 * https://www.huayanginfo.com ©2008-2021 huayanginfo.com
 * All Rights Reserved.
 * @since 2021年08月12日 星期四 19:35:19
 */
@Repository
public interface JdpSysUserRoleRepository extends JpaRepository<JdpSysUserRole, String> {

    @Query("select jsr from JdpSysUserRole jsur" +
            " inner join JdpSysRole jsr on jsur.roleId = jsr.roleId and jsur.userId=:userId")
    List<JdpSysRole> findRolesByUserId(String userId);

    @Query("select jsm from JdpSysUserRole jsur" +
            " inner join JdpSysRole jsr on jsur.roleId = jsr.roleId and jsur.userId=:userId" +
            " inner join JdpSysRoleMenu jsrm on jsr.roleId = jsrm.roleId" +
            " inner join JdpSysMenu jsm on jsrm.menuId = jsm.menuId")
    List<JdpSysMenu> findMenusByUserId(String userId);

    @Modifying
    @Query(value = "delete from JdpSysUserRole where userId = :userId")
    void deleteByUserId(String userId);

    @Modifying
    @Query(value = "delete from JdpSysUserRole where roleId = :roleId")
    void deleteByRoleId(String roleId);
}
