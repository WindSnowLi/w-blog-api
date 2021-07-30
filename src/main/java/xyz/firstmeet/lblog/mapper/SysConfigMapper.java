package xyz.firstmeet.lblog.mapper;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface SysConfigMapper {

    /**
     * 获取用户配置表
     *
     * @param userId 用户ID
     * @return 配置表
     */
    List<JSONObject> getSysUiConfigByUserId(@Param("userId") int userId);

    /**
     * 删除用户的所有UI配置
     *
     * @param userId 用户和ID
     */
    void deleteUiConfigByUserId(@Param("userId") int userId);

    /**
     * 设置某个用户的UI配置
     *
     * @param userId    用户ID
     * @param configMap 配置表
     */
    void setUiConfigByUserId(@Param("userId") int userId, @Param("configMap") Map<String, String> configMap);

    /**
     * 获取OSS的相关配置
     *
     * @return json配置串
     */
    @Select("SELECT value FROM sys_setting WHERE item = \"oss\"")
    String getOSSConfig();

    /**
     * 获取系统默认配置
     * @return 系统配置表
     */
    @Select("SELECT item, value FROM sys_setting")
    List<JSONObject> getSysUiConfig();

    /**
     * 设置系统存储配置文件
     * @param config 存储设置整体对象
     */
    @Insert("UPDATE sys_setting SET value=#{config} WHERE item='oss'")
    void setStorageConfig(@Param("config") String config);
}
