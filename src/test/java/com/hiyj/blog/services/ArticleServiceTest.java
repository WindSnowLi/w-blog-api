package com.hiyj.blog.services;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.hiyj.blog.object.Article;

import java.math.BigDecimal;
import java.util.*;

@SpringBootTest
public class ArticleServiceTest {
    private ArticleJsonService articleJsonService;

    @Autowired
    public void setArticleJsonService(ArticleJsonService articleJsonService) {
        this.articleJsonService = articleJsonService;
    }

    @Test
    public void testFindArticleAuthor() {
        System.out.println(articleJsonService.findArticleAuthor(1));
    }

    @Test
    public void testFindArticleJson() {
        System.out.println(articleJsonService.findArticleJson(1));
    }

    @Test
    public void testGetLabelArticlePageJson() {
        System.out.println(articleJsonService.getArticlesByLabelJson(2, 10, 1, "-id", Article.Status.PUBLISHED));
    }

    /**
     * 创建文章
     */
    @Test
    public void testCreateArticle() {
//        Article article = new Article();
//        article.setContent("TEST");
//        article.setSummary("TEST");
//        article.setTitle("TEST");
//        article.setCoverPic("TEST");
//        ArrayList<ArticleLabel> labbels = new ArrayList<>();
//        ArticleLabel label1 = new ArticleLabel();
//        label1.setName("C++");
//        labbels.add(label1);
//        ArticleLabel label2 = new ArticleLabel();
//        labbels.add(label2);
//        label2.setName("TEST");
//        article.setLabels(labbels);
//        articleJsonService.createArticle(article, 1);
    }

    /**
     * 按天获取访问总量和趋势数据
     */
    @Test
    public void testGetVisitLogByDay() {
        JSONObject jsonObject = new JSONObject();
        ArrayList<String> x = new ArrayList<>();
        ArrayList<Integer> y = new ArrayList<>();
        List<Map<String, Object>> visitLogByDay = articleJsonService.getVisitLogByDay();
        if (visitLogByDay != null && !visitLogByDay.contains(null)) {
            for (Object temp : visitLogByDay) {
                x.add((String) ((HashMap) temp).get("day_time"));
                y.add(((BigDecimal) ((HashMap) temp).get("total")).intValue());
            }
        }
        jsonObject.put("visitsAllCount", articleJsonService.getPV());
        jsonObject.put("title", "浏览量");
        //图从左至右，数据应逆序
        Collections.reverse(x);
        Collections.reverse(y);
        jsonObject.put("x", x);
        jsonObject.put("y", y);
        System.out.println(jsonObject.toJSONString());
    }

    /**
     * 分页获取文章ID列表
     */
    @Test
    public void testGetArticleIdByPageJson() {
        System.out.println(articleJsonService.getArticleIdByPageJson(20, 1, "-id", Article.Status.PUBLISHED));
    }
}
