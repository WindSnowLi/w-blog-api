package com.hiyj.blog.controller;

import com.alibaba.fastjson.JSONObject;
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
import com.hiyj.blog.model.request.CodeUrlModel;
import com.hiyj.blog.model.request.IdModel;
import com.hiyj.blog.model.request.TokenModel;
import com.hiyj.blog.model.request.TokenTypeModel;
import com.hiyj.blog.object.Msg;
import com.hiyj.blog.object.User;
import com.hiyj.blog.services.UserJsonService;
import com.hiyj.blog.services.otherlogin.GiteeService;
import com.hiyj.blog.utils.JwtUtils;

@Slf4j
@RestController
@Api(tags = "用户个人相关", value = "用户相关")
@RequestMapping(value = "/api/user", produces = "application/json;charset=UTF-8")
public class UserController {
    private UserJsonService userJsonService;

    @Autowired
    public void setUserJsonService(UserJsonService userJsonService) {
        this.userJsonService = userJsonService;
    }

    private GiteeService giteeService;

    @Autowired
    public void setGiteeService(GiteeService giteeService) {
        this.giteeService = giteeService;
    }

    /**
     * 登录请求
     *
     * @param userJson 用户信息json串，包含账户与密码
     * @return 如果验证正确返回信息串，验证错误返回空
     */
    @ApiOperation(value = "登录请求")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "login")
    @PassToken
    public String login(@RequestBody JSONObject userJson) {
        final User user = JSONObject.parseObject(userJson.toJSONString(), User.class);
        log.info("账号：{} 登录", user.getAccount());
        return userJsonService.loginJson(user);
    }

    /**
     * 获取用户信息
     *
     * @param tokenModel { token:token }
     * @return 如果验证正确返回信息串，验证错误返回空
     */
    @ApiOperation(value = "获取用户信息")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getInfo")
    @Permission(value = {"BACKGROUND-LOGIN"})
    public String getInfo(@RequestBody TokenModel tokenModel) {
        int userId = JwtUtils.getTokenUserId(tokenModel.getToken());
        log.info("getInfo\t用户ID：{}", userId);
        return userJsonService.getInfoJson(tokenModel.getToken());
    }

    /**
     * 获取用户非隐私信息
     *
     * @param idModel { id:int}
     * @return 如果验证正确返回信息串，验证错误返回空
     */
    @ApiOperation(value = "获取用户非隐私信息")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getVisitorInfo")
    @PassToken
    public String getVisitorInfo(@RequestBody IdModel idModel) {
        log.info("getVisitorInfo\t  请求ID：{}", idModel.getId());
        return userJsonService.visitorGetAuthorInfo(idModel.getId());
    }

    /**
     * 设置用户信息
     *
     * @param tokenTypeModel {
     *                       token:token ,
     *                       content: User
     *                       }
     * @return Msg
     */
    @ApiOperation(value = "设置用户信息")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "setInfo")
    @Permission(value = {"USER-BASE-INFO"})
    public String setInfo(@RequestBody TokenTypeModel<JSONObject> tokenTypeModel) {
        int userId = JwtUtils.getTokenUserId(tokenTypeModel.getToken());
        log.info("setInfo\t用户ID：{}", userId);
        User user = JSONObject.parseObject(tokenTypeModel.getContent().toJSONString(), User.class);
        user.setId(userId);
        return userJsonService.setInfoJson(user);
    }

    /**
     * 登出
     *
     * @param tokenModel { token:token }
     * @return Msg
     */
    @ApiOperation(value = "登出")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "logout")
    @PassToken
    public String logout(@RequestBody TokenModel tokenModel) {
        log.info("logout\t用户ID：{}", JwtUtils.getTokenUserId(tokenModel.getToken()));
        return Msg.getSuccessMsg();
    }

    /**
     * 获取文章爱好
     *
     * @param tokenModel {"token":"token"}
     * @return Msg
     */
    @ApiOperation(value = "获取文章爱好")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getWork")
    @Permission(value = {"BACKGROUND-LOGIN"})
    public String getActivity(@RequestBody TokenModel tokenModel) {
        int userId = JwtUtils.getTokenUserId(tokenModel.getToken());
        log.info("getWork\t用户ID：{}", userId);
        return userJsonService.getWorkByUserIdJson(userId);
    }

    /**
     * 设置头像
     *
     * @param tokenTypeModel {"token":"token", "content": url}
     * @return Msg
     */
    @ApiOperation(value = "设置头像")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "setAvatar")
    @Permission(value = {"USER-BASE-INFO"})
    public String setAvatar(@RequestBody TokenTypeModel<String> tokenTypeModel) {
        int userId = JwtUtils.getTokenUserId(tokenTypeModel.getToken());
        log.info("setAvatar\t用户ID：{}", userId);
        return userJsonService.setAvatarJson(userId, tokenTypeModel.getContent());
    }

    /**
     * 获取作者关于信息
     *
     * @param idModel { "id":int }
     * @return Msg
     */
    @ApiOperation(value = "通过用户ID获取用户关于信息")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS, response = String.class),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getAboutByUserId")
    @PassToken
    public String getAbout(@RequestBody IdModel idModel) {
        log.info("getAboutByUserId\t用户ID：{}", idModel.getId());
        return userJsonService.getAboutByUserIdJson(idModel.getId());
    }

    /**
     * 设置作者关于信息
     *
     * @param tokenTypeModel { "token":token, "content":String }
     * @return Msg
     */
    @ApiOperation(value = "设置作者关于信息")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "setAboutByUserToken")
    @Permission(value = {"ABOUT-INFO"})
    public String setAbout(@RequestBody TokenTypeModel<String> tokenTypeModel) {
        int userId = JwtUtils.getTokenUserId(tokenTypeModel.getToken());
        log.info("setAboutByUserToken\t用户ID：{}", userId);
        return userJsonService.setAboutByUserTokenJson(userId, tokenTypeModel.getContent());
    }

    /**
     * 通过Gitee授权码获取本地token
     *
     * @param codeUrlModel 授权码与重定向信息
     * @return Msg
     */
    @ApiOperation(value = "通过Gitee授权码获取本地Token")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS, response = TokenModel.class),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "giteeLogin")
    @PassToken
    public String giteeLogin(@RequestBody CodeUrlModel codeUrlModel) {
        log.info("giteeLogin");
        return Msg.getSuccessMsg(giteeService.getLocalToken(codeUrlModel.getCode(), codeUrlModel.getRedirect()));
    }
}
