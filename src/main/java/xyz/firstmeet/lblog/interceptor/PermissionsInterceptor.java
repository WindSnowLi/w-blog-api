package xyz.firstmeet.lblog.interceptor;

import com.auth0.jwt.exceptions.JWTVerificationException;
import xyz.firstmeet.lblog.annotation.PassToken;
import xyz.firstmeet.lblog.annotation.Permission;
import xyz.firstmeet.lblog.annotation.UserLoginToken;
import xyz.firstmeet.lblog.object.Msg;
import xyz.firstmeet.lblog.object.User;
import xyz.firstmeet.lblog.services.base.PermissionService;
import xyz.firstmeet.lblog.services.base.UserService;
import xyz.firstmeet.lblog.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author windSnowLi
 */
@Component
public class PassTokenInterceptor implements HandlerInterceptor {

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
            Permission permission = method.getAnnotation(Permission.class) == null ?
                    method.getDeclaringClass().getAnnotation(Permission.class) :
                    method.getAnnotation(Permission.class);
            if (permission.value().length == 0) {
                return true;
            } else {
                // 从 http 请求头中取出 token
                String token = request.getHeader("token");
                // 获取 token 中的 user id
                int userId = JwtUtils.getTokenUserId(token);
                User user = userService.findUserById(userId);
                String userAccount = JwtUtils.getTokenUserAccount(token);
                String userPassword = JwtUtils.getTokenUserPassword(token);
                for (String value : permission.value()) {

                }
            }
        }
        return true;
    }
}