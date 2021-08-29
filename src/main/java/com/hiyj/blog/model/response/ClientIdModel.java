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
public class ClientIdModel {
    public ClientIdModel() {
    }

    public ClientIdModel(String clientId) {
        this.clientId = clientId;
    }

    // token
    @ApiModelProperty(value = "客户端ID", required = true)
    protected String clientId;
}
