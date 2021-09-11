package com.hiyj.blog.services.base;

import com.alibaba.fastjson.JSONObject;
import com.hiyj.blog.object.base.LabelBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hiyj.blog.mapper.ArticleMapper;
import com.hiyj.blog.object.Article;
import com.hiyj.blog.object.User;

import java.util.List;
import java.util.Map;

@Service("articleService")
public class ArticleService {

    protected static final int TYPE_ARTICLE = 1;
    protected static final int TYPE_LABEL = 10;
    protected static final int TYPE_TYPE = 100;

    protected ArticleMapper articleMapper;

    @Autowired
    public void setArticleMapper(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    protected ArticleLabelService articleLabelService;

    @Autowired
    public void setArticleLabelService(ArticleLabelService articleLabelService) {
        this.articleLabelService = articleLabelService;
    }

    protected FileService fileService;

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * 根据文章ID查找文章
     *
     * @param articleId 文章ID
     * @return Article，文章
     */
    public Article findArticle(int articleId) {
        Article article = articleMapper.findArticleId(articleId);
        if (article == null) {
            return null;
        }
        article.setLabels(articleMapper.getArticleMapLabels(articleId));
        return article;
    }

    public User findArticleAuthor(int articleId) {
        return articleMapper.findArticleAuthor(articleId);
    }

    /**
     * 获取标签所属文章
     *
     * @param id     标签ID
     * @param limit  限制数
     * @param offset 偏移量
     * @param sort   排序方式 默认-id,
     * @param status 文章状态
     * @return 文章列表
     */
    public List<Article> getArticlesByLabel(int id, int limit, int offset, String sort, Article.Status status) {
        return articleMapper.getArticlesByLabel(id, limit, offset, sort, status);
    }

    /**
     * 访问量加一
     *
     * @param targetId 目标ID
     * @param type     目标类型
     */
    private void addPV(int targetId, int type) {
        if (articleMapper.addPV(targetId, type) < 1) {
            articleMapper.addVisitsRow(targetId, type);
            articleMapper.addPV(targetId, type);
        }
    }

    /**
     * 文章访问量、分类访问量、标签+1
     *
     * @param articleId 文章ID
     */
    public void addArticleVisits(int articleId) {
        //文章访问量+1
        addPV(articleId, TYPE_ARTICLE);
        //标签访问量+1
        final List<LabelBase> articleMapLabels = articleMapper.getArticleMapLabels(articleId);
        for (LabelBase articleLabel : articleMapLabels) {
            addLabelPV(articleLabel.getId());
        }
        LabelBase articleTypeById = articleLabelService.getArticleMapType(articleId);
        // 添加类型访问量
        addTypePV(articleTypeById.getId());
    }

    /**
     * 分类访问量+1
     *
     * @param typeId 分类ID
     */
    public void addTypePV(int typeId) {
        addPV(typeId, TYPE_TYPE);
    }

    /**
     * 标签访问量+1
     *
     * @param labelId 标签ID
     */
    public void addLabelPV(int labelId) {
        addPV(labelId, TYPE_LABEL);
    }

    /**
     * 获取用户文章数量
     *
     * @return 数量
     */
    public int getArticleCount() {
        return articleMapper.getArticleCount();
    }

    /**
     * 获取用户所有访问量
     *
     * @return 访问量
     */
    public int getPV() {
        return articleMapper.getPV();
    }

    /**
     * 获取访问总量和趋势数据
     *
     * @return List<Map < String, Object>>, 日期数值键值对{total=int, day_time=String}
     */
    public List<Map<String, Object>> getVisitLogByDay() {
        return articleMapper.getPVLogByDay();
    }

    /**
     * 获取文章创建历史
     *
     * @return List<Map < String, Object>>, 日期数值键值对{total=int, week_time=String}
     */
    public List<Map<String, Object>> getAddArticleLogByWeek() {
        return articleMapper.getArticleCreateLogByWeek();
    }

    /**
     * 添加文章
     *
     * @param article 文章对象
     * @param userId  文章所属用户用户ID
     */
    @Transactional
    public void createArticle(Article article, int userId) {
        //插入文章
        articleMapper.addArticle(article);
        //插入用户——文章映射
        articleMapper.addUserMapArticle(userId, article.getId());
        //添加新属性
        articleLabelService.addProperty(article);
    }

    /**
     * 更新文章
     *
     * @param article 文章对象
     */
    @Transactional
    public void updateArticle(Article article) {
        //更新文章实际内容
        articleMapper.updateArticle(article);
        //删除所有属性
        articleLabelService.deleteProperty(article.getId());
        //添加新属性
        articleLabelService.addProperty(article);
    }

    /**
     * 获取访问最多的文章
     *
     * @param limit 获取截取
     * @return 文章列表
     */
    public List<Article> getMostPV(int limit) {
        return articleMapper.getMostVisits(limit);
    }

    /**
     * 根据文章ID获取文章详情
     *
     * @param article 包含文章ID的文章对象
     * @return 详情文章对象JSON串
     */
    public JSONObject getDetailById(Article article) {
        return getDetailById(article.getId());
    }

    /**
     * 根据文章ID获取文章详情
     *
     * @param articleId 文章ID的
     * @return 详情文章对象JSON串
     */
    public JSONObject getDetailById(int articleId) {
        Article article = findArticle(articleId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("article", article);
        jsonObject.put("user", findArticleAuthor(articleId));
        jsonObject.put("next", getNextArticle(articleId));
        jsonObject.put("pre", getPreArticle(articleId));
        return jsonObject;
    }

    /**
     * 设置文章状态
     *
     * @param articleId 文章ID
     * @param status    状态
     */
    public void setStatus(int articleId, Article.Status status) {
        articleMapper.setStatus(articleId, status);
    }

    /**
     * 根据文章ID删除文章
     *
     * @param articleId 文章ID
     */
    public void delArticle(int articleId) {
        articleMapper.delArticle(articleId);
    }

    /**
     * 获取该文章的下一篇文章
     *
     * @param articleId 当前文章ID
     * @return 文章对象
     */
    public Article getNextArticle(int articleId) {
        return articleMapper.getNextArticle(articleId);
    }

    /**
     * 获取该文章的上一篇文章
     *
     * @param articleId 当前文章ID
     * @return 文章对象
     */
    public Article getPreArticle(int articleId) {
        return articleMapper.getPreArticle(articleId);
    }
}
