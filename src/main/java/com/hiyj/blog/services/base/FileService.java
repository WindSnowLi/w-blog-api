package com.hiyj.blog.services.base;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hiyj.blog.mapper.FileMapper;
import com.hiyj.blog.oss.OssUtils;
import com.hiyj.blog.utils.CodeUtils;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Service("fileService")
public class FileService {
    protected SysConfigService sysConfigService;

    @Autowired
    public void setSysConfigService(SysConfigService sysConfigService) {
        this.sysConfigService = sysConfigService;
    }

    protected FileMapper fileMapper;

    @Autowired
    public void setFileMapper(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }


    //项目文件存储根路径
    protected String rootPath;
    //文章封面存储路径
    protected String articleCoverImagePath;
    //头像存储路径
    protected String avatarImagePath;
    //文章内容图片路径
    protected String articleImagePath;
    //是否开启上传
    protected boolean status;

    /**
     * 获取OSS工具对象
     *
     * @return OssUtils
     */
    public OssUtils getOssUtils() {
        final JSONObject ossConfigJson = JSONObject.parseObject(sysConfigService.getStorageConfig().getString("storage"));
        rootPath = ossConfigJson.getString("rootPath");
        ossConfigJson.remove("ossConfigJson");
        articleCoverImagePath = ossConfigJson.getString("articleCoverImagePath");
        ossConfigJson.remove("imagePath");
        avatarImagePath = ossConfigJson.getString("avatarImagePath");
        ossConfigJson.remove("avatarImagePath");
        articleImagePath = ossConfigJson.getString("articleImagePath");
        ossConfigJson.remove("articleImagePath");
        status = ossConfigJson.getBoolean("status");
        ossConfigJson.remove("status");
        return JSONObject.parseObject(ossConfigJson.toJSONString(), OssUtils.class);
    }

    /**
     * 获取OSS上传参数表
     *
     * @param path  存储路径
     * @param token 验证信息
     * @return 返回链接和Object路径
     */
    protected Map<String, Object> getUploadMap(String path, String token) {
        OssUtils ossUtils = getOssUtils();
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        if (status) {
            map.putAll(ossUtils.getUploadUrl(path + "/" + CodeUtils.getUUID(), "&token=" + token, true));
        }
        return map;
    }

    /**
     * 获取上传头像的url和Object路径
     *
     * @return 返回上传的头像链接和Object路径
     */
    public Map<String, Object> getUploadAvatarMap(String token) {
        return getUploadMap(avatarImagePath, token);
    }

    /**
     * 获取上传文章封面的url和Object路径
     *
     * @return 返回上传的文章封面链接和Object路径
     */
    public Map<String, Object> getUploadArticleCoverImageUrl(String token) {
        return getUploadMap(articleCoverImagePath, token);
    }

    /**
     * 获取文章图片上传url和Object路径
     *
     * @return 返回上传的文章封面链接和Object路径
     */
    public Map<String, Object> getUploadArticleImageUrl(String token) {
        return getUploadMap(articleImagePath, token);
    }

    /**
     * 删除文件
     *
     * @param objectName 文件路径
     */
    public void deleteObject(String objectName) {
        getOssUtils().deleteObject(objectName);
    }

    /**
     * 检查文件映射
     *
     * @param userId 用户ID
     * @param object 文件路径
     * @return 是否映射正确
     */
    public boolean checkFileMap(int userId, String object) {
        return fileMapper.checkFileMap(userId, object);
    }

    /**
     * 删除用户文件映射
     *
     * @param userId   用户ID
     * @param fileName 文件名
     */
    public void deleteUserFile(int userId, String fileName) {
        if (fileName != null && checkFileMap(userId, fileName)) {
            deleteObject(fileName);
        }
    }

    /**
     * 添加用户文件映射
     *
     * @param userId   用户ID
     * @param fileName 文件名
     */
    public void addUserFile(int userId, String fileName) {
        fileMapper.addUserFile(userId, fileName);
    }

}