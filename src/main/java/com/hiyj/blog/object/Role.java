/*
 *  Role.java, 2021-08-26
 *
 *  Copyright 2021 by WindSnowLi, Inc. All rights reserved.
 *
 */

package com.hiyj.blog.object;

import com.hiyj.blog.object.base.PermissionRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Role extends PermissionRole implements Serializable {
    // 这里列举三个最常用的角色类型
    public static String ADMIN = "admin";
    public static String USER = "user";
    public static String VISITOR = "visitor";
}
