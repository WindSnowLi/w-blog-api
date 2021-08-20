package xyz.firstmeet.lblog.model.request;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import xyz.firstmeet.lblog.object.Article;

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
