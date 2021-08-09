package xyz.firstmeet.lblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.firstmeet.lblog.annotation.PassToken;
import xyz.firstmeet.lblog.annotation.UserLoginToken;
import xyz.firstmeet.lblog.model.request.IdModel;
import xyz.firstmeet.lblog.model.response.UiConfigModel;
import xyz.firstmeet.lblog.object.Msg;
import xyz.firstmeet.lblog.services.SysConfigJsonService;
import xyz.firstmeet.lblog.utils.JwtUtils;

import java.util.Map;

@Slf4j
@Api(tags = "系统相关", value = "系统相关")
@RestController
@RequestMapping(value = "/api/sys", produces = "application/json;charset=UTF-8")
public class SysConfigController {
    private SysConfigJsonService sysConfigJsonService;

    @Autowired
    public void setSysConfigJsonService(SysConfigJsonService sysConfigJsonService) {
        this.sysConfigJsonService = sysConfigJsonService;
    }

    /**
     * 获取用户Ui配置
     *
     * @param idModel { "id": int }
     * @return config Msg
     */
    @ApiOperation(value = "通过用户id获取用户Ui配置")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS, response = UiConfigModel.class),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getUiConfig")
    @PassToken
    public String getUiConfig(@RequestBody IdModel idModel) {
        int userId;
        if (idModel.getId() != 0) {
            userId = idModel.getId();
        } else {
            return Msg.getFailMsg();
        }
        log.info("用户ID：{}  请求getUiConfig", userId);
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, sysConfigJsonService.getUiConfigByUserId(userId));
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
     * @param json { "token":token } / null，若为空直接获取默认可公开的系统配置，保留参数
     * @return Msg
     */
    @PostMapping(value = "getSysConfig")
    @PassToken
    public String getSysConfig(@RequestBody JSONObject json) {
        // 保留项
        if (!json.containsKey("token")) {
            return sysConfigJsonService.getSysConfigJson();
        }
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
