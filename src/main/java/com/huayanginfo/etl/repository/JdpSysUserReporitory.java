package com.huayanginfo.etl.repository;

import com.huayanginfo.etl.model.entity.JdpSysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wangrd 北京华洋峻峰信息工程股份公司
 * https://www.huayanginfo.com ©2008-2021 huayanginfo.com
 * All Rights Reserved.
 * @since 2021年08月12日 星期四 19:25:53
 */
@Repository
public interface JdpSysUserReporitory extends JpaRepository<JdpSysUser, String> {

}
