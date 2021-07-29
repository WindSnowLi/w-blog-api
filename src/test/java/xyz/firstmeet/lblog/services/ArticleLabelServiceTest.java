package xyz.firstmeet.lblog.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ArticleLabelServiceTest {
    private ArticleLabelJsonService articleLabelJsonService;

    @Autowired
    public void setArticleLabelJsonService(ArticleLabelJsonService articleLabelJsonService) {
        this.articleLabelJsonService = articleLabelJsonService;
    }

    @Test
    public void testGetAllLabelJson() {
        System.out.println(articleLabelJsonService.getAllLabelJson());
    }
}
