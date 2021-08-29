package com.hiyj.blog.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FileMapper {
    /**
     * 添加用户文件映射
     *
     * @param userId   用户ID
     * @param fileName 文件名
     */
    @Insert("INSERT INTO user_map_file (user_id, file_name) VALUES(#{userId}, #{fileName});")
    void addUserFile(@Param("userId") int userId, @Param("fileName") String fileName);

    /**
     * 删除用户文件映射
     *
     * @param userId   用户ID
     * @param fileName 文件名
     */
    @Delete("DELETE FROM user_map_file" +
            "WHERE user_id=#{userId} AND file_name=#{fileName}")
    void deleteUserFile(@Param("userId") int userId, @Param("fileName") String fileName);


    /**
     * 检查文件映射
     *
     * @param userId 用户ID
     * @param object 文件路径
     * @return 是否映射正确
     */
    @Select("SELECT COUNT(*) FROM user_map_file WHERE " +
            "user_map_file.user_id = #{userId} and " +
            "user_map_file.file_name = #{object}")
    boolean checkFileMap(@Param("userId") int userId, @Param("object") String object);
}
