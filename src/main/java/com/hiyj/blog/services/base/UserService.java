package com.hiyj.blog.services.base;


import com.hiyj.blog.mapper.ArticleMapper;
import com.hiyj.blog.mapper.UserMapper;
import com.hiyj.blog.object.OtherUser;
import com.hiyj.blog.object.User;
import com.hiyj.blog.utils.CodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserService {

    protected UserMapper userMapper;

    protected ArticleMapper articleMapper;

    protected FileService fileService;

    protected ArticleLabelService articleLabelService;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setArticleMapper(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @Autowired
    public void setArticleLabelService(ArticleLabelService articleLabelService) {
        this.articleLabelService = articleLabelService;
    }

    public User findUserByAccount(String account) {
        return userMapper.findUserAccount(account);
    }

    public User findUserById(int id) {
        return userMapper.findUserId(id);
    }

    /**
     * 设置用户信息
     *
     * @param user 用户新对象
     */
    public void setInfo(User user) {
        userMapper.setInfo(user);
    }

    /**
     * 获取用户喜好分类占比
     *
     * @param userId 用户ID
     * @return List  [{all_count=int, name=String}]
     */
    public List<Object> getWorkByUserId(int userId) {
        return userMapper.getActivityByUserId(userId);
    }

    /**
     * 设置用户头像
     *
     * @param userId    用户ID
     * @param avatarUrl 头像链接
     */
    public void setAvatar(int userId, String avatarUrl) {
        userMapper.setAvatar(userId, avatarUrl);
    }

    /**
     * 添加用户文件映射
     *
     * @param userId   用户ID
     * @param fileName 文件名
     */
    public void addUserFile(int userId, String fileName) {
        fileService.addUserFile(userId, fileName);
    }

    /**
     * 获取作者关于信息
     *
     * @param userId 用户ID
     * @return String
     */
    public String getAboutByUserId(int userId) {
        return userMapper.getAboutByUserId(userId);
    }

    /**
     * 设置作者关于信息
     *
     * @param userId  用户ID
     * @param content 内容
     */
    public void setAboutByUserToken(int userId, String content) {
        userMapper.setAbout(userId, content);
    }

    /**
     * 添加用户
     *
     * @param user 用户对象
     */
    public void addUser(User user) {
        userMapper.addUser(user);
    }

    /**
     * 添加第三方平台登录用户
     *
     * @param otherUser 第三方对象
     */
    public void addOtherUser(OtherUser otherUser) {
        userMapper.addOtherUser(otherUser);
    }

    /**
     * 查找第三方登录账户
     *
     * @param other_id 第三方识别码
     * @param platform 平台
     * @return 第三方对象
     */
    public OtherUser getOtherUser(String other_id, User.Platform platform) {
        return userMapper.getOtherUser(other_id, platform);
    }

    /**
     * 刷新第三方用户验证信息
     *
     * @param otherUser 第三方信息对象
     */
    public void refreshKeyInfo(OtherUser otherUser) {
        userMapper.refreshInfo(otherUser);
    }

    /**
     * 由明文密码计算密文密码
     *
     * @param text 明文
     * @return MD5(SHA512 ( 明文)+明文)
     */
    public String encryptPasswd(String text) {
        return CodeUtils.strWithMd5(CodeUtils.strWithSha512(text));
    }

    /**
     * 添加用户-角色映射
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    public void addUserMapRole(int userId, int roleId) {
        userMapper.addUserMapRole(userId, roleId);
    }
}
