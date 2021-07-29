package xyz.firstmeet.lblog.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.firstmeet.lblog.object.SystemConfig;

import java.util.HashMap;

@SpringBootTest
public class SysConfigServiceTest {
    private SysConfigJsonService sysConfigJsonService;

    @Autowired
    public void setSysConfigJsonService(SysConfigJsonService sysConfigJsonService) {
        this.sysConfigJsonService = sysConfigJsonService;
    }

    /**
     * 获取用户配置Json
     */
    @Test
    public void testGetUserSettingJson() {
        final SystemConfig userSetting = sysConfigJsonService.getUserSetting(1);
        System.out.println(userSetting);
        System.out.println(userSetting.getBackground_list());
    }

    /**
     * 设置某个用户的UI配置
     */
    @Test
    public void testSetUiConfigByUserId() {
        HashMap<String, String> config = new HashMap<>();
        config.put("topbar_title", "纯天然色的菜狗");
        config.put("main_title", "WindSnowLi");
        System.out.println(config);
        sysConfigJsonService.setUiConfigByUserId(1, config);
    }
}
