/*
 *  RoleMapper.java, 2021-08-26
 *
 *  Copyright 2021 by WindSnowLi, Inc. All rights reserved.
 *
 */

package com.hiyj.blog.mapper;

import com.hiyj.blog.object.Role;
import com.hiyj.blog.object.base.PermissionRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper {
    /**
     * 根据用户ID和角色状态获取用户角色表
     *
     * @param userId 用户ID
     * @param status 角色状态
     * @return List<Role>
     */
    List<Role> getRoles(@Param("userId") int userId, @Param("status") PermissionRole.Status status);

    /**
     * 获取用户角色ID列表
     *
     * @param userId 用户ID
     * @param status 角色状态
     * @return 角色ID List
     */
    List<Integer> getRolesId(@Param("userId") int userId, @Param("status") PermissionRole.Status status);

    /**
     * 获取用户角色name列表
     *
     * @param userId 用户ID
     * @param status 角色状态
     * @return 角色name List
     */
    List<String> getRolesName(@Param("userId") int userId, @Param("status") PermissionRole.Status status);

    /**
     * 根据Role名字获取角色对象
     *
     * @param name 角色名
     * @return 角色对象
     */
    @Select("select * from `role` r  where r.name = #{name}")
    Role getRoleByName(@Param("name") String name);
}
