package com.hiyj.blog.mapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ArticleLabelMapperTest {
    private ArticleLabelMapper articleLabelMapper;

    @Autowired
    public void setArticleLabelMapper(ArticleLabelMapper articleLabelMapper) {
        this.articleLabelMapper = articleLabelMapper;
    }

    @Test
    public void testGetTypeById() {
        System.out.println(JSON.toJSONString(articleLabelMapper.getTypeById(1)));
    }

    @Test
    public void testGetArticleTypeById() {
        System.out.println(articleLabelMapper.getArticleTypeById(1));
    }

    /**
     * 按分类获取每个分类多少文章
     */
    @Test
    public void testGetArticleCountByType() {
        System.out.println(articleLabelMapper.getArticleCountByType(10));
    }

    @Test
    public void testGetTypes() {
        System.out.println(articleLabelMapper.getTypes());
    }

    @Test
    public void testGetAllLabel() {
        System.out.println(articleLabelMapper.getAllLabel());
    }
}
