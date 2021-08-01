package xyz.firstmeet.lblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.firstmeet.lblog.annotation.PassToken;
import xyz.firstmeet.lblog.annotation.UserLoginToken;
import xyz.firstmeet.lblog.object.Msg;
import xyz.firstmeet.lblog.services.SysConfigJsonService;
import xyz.firstmeet.lblog.utils.JwtUtils;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/api/sys", produces = "application/json;charset=UTF-8")
public class SysConfigController {
    private SysConfigJsonService sysConfigJsonService;

    @Autowired
    public void setSysConfigJsonService(SysConfigJsonService sysConfigJsonService) {
        this.sysConfigJsonService = sysConfigJsonService;
    }

    /**
     * 获取用户配置
     *
     * @param json {"id":"int"}
     * @return config Msg
     */
    @PostMapping(value = "getConfigByUserId")
    @PassToken
    public String getUserConfig(@RequestBody JSONObject json) {
        int id = json.getIntValue("id");
        log.info("用户ID：{}  请求getConfigByUserId", id);
        return sysConfigJsonService.getUserSettingJson(id);
    }

    /**
     * 设置用户配置
     *
     * @param json { "token":token, "sysConfig":SystemConfig }
     * @return Msg
     */
    @PostMapping(value = "setUiConfig")
    @UserLoginToken
    public String setUiConfig(@RequestBody JSONObject json) {
        int userId = JwtUtils.getTokenUserId(json.getString("token"));
        log.info("用户ID：{}  setUiConfig", userId);
        return sysConfigJsonService.setUiConfigByUserIdJson(userId, json.getJSONObject("sysConfig"));
    }

    /**
     * 获取系统存储配置文件
     *
     * @param json { "token":token }
     * @return Msg
     */
    @PostMapping(value = "getStorageConfig")
    @UserLoginToken
    public String getStorageConfig(@RequestBody JSONObject json) {
        int userId = JwtUtils.getTokenUserId(json.getString("token"));
        log.info("用户ID：{}  getStorageConfig", userId);
        if (userId == 1) {
            return sysConfigJsonService.getStorageConfigJson();
        } else {
            return Msg.getFailMsg();
        }
    }

    /**
     * 设置系统存储配置文件
     *
     * @param json { "token":token, "storage": 存储设置Json对象 }
     * @return Msg
     */
    @PostMapping(value = "setStorageConfig")
    @UserLoginToken
    public String setStorageConfig(@RequestBody JSONObject json) {
        int userId = JwtUtils.getTokenUserId(json.getString("token"));
        JSONObject storage = json.getJSONObject("storage");
        log.info("用户ID：{}  setStorageConfig", userId);
        if (userId == 1) {
            return sysConfigJsonService.setStorageConfigJson(storage);
        } else {
            return Msg.getFailMsg();
        }
    }

    /**
     * 获取系统配置信息
     *
     * @param json { "token":token }
     * @return Msg
     */
    @PostMapping(value = "getSysConfig")
    @UserLoginToken
    public String getSysConfig(@RequestBody JSONObject json) {
        int userId = JwtUtils.getTokenUserId(json.getString("token"));
        log.info("用户ID：{}  getSysConfig", userId);
        if (userId == 1) {
            return sysConfigJsonService.getSysConfigJson();
        } else {
            return Msg.getFailMsg();
        }
    }

    /**
     * 获取系统配置信息
     *
     * @param json { "token":token, "sys": 系统设置Json对象 }
     * @return Msg
     */
    @PostMapping(value = "setSysConfig")
    @UserLoginToken
    public String setSysConfig(@RequestBody JSONObject json) {
        int userId = JwtUtils.getTokenUserId(json.getString("token"));
        log.info("用户ID：{}  getSysConfig", userId);
        if (userId == 1) {
            return sysConfigJsonService.setSysConfigJson(json.getObject("sys", new TypeReference<Map<String, String>>() {
            }));
        } else {
            return Msg.getFailMsg();
        }
    }
}
