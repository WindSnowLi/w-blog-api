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
public class IdModel {
    //ID
    @ApiModelProperty(value = "ID信息", required = true)
    protected int id;
}
