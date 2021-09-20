package com.hiyj.blog.services;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hiyj.blog.object.Msg;
import com.hiyj.blog.services.base.SysConfigService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

        String []ready={"main_title","topbar_title","footer","background_list"};

        for (String key : ready) {
            configTable.put(key, systemConfig.get(key));
        }
        configTable.remove("user_id");
        if (configTable.size() != 0) {
            this.setUiConfigByUserId(user_id, configTable);
        }
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
    public String getSysConfigJson() {
        //对前端的提交格式描述，OSS的直接合并到了本身配置里
        String template = "{\"title\":\"系统配置\",\"type\":\"object\",\"properties\":{\"filing_icp\":{\"type\":\"string\",\"title\":\"ICP备案\",\"description\":\"完整ICP备案备案号\"},\"filing_security\":{\"type\":\"string\",\"title\":\"公网备案\",\"description\":\"完整公网备案备案号\"},\"admin_url\":{\"type\":\"string\",\"title\":\"后台url根链接\",\"description\":\"后台url根链接\"}}}";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("template", JSONObject.parseObject(template));
        jsonObject.put("sys", getSysConfig());
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, jsonObject);
    }

    /**
     * 设置系统配置
     *
     * @param configMap 系统配置表
     * @return Msg
     */
    public String setSysConfigJson(Map<String, String> configMap) {
        setSysConfig(configMap);
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, null);
    }

}
