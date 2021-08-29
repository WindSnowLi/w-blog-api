package com.hiyj.blog.mapper;

import com.hiyj.blog.object.OtherUser;
import com.hiyj.blog.object.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    /**
     * 查找用户
     *
     * @param account 账号
     * @return 对象
     */
    @Select("select * from user where user.account=#{account}")
    User findUserAccount(@Param("account") String account);

    /**
     * 查找用户
     *
     * @param id 账号
     * @return 对象
     */
    @Select("select * from user where user.id=#{id}")
    User findUserId(@Param("id") int id);

    /**
     * 设置用户信息
     *
     * @param user 用户新对象
     */
    @Update("UPDATE `user` SET nickname=#{user.nickname}, qq=#{user.qq}, introduction=#{user.introduction} WHERE id=#{user.id};")
    void setInfo(@Param("user") User user);

    /**
     * 获取用户喜好分类占比
     *
     * @param userId 用户ID
     * @return List   [{all_count=int, name=String}]
     */
    List<Object> getActivityByUserId(@Param("userId") int userId);

    /**
     * 设置用户头像
     *
     * @param userId    用户ID
     * @param avatarUrl 头像链接
     */
    @Select("UPDATE `user` SET avatar=#{avatarUrl} WHERE id=#{userId};")
    void setAvatar(@Param("userId") int userId, @Param("avatarUrl") String avatarUrl);

    /**
     * 获取作者关于信息
     *
     * @param userId 用户ID
     * @return String
     */
    @Select("SELECT value FROM ui_config WHERE user_id = #{userId} AND item = \"about\"")
    String getAboutByUserId(@Param("userId") int userId);

    /**
     * 设置作者关于信息
     *
     * @param userId  用户ID
     * @param content 内容
     */
    @Insert("REPLACE INTO ui_config(user_id, item, value) VALUES (#{userId}, \"about\", #{content})")
    void setAbout(int userId, String content);

    /**
     * 添加用户
     *
     * @param user 用户对象
     */
    void addUser(@Param("user") User user);

    /**
     * 添加第三方平台登录用户
     *
     * @param otherUser 第三方对象
     */
    @Insert("INSERT INTO " +
            "other_user (other_id, other_platform, user_id, " +
            "access_token, expires_in, refresh_token, scope, created_at) " +
            "VALUES(#{otherUser.other_id}, #{otherUser.other_platform}, #{otherUser.user_id}," +
            "#{otherUser.access_token},#{otherUser.expires_in}," +
            "#{otherUser.refresh_token},#{otherUser.scope},#{otherUser.created_at})")
    void addOtherUser(@Param("otherUser") OtherUser otherUser);

    /**
     * 刷新第三方用户验证信息
     *
     * @param otherUser 第三方信息对象
     */
    @Update("UPDATE other_user " +
            "SET access_token=#{otherUser.access_token}, refresh_token=#{otherUser.refresh_token}, " +
            "`scope`=#{otherUser.scope}," +
            "created_at=#{otherUser.created_at}, expires_in=#{otherUser.expires_in} " +
            "WHERE other_id=#{otherUser.other_id} AND other_platform=#{otherUser.other_platform}")
    void refreshInfo(@Param("otherUser") OtherUser otherUser);

    /**
     * 查找第三方登录账户
     *
     * @param other_id 第三方识别码
     * @param platform 平台
     * @return 第三方对象
     */
    @Select("SELECT id, other_id, other_platform, user_id FROM other_user WHERE other_id=#{other_id} AND other_platform=#{platform}")
    OtherUser getOtherUser(@Param("other_id") String other_id, @Param("platform") User.Platform platform);
}