package com.hiyj.blog.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hiyj.blog.model.share.ClientModel;
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
import com.hiyj.blog.annotation.PassToken;
import com.hiyj.blog.annotation.Permission;
import com.hiyj.blog.model.request.ContentModel;
import com.hiyj.blog.model.request.IdModel;
import com.hiyj.blog.model.request.TokenTypeModel;
import com.hiyj.blog.model.response.ClientIdModel;
import com.hiyj.blog.model.response.UiConfigModel;
import com.hiyj.blog.object.Msg;
import com.hiyj.blog.services.SysConfigJsonService;
import com.hiyj.blog.utils.JwtUtils;

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
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, sysConfigJsonService.getUiConfigByUserId(userId));
    }

    /**
     * 设置用户配置
     *
     * @param tokenTypeModel {
     *                       "token":token,
     *                       "content":SystemConfig
     *                       }
     * @return Msg
     */
    @ApiOperation(value = "设置用户配置")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "setUiConfig")
    @Permission(value = {"UI-SETTING"})
    public String setUiConfig(@RequestBody TokenTypeModel<JSONObject> tokenTypeModel) {
        int userId = JwtUtils.getTokenUserId(tokenTypeModel.getToken());
        return sysConfigJsonService.setUiConfigByUserIdJson(userId, tokenTypeModel.getContent());
    }

    /**
     * 获取系统存储配置文件
     *
     * @return Msg
     */
    @ApiOperation(value = "获取系统存储配置文件")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getStorageConfig")
    @Permission(value = {"STORE-SETTING"})
    public String getStorageConfig() {
        return sysConfigJsonService.getStorageConfigJson();
    }

    /**
     * 设置系统存储配置文件
     *
     * @param contentModel { "content": 存储设置Json对象 }
     * @return Msg
     */
    @ApiOperation(value = "设置系统存储配置文件")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "setStorageConfig")
    @Permission(value = {"STORE-SETTING"})
    public String setStorageConfig(@RequestBody ContentModel<JSONObject> contentModel) {
        return sysConfigJsonService.setStorageConfigJson(contentModel.getContent());

    }

    /**
     * 获取系统配置信息
     *
     * @return Msg
     */
    @ApiOperation(value = "获取系统配置信息")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getSysConfig")
    @PassToken
    public String getSysConfig() {
        return sysConfigJsonService.getSysConfigJson();
    }

    /**
     * 设置系统配置信息
     *
     * @param contentModel { content: 系统设置Json对象 }
     * @return Msg
     */
    @ApiOperation(value = "设置系统配置信息")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "setSysConfig")
    @Permission(value = {"SYS-SETTING"})
    public String setSysConfig(@RequestBody ContentModel<JSONObject> contentModel) {
        return sysConfigJsonService.setSysConfigJson(JSONObject.parseObject(contentModel.getContent().toJSONString(), new TypeReference<>() {
        }));
    }

    /**
     * 获取Gitee应用程序ID，用于Gitee登录
     *
     * @return Msg(client_id)
     */
    @ApiOperation(value = "获取Gitee应用程序ID，用于Gitee登录")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS, response = ClientIdModel.class),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getGiteeClientId")
    @PassToken
    public String getGiteeClientId() {
        return Msg.getSuccessMsg(sysConfigJsonService.getGiteeClientId());
    }

    /**
     * 获取Gitee的登录配置信息
     *
     * @return Msg(client_id, client_secret)
     */
    @ApiOperation(value = "获取Gitee的登录配置信息")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS, response = ClientModel.class),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getGiteeConfig")
    @Permission(value = {"SYS-SETTING"})
    public String getGiteeConfig() {
        return Msg.getSuccessMsg(sysConfigJsonService.getGiteeLoginConfig());
    }

    /**
     * 设置Gitee登录配置
     *
     * @return Msg
     */
    @ApiOperation(value = "获取Gitee的登录配置信息")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "setGiteeConfig")
    @Permission(value = {"SYS-SETTING"})
    public String setGiteeConfig(@RequestBody ClientModel clientModel) {
        sysConfigJsonService.setGiteeConfig(clientModel);
        return Msg.getSuccessMsg();
    }

    /**
     * 获取杂项设置,含格式描述
     *
     * @return Msg
     */
    @ApiOperation(value = "获取杂项设置,含格式描述")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getSundry")
    @PassToken
    public String getSundry() {
        return Msg.getSuccessMsg(sysConfigJsonService.getSundry());
    }

    /**
     * 设置杂项
     *
     * @return Msg
     */
    @ApiOperation(value = "设置杂项")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "setSundry")
    @Permission(value = {"SYS-SETTING"})
    public String setSundry(@RequestBody ContentModel<JSONObject> contentModel) {
        sysConfigJsonService.setSundry(contentModel.getContent());
        return Msg.getSuccessMsg();
    }
}
