package xyz.firstmeet.lblog.interceptor;

import com.auth0.jwt.exceptions.JWTVerificationException;
import xyz.firstmeet.lblog.annotation.PassToken;
import xyz.firstmeet.lblog.annotation.UserLoginToken;
import xyz.firstmeet.lblog.object.Msg;
import xyz.firstmeet.lblog.object.User;
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
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class) || method.getDeclaringClass().isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class) == null ? method.getDeclaringClass().getAnnotation(UserLoginToken.class) : method.getAnnotation(UserLoginToken.class);
            if (userLoginToken == null) {
                return false;
            }
            if (userLoginToken.required()) {
                // 从 http 请求头中取出 token
                String token = request.getHeader("token");
                // 执行认证
                if (token == null) {
                    response.getWriter().println(Msg.getFailMsg());
                    return false;
                }
                // 获取 token 中的 user id
                int userId;
                // 验证 token
                try {
                    userId = JwtUtils.getTokenUserId(token);
                } catch (JWTVerificationException e) {
                    response.getWriter().println(Msg.getFailMsg());
                    return false;
                }

                User user = userService.findUserById(userId);
                if (user == null) {
                    response.getWriter().println(Msg.getFailMsg());
                    return false;
                }

                String userAccount = JwtUtils.getTokenUserAccount(token);
                String userPassword = JwtUtils.getTokenUserPassword(token);
                if (!user.getAccount().equals(userAccount) || !user.getPassword().equals(userPassword)) {
                    response.getWriter().println(Msg.getFailMsg());
                }
            }
        }
        return true;
    }
}