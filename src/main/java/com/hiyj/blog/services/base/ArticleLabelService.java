package com.hiyj.blog.services.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hiyj.blog.mapper.ArticleLabelMapper;
import com.hiyj.blog.mapper.ArticleMapper;
import com.hiyj.blog.object.Article;
import com.hiyj.blog.object.ArticleLabel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("articleLabelService")
public class ArticleLabelService {
    protected ArticleLabelMapper articleLabelMapper;

    @Autowired
    public void setArticleLabelMapper(ArticleLabelMapper articleLabelMapper) {
        this.articleLabelMapper = articleLabelMapper;
    }

    protected ArticleMapper articleMapper;

    @Autowired
    public void setArticleMapper(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    /**
     * 通过标签ID获取标签
     *
     * @param id 标签ID
     * @return 标签对象
     */
    public ArticleLabel getTypeById(int id) {
        return articleLabelMapper.getTypeById(id);
    }


    /**
     * 通过文章ID获取文章所属类型
     *
     * @param articleId 文章ID
     * @return 文章类型对象
     */
    public ArticleLabel getArticleTypeById(int articleId) {
        int articleTypeById = articleLabelMapper.getArticleTypeById(articleId);
        return getTypeById(articleTypeById);
    }

    /**
     * 添加文章——分类映射
     *
     * @param articleId 文章ID
     * @param typeId    所属类型
     */
    public void addArticleMapType(int articleId, int typeId) {
        articleLabelMapper.addArticleMapType(articleId, typeId);
    }

    /**
     * 获取所有标签
     *
     * @return List ArticleLabel
     */
    public List<ArticleLabel> getAllLabel() {
        return articleLabelMapper.getAllLabel();
    }

    /**
     * 通过标签ID获取标签
     *
     * @param labelId 标签ID
     * @return 标签对象
     */
    public ArticleLabel getLabelById(int labelId) {
        return articleLabelMapper.getLabelById(labelId);
    }

    /**
     * 通过类型名获取类型
     *
     * @param typeName 类型名
     * @return 类型对象
     */
    public ArticleLabel getTypeByName(String typeName) {
        ArticleLabel typeByName = articleLabelMapper.getTypeByName(typeName);
        if (typeByName == null) {
            return null;
        }
        ArticleLabel typeById = getTypeById(typeByName.getId());
        if (typeById == null) {
            return typeByName;
        }
        return typeById;
    }

    /**
     * 清空文章标签与分类信息
     *
     * @param articleId 文章ID
     */
    public void deleteProperty(int articleId) {
        articleLabelMapper.deleteLabels(articleId);
        articleLabelMapper.deleteType(articleId);
    }

    /**
     * 添加文章标签与分类信息
     *
     * @param article 文章对象
     */
    public void addProperty(Article article) {
        //文章标签信息
        List<ArticleLabel> labels = article.getLabels();
        //已经存在的标签
        List<ArticleLabel> articleLabels = articleMapper.batchCheckLabelByNames(labels);
        if (articleLabels.size() != 0) {
            //先添加已存在的标签
            articleMapper.addArticleMapLabels(article.getId(), articleLabels);
        }

        //整理出已存在的标签名
        ArrayList<String> existentLabels = new ArrayList<>();
        for (ArticleLabel articleLabel : articleLabels) {
            existentLabels.add(articleLabel.getName());
        }
        //获取目前不存在的标签
        ArrayList<ArticleLabel> noExistentLabels = new ArrayList<>();
        for (ArticleLabel articleLabel : labels) {
            if (!existentLabels.contains(articleLabel.getName())) {
                noExistentLabels.add(articleLabel);
            }
        }
        if (noExistentLabels.size() != 0) {
            //新添加的标签
            articleMapper.addLabels(noExistentLabels);
            //添加之前不存在的标签
            articleMapper.addArticleMapLabels(article.getId(), noExistentLabels);
        }

        ArticleLabel typeByName = getTypeByName(article.getArticleType().getName());
        if (typeByName == null) {
            ArrayList<ArticleLabel> typeList = new ArrayList<>();
            typeList.add(article.getArticleType());
            //新添加的标签
            articleMapper.addLabels(typeList);
        }
        article.setArticleType(typeByName);
        //添加类型映射
        addArticleMapType(article.getId(), article.getArticleType().getId());
    }

    /**
     * 按分类获取每个分类多少文章
     *
     * @param limit 取最多的前几条
     * @return [{name=String, value=Object}]
     */
    public List<Map<String, Object>> getArticleCountByType(int limit) {
        return articleLabelMapper.getArticleCountByType(limit);
    }

    /**
     * 获取所有分类数量
     *
     * @return 分类数量
     */
    public int getTypeSize() {
        return articleLabelMapper.getTypeSize();
    }
}
