package com.hiyj.blog.object;

import com.alibaba.fastjson.annotation.JSONField;
import com.hiyj.blog.object.base.LabelBase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Article implements Serializable {
    public enum Status {
        PUBLISHED,
        DRAFT,
        DELETED,
        ALL
    }

    public enum PublishType {
        //原创
        ORIGINAL,
        //转载
        REPRINT,
        //翻译
        TRANSLATE
    }

    //文章ID
    private int id;
    //文章标题
    private String title;
    //摘要
    private String summary;
    //内容
    private String content;
    //头像链接
    private String coverPic;
    //文章分类
    private LabelBase articleType;
    //文章标签
    private List<LabelBase> labels;
    //发布时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //浏览次数
    private int pv;
    //最后更新时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    //状态 PUBLISHED发布、DRAFT草稿
    private Status status;
    //文章是否禁用评论，默认不禁用
    private boolean commentDisabled;
    //发布类型
    private PublishType publishType;
}