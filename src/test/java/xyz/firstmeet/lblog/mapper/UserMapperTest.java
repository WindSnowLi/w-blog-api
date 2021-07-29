package xyz.firstmeet.lblog.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserMapperTest {
    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Test
    public void testFindUser() {
        System.out.println(userMapper.findUserAccount("111111"));
    }

    @Test
    public void testUserAdmin() {
        System.out.println(userMapper.findAdmin());
    }

    @Test
    public void testGetUserLinks() {
        System.out.println(userMapper.getUserLinks(1));
    }

    /**
     * 获取用户喜好分类占比
     */
    @Test
    public void testGetActivityByUserId(){
        System.out.println(userMapper.getActivityByUserId(1));
    }
}
