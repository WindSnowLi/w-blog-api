package xyz.firstmeet.lblog.model.request;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@ToString
@Setter
@Getter
public class ArticlePageModel {

    //用户ID
    @ApiModelProperty(value = "身份验证信息")
    @JSONField(defaultValue = "-1")
    private int userId;

    //页数
    @ApiModelProperty(value = "页数")
    @JSONField(defaultValue = "1")
    private int page;

    //一页行数
    @ApiModelProperty(value = "一页行数")
    @JSONField(defaultValue = "10")
    private int limit;

    //排序方式，默认逆序
    @ApiModelProperty(value = "排序方式，默认逆序,+id正序,-id逆序")
    @JSONField(defaultValue = "-id")
    private String sort;

    //分页查询状态
    @JSONField(defaultValue = "文章状态，默认published，all为全部文章类型")
    private String status;
}
