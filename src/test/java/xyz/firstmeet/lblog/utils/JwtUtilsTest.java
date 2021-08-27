package xyz.firstmeet.lblog.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.firstmeet.lblog.services.UserJsonService;

@SpringBootTest
public class JwtUtilsTest {
    private UserJsonService userJsonService;

    @Autowired
    public void setUserService(UserJsonService userJsonService) {
        this.userJsonService = userJsonService;
    }

    @Test
    public void testReadJwt() {
        String rs = JwtUtils.getToken(userJsonService.findUserByAccount("admin@163.com"));
        System.out.println(rs);
        System.out.println(JwtUtils.getTokenUserId(rs));
        System.out.println(JwtUtils.getTokenUserAccount(rs));
    }
}
