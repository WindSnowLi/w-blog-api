package xyz.firstmeet.lblog.mapper;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.firstmeet.lblog.object.Article;

import java.math.BigDecimal;
import java.util.*;

@SpringBootTest
public class ArticleMapperTest {
    private ArticleMapper articleMapper;

    @Autowired
    public void setArticleMapper(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Test
    public void tesFindArticleJson() {
        System.out.println(articleMapper.findArticleAuthor(1));
    }

    @Test
    public void testFindArticle() {
        System.out.println(articleMapper.findArticleId(1));
    }

    @Test
    public void testGetHotLabels() {
        System.out.println(articleMapper.getAllLabels());
    }

    @Test
    public void testGetLabelArticlePage() {
        System.out.println(articleMapper.getLabelArticlePage(2, 0, 100));
    }

    @Test
    public void testGetAllTypes() {
        System.out.println(articleMapper.getAllTypes());
    }

    @Test
    public void testGetArticleByType() {
        System.out.println(articleMapper.getArticlesByType(2, "published"));
    }

    @Test
    public void testAddVisitsCount() {
        articleMapper.addVisitsCount(1, 1);
    }

    @Test
    public void testGetArticleCountByUserId() {
        System.out.println(articleMapper.getArticleCountByUserId(1));
    }

    @Test
    public void testGetVisitsAllCountByUserId() {
        System.out.println(articleMapper.getVisitsAllCountByUserId(1));
    }

    @Test
    public void testGetVisitCountByType() {
        System.out.println(articleMapper.getVisitCountByTypeByUserId(1, 5));
    }

    /**
     * 按天获取访问总量和趋势数据
     */
    @Test
    public void testGetVisitHistoryCountByDay() {
        final List<Object> visitHistoryCountByDay = articleMapper.getVisitLogByDay(1);
        for (Object temp : visitHistoryCountByDay) {
            System.out.println(temp);
            System.out.println(((HashMap) temp).get("total").getClass().getName());
            System.out.println(temp.getClass().getName());
        }
        JSONObject jsonObject = new JSONObject();
        ArrayList<String> x = new ArrayList<>();
        ArrayList<Integer> y = new ArrayList<>();
        for (Object temp : visitHistoryCountByDay) {
            x.add((String) ((HashMap) temp).get("day_time"));
            y.add(((BigDecimal) ((HashMap) temp).get("total")).intValue());
        }
        jsonObject.put("x", x);
        jsonObject.put("y", y);
        System.out.println(jsonObject.toJSONString());
    }

    /**
     * 获取文章创建历史
     */
    @Test
    public void testGetArticleCreateHistoryByWeek() {
        JSONObject jsonObject = new JSONObject();
        ArrayList<String> x = new ArrayList<>();
        ArrayList<Integer> y = new ArrayList<>();
        for (Object temp : articleMapper.getArticleCreateLogByWeek(1)) {
            x.add((String) ((HashMap) temp).get("week_time"));
            y.add(((Long) ((HashMap) temp).get("total")).intValue());
        }
        jsonObject.put("x", x);
        jsonObject.put("y", y);
        System.out.println(jsonObject.toJSONString());
    }

    /**
     * 添加文章，不含标签和文章所属用户映射
     */
    @Test
    public void addArticle() {
        Article article = new Article();
        article.setContent("TEST");
        article.setSummary("TEST");
        article.setTitle("TEST");
        article.setCoverPic("TEST");
        System.out.println(articleMapper.addArticle(article));
        System.out.println(article.getId());
    }

    /**
     * 分页查询文章
     */
    @Test
    public void testGetArticlesByPage() {
        System.out.println(articleMapper.getArticlesByPage(3, 0, null, "'published'").size());
        System.out.println(articleMapper.getArticlesByPage(3, 1, null, "'published'").size());
        System.out.println(articleMapper.getArticlesByPage(3, 2, null, "'published'").size());
    }

    /**
     * 分页查询文章Id
     */
    @Test
    public void testGetArticleIdByPage() {
        System.out.println(articleMapper.getArticleIdByPage(3, 0, null, "'published'"));
        System.out.println(articleMapper.getArticleIdByPage(3, 1, null, "'published'"));
        System.out.println(articleMapper.getArticleIdByPage(3, 2, null, "'published'"));
        System.out.println(JSONObject.toJSONString(articleMapper.getArticleIdByPage(3, 2, null, "'published'")));
    }
}

