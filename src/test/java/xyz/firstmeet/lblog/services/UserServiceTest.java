package xyz.firstmeet.lblog.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {
    private UserJsonService userJsonService;

    @Autowired
    public void setUserService(UserJsonService userJsonService) {
        this.userJsonService = userJsonService;
    }

    @Test
    public void testFindUser() {
        System.out.println(userJsonService.findUserByAccount("goyujie@163.com"));
    }

    /**
     * 获取用户喜好分类占比
     */
    @Test
    public void testGetWorkByUserIdJson() {
        System.out.println(userJsonService.getWorkByUserIdJson(1));
    }

    /**
     * 初始密码123456，值 b8a1099b57fb53d28fba7d5717e317ea
     */
    @Test
    public void testEncryptPasswd() {
        System.out.println(userJsonService.encryptPasswd("123456"));
    }

    @Test
    public void testGetUserToken() {
        System.out.println(userJsonService.loginJson(userJsonService.findUserById(1)));
    }
}
