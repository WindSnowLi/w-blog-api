package com.hiyj.blog.services;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.hiyj.blog.object.Article;
import com.hiyj.blog.object.ArticleLabel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
        System.out.println(articleJsonService.getLabelArticlePageJson(2, 10, 1));
    }

    @Test
    public void testGetAllVisitCountByType() {
        int userId = 1;
        final List<ArticleLabel> visitCountByTypeByUserId = articleJsonService.getVisitCountByTypeByUserId(userId, 10);

        JSONObject jsonObject = new JSONObject();
        ArrayList<String> dataName = new ArrayList<>();
        for (ArticleLabel articleLabel : visitCountByTypeByUserId) {
            dataName.add(articleLabel.getName());
        }
        final ArrayList<JSONObject> visitData = new ArrayList<>();
        int top10 = 0;
        for (ArticleLabel articleLabel : visitCountByTypeByUserId) {
            JSONObject temp = new JSONObject();
            top10 += articleLabel.getVisitsCount();
            temp.put("value", articleLabel.getVisitsCount());
            temp.put("name", articleLabel.getName());
            visitData.add(temp);
        }
        int other = articleJsonService.getVisitsCountByUserId(userId) - top10;
        if (visitData.size() == 10 && other > 0) {
            JSONObject temp = new JSONObject();
            temp.put("value", other);
            dataName.add("其它");
            temp.put("name", "其它");
            visitData.add(temp);
        }
        jsonObject.put("dataName", dataName);
        jsonObject.put("visitData", visitData);
        System.out.println(jsonObject.toJSONString());
    }

    /**
     * 创建文章
     */
    @Test
    public void testCreateArticle() {
        Article article = new Article();
        article.setContent("TEST");
        article.setSummary("TEST");
        article.setTitle("TEST");
        article.setCoverPic("TEST");
        ArrayList<ArticleLabel> labbels = new ArrayList<>();
        ArticleLabel label1 = new ArticleLabel();
        label1.setName("C++");
        labbels.add(label1);
        ArticleLabel label2 = new ArticleLabel();
        labbels.add(label2);
        label2.setName("TEST");
        article.setLabels(labbels);
        articleJsonService.createArticle(article, 1);
    }

    /**
     * 按天获取访问总量和趋势数据
     */
    @Test
    public void testGetVisitLogByDay() {
        JSONObject jsonObject = new JSONObject();
        ArrayList<String> x = new ArrayList<>();
        ArrayList<Integer> y = new ArrayList<>();
        for (Object temp : articleJsonService.getVisitLogByDay(1)) {
            x.add((String) ((HashMap) temp).get("day_time"));
            y.add(((BigDecimal) ((HashMap) temp).get("total")).intValue());
        }
        jsonObject.put("visitsAllCount", articleJsonService.getVisitsCountByUserId(1));
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
