package xyz.firstmeet.lblog.services.base;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.firstmeet.lblog.mapper.ArticleMapper;
import xyz.firstmeet.lblog.mapper.FileMapper;
import xyz.firstmeet.lblog.mapper.UserMapper;
import xyz.firstmeet.lblog.object.PersonalLink;
import xyz.firstmeet.lblog.object.User;

import java.util.List;

@Service("userService")
public class UserService {

    protected UserMapper userMapper;

    protected ArticleMapper articleMapper;

    protected FileService fileService;

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

    public User findUserByAccount(String account) {
        return userMapper.findUserAccount(account);
    }

    public User findUserById(int id) {
        return userMapper.findUserId(id);
    }


    public User findAdmin() {
        return userMapper.findAdmin();
    }

    public List<PersonalLink> getUserLinks(int user_id) {
        return userMapper.getUserLinks(user_id);
    }

    /**
     * 设置用户信息
     *
     * @param userId 用户ID
     * @param user   用户新对象
     */
    public void setInfo(int userId, User user) {
        userMapper.setInfo(userId, user);
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
     * @param userId    用户ID
     * @return String
     */
    public String getAboutByUserId(int userId){
        return userMapper.getAboutByUserId(userId);
    }
}
