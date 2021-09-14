package com.hiyj.blog.controller;

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
import com.hiyj.blog.model.request.IdTypeModel;
import com.hiyj.blog.model.request.ReqFriendLinkModel;
import com.hiyj.blog.model.request.ReqFriendLinkStatusModel;
import com.hiyj.blog.model.request.TokenTypeModel;
import com.hiyj.blog.model.response.RspFriendLinkModel;
import com.hiyj.blog.object.FriendLink;
import com.hiyj.blog.object.Msg;
import com.hiyj.blog.services.FriendLinkJsonService;
import com.hiyj.blog.utils.JwtUtils;

@Slf4j
@RestController
@Api(tags = "链接相关", value = "链接相关")
@RequestMapping(value = "/api/links", produces = "application/json;charset=UTF-8")
public class FriendLinkController {
    private FriendLinkJsonService friendLinkJsonService;

    @Autowired
    public void setFriendLinkJsonService(FriendLinkJsonService friendLinkJsonService) {
        this.friendLinkJsonService = friendLinkJsonService;
    }

    /**
     * 获取友链列表
     *
     * @param fiendLinkStatusModel 友链状态 {status: FiendLink.Status}
     * @return Msg
     */
    @ApiOperation(value = "获取友链列表")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS, response = RspFriendLinkModel.class),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getFriendLinks")
    @PassToken
    public String getFriendLinks(@RequestBody ReqFriendLinkStatusModel fiendLinkStatusModel) {
        return friendLinkJsonService.getFriendLinksJson(fiendLinkStatusModel.getStatus());
    }

    /**
     * 申请友链
     *
     * @param fiendLinkModel 友链信息
     * @return Msg
     */
    @ApiOperation(value = "申请友链")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "applyFriendLink")
    @PassToken
    public String applyFriendLink(@RequestBody ReqFriendLinkModel fiendLinkModel) {
        return friendLinkJsonService.applyFriendLinkJson(fiendLinkModel);
    }

    /**
     * 设置友链整体对象
     *
     * @param friendLink 友链对象
     * @return Msg
     */
    @ApiOperation(value = "设置友链状")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "setFriendLink")
    @Permission(value = {"VERIFY-LINK"})
    public String setFriendLink(@RequestBody FriendLink friendLink) {
        return friendLinkJsonService.setFriendLinkJson(friendLink);
    }
}