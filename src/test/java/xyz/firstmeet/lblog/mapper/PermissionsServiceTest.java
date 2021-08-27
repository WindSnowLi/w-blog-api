/*
 *  PermissionsServiceTest.java, 2021-08-26
 *
 *  Copyright 2021 by WindSnowLi, Inc. All rights reserved.
 *
 */

package xyz.firstmeet.lblog.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.firstmeet.lblog.object.base.PermissionRole;

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
