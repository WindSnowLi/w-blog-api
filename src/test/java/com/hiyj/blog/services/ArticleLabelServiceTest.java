package com.hiyj.blog.services;

import com.alibaba.fastjson.JSONObject;
import com.hiyj.blog.object.base.LabelBase;
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
    public void testGetTypeOfPVPage() {
        final List<LabelBase> visitCountByTypeByUserId = articleLabelJsonService.getTypeOfPVPage(10, 0);
        System.out.println(JSONObject.toJSONString(visitCountByTypeByUserId));
        JSONObject jsonObject = new JSONObject();
        ArrayList<String> dataName = new ArrayList<>();
        for (LabelBase articleLabel : visitCountByTypeByUserId) {
            dataName.add(articleLabel.getName());
        }
        final ArrayList<JSONObject> pv = new ArrayList<>();
        int top10 = 0;
        for (LabelBase articleLabel : visitCountByTypeByUserId) {
            JSONObject temp = new JSONObject();
            top10 += articleLabel.getPv();
            temp.put("value", articleLabel.getPv());
            temp.put("name", articleLabel.getName());
            pv.add(temp);
        }
        int other = articleJsonService.getPV() - top10;
        if (pv.size() == 10 && other > 0) {
            JSONObject temp = new JSONObject();
            temp.put("value", other);
            dataName.add("其它");
            temp.put("name", "其它");
            pv.add(temp);
        }
        jsonObject.put("dataName", dataName);
        jsonObject.put("pv", pv);
        System.out.println(jsonObject.toJSONString());
    }

}
