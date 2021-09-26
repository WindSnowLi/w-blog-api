package com.hiyj.blog.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
public class SysConfigMapperTest {
    private SysConfigMapper sysConfigMapper;

    @Autowired
    public void setSystemSettingMapper(SysConfigMapper sysConfigMapper) {
        this.sysConfigMapper = sysConfigMapper;
    }


    /**
     * 获取用户配置表
     */
    @Test
    public void testGetSysUiConfigByUserId() {
        System.out.println(sysConfigMapper.getUiConfigByUserId(1));
    }

    /**
     * 设置某个用户的UI配置
     */
    @Test
    public void testSetUiConfigByUserId() {
        HashMap<String, String> config = new HashMap<>();
        config.put("footer", "额滴神");
        sysConfigMapper.setUiConfigByUserId(1, config);
    }

    /**
     * 获取OSS的相关配置
     */
    @Test
    public void testGetOSSConfig() {
        System.out.println(sysConfigMapper.getOSSConfig());
    }

    /**
     * 获取系统默认配置
     */
    @Test
    public void testGetSysUiConfig() {
        System.out.println(sysConfigMapper.getFixedConfig());
    }

    @Test
    void testGetOtherLoginConfig() {
        System.out.println(sysConfigMapper.getOtherLoginConfig());
    }
}
