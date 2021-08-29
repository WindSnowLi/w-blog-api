/*
 *  PermissionRole.java, 2021-08-26
 *
 *  Copyright 2021 by WindSnowLi, Inc. All rights reserved.
 *
 */

package com.hiyj.blog.object.base;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class PermissionRole {
    public enum Status {
        //正常
        NORMAL,
        //忽略
        IGNORE,
        //所有
        ALL
    }

    // 权限ID
    protected int id;
    // 权限命名
    protected String name;
    // 权限介绍
    protected String info;
    // 权限创建时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    protected Date createTime;
    // 权限更新时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    protected Date updateTime;
    //权限状态
    protected Status status;
}
