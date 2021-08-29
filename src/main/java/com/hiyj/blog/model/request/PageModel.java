package com.hiyj.blog.model.request;

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
public class PageModel<T> extends PageBaseModel{

    //排序方式，默认逆序
    @ApiModelProperty(value = "排序方式，默认逆序,+id正序,-id逆序")
    @JSONField(defaultValue = "-id")
    protected String sort;

    //分页查询状态
    @JSONField(defaultValue = "状态,ALL为全部")
    protected T status;
}
