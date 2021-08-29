package com.hiyj.blog.object;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SystemConfig implements Serializable {
    //所属用户
    private int user_id;
    //主页标题
    private String main_title;
    //顶栏标题
    private String topbar_title;
    //页脚
    private String footer;
    //ICP备案信息
    private String filing_icp;
    //网安备案信息
    private String filing_security;
    //轮播图背景
    private String background_list;
    //关于信息
    private String about;
    //系统设置存储信息
    @JSONField(serialize = false)
    private JSONObject oss;
    //后台URL
    private String admin_url;
}
