package com.hiyj.blog.services.otherlogin;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import com.hiyj.blog.model.request.TokenModel;
import com.hiyj.blog.object.OtherUser;
import com.hiyj.blog.object.User;
import com.hiyj.blog.services.SysConfigJsonService;
import com.hiyj.blog.services.UserJsonService;
import com.hiyj.blog.utils.CodeUtils;
import com.hiyj.blog.utils.JwtUtils;

import java.util.List;

@Service("giteeService")
public class GiteeService {

    private SysConfigJsonService sysConfigJsonService;

    @Autowired
    public void setSysConfigJsonService(SysConfigJsonService sysConfigJsonService) {
        this.sysConfigJsonService = sysConfigJsonService;
    }

    private UserJsonService userJsonService;

    @Autowired
    public void setUserJsonService(UserJsonService userJsonService) {
        this.userJsonService = userJsonService;
    }

    /**
     * 通过授权码获取access_token
     *
     * @param code     授权码
     * @param redirect 重定向uri
     * @return access_token
     */
    public JSONObject getAccessToken(String code, String redirect) {
        JSONObject giteeLoginConfig = sysConfigJsonService.getGiteeLoginConfig().getJSONObject("client");
        String url = "https://gitee.com/oauth/token?" +
                "grant_type=authorization_code&" +
                "code={code}&".replace("{code}", code) +
                "client_id={client_id}&".replace("{client_id}", giteeLoginConfig.getString("client_id")) +
                "redirect_uri={redirect_uri}".replace("{redirect_uri}", redirect);
        JSONObject paramMap = new JSONObject();
        paramMap.put("client_secret", giteeLoginConfig.getString("client_secret"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("user-agent", "Mozilla/5.0 WindSnowLi-Blog");
        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(paramMap, headers);
        RestTemplate client = new RestTemplate();
        return client.postForEntity(url, httpEntity, JSONObject.class).getBody();
    }

    /**
     * 通过验证信息获取Gitee基础信息对象
     *
     * @param access_token 验证信息
     * @return 用户对象
     */
    public User getUserBaseInfo(String access_token) {
        //获取gitee基础信息
        String userInfoUrl = "https://gitee.com/api/v5/user?access_token=" + access_token;
        RestTemplate userClient = new RestTemplate();
        JSONObject userBody = userClient.getForEntity(userInfoUrl, JSONObject.class).getBody();
        User user = new User();
        assert userBody != null;
        user.setAvatar(userBody.getString("avatar_url"));
        user.setNickname(userBody.getString("name"));
        //取消明文密码
        user.setPassword(userJsonService.encryptPasswd(CodeUtils.getUUID()));
        user.setId(userBody.getInteger("id"));
        return user;
    }


    /**
     * 通过验证信息获取Gitee邮箱
     *
     * @param access_token 验证信息
     * @return 邮箱
     */
    public String getUserEmail(String access_token) {
        //获取gitee邮箱
        String userEmailUrl = "https://gitee.com/api/v5/emails?access_token=" + access_token;
        RestTemplate emailClient = new RestTemplate();
        List<JSONObject> emailBody = JSONObject.parseArray(emailClient.getForEntity(userEmailUrl, String.class).getBody(), JSONObject.class);
        assert emailBody != null;
        return emailBody.get(0).getString("email");
    }

    /**
     * 通过授权码获取本地授权码，含添加进新用户列表
     *
     * @param code     授权码
     * @param redirect 重定向uri
     * @return UserJson
     */
    @Transactional
    public TokenModel getLocalToken(String code, String redirect) {
        JSONObject token = getAccessToken(code, redirect);
        String access_token = token.getString("access_token");
        token.put("platform", User.Platform.GITEE);

        //获取gitee基础信息
        User user = getUserBaseInfo(access_token);
        //Gitee的ID信息
        int giteeId = user.getId();
        //设置Gitee邮箱
        user.setAccount(getUserEmail(access_token));

        //如果第三方账户不存在当前账户则插入当前账户
        OtherUser otherUserQuery = userJsonService.getOtherUser(String.valueOf(giteeId), User.Platform.GITEE);
        OtherUser otherUser = JSONObject.parseObject(token.toJSONString(), OtherUser.class);
        otherUser.setOther_platform(User.Platform.GITEE);
        otherUser.setOther_id(String.valueOf(giteeId));
        if (otherUserQuery == null) {
            //添加用户信息，ID为自增，不影响,添加后自动获取新的自增用户ID
            userJsonService.addUser(user);
            //赋值本地ID
            otherUser.setUser_id(user.getId());
            //记录授权信息
            userJsonService.addOtherUser(otherUser);
        } else {
            //赋值本地ID
            user.setId(otherUserQuery.getUser_id());
            //刷新第三方密钥信息
            userJsonService.refreshKeyInfo(otherUser);
            //刷新本地信息
            userJsonService.setInfo(user);
        }
        // 返回本地Token
        return new TokenModel(JwtUtils.getToken(user));
    }
}
