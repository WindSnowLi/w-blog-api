package xyz.firstmeet.lblog.object;

import com.alibaba.fastjson.annotation.JSONField;
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
    private ArticleLabel articleType;
    //文章标签
    private List<ArticleLabel> labels;
    //发布时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //浏览次数
    private int visitsCount;
    //最后更新时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    //状态 PUBLISHED发布、DRAFT草稿
    private Status status;
}