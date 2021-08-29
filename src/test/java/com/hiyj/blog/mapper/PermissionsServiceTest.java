/*
 *  PermissionsServiceTest.java, 2021-08-26
 *
 *  Copyright 2021 by WindSnowLi, Inc. All rights reserved.
 *
 */

package com.hiyj.blog.mapper;

import com.hiyj.blog.object.base.PermissionRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PermissionsServiceTest {
    private PermissionMapper permissionMapper;

    @Autowired
    public void setPermissionMapper(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Test
    public void testGetPermissions() {
        System.out.println(permissionMapper.getRolePermissionsName(2, PermissionRole.Status.ALL));
    }
}
