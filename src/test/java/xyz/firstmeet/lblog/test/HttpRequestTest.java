package xyz.firstmeet.lblog.test;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class HttpRequestTest {
    /**
     * Spring Boot Post请求
     */
    @Test
    public void testPostRequest() {
        String url = "https://www.blog.firstmeet.xyz/api/article/getArticleById";
        //        请求表
        JSONObject paramMap = new JSONObject();
        paramMap.put("id", 93);
        //        请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("user-agent", "Mozilla/5.0 WindSnowLi-Blog");
        //        整合请求头和请求参数
        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(paramMap, headers);
        //         请求客户端
        RestTemplate client = new RestTemplate();
        //      发起请求
        JSONObject body = client.postForEntity(url, httpEntity, JSONObject.class).getBody();
        System.out.println("******** POST请求 *********");
        assert body != null;
        System.out.println(body.toJSONString());
    }

    /**
     * Spring Boot Get请求
     */
    @Test
    public void testGetRequest() {
        String url = "https://www.blog.firstmeet.xyz/";
        //         请求客户端
        RestTemplate client = new RestTemplate();
        //      发起请求
        String body = client.getForEntity(url, String.class).getBody();
        System.out.println("******** Get请求 *********");
        assert body != null;
        System.out.println(body);
    }
}
