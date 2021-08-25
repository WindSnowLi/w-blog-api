package xyz.firstmeet.lblog.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import xyz.firstmeet.lblog.object.ArticleLabel;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ArticleLabelMapper {
    /**
     * 通过类型ID获取类型
     *
     * @param typeId 标签ID
     * @return 类型对象
     */
    ArticleLabel getTypeById(@Param("typeId") int typeId);

    /**
     * 通过类型名获取类型
     *
     * @param typeName 类型名
     * @return 类型对象
     */
    ArticleLabel getTypeByName(@Param("typeName") String typeName);

    /**
     * 通过标签ID获取标签
     *
     * @param labelId 标签ID
     * @return 标签对象
     */
    ArticleLabel getLabelById(@Param("labelId") int labelId);

    /**
     * 通过文章ID获取文章所属类型ID
     *
     * @param articleId 文章ID
     * @return 文章类型ID
     */
    int getArticleTypeById(@Param("articleId") int articleId);

    /**
     * 添加文章——分类映射
     *
     * @param articleId 文章ID
     * @param typeId    所属类型
     */
    void addArticleMapType(@Param("articleId") int articleId, @Param("typeId") int typeId);

    /**
     * 获取所有标签
     *
     * @return List ArticleLabel
     */
    List<ArticleLabel> getAllLabel();

    /**
     * 获取所有分类数量
     *
     * @return 分类数量
     */
    @Select("select count(distinct amt.type_id) from article_map_type amt")
    int getTypeSize();

    /**
     * 清空文章标签
     *
     * @param articleId 文章标签
     */
    @Delete("DELETE FROM article_map_label WHERE article_id=#{articleId}")
    void deleteLabels(@Param("articleId") int articleId);

    /**
     * 清空文章分类
     *
     * @param articleId 文章标签
     */
    @Delete("DELETE FROM article_map_type WHERE article_id=#{articleId}")
    void deleteType(@Param("articleId") int articleId);

    /**
     * 按分类获取每个分类多少文章
     *
     * @param limit 取最多的前几条
     * @return [{name=String, value=Object}]
     */
    @Select("select t.num as value, al.name from " +
            "( " +
            "select count(*) as num, amt.type_id from article_map_type amt group by type_id order by num desc limit #{limit} " +
            ") " +
            "t " +
            "LEFT JOIN article_label al on t.type_id = al.id " +
            "order by num desc ")
    List<Map<String, Object>> getArticleCountByType(int limit);
}
