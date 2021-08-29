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
public class CodeUrlModel {
    // 授权码
    @ApiModelProperty(value = "授权码", required = true)
    private String code;

    // 重定向URL
    @ApiModelProperty(value = "重定向URL", required = true)
    private String redirect;
}
