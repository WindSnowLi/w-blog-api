package xyz.firstmeet.lblog.services.base;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.firstmeet.lblog.mapper.ArticleMapper;
import xyz.firstmeet.lblog.object.Article;
import xyz.firstmeet.lblog.object.ArticleLabel;
import xyz.firstmeet.lblog.object.User;

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
     * @param article_id 文章ID
     * @return Article，文章
     */
    public Article findArticle(int article_id) {
        Article article = articleMapper.findArticleId(article_id);
        article.setLabels(articleMapper.getArticleMapLabels(article_id));
        return article;
    }


    public User findArticleAuthor(int article_id) {
        return articleMapper.findArticleAuthor(article_id);
    }


    public List<ArticleLabel> getAllLabels() {
        return articleMapper.getAllLabels();
    }

    public List<ArticleLabel> getHotLabels() {
        return articleMapper.getHotLabels();
    }

    /**
     * 或许标签所属文章
     *
     * @param id     标签ID
     * @param limit  限制数
     * @param offset 偏移量量
     * @return 文章列表
     */
    public List<Article> getLabelArticlePage(int id, int limit, int offset) {
        return articleMapper.getLabelArticlePage(id, limit, offset);
    }

    /**
     * 访问量加一
     *
     * @param target_id 目标ID
     * @param type      目标类型
     */
    private void addVisitsCount(int target_id, int type) {
        if (articleMapper.addVisitsCount(target_id, type) < 1) {
            articleMapper.addVisitsRow(target_id, type);
            articleMapper.addVisitsCount(target_id, type);
        }
    }

    /**
     * 文章访问量、分类访问量、标签+1
     *
     * @param article_id 文章ID
     */
    public void addArticleVisits(int article_id) {
        //文章访问量
        addVisitsCount(article_id, TYPE_ARTICLE);
        //1为分类，其余为标签
        final List<ArticleLabel> articleMapLabels = articleMapper.getArticleMapLabels(article_id);
        boolean type = true;
        for (ArticleLabel articleLabel : articleMapLabels) {
            if (type) {
                addTypeVisitsCount(articleLabel.getId());
                type = false;
            } else {
                addLabelVisitsCount(articleLabel.getId());
            }
        }
    }

    /**
     * 分类访问量+1
     *
     * @param type_id 分类ID
     */
    public void addTypeVisitsCount(int type_id) {
        addVisitsCount(type_id, TYPE_TYPE);
    }

    /**
     * 标签访问量+1
     *
     * @param label_id 标签ID
     */
    public void addLabelVisitsCount(int label_id) {
        addVisitsCount(label_id, TYPE_LABEL);
    }

    /**
     * 获取用户文章数量
     *
     * @param userId 用户ID
     * @return 数量
     */
    public int getArticleCountByUserId(int userId) {
        return articleMapper.getArticleCountByUserId(userId);
    }

    /**
     * 获取用户所有访问量
     *
     * @param userId 用户ID
     * @return 访问量
     */
    public int getVisitsCountByUserId(int userId) {
        return articleMapper.getVisitsAllCountByUserId(userId);
    }

    /**
     * 获取访问总量和趋势数据
     *
     * @param userId 用户ID
     * @return List<Map<String, Object>>, 日期数值键值对{total=int, day_time=String}
     */
    public List<Map<String, Object>> getVisitLogByDay(int userId) {
        return articleMapper.getVisitLogByDay(userId);
    }

    /**
     * 获取文章创建历史
     *
     * @param userId 用户ID
     * @return List<Map<String, Object>>, 日期数值键值对{total=int, week_time=String}
     */
    public List<Map<String, Object>> getAddArticleLogByWeek(int userId) {
        return articleMapper.getArticleCreateLogByWeek(userId);
    }


    /**
     * 获取用户分类的访问量前10个
     *
     * @param userId 用户ID
     * @param cut    前cut个
     * @return List<ArticleLabel>
     */
    public List<ArticleLabel> getVisitCountByTypeByUserId(int userId, int cut) {
        return articleMapper.getVisitCountByTypeByUserId(userId, cut);
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
     * @param limit  获取截取
     * @return 文章列表
     */
    public List<Article> getMostVisits(int limit) {
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
        article.setArticleType(articleLabelService.getArticleTypeById(articleId));
        article.setLabels(articleMapper.getArticleMapLabels(article.getId()));
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
