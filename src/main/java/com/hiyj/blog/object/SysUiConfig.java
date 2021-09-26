package com.hiyj.blog.object;

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
public class SysUiConfig {
    @JSONField(name = "topbar_title")
    @ApiModelProperty(name = "topbar_title", value = "顶栏标题")
    private String topBarTitle;

    @JSONField(name = "footer")
    @ApiModelProperty(name = "footer", value = "页脚代码块")
    private String footer;

    @JSONField(name = "main_title")
    @ApiModelProperty(name = "main_title", value = "浏览器新页标签标题")
    private String mainTitle;

    @JSONField(name = "background_list")
    @ApiModelProperty(name = "background_list", value = "背景图片链接，一行一个")
    private String backgroundList;

    @JSONField(name = "include")
    @ApiModelProperty(name = "include", value = "全局引入")
    private String include;

    //ICP备案信息
    @JSONField(name = "filing_icp")
    @ApiModelProperty(name = "filing_icp", value = "ICP备案信息")
    private String filing_icp;

    //网安备案信息
    @JSONField(name = "filing_security")
    @ApiModelProperty(name = "filing_security", value = "网安备案信息")
    private String filing_security;

    //后台URL
    @JSONField(name = "admin_url")
    @ApiModelProperty(name = "admin_url", value = "后台URL")
    private String admin_url;

    //关于信息
    @JSONField(name = "about")
    @ApiModelProperty(name = "about", value = "关于信息")
    private String about;
}
