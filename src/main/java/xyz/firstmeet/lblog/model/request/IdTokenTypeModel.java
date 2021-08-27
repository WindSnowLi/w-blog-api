/*
 *  IdTokenTypeModel.java, 2021-08-26
 *
 *  Copyright 2021 by WindSnowLi, Inc. All rights reserved.
 *
 */

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
public class IdTokenTypeModel<T> extends IdTokenModel {
    // 负载
    @ApiModelProperty(value = "负载信息", required = true)
    private T content;
}
