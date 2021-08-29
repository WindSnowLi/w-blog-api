package com.hiyj.blog.mapper;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

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
        final List<JSONObject> sysUiConfigByUserId = sysConfigMapper.getUiConfigByUserId(1);
        HashMap<String, String> rs = new HashMap<>();
        for (JSONObject json : sysUiConfigByUserId) {
            rs.put(json.getString("item"), json.getString("value"));
        }
        if (sysUiConfigByUserId.size() > 0) {
            rs.put("user_id", sysUiConfigByUserId.get(0).getString("user_id"));
        }
        System.out.println(rs);
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
        System.out.println(sysConfigMapper.getSysConfig());
    }

    @Test
    void testGetOtherLoginConfig() {
        System.out.println(sysConfigMapper.getOtherLoginConfig());
    }
}
