package com.hiyj.blog.model.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.hiyj.blog.object.Article;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@ToString
@Setter
@Getter
public class ArticlePageModel extends PageModel<Article.Status> {
    //用户ID
    @ApiModelProperty(value = "所查用户ID")
    @JSONField(defaultValue = "-1")
    protected int userId;
}
