package xyz.firstmeet.lblog.mapper;

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
        System.out.println(articleLabelMapper.getTypeById(2));
    }

    @Test
    public void testGetArticleTypeById() {
        System.out.println(articleLabelMapper.getArticleTypeById(13));
    }

    /**
     * 按分类获取每个分类多少文章
     */
    @Test
    public void testGetArticleCountByType() {
        System.out.println(articleLabelMapper.getArticleCountByType(10));
    }
}
