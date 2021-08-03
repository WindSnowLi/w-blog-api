package xyz.firstmeet.lblog.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.firstmeet.lblog.annotation.PassToken;
import xyz.firstmeet.lblog.annotation.UserLoginToken;
import xyz.firstmeet.lblog.object.Msg;
import xyz.firstmeet.lblog.object.User;
import xyz.firstmeet.lblog.services.UserJsonService;
import xyz.firstmeet.lblog.utils.JwtUtils;

@Slf4j
@RestController
@RequestMapping(value = "/api/user", produces = "application/json;charset=UTF-8")
public class UserController {
    private UserJsonService userJsonService;

    @Autowired
    public void setUserJsonService(UserJsonService userJsonService) {
        this.userJsonService = userJsonService;
    }


    /**
     * 登录请求
     *
     * @param userJson 用户信息json串，包含账户与密码
     * @return 如果验证正确返回信息串，验证错误返回空
     */
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
     * @param userJson { token:token}
     * @return 如果验证正确返回信息串，验证错误返回空
     */
    @PostMapping(value = "getInfo")
    @UserLoginToken
    public String getInfo(@RequestBody JSONObject userJson) {
        int userId = JwtUtils.getTokenUserId(userJson.getString("token"));
        log.info("getInfo\t用户ID：{}", userId);
        return userJsonService.getInfoJson(userJson.getString("token"));
    }

    /**
     * 获取用户信息
     *
     * @param userJson { id:int}
     * @return 如果验证正确返回信息串，验证错误返回空
     */
    @PostMapping(value = "getVisitorInfo")
    @PassToken
    public String getVisitorInfo(@RequestBody JSONObject userJson) {
        log.info("getVisitorInfo\t  请求ID：{}", userJson.getString("id"));
        return userJsonService.visitorGetAuthorInfo(userJson.getIntValue("id"));
    }

    /**
     * 设置用户信息
     *
     * @param userJson { token:token}
     * @return 如果验证正确返回信息串，验证错误返回空
     */
    @PostMapping(value = "setInfo")
    @UserLoginToken
    public String setInfo(@RequestBody JSONObject userJson) {
        int userId = JwtUtils.getTokenUserId(userJson.getString("token"));
        log.info("setInfo\t用户ID：{}", userId);
        User user = JSONObject.parseObject(userJson.getJSONObject("user").toJSONString(), User.class);
        return userJsonService.setInfoJson(userId, user);

    }

    /**
     * 登出
     *
     * @param userJson 用户信息json串，
     * @return Msg
     */
    @PostMapping(value = "logout")
    @UserLoginToken
    public String logout(@RequestBody JSONObject userJson) {
        log.info("logout\t用户ID：{}", JwtUtils.getTokenUserId(userJson.getString("token")));
        return Msg.getSuccessMsg();
    }


    /**
     * @param userJson {"id":"int"}
     * @return Msg
     */
    @PostMapping(value = "getSidebarLabelsByUserId")
    @PassToken
    public String getSidebarLabels(@RequestBody JSONObject userJson) {
        log.info("getSidebarLabelsByUserId\t用户ID：{}", userJson.getIntValue("id"));
        return userJsonService.getSidebarLabels(userJson.getIntValue("id"));
    }

    /**
     * 获取文章爱好
     * @param userJson {"token":"token"}
     * @return Msg
     */
    @PostMapping(value = "getWork")
    @UserLoginToken
    public String getActivity(@RequestBody JSONObject userJson) {
        int userId = JwtUtils.getTokenUserId(userJson.getString("token"));
        log.info("getWork\t用户ID：{}", userId);
        return userJsonService.getWorkByUserIdJson(userId);
    }

    /**
     * 设置头像
     * @param userJson {"token":"token", "avatar": url}
     * @return Msg
     */
    @PostMapping(value = "setAvatar")
    @UserLoginToken
    public String setAvatar(@RequestBody JSONObject userJson) {
        int userId = JwtUtils.getTokenUserId(userJson.getString("token"));
        log.info("setAvatar\t用户ID：{}", userId);
        return userJsonService.setAvatarJson(userId, userJson.getString("avatar"));
    }

    /**
     * 访客获取作者信息
     *
     * @param userJson {"id": userId}
     * @return Msg
     */
    @PostMapping(value = "visitorGetAuthorInfo")
    @PassToken
    public String visitorGetAuthorInfo(@RequestBody JSONObject userJson) {
        int userId = userJson.getIntValue("id");
        log.info("visitorGetAuthorInfo\t用户ID：{}", userId);
        return userJsonService.visitorGetAuthorInfo(userId);
    }

    /**
     * 获取作者关于信息
     *
     * @param userJson {"id": int}
     * @return Msg
     */
    @PostMapping(value = "getAboutByUserId")
    @PassToken
    public String getAboutByUserId(@RequestBody JSONObject userJson) {
        log.info("getAboutByUserId\t用户ID：{}", userJson.getIntValue("id"));
        return userJsonService.getAboutByUserIdJson(userJson.getIntValue("id"));
    }
}
