package com.hiyj.blog.services;

import com.alibaba.fastjson.JSONObject;
import com.hiyj.blog.services.base.ArticleService;
import org.springframework.stereotype.Service;
import com.hiyj.blog.object.Article;
import com.hiyj.blog.object.Msg;

import java.beans.Transient;
import java.util.ArrayList;

@Service("articleJsonService")
public class ArticleJsonService extends ArticleService {
    /**
     * 分页获取文章列表
     *
     * @param userId 用户ID
     * @param limit  限制条
     * @param page   第几页
     * @param sort   排序方式
     * @param status 文章状态，默认published，all为全部文章类型
     * @return Msg 内含文章列表
     */
    public String getArticlesByPageJson(int userId, int limit, int page, String sort, Article.Status status) {
        if (page <= 0) {
            return Msg.getFailMsg();
        }
        ArrayList<JSONObject> arrayList = new ArrayList<>();
        for (Integer id : articleMapper.getArticleIdByPage(limit, (page - 1) * limit, sort, status)) {
            arrayList.add(getDetailById(id));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("items", arrayList);
        jsonObject.put("total", articleMapper.getArticleCountByUserId(userId));
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, jsonObject);
    }

    /**
     * 分页获取文章列表
     *
     * @param limit  限制条
     * @param page   第几页
     * @param sort   排序方式
     * @param status 文章状态，默认published，all为全部文章类型
     * @return Msg 内含文章列表
     */
    public String getArticleIdByPageJson(int limit, int page, String sort, Article.Status status) {
        if (page <= 0) {
            return Msg.getFailMsg();
        }
        ArrayList<JSONObject> rs = new ArrayList<>();
        for (int id : articleMapper.getArticleIdByPage(limit, (page - 1) * limit, sort, status)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
            jsonObject.put("updateTime", articleMapper.findArticleId(id).getUpdateTime());
            rs.add(jsonObject);
        }
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, rs);
    }

    public String getAllLabelsJson() {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, getAllLabels());
    }

    public String getHotLabelsJson() {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, getHotLabels());
    }

    public String findArticleJson(int articleId) {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, getDetailById(articleId));
    }

    public String findArticleAuthorJson(int article_id) {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, findArticleAuthor(article_id));
    }

    /**
     * 获取标签所属文章
     *
     * @param id    标签ID
     * @param limit 限制数
     * @param page  页数
     * @return Msg
     */
    public String getLabelArticlePageJson(int id, int limit, int page) {
        ArrayList<JSONObject> arrayList = new ArrayList<>();
        for (Article article : getLabelArticlePage(id, limit, (page - 1) * limit)) {
            arrayList.add(getDetailById(article));
        }
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, arrayList);
    }

    /**
     * 获取文章所有分类
     *
     * @return Msg
     */
    public String getAllTypeJson() {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, articleMapper.getAllTypes());
    }

    /**
     * 获取用户所有分类信息
     *
     * @param userId 用户ID
     * @return Msg
     */
    public String getAllTypeByUserIdJson(int userId) {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, articleMapper.getAllTypeByUserId(userId));
    }

    /**
     * 获取所属分类文章
     *
     * @param type   分类ID
     * @param status 文章状态，默认PUBLISHED，ALL为全部文章类型
     * @return Msg
     */
    public String getArticleByTypeJson(int type, Article.Status status) {
        ArrayList<JSONObject> arrayList = new ArrayList<>();
        for (Article article : articleMapper.getArticlesByType(type, status)) {
            arrayList.add(getDetailById(article));
        }
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, arrayList);
    }

    /**
     * 添加文章
     *
     * @param article 文章对象
     * @param userId  文章所属用户用户ID
     * @return Msg添加状态
     */
    public String createArticleJson(Article article, int userId) {
        createArticle(article, userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("articleId", article.getId());
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, "发布成功", jsonObject);
    }

    /**
     * 更新文章
     *
     * @param article 文章对象
     * @return Msg更新状态
     */
    @Transient
    public String updateArticleJson(Article article) {
        if (article.getId() < 1) {
            return Msg.makeJsonMsg(Msg.CODE_FAIL, "文章不存在", null);
        }
        updateArticle(article);
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, "编辑成功", null);
    }


    /**
     * 获取访问最多的文章
     *
     * @param limit 获取截取
     * @return 文章列表Msg
     */
    public String getMostVisitsJson(int limit) {
        ArrayList<JSONObject> arrayList = new ArrayList<>();
        for (Article article : getMostVisits(limit)) {
            if (findArticle(article.getId()) == null) {
                continue;
            }
            arrayList.add(getDetailById(article));
        }
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, arrayList);
    }

    /**
     * 设置文章状态
     *
     * @param articleId 文章ID
     * @param status    状态
     * @return Msg状态
     */
    public String setStatusJson(int articleId, Article.Status status) {
        setStatus(articleId, status);
        return Msg.getSuccessMsg();
    }

    /**
     * 删除文章
     *
     * @param articleId 文章ID
     * @param userId    用户ID
     * @return Msg状态
     */
    public String delArticleJson(int userId, int articleId) {
        if (userId == findArticleAuthor(articleId).getId()) {
            delArticle(articleId);
            return Msg.getSuccessMsg();
        }
        return Msg.getFailMsg();
    }
}
