package xyz.firstmeet.lblog.model.request;

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
    // token
    @ApiModelProperty(value = "身份验证信息", required = true)
    protected String token;
}
