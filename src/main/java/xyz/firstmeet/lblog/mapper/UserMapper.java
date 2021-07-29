package xyz.firstmeet.lblog.mapper;

import org.apache.ibatis.annotations.*;
import xyz.firstmeet.lblog.object.PersonalLink;
import xyz.firstmeet.lblog.object.User;
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


    @Select("select * from user inner join (select user_id from power p where p.power=0 limit 1) t where t.user_id=user.id")
    User findAdmin();


    @Select("SELECT * FROM personal_links where user_id = #{user_id}")
    List<PersonalLink> getUserLinks(@Param("user_id") int user_id);

    /**
     * 设置用户信息
     *
     * @param userId 用户ID
     * @param user   用户新对象
     */
    @Update("UPDATE `user` SET nickname=#{user.nickname}, qq=#{user.qq}, introduction=#{user.introduction} WHERE id=#{userId};")
    void setInfo(@Param("userId") int userId, @Param("user") User user);

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
}
