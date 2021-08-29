package com.hiyj.blog.services;

import com.alibaba.fastjson.JSONObject;
import com.hiyj.blog.object.ArticleLabel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ArticleLabelServiceTest {
    private ArticleLabelJsonService articleLabelJsonService;

    private ArticleJsonService articleJsonService;

    @Autowired
    public void setArticleLabelJsonService(ArticleLabelJsonService articleLabelJsonService) {
        this.articleLabelJsonService = articleLabelJsonService;
    }

    @Autowired
    public void setArticleJsonService(ArticleJsonService articleJsonService) {
        this.articleJsonService = articleJsonService;
    }

    @Test
    public void testGetAllLabelJson() {
        System.out.println(articleLabelJsonService.getAllLabelJson());
    }

    @Test
    public void testGetAllVisitCountByType() {
        int userId = 1;
        final List<ArticleLabel> visitCountByTypeByUserId = articleLabelJsonService.getVisitCountByTypeByUserId(userId, 10);

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

}
