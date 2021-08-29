/*
 *  PermissionMapper.java, 2021-08-26
 *
 *  Copyright 2021 by WindSnowLi, Inc. All rights reserved.
 *
 */

package com.hiyj.blog.mapper;

import com.hiyj.blog.object.Permission;
import com.hiyj.blog.object.base.PermissionRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermissionMapper {

    /**
     * 获取角色权限对象列表
     *
     * @param roleId 角色ID
     * @param status 权限状态
     * @return 权限对象List
     */
    List<Permission> getRolePermissions(@Param("roleId") int roleId, @Param("status") PermissionRole.Status status);

    /**
     * 获取角色权限ID列表
     *
     * @param roleId 角色ID
     * @param status 权限状态
     * @return 权限ID List
     */
    List<Integer> getRolePermissionsId(@Param("roleId") int roleId, @Param("status") PermissionRole.Status status);

    /**
     * 获取角色权限name列表
     *
     * @param roleId 角色ID
     * @param status 权限状态
     * @return 权限name List
     */
    List<String> getRolePermissionsName(@Param("roleId") int roleId, @Param("status") PermissionRole.Status status);

    /**
     * 获取用户可用权限对象列表
     *
     * @param userId 角色ID
     * @return 权限对象List
     */
    List<Permission> getUserPermissions(@Param("userId") int userId);

    /**
     * 获取用户可用权限ID列表
     *
     * @param userId 角色ID
     * @return 权限ID List
     */
    List<Integer> getUserPermissionsId(@Param("userId") int userId);

    /**
     * 获取用户可用权限name列表
     *
     * @param userId 角色ID
     * @return 权限name List
     */
    List<String> getUserPermissionsName(@Param("userId") int userId);
}
