package com.huayanginfo.etl.repository;

import com.huayanginfo.etl.model.entity.JdpSysRoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author wangrd 北京华洋峻峰信息工程股份公司
 * https://www.huayanginfo.com ©2008-2021 huayanginfo.com
 * All Rights Reserved.
 * @since 2021年08月12日 星期四 19:34:47
 */
@Repository
public interface JdpSysRoleMenuRepository extends JpaRepository<JdpSysRoleMenu, String> {

    @Modifying
    @Query("delete from JdpSysRoleMenu where roleId = :roleId")
    void deleteByRoleId(String roleId);

    @Modifying
    @Query("delete from JdpSysRoleMenu where menuId = :menuId")
    void deleteByMenuId(String menuId);
}
