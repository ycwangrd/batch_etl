package com.huayanginfo.etl.tasks.contentExt;

import com.huayanginfo.etl.model.slave.SlaveContentExt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wangrd 北京华洋峻峰信息工程股份公司
 * https://www.huayanginfo.com ©2008-2021 huayanginfo.com
 * All Rights Reserved.
 * @since 2021年08月24日 星期二 19:32:22
 */
@Repository
public interface SlaveContentExtRepository extends JpaRepository<SlaveContentExt, Integer> {
}
