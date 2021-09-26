package com.hiyj.blog.services;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hiyj.blog.object.Msg;
import com.hiyj.blog.services.base.SysConfigService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("sysConfigJsonService")
public class SysConfigJsonService extends SysConfigService {

    /**
     * 设置某个用户的UI配置
     *
     * @param user_id          用户ID
     * @param systemConfigJson 配置表
     * @return Msg
     */
    public String setUiConfigByUserIdJson(int user_id, JSONObject systemConfigJson) {
        HashMap<String, String> systemConfig = JSONObject.parseObject(systemConfigJson.toJSONString(), new TypeReference<>() {
        });
        HashMap<String, String> configTable = new HashMap<>();

        String[] ready = {"main_title", "topbar_title", "footer", "background_list", "include"};

        for (String key : ready) {
            configTable.put(key, systemConfig.get(key));
        }
        this.setUiConfigByUserId(user_id, configTable);

        return Msg.getSuccessMsg();
    }

    /**
     * 获取系统存储配置文件
     *
     * @return Msg
     */
    public String getStorageConfigJson() {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, this.getStorageConfig());
    }

    /**
     * 设置系统存储配置文件
     *
     * @param storage 存储设置Json对象
     * @return Msg
     */
    public String setStorageConfigJson(JSONObject storage) {
        setStorageConfig(storage);
        return Msg.getSuccessMsg();
    }

    /**
     * 获取系统配置信息
     *
     * @return Msg
     */
    public String getFixedConfigJson() {
        //对前端的提交格式描述，OSS需特殊处理，直接合并到了本身配置里
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, getFixedConfig());
    }

    /**
     * 设置系统配置
     *
     * @param config 系统配置表
     * @return Msg
     */
    public String setFixedConfigJson(JSONObject config) {
        setFixedConfig(config);
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, null);
    }

}
