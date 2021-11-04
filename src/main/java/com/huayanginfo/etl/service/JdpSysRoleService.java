package com.huayanginfo.etl.service;

import cn.hutool.core.collection.CollUtil;
import com.huayanginfo.etl.model.entity.JdpSysRole;
import com.huayanginfo.etl.repository.JdpSysRoleMenuRepository;
import com.huayanginfo.etl.repository.JdpSysRoleRepository;
import com.huayanginfo.etl.repository.JdpSysUserRoleRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wangrd 北京华洋峻峰信息工程股份公司
 * https://www.huayanginfo.com ©2008-2021 huayanginfo.com
 * All Rights Reserved.
 * @since 2021年08月19日 星期四 17:14:56
 */
@Log4j2
@Transactional(rollbackFor = Exception.class)
public class JdpSysRoleService {
    @Autowired
    private JdpSysRoleRepository repository;
    @Autowired
    private JdpSysRoleMenuRepository roleMenuRepository;
    @Autowired
    private JdpSysUserRoleRepository userRoleRepository;

    public Page<JdpSysRole> findPages(int pageNum, int pageSize) {
        // TODO 查询条件
        Example<JdpSysRole> example = Example.of(new JdpSysRole());
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Order.desc("roleCode")));
        return repository.findAll(example, pageable);
    }

    public JdpSysRole save(JdpSysRole sysRole) {
        return repository.save(sysRole);
    }

    public void delete(String roleId) {
        // 删除角色关联的菜单
        roleMenuRepository.deleteByRoleId(roleId);
        // 删除用户关联的角色
        userRoleRepository.deleteByRoleId(roleId);
        // 删除角色本身
        repository.deleteById(roleId);
    }

    public void batchDelete(List<String> roleIds) {
        if (CollUtil.isNotEmpty(roleIds)) {
            roleIds.forEach(roleId -> delete(roleId));
        }
    }
}
