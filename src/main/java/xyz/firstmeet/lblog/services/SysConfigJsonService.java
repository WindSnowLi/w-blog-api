package xyz.firstmeet.lblog.services;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.springframework.stereotype.Service;
import xyz.firstmeet.lblog.object.Msg;
import xyz.firstmeet.lblog.object.SystemConfig;
import xyz.firstmeet.lblog.services.base.SysConfigService;

import java.util.HashMap;

@Service("sysConfigJsonService")
public class SysConfigJsonService extends SysConfigService {
    /**
     * 获取用户配置Json
     *
     * @param user_id 用户ID
     * @return 配置Msg
     */
    public String getUserSettingJson(int user_id) {
        final SystemConfig userSetting = getUserSetting(user_id);
        if (userSetting.getMain_title() != null) {
            userSetting.setMain_title(userMapper.findUserId(user_id).getNickname());
        }
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, userSetting);
    }

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

        for (String key : systemConfig.keySet()) {
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
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, null);
    }
}
