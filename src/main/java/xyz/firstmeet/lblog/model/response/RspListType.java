/*
 *  RspListType.java, 2021-08-25
 *
 *  Copyright 2021 by WindSnowLi, Inc. All rights reserved.
 *
 */

package xyz.firstmeet.lblog.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ApiModel
@ToString
@Setter
@Getter
public class RspListType<T> {
    // 总数
    @ApiModelProperty(name = "total", value = "总数")
    protected int total;

    @ApiModelProperty(name = "items", value = "列表数据")
    protected List<T> items;
}
