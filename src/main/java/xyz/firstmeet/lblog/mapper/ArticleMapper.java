package xyz.firstmeet.lblog.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import xyz.firstmeet.lblog.object.Article;
import xyz.firstmeet.lblog.object.ArticleLabel;
import xyz.firstmeet.lblog.object.User;

import java.util.List;

@Mapper
@Repository
public interface ArticleMapper {

    /**
     * 获取用户文章数量
     *
     * @param userId 用户ID
     * @return 数量
     */
    int getArticleCountByUserId(@Param("userId") int userId);


    /**
     * 获取用户所有访问量
     *
     * @param userId 用户ID
     * @return 访问量
     */
    int getVisitsAllCountByUserId(@Param("userId") int userId);

    /**
     * 获取用户分类的访问量前cut个
     *
     * @param cut 前cut个
     * @return list分类
     */
    List<ArticleLabel> getVisitCountByTypeByUserId(@Param("userId") int userId, @Param("cut") int cut);

    /**
     * 逆序分页获取文章
     *
     * @param start 开始条
     * @param end   结束条
     * @param sort  排序方式 默认-id,
     * @return 文章列表
     */
    List<Article> getArticlesByPage(@Param("start") int start, @Param("end") int end, @Param("sort") String sort, @Param("status") String status);

    /**
     * 根据ID排序，第一个为所属分类
     *
     * @param article_id 文章ID
     * @return 标签列表
     */
    List<ArticleLabel> getArticleMapLabels(@Param("article_id") int article_id);

    /**
     * 根据文章ID查找文章
     *
     * @param article_id 文章ID
     * @return 文章
     */
    Article findArticleId(@Param("article_id") int article_id);

    /**
     * 查找文章作者
     *
     * @param article_id 文章ID
     * @return User
     */
    @Select("select * from (SELECT user_id FROM user_map_article uma where uma.article_id =#{article_id}) uid, `user` u where u.id =uid.user_id ")
    User findArticleAuthor(@Param("article_id") int article_id);

    /**
     * 获取所有标签
     *
     * @return 标签列表
     */
    List<ArticleLabel> getAllLabels();

    @Select("SELECT id, name FROM article_label limit 0,10")
    List<ArticleLabel> getHotLabels();

    /**
     * 获取用户所属热门标签
     *
     * @param user_id 用户ID
     * @return ArticleLabel List
     */
    List<ArticleLabel> getHotLabelsByUserId(@Param("user_id") int user_id);

    /**
     * @param id    标签ID
     * @param start 开始条
     * @param end   结束条
     * @return 文章列表
     */
    List<Article> getLabelArticlePage(@Param("label_id") int id, @Param("start") int start, @Param("end") int end);


    /**
     * 获取所有分类
     *
     * @return List ArticleLabel
     */
    List<ArticleLabel> getAllTypes();


    /**
     * 获取所属分类文章
     *
     * @param type   分类ID
     * @param status 文章状态，默认published，all为全部文章类型
     * @return List Article
     */
    List<Article> getArticlesByType(@Param("label_id") int type, @Param("status") String status);

    /**
     * 访问量加一
     *
     * @param target_id 目标ID
     * @param type      目标类型
     * @return 影响row
     */
    @Update("UPDATE visits_count SET count=count+1 WHERE target_id=#{target_id} AND `type`=#{type} AND TIME= CURDATE();")
    int addVisitsCount(@Param("target_id") int target_id, @Param("type") int type);

    /**
     * 添加新的访问量行
     *
     * @param target_id 目标ID
     * @param type      类型
     */
    @Insert("INSERT INTO visits_count(`target_id`, `type`, `time`) VALUES(#{target_id}, #{type}, CURDATE());")
    void addVisitsRow(@Param("target_id") int target_id, @Param("type") int type);

    /**
     * 获取访问总量和趋势数据
     *
     * @param userId 用户ID
     * @return List<Map>, 日期数值键值对{total=int, day_time=String}
     */
    List<Object> getVisitLogByDay(@Param("userId") int userId);

    /**
     * 获取文章创建历史
     *
     * @param userId 用户ID
     * @return List<Map>, 日期数值键值对{total=int, day_time=week_time}
     */
    List<Object> getArticleCreateLogByWeek(@Param("userId") int userId);

    /**
     * 添加文章，不含标签和文章所属用户映射
     *
     * @param article 文章对象
     * @return 插入条
     */
    int addArticle(@Param("article") Article article);

    /**
     * 添加用户——文章映射
     *
     * @param userId    用户ID
     * @param articleId 文章ID
     */
    void addUserMapArticle(@Param("userId") int userId, @Param("articleId") int articleId);

    /**
     * 添加新标签
     *
     * @param articleLabels 标签列表
     */
    void addLabels(@Param("articleLabels") List<ArticleLabel> articleLabels);

    /**
     * 根据名字批量检查已经存在的标签
     *
     * @param articleLabels 文章标签对象列表
     * @return 已经存在的标签
     */
    List<ArticleLabel> batchCheckLabelByNames(@Param("articleLabels") List<ArticleLabel> articleLabels);

    /**
     * 根据标签名批量查询标签对象
     *
     * @param articleLabels 文章标签对象列表
     * @return 标签对象列表
     */
    List<ArticleLabel> batchFindLabelByNames(@Param("articleLabels") List<ArticleLabel> articleLabels);

    /**
     * 添加文章——标签映射
     *
     * @param articleId     文章ID
     * @param articleLabels 文章标签对象列表
     */
    void addArticleMapLabels(@Param("articleId") int articleId, @Param("articleLabels") List<ArticleLabel> articleLabels);

    /**
     * 获取访问最多的文章
     *
     * @param limit  获取截取
     * @param status 文章状态，默认published，all为全部文章类型
     * @return 文章列表
     */
    List<Article> getMostVisits(@Param("limit") int limit, @Param("status") String status);

    /**
     * 获取用户所有分类信息
     *
     * @param userId 用户ID
     * @return 分类信息
     */
    List<ArticleLabel> getAllTypeByUserId(@Param("userId") int userId);

    /**
     * 修改文章
     *
     * @param article 文章ID
     */
    void updateArticle(@Param("article") Article article);

    /**
     * 设置文章状态
     *
     * @param articleId 文章ID
     * @param status    状态
     */
    @Update("UPDATE article SET status=#{status} WHERE id=#{articleId}")
    void setStatus(@Param("articleId") int articleId, @Param("status") Article.Status status);

    /**
     * 根据文章ID删除文章
     *
     * @param articleId 文章ID
     */
    @Delete("DELETE FROM article WHERE id=#{articleId};")
    void delArticle(@Param("articleId") int articleId);
}
