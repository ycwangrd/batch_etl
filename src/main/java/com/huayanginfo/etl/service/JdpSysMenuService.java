package com.huayanginfo.etl.service;

import com.huayanginfo.etl.model.entity.JdpSysMenu;
import com.huayanginfo.etl.repository.JdpSysMenuRepository;
import com.huayanginfo.etl.repository.JdpSysRoleMenuRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wangrd 北京华洋峻峰信息工程股份公司
 * https://www.huayanginfo.com ©2008-2021 huayanginfo.com
 * All Rights Reserved.
 * @since 2021年08月19日 星期四 17:13:41
 */
@Log4j2
@Service
@Transactional(rollbackFor = Exception.class)
public class JdpSysMenuService {
    @Autowired
    private JdpSysMenuRepository repository;
    @Autowired
    private JdpSysRoleMenuRepository roleMenuRepository;

    public Page<JdpSysMenu> findPages(int pageNum, int pageSize) {
        // TODO 查询条件
        Example<JdpSysMenu> example = Example.of(new JdpSysMenu());
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Order.desc("orderNum")));
        return repository.findAll(example, pageable);
    }

    public JdpSysMenu save(JdpSysMenu sysMenu) {
        return repository.save(sysMenu);
    }

    public void delete(String menuId) {
        roleMenuRepository.deleteByMenuId(menuId);
        repository.deleteById(menuId);
    }

    public void batchDelete(List<String> menuIds) {
        repository.deleteAllById(menuIds);
    }
}
