/*
 *  PageBaseModel.java, 2021-08-26
 *
 *  Copyright 2021 by WindSnowLi, Inc. All rights reserved.
 *
 */

package com.hiyj.blog.model.request;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@ToString
@Setter
@Getter
public class PageBaseModel {
    //页数
    @ApiModelProperty(value = "页数")
    @JSONField(defaultValue = "1")
    protected int page;

    //一页行数
    @ApiModelProperty(value = "一页行数")
    @JSONField(defaultValue = "10")
    protected int limit;
}
