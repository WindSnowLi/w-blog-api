package com.hiyj.blog.services.base;

import com.hiyj.blog.mapper.ArticleLabelMapper;
import com.hiyj.blog.mapper.ArticleMapper;
import com.hiyj.blog.object.Article;
import com.hiyj.blog.object.ArticleLabel;
import com.hiyj.blog.object.ArticleType;
import com.hiyj.blog.object.base.LabelBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * 通过类型ID获取类型
     *
     * @param id 类型ID
     * @return 类型对象
     */
    public LabelBase getTypeById(int id) {
        return articleLabelMapper.getTypeById(id);
    }


    /**
     * 通过文章ID获取文章所属类型
     *
     * @param articleId 文章ID
     * @return 文章类型对象
     */
    public LabelBase getArticleMapType(int articleId) {
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
    public LabelBase getTypeByName(String typeName) {
        ArticleType typeByName = articleLabelMapper.getTypeByName(typeName);
        if (typeByName == null) {
            return null;
        }
        LabelBase typeById = getTypeById(typeByName.getId());
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
        List<LabelBase> labels = article.getLabels();
        //已经存在的标签
        List<LabelBase> articleLabels = articleLabelMapper.batchCheckLabelByNames(labels);
        if (articleLabels.size() != 0) {
            //先添加已存在的标签
            articleMapper.addArticleMapLabels(article.getId(), articleLabels);
        }

        //整理出已存在的标签名
        ArrayList<String> existentLabels = new ArrayList<>();
        for (LabelBase articleLabel : articleLabels) {
            existentLabels.add(articleLabel.getName());
        }
        //获取目前不存在的标签
        ArrayList<LabelBase> noExistentLabels = new ArrayList<>();
        for (LabelBase articleLabel : labels) {
            if (!existentLabels.contains(articleLabel.getName())) {
                noExistentLabels.add(articleLabel);
            }
        }
        if (noExistentLabels.size() != 0) {
            //新添加的标签
            articleLabelMapper.addLabels(noExistentLabels);
            //添加之前不存在的标签
            articleMapper.addArticleMapLabels(article.getId(), noExistentLabels);
        }

        LabelBase typeByName = getTypeByName(article.getArticleType().getName());
        if (typeByName == null) {
            ArrayList<LabelBase> typeList = new ArrayList<>();
            typeList.add(article.getArticleType());
            //新添加的标签
            articleLabelMapper.addLabels(typeList);
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
     * 分页获取标签
     *
     * @param limit  限制数
     * @param offset 偏移量量
     * @return List<ArticleLabel>
     */
    public List<LabelBase> getLabelByPage(int limit, int offset) {
        return articleLabelMapper.getLabelByPage(limit, offset);
    }

    /**
     * 设置标签内容
     *
     * @param articleLabel 标签对象
     */
    public void setLabel(ArticleLabel articleLabel) {
        articleLabelMapper.setLabel(articleLabel);
    }

    /**
     * 通过用户ID按照访问量排序分页获取用户分类
     *
     * @param limit  限制数
     * @param offset 偏移量
     * @return List<LabelBase>
     */
    public List<LabelBase> getTypeOfPVPage(int limit, int offset) {
        List<LabelBase> allType = articleLabelMapper.getTypes();
        if (offset + limit > allType.size()) {
            limit = allType.size() - offset;
        }
        if (limit < 1) {
            return null;
        }
        allType.sort((o1, o2) -> Integer.compare(o2.getPv(), o1.getPv()));
        return allType.subList(offset, offset + limit);
    }
}
