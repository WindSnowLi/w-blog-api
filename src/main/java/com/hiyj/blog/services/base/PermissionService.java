/*
 *  PermissionService.java, 2021-08-26
 *
 *  Copyright 2021 by WindSnowLi, Inc. All rights reserved.
 *
 */

package com.hiyj.blog.services.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hiyj.blog.mapper.PermissionMapper;
import com.hiyj.blog.object.Permission;
import com.hiyj.blog.object.base.PermissionRole;

import java.util.List;

@Service("permissionService")
public class PermissionService {
    private PermissionMapper permissionMapper;

    @Autowired
    public void setPermissionMapper(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    /**
     * 获取角色权限对象列表
     *
     * @param roleId 角色ID
     * @param status 权限状态
     * @return 权限对象List
     */
    public List<Permission> getRolePermissions(int roleId, PermissionRole.Status status) {
        return permissionMapper.getRolePermissions(roleId, status);
    }

    /**
     * 获取角色权限ID列表
     *
     * @param roleId 角色ID
     * @param status 权限状态
     * @return 权限ID List
     */
    public List<Integer> getRolePermissionsId(int roleId, PermissionRole.Status status) {
        return permissionMapper.getRolePermissionsId(roleId, status);
    }

    /**
     * 获取角色权限name列表
     *
     * @param roleId 角色ID
     * @param status 权限状态
     * @return 权限name List
     */
    public List<String> getRolePermissionsName(int roleId, PermissionRole.Status status) {
        return permissionMapper.getRolePermissionsName(roleId, status);
    }

    /**
     * 获取用户可用权限对象列表
     *
     * @param userId 角色ID
     * @return 权限对象List
     */
    public List<Permission> getUserPermissions(int userId) {
        return permissionMapper.getUserPermissions(userId);
    }

    /**
     * 获取用户可用权限ID列表
     *
     * @param userId 角色ID
     * @return 权限ID List
     */
    public List<Integer> getUserPermissionsId(int userId) {
        return permissionMapper.getUserPermissionsId(userId);
    }

    /**
     * 获取用户可用权限name列表
     *
     * @param userId 角色ID
     * @return 权限name List
     */
    public List<String> getUserPermissionsName(int userId) {
        return permissionMapper.getUserPermissionsName(userId);
    }
}
