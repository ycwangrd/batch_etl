package com.huayanginfo.etl.model.slave;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "jc_content_ext_bak")
@Data
@Accessors(chain = true)
public class SlaveContentExt implements Serializable {
    @Id
    @Column(name = "content_id", unique = true, nullable = false)
    private Integer content_id;

    /**
     * 标题
     */
    private String title;

    /**
     * 简短标题
     */
    private String short_title;

    /**
     * 作者
     */
    private String author;

    /**
     * 来源
     */
    private String origin;

    /**
     * 来源链接
     */
    private String origin_url;

    /**
     * 描述
     */
    private String description;

    /**
     * 发布日期
     */
    private LocalDateTime release_date;

    /**
     * 媒体路径
     */
    private String media_path;

    /**
     * 媒体类型
     */
    private String media_type;

    /**
     * 标题颜色
     */
    private String title_color;

    /**
     * 是否加粗
     */
    private Integer is_bold;

    /**
     * 标题图片
     */
    private String title_img;

    /**
     * 内容图片
     */
    private String content_img;

    /**
     * 类型图片
     */
    private String type_img;

    /**
     * 外部链接
     */
    private String link;

    /**
     * 指定模板
     */
    private String tpl_content;

    /**
     * 需要重新生成静态页
     */
    private Integer need_regenerate;

    /**
     * 手机内容页模板
     */
    private String tpl_mobile_content;

    /**
     * 固顶到期日期
     */
    private LocalDateTime toplevel_date;

    /**
     * 归档日期
     */
    private LocalDateTime pigeonhole_date;

    private static final long serialVersionUID = 1L;
}

