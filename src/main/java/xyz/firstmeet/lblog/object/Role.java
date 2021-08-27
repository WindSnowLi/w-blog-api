/*
 *  Role.java, 2021-08-26
 *
 *  Copyright 2021 by WindSnowLi, Inc. All rights reserved.
 *
 */

package xyz.firstmeet.lblog.object;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import xyz.firstmeet.lblog.object.base.PermissionRole;

@Getter
@Setter
@ToString
public class Role extends PermissionRole {
    // 这里列举三个最常用的角色类型
    public static String ADMIN = "admin";
    public static String USER = "user";
    public static String VISITOR = "visitor";
}
