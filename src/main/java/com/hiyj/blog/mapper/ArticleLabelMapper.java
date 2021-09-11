package com.hiyj.blog.mapper;

import com.hiyj.blog.object.ArticleLabel;
import com.hiyj.blog.object.ArticleType;
import com.hiyj.blog.object.base.LabelBase;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

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
    LabelBase getTypeById(@Param("typeId") int typeId);

    /**
     * 通过类型名获取类型
     *
     * @param typeName 类型名
     * @return 类型对象
     */
    ArticleType getTypeByName(@Param("typeName") String typeName);

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

    /**
     * 获取所有分类
     *
     * @return List ArticleLabel
     */
    List<LabelBase> getTypes();

    /**
     * 添加新标签
     *
     * @param articleLabels 标签列表
     */
    void addLabels(@Param("articleLabels") List<LabelBase> articleLabels);

    /**
     * 根据名字批量检查已经存在的标签
     *
     * @param articleLabels 文章标签对象列表
     * @return 已经存在的标签
     */
    List<LabelBase> batchCheckLabelByNames(@Param("articleLabels") List<LabelBase> articleLabels);

    /**
     * 根据标签名批量查询标签对象
     *
     * @param articleLabels 文章标签对象列表
     * @return 标签对象列表
     */
    List<LabelBase> batchFindLabelByNames(@Param("articleLabels") List<LabelBase> articleLabels);

    /**
     * 分页获取标签
     *
     * @param limit  限制数
     * @param offset 偏移量
     * @return List<LabelBase>
     */
    List<LabelBase> getLabelByPage(@Param("limit") int limit, @Param("offset") int offset);

    /**
     * 设置标签内容
     *
     * @param articleLabel 标签对象
     */
    void setLabel(@Param("articleLabel") ArticleLabel articleLabel);

    /**
     * 获取标签所属文章总数
     *
     * @param id 标签ID
     * @return 总数
     */
    int getArtSumLabel(@Param("id") int id);

    /**
     * 获取标签所属文章总数
     *
     * @param id 分类ID
     * @return 总数
     */
    int getArtSumType(@Param("id") int id);

    /**
     * 通过标签ID获取标签访问量
     *
     * @param id 标签ID
     * @return 总数
     */
    int getLabelPV(@Param("id") int id);

    /**
     * 通过分类ID获取分类访问量
     *
     * @param id 分类ID
     * @return 总数
     */
    int getTypePV(@Param("id") int id);

}
