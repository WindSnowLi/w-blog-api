package com.hiyj.blog.services.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hiyj.blog.mapper.LinkMapper;
import com.hiyj.blog.object.FriendLink;

import java.util.List;

@Service("friendLinkService")
public class FriendLinkService {
    private LinkMapper linkMapper;

    @Autowired
    public void setLinkMapper(LinkMapper linkMapper) {
        this.linkMapper = linkMapper;
    }

    /**
     * 获取友链列表
     *
     * @param status 友链状态
     * @return List<FriendLink>
     */
    public List<FriendLink> getFriendLinks(FriendLink.Status status) {
        return linkMapper.getFriendLinks(status);
    }

    /**
     * 申请友链
     *
     * @param friendLink 友链对象
     */
    public void applyFriendLink(FriendLink friendLink) {
        linkMapper.applyFriendLink(friendLink);
    }

    /**
     * 设置友链整体对象
     *
     * @param friendLink 友链对象
     */
    public void setFriendLink(FriendLink friendLink) {
        linkMapper.setFriendLink(friendLink);
    }
}
