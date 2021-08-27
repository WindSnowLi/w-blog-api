/*
 *  RoleMapperTest.java, 2021-08-26
 *
 *  Copyright 2021 by WindSnowLi, Inc. All rights reserved.
 *
 */

package xyz.firstmeet.lblog.mapper;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.firstmeet.lblog.object.base.PermissionRole;

@SpringBootTest
public class RoleMapperTest {
    private RoleMapper roleMapper;

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Test
    public void testGetRoles() {
        System.out.println(JSON.toJSONString(roleMapper.getRoles(1, PermissionRole.Status.ALL)));
    }
}
