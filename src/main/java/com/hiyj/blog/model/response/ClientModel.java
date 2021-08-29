package com.hiyj.blog.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@ToString
@Setter
@Getter
public class ClientModel extends ClientIdModel {
    public ClientModel(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public ClientModel(String clientId, String clientSecret) {
        super(clientId);
        this.clientSecret = clientSecret;
    }

    //客户端秘钥
    @ApiModelProperty(value = "客户端秘钥", required = true)
    protected String clientSecret;
}
