package xyz.firstmeet.lblog.controller;

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
import xyz.firstmeet.lblog.model.request.IdTypeModel;
import xyz.firstmeet.lblog.model.request.ReqFriendLinkModel;
import xyz.firstmeet.lblog.model.request.ReqFriendLinkStatusModel;
import xyz.firstmeet.lblog.model.request.TokenTypeModel;
import xyz.firstmeet.lblog.model.response.RspFriendLinkModel;
import xyz.firstmeet.lblog.object.FriendLink;
import xyz.firstmeet.lblog.object.Msg;
import xyz.firstmeet.lblog.services.FriendLinkJsonService;
import xyz.firstmeet.lblog.utils.JwtUtils;

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
        log.info("getFriendLinks");
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
        log.info("applyFriendLink");
        return friendLinkJsonService.applyFriendLinkJson(fiendLinkModel);
    }

    /**
     * 设置友链状态
     *
     * @param tokenTypeModel 友链信息 { "token":token, "content": {"id":int, "status":}  }
     * @return Msg
     */
    @ApiOperation(value = "设置友链状态")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "setFriendLinkStatus")
    @UserLoginToken
    public String setFriendLinkStatus(@RequestBody TokenTypeModel<IdTypeModel<FriendLink.Status>> tokenTypeModel) {
        int userId = JwtUtils.getTokenUserId(tokenTypeModel.getToken());
        log.info("setFriendLinkStatus");
        if (userId != 1) {
            Msg.getFailMsg();
        }
        return friendLinkJsonService.setFriendLinkStatusJson(tokenTypeModel.getContent().getId(), tokenTypeModel.getContent().getContent());
    }
}
