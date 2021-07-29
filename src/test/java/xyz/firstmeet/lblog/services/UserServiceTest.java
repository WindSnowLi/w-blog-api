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

    @Test
    public void testUserAdmin() {
        System.out.println(userJsonService.findAdminJson());
    }

    /**
     * 获取用户喜好分类占比
     */
    @Test
    public void testGetWorkByUserIdJson() {
        System.out.println(userJsonService.getWorkByUserIdJson(1));
    }
}
