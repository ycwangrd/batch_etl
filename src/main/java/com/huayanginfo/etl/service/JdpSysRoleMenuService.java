package com.huayanginfo.etl.service;

import com.huayanginfo.etl.model.entity.JdpSysRoleMenu;
import com.huayanginfo.etl.repository.JdpSysRoleMenuRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wangrd 北京华洋峻峰信息工程股份公司
 * https://www.huayanginfo.com ©2008-2021 huayanginfo.com
 * All Rights Reserved.
 * @since 2021年08月19日 星期四 17:16:46
 */
@Log4j2
@Transactional(rollbackFor = Exception.class)
public class JdpSysRoleMenuService {
    @Autowired
    private JdpSysRoleMenuRepository repository;

    @Transactional(rollbackFor = Exception.class)
    public void saveRoleMenus(String roleId, List<JdpSysRoleMenu> sysRoleMenuList) {
        // 通过角色id删除角色绑定的所有菜单
        repository.deleteByRoleId(roleId);
        // 保存用户新授权的所有角色
        repository.saveAll(sysRoleMenuList);
    }
}
