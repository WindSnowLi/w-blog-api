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
public class TokenModel {
    public TokenModel(String token) {
        this.token = token;
    }

    public TokenModel() {
    }

    // token
    @ApiModelProperty(value = "身份验证信息", required = true)
    protected String token;
}
