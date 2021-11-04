package com.huayanginfo.etl.model.master;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "jc_content_ext")
@Data
@Accessors(chain = true)
public class MasterContentExt implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "content_id")
    private Integer id;

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
    private Boolean is_bold;

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
    private Boolean need_regenerate;

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
}

