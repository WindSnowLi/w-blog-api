package com.hiyj.blog.services.base;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.hiyj.blog.mapper.SysConfigMapper;
import com.hiyj.blog.mapper.UserMapper;
import com.hiyj.blog.model.response.ClientIdModel;
import com.hiyj.blog.object.SysUiConfig;
import com.hiyj.blog.model.share.ClientModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sysConfigService")
public class SysConfigService {
    protected SysConfigMapper sysConfigMapper;
    protected UserMapper userMapper;

    @Autowired
    public void setSystemConfigMapper(SysConfigMapper sysConfigMapper) {
        this.sysConfigMapper = sysConfigMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 获取用户UI配置
     *
     * @param user_id 用户ID
     * @return 配置表
     */
    public SysUiConfig getUiConfigByUserId(int user_id) {
        final List<Map<String, String>> sysUiConfigByUserId = sysConfigMapper.getUiConfigByUserId(user_id);
        HashMap<String, String> rs = new HashMap<>();
        for (Map<String, String> map : sysUiConfigByUserId) {
            rs.put(map.get("item"), map.get("value"));
        }
        JSONObject baseSysConfig = getFixedConfig().getJSONObject("sys");
        rs.put("filing_icp", baseSysConfig.getOrDefault("filing_icp", "").toString());
        rs.put("filing_security", baseSysConfig.getOrDefault("filing_security", "").toString());
        rs.put("admin_url", baseSysConfig.getOrDefault("admin_url", "").toString());
        return JSONObject.parseObject(JSONObject.toJSONString(rs), SysUiConfig.class);
    }

    /**
     * 获取系统默认配置
     *
     * @return 系统配置表
     */
    public JSONObject getFixedConfig() {
        return JSONObject.parseObject(sysConfigMapper.getFixedConfig());
    }

    /**
     * 设置系统配置
     *
     * @param config 配置表
     */
    public void setFixedConfig(JSONObject config) {
        JSONObject configJson = getFixedConfig();
        configJson.put("sys", config);
        sysConfigMapper.setFixedConfig(configJson.toJSONString());
    }

    /**
     * 设置某个用户的UI配置
     *
     * @param user_id   用户ID
     * @param configMap 配置表
     */
    public void setUiConfigByUserId(int user_id, Map<String, String> configMap) {
        sysConfigMapper.setUiConfigByUserId(user_id, configMap);
    }

    /**
     * 获取系统存储配置文件
     *
     * @return SystemConfig
     */
    public JSONObject getStorageConfig() {
        return JSONObject.parseObject(sysConfigMapper.getOSSConfig(), Feature.OrderedField);
    }

    /**
     * 设置系统存储配置文件
     *
     * @param storage 存储设置Json对象
     */
    public void setStorageConfig(JSONObject storage) {
        JSONObject storageConfig = getStorageConfig();
        storageConfig.put("storage", storage);
        sysConfigMapper.setStorageConfig(storageConfig.toJSONString());
    }

    /**
     * 获取Gitee登录配置信息
     *
     * @return Gitee的JSON格式信息
     */
    public JSONObject getGiteeLoginConfig() {
        return JSONObject.parseObject(sysConfigMapper.getOtherLoginConfig()).getJSONObject("gitee");
    }

    /**
     * 获取Gitee应用程序ID，用于Gitee登录
     *
     * @return client_id
     */
    public ClientIdModel getGiteeClientId() {
        return getGiteeLoginConfig().getJSONObject("client").toJavaObject(ClientIdModel.class);
    }

    /**
     * 设置Gitee登录配置
     */
    public void setGiteeConfig(ClientModel clientModel) {
        JSONObject config = JSONObject.parseObject(sysConfigMapper.getOtherLoginConfig());
        config.getJSONObject("gitee").put("client", clientModel);
        sysConfigMapper.setGiteeConfig(config.toJSONString());
    }
}
