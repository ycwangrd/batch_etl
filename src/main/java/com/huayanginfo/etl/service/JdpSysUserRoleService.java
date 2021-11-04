package com.huayanginfo.etl.service;

import com.huayanginfo.etl.model.entity.JdpSysMenu;
import com.huayanginfo.etl.model.entity.JdpSysRole;
import com.huayanginfo.etl.model.entity.JdpSysUserRole;
import com.huayanginfo.etl.repository.JdpSysUserRoleRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wangrd 北京华洋峻峰信息工程股份公司
 * https://www.huayanginfo.com ©2008-2021 huayanginfo.com
 * All Rights Reserved.
 * @since 2021年08月13日 星期五 13:37:08
 */
@Log4j2
@Service
public class JdpSysUserRoleService {
    @Autowired
    private JdpSysUserRoleRepository repository;

    /**
     * 通过用户id获取用户角色列表
     *
     * @param userId 用户主键
     * @return
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<JdpSysRole> findRolesByUserId(String userId) {
        return repository.findRolesByUserId(userId);
    }

    /**
     * 通过用户id获取用户拥有的所有菜单
     * @param userId
     * @return
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<JdpSysMenu> findMenusByUserId(String userId) {
        return repository.findMenusByUserId(userId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveUserRoles(String userId, List<JdpSysUserRole> sysUserRoleList) {
        // 通过用户id删除该用户的所有角色
        repository.deleteByUserId(userId);
        // 保存用户新授权的所有角色
        repository.saveAll(sysUserRoleList);
    }
}
