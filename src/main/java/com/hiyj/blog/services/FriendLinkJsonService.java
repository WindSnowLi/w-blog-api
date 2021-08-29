package com.hiyj.blog.services;

import com.hiyj.blog.services.base.FriendLinkService;
import org.springframework.stereotype.Service;
import com.hiyj.blog.object.FriendLink;
import com.hiyj.blog.object.Msg;

@Service("friendLinkJsonService")
public class FriendLinkJsonService extends FriendLinkService {
    /**
     * 获取友链列表
     *
     * @param status 友链状态
     * @return Msg
     */
    public String getFriendLinksJson(FriendLink.Status status) {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, getFriendLinks(status));
    }

    /**
     * 申请友链
     *
     * @param friendLink 友链对象
     * @return Msg
     */
    public String applyFriendLinkJson(FriendLink friendLink) {
        applyFriendLink(friendLink);
        return Msg.getSuccessMsg();
    }

    /**
     * 修改友链 状态
     *
     * @param id     友链对应ID
     * @param status 状态
     * @return Msg
     */
    public String setFriendLinkStatusJson(int id, FriendLink.Status status) {
        setFriendLinkStatus(id, status);
        return Msg.getSuccessMsg();
    }
}
