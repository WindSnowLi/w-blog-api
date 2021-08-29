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
public class ContentModel<T> {
    //负载
    @ApiModelProperty(value = "负载", required = true)
    protected T content;
}
