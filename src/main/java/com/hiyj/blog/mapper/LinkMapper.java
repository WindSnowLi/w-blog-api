package com.hiyj.blog.mapper;

import com.hiyj.blog.object.FriendLink;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface LinkMapper {
    /**
     * 获取友链列表
     *
     * @param status 友链状态
     * @return List<FriendLink>
     */
    List<FriendLink> getFriendLinks(@Param("status") FriendLink.Status status);

    /**
     * 申请友链
     *
     * @param friendLink 友链对象
     */
    void applyFriendLink(@Param("friendLink") FriendLink friendLink);

    /**
     * 设置友链整体对象
     *
     * @param friendLink 友链对象
     */
    void setFriendLink(@Param("friendLink") FriendLink friendLink);
}
