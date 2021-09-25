package com.hiyj.blog.object.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ApiModel
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class LabelBase implements Serializable {
    //label ID
    @ApiModelProperty(name = "id", value = "label ID")
    protected int id;
    //label名称
    @ApiModelProperty(name = "name", value = "label名称")
    protected String name;
    //封面
    @ApiModelProperty(name = "coverPic", value = "封面")
    protected String coverPic;
    //描述
    @ApiModelProperty(name = "describe", value = "描述")
    protected String describe;
    //浏览次数
    @ApiModelProperty(name = "pv", value = "浏览次数")
    protected int pv;
    //所属文章总数
    @ApiModelProperty(name = "num", value = "所属文章总数")
    protected int num;
}
