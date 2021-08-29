package com.hiyj.blog.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@ToString
@Setter
@Getter
public  class TokenTypeModel<T> extends TokenModel{
    // 负载
    @ApiModelProperty(value = "负载信息", required = true)
    protected T content;
}
