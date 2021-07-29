package xyz.firstmeet.lblog.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.firstmeet.lblog.object.User;

@SpringBootTest
public class JwtUtilsTest {
    @Test
    public void testReadJwt() {
        User user = new User();
        user.setAccount("goyujie@163.com");
        user.setPassword("111111");
        String rs = JwtUtils.getToken(user);
        System.out.println(rs);
        System.out.println(JwtUtils.getTokenUserId(rs));
        System.out.println(JwtUtils.getTokenUserAccount(rs));
    }
}
