package xyz.firstmeet.lblog.services;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import xyz.firstmeet.lblog.object.Msg;
import xyz.firstmeet.lblog.object.User;
import xyz.firstmeet.lblog.services.base.UserService;
import xyz.firstmeet.lblog.utils.JwtUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("userJsonService")
public class UserJsonService extends UserService {

    public String getSidebarLabels(int user_id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("links", getUserLinks(user_id));
        jsonObject.put("labels", articleMapper.getHotLabelsByUserId(user_id));
        jsonObject.put("user", findUserById(user_id));
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, jsonObject);
    }

    /**
     * 获取用户信息
     *
     * @param token 用户token
     * @return 用户对象Msg
     */
    public String getInfoJson(String token) {
        // 获取 token 中的 user id
        int userId = JwtUtils.getTokenUserId(token);
        User user = findUserById(userId);
        if (user == null) {
            return Msg.getFailMsg();
        }
        String userAccount = JwtUtils.getTokenUserAccount(token);
        String userPassword = JwtUtils.getTokenUserPassword(token);
        if (!user.getAccount().equals(userAccount) || !user.getPassword().equals(userPassword)) {
            return Msg.getFailMsg();
        }
        ArrayList<String> roles = new ArrayList<>();
        roles.add("admin");
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(user));
        jsonObject.put("roles", roles);
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, jsonObject);
    }

    public String loginJson(User user) {
        User tempUser = userMapper.findUserAccount(user.getAccount());
        if (tempUser == null) {
            return Msg.makeJsonMsg(Msg.CODE_FAIL, Msg.LOGIN_PASSWORD_ORNUMBER_FAIL, null);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", JwtUtils.getToken(tempUser));
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, jsonObject);
    }

    public String findAdminJson() {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, findAdmin());
    }

    /**
     * 访客获取作者信息
     * @param userId 作者ID
     * @return 作者信息
     */
    public String visitorGetAuthorInfo(int userId) {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, findUserById(userId));
    }

    /**
     * 设置用户信息
     *
     * @param userId 用户ID
     * @param user   用户新对象
     * @return Msg
     */
    public String setInfoJson(int userId, User user) {
        if (user.getNickname().isEmpty()) {
            return Msg.makeJsonMsg(Msg.CODE_FAIL, "昵称不可为空", null);
        }
        userMapper.setInfo(userId, user);
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, "保存成功", null);
    }

    /**
     * 获取用户喜好分类占比
     *
     * @param userId 用户ID
     * @return List
     */
    public String getWorkByUserIdJson(int userId) {
        final List<Object> activityByUserId = this.getWorkByUserId(userId);
        int allArticleCount = articleMapper.getArticleCountByUserId(userId);
        List<JSONObject> rs = new ArrayList<>();
        for (Object temp : activityByUserId) {
            final HashMap<String, Object> temp1 = (HashMap) temp;
            JSONObject tempJson = new JSONObject();
            tempJson.put("name", temp1.get("name"));
            tempJson.put("value", ((BigDecimal) temp1.getOrDefault("value", new BigDecimal(0))).intValue() * 100 / allArticleCount);
            rs.add(tempJson);
        }
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, rs);
    }

    /**
     * 设置用户头像
     *
     * @param userId    用户ID
     * @param avatarUrl 头像链接
     */
    public String setAvatarJson(int userId, String avatarUrl) {
        setAvatar(userId, avatarUrl);
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, null);
    }

    /**
     * 获取作者关于信息
     *
     * @param userId    用户ID
     * @return Msg
     */
    public String getAboutByUserIdJson(int userId){
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS,Msg.MSG_SUCCESS,getAboutByUserId(userId));
    }
}
