package com.hiyj.blog.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.hiyj.blog.annotation.PassToken;
import com.hiyj.blog.annotation.Permission;
import com.hiyj.blog.model.request.TokenModel;
import com.hiyj.blog.object.Msg;
import com.hiyj.blog.services.OtherJsonService;
import com.hiyj.blog.utils.JwtUtils;

@Slf4j
@RestController
@Api(tags = "后台杂项", value = "后台杂项")
@RequestMapping(value = "/api/other", produces = "application/json;charset=UTF-8")
@Permission(value = {"BACKGROUND-LOGIN"})
public class OtherController {
    private OtherJsonService otherJsonService;

    @Autowired
    public void setOtherJsonService(OtherJsonService otherJsonService) {
        this.otherJsonService = otherJsonService;
    }

    /**
     * 获取仪表盘折线图和panel-group部分
     *
     * @param tokenModel 验证信息
     * @return Msg
     */
    @ApiOperation(value = "获取仪表盘折线图和panel-group部分")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getPanel")
    @PassToken
    public String getPanel(@RequestBody TokenModel tokenModel) {
        int userId = JwtUtils.getTokenUserId(tokenModel.getToken());
        log.info("getPanel");
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, otherJsonService.getPanel(userId));
    }

    /**
     * 获取图表信息
     *
     * @param tokenModel 验证信息
     * @return Msg
     */
    @ApiOperation(value = "获取图表信息")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getChart")
    @PassToken
    public String getChart(@RequestBody TokenModel tokenModel) {
        int userId = JwtUtils.getTokenUserId(tokenModel.getToken());
        log.info("getChart");
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, otherJsonService.getChart(userId));
    }
}
