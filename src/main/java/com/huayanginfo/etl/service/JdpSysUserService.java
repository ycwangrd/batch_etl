package com.huayanginfo.etl.service;

import com.huayanginfo.etl.model.entity.JdpSysUser;
import com.huayanginfo.etl.repository.JdpSysUserReporitory;
import com.huayanginfo.etl.repository.JdpSysUserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author wangrd 北京华洋峻峰信息工程股份公司
 * https://www.huayanginfo.com ©2008-2021 huayanginfo.com
 * All Rights Reserved.
 * @since 2021年08月12日 星期四 19:36:17
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class JdpSysUserService {
    @Autowired
    private JdpSysUserReporitory reporitory;
    @Autowired
    private JdpSysUserRoleRepository userRoleRepository;

    /**
     * 通过用户名查询用户
     *
     * @param username
     * @return
     */
    @Transactional(readOnly = true)
    public JdpSysUser findOne(String username) {
        Example<JdpSysUser> example = Example.of(new JdpSysUser().setUserName(username));
        Optional<JdpSysUser> sysUser = reporitory.findOne(example);
        return sysUser.orElse(null);
    }

    public JdpSysUser save(JdpSysUser entity) {
        return reporitory.save(entity);
    }

    public void delete(String userId) {
        // 删除用户绑定的角色
        userRoleRepository.deleteByUserId(userId);
        // 删除用户本身
        reporitory.deleteById(userId);
    }
}
