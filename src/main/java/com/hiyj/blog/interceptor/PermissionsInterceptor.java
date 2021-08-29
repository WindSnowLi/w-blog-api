/*
 *  PermissionsInterceptor.java, 2021-08-26
 *
 *  Copyright 2021 by WindSnowLi, Inc. All rights reserved.
 *
 */

package com.hiyj.blog.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import com.hiyj.blog.annotation.PassToken;
import com.hiyj.blog.annotation.Permission;
import com.hiyj.blog.object.Msg;
import com.hiyj.blog.object.Role;
import com.hiyj.blog.object.User;
import com.hiyj.blog.object.base.PermissionRole;
import com.hiyj.blog.services.base.PermissionService;
import com.hiyj.blog.services.base.RoleService;
import com.hiyj.blog.services.base.UserService;
import com.hiyj.blog.utils.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author windSnowLi
 */
@Component
public class PermissionsInterceptor implements HandlerInterceptor {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private PermissionService permissionService;

    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passToken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        if (method.isAnnotationPresent(Permission.class) || method.getDeclaringClass().isAnnotationPresent(Permission.class)) {
            Permission permissionClass = method.getAnnotation(Permission.class) == null ?
                    method.getDeclaringClass().getAnnotation(Permission.class) :
                    method.getAnnotation(Permission.class);
            if (permissionClass.value().length == 0) {
                response.getWriter().println(Msg.getFailMsg());
                return false;
            } else {
                // 从 http 请求头中取出 token
                String token = request.getHeader("token");
                List<String> rolePermissionsName;
                if (token == null || token.equals("")) {
                    //获取默认的访问角色权限
                    rolePermissionsName = permissionService.getRolePermissionsName(
                            roleService.getRoleByName(Role.VISITOR).getId(), PermissionRole.Status.NORMAL);
                } else {
                    // 获取 token 中的 user id
                    int userId = JwtUtils.getTokenUserId(token);
                    User user = userService.findUserById(userId);
                    String userAccount = JwtUtils.getTokenUserAccount(token);
                    String userPassword = JwtUtils.getTokenUserPassword(token);
                    if (user == null || !user.getPassword().equals(userPassword) || !user.getAccount().equals(userAccount)) {
                        response.getWriter().println(Msg.getFailMsg());
                        return false;
                    }
                    rolePermissionsName = permissionService.getUserPermissionsName(userId);
                }

                for (String value : permissionClass.value()) {
                    // 如果用户不包含所需权限则不允许访问
                    if (!rolePermissionsName.contains(value)) {
                        response.getWriter().println(Msg.getFailMsg());
                        return false;
                    }
                }
            }
        }
        return true;
    }
}