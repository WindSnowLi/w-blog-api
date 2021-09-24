package com.hiyj.blog.model.response;

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
public class UiConfigModel {
    @JSONField(name = "topbar_title")
    @ApiModelProperty(name = "topbar_title",value = "顶栏标题")
    private String topBarTitle;

    @JSONField(name = "footer")
    @ApiModelProperty(name = "footer",value = "页脚代码块")
    private String footer;

    @JSONField(name = "main_title")
    @ApiModelProperty(name = "main_title",value = "浏览器新页标签标题")
    private String mainTitle;

    @JSONField(name = "background_list")
    @ApiModelProperty(name = "background_list",value = "背景图片链接，一行一个")
    private String backgroundList;

    @JSONField(name = "include")
    @ApiModelProperty(name = "include",value = "全局引入")
    private String include;
}
