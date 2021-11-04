package com.huayanginfo.etl.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * @author wangrd 北京华洋峻峰信息工程股份公司
 * https://www.huayanginfo.com ©2008-2021 huayanginfo.com
 * All Rights Reserved.
 * @since 2021年08月19日 星期四 18:48:49
 */
@Entity
@Table(name = "access")
@Data
@Accessors(chain = true)
public class Access {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "shop_name")
    private String shopName;
    @Column(name = "category_name")
    private String categoryName;
    @Column(name = "brand_name")
    private String brandName;
    @Column(name = "shop_id")
    private String shopId;
    @Column(name = "omit")
    private String omit;
    @Column(name = "update_time")
    private String updateTime;
    @Column(name = "delete_status")
    private boolean deleteStatus;
    @Column(name = "create_time")
    private String createTime;
    @Column(name = "description")
    private String description;

}
