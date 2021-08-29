package com.hiyj.blog.services;

import com.hiyj.blog.object.SystemConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        final SystemConfig userSetting = sysConfigJsonService.getConfigByUserId(1);
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

    /**
     * 获取系统默认配置
     */
    @Test
    public void testGetSysConfig() {
        System.out.println(sysConfigJsonService.getSysConfigJson());
    }

    /**
     * 获取Gitee登录配置信息
     */
    @Test
    public void testGetGiteeLoginConfig() {
        System.out.println(sysConfigJsonService.getGiteeLoginConfig());
    }
}
