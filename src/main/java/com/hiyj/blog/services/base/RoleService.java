/*
 *  RoleService.java, 2021-08-26
 *
 *  Copyright 2021 by WindSnowLi, Inc. All rights reserved.
 *
 */

package com.hiyj.blog.services.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hiyj.blog.mapper.RoleMapper;
import com.hiyj.blog.object.Role;
import com.hiyj.blog.object.base.PermissionRole;

import java.util.List;

@Service("roleService")
public class RoleService {
    private RoleMapper roleMapper;

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    /**
     * 根据用户ID和角色状态获取用户角色表
     *
     * @param userId 用户ID
     * @param status 角色状态
     * @return List<Role>
     */
    public List<Role> getRoles(int userId, PermissionRole.Status status) {
        return roleMapper.getRoles(userId, status);
    }

    /**
     * 获取用户角色ID列表
     *
     * @param userId 用户ID
     * @param status 角色状态
     * @return 角色ID List
     */
    public List<Integer> getRolesId(int userId, PermissionRole.Status status) {
        return roleMapper.getRolesId(userId, status);
    }

    /**
     * 获取用户角色name列表
     *
     * @param userId 用户ID
     * @param status 角色状态
     * @return 角色name List
     */
    public List<String> getRolesName(int userId, PermissionRole.Status status) {
        return roleMapper.getRolesName(userId, status);
    }

    /**
     * 根据Role名字获取角色对象
     *
     * @param name 角色名
     * @return 角色对象
     */
    public Role getRoleByName(String name) {
        return roleMapper.getRoleByName(name);
    }
}
