package xyz.firstmeet.lblog.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.firstmeet.lblog.annotation.PassToken;
import xyz.firstmeet.lblog.annotation.UserLoginToken;
import xyz.firstmeet.lblog.model.request.ArticlePageModel;
import xyz.firstmeet.lblog.model.request.PageModel;
import xyz.firstmeet.lblog.object.Article;
import xyz.firstmeet.lblog.object.ArticleLabel;
import xyz.firstmeet.lblog.object.Msg;
import xyz.firstmeet.lblog.services.ArticleJsonService;
import xyz.firstmeet.lblog.utils.JwtUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/article", produces = "application/json;charset=UTF-8")
public class ArticleController {

    private ArticleJsonService articleJsonService;

    @Autowired
    public void setArticleJsonService(ArticleJsonService articleJsonService) {
        this.articleJsonService = articleJsonService;
    }

    /**
     * 分页获取文章列表
     *
     * @param articlePageModel {
     *                         "userId":"int",
     *                         "page":int,
     *                         "limit":int,
     *                         "sort":"+id/-id,默认-id",
     *                         "status":"文章状态，默认published，all为全部文章类型"
     *                         }
     * @return Msg 内含文章列表
     */
    @ApiOperation(value = "分页获取文章列表")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS, response = ArrayList.class),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getArticlesByPage")
    @PassToken
    public String getArticlesByPage(@RequestBody ArticlePageModel articlePageModel) {
        log.info("getArticlesByPage");
        return articleJsonService.getArticlesByPageJson(articlePageModel.getUserId(),
                articlePageModel.getLimit(),
                articlePageModel.getPage(),
                articlePageModel.getSort(),
                articlePageModel.getStatus());
    }

    /**
     * 分页获取文章ID列表
     *
     * @param pageModel {
     *                  "page":int,
     *                  "limit":int,
     *                  "sort":"+id/-id,默认-id",
     *                  "status":"文章状态，默认published，all为全部文章类型"
     *                  }
     * @return Msg 内含文章Id列表
     */
    @ApiOperation(value = "分页获取文章ID列表")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS, response = ArrayList.class),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getArticleIdByPage")
    @PassToken
    public String getArticleIdByPage(@RequestBody PageModel<Article.Status> pageModel) {
        log.info("getArticleIdByPage");
        return articleJsonService.getArticleIdByPageJson(
                pageModel.getLimit(),
                pageModel.getPage(),
                pageModel.getSort(),
                pageModel.getStatus());
    }

    /**
     * 根据文章ID获取文章信息
     *
     * @param articleJson {"id":"articleId"}
     * @return Msg
     */
    @PostMapping(value = "getArticleById")
    @PassToken
    public String getArticleById(@RequestBody JSONObject articleJson) {
        int articleId = articleJson.getIntValue("id");
        log.info("getArticleById\t文章ID：{}", articleId);
        articleJsonService.addArticleVisits(articleId);
        return articleJsonService.findArticleJson(articleId);
    }

    /**
     * 文章可选标签
     *
     * @return {
     * code: 20000,
     * message: "请求成功",
     * data: [{value:"",label:""}]
     * }
     */
    @PostMapping(value = "labels")
    @PassToken
    public String labels() {
        final List<ArticleLabel> allLabels = articleJsonService.getAllLabels();
        final ArrayList<JSONObject> labelList = new ArrayList<>();
        for (ArticleLabel label : allLabels) {
            JSONObject temp = new JSONObject();
            temp.put("value", label.getName());
            temp.put("label", label.getName());
            labelList.add(temp);
        }
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, labelList);
    }

    /**
     * 根据文章ID获取作者
     *
     * @param articleJson {"id":"articleId"}
     * @return Msg
     */
    @PostMapping(value = "getAuthorByArticleId")
    @PassToken
    public String findArticleAuthor(@RequestBody JSONObject articleJson) {
        return articleJsonService.findArticleAuthorJson(articleJson.getIntValue("id"));
    }

    /**
     * 获取热门标签列表
     *
     * @return Msg
     */
    @PostMapping(value = "getHotLabels")
    @PassToken
    public String getHotLabels() {
        return articleJsonService.getHotLabelsJson();
    }

    /**
     * 分页获取标签列表
     *
     * @param json {"id":"int","limit":"int","page":"int"}
     * @return Msg
     */
    @PostMapping(value = "getArticlesByLabel")
    @PassToken
    public String getLabelArticles(@RequestBody JSONObject json) {
        articleJsonService.addLabelVisitsCount(json.getIntValue("id"));
        return articleJsonService.getLabelArticlePageJson(json.getIntValue("id"), json.getIntValue("limit"), json.getIntValue("page"));
    }

    /**
     * 获取所有标签
     *
     * @return Msg
     */
    @PostMapping(value = "getAllLabels")
    @PassToken
    public String getAllLabelsJson() {
        log.info("getAllLabels");
        return articleJsonService.getAllLabelsJson();
    }

    /**
     * 获取用户所有分类
     *
     * @param json 用户信息{id:int}
     * @return 分类表
     */
    @PostMapping(value = "getAllTypeByUserId")
    @PassToken
    public String getAllTypeByUserId(@RequestBody JSONObject json) {
        log.info("getAllTypeByUserId");
        return articleJsonService.getAllTypeByUserIdJson(json.getIntValue("id"));
    }

    /**
     * 获取所有分类
     *
     * @return Msg
     */
    @PostMapping(value = "getAllClassify")
    @PassToken
    public String getAllType() {
        log.info("getAllClassify");
        return articleJsonService.getAllTypeJson();
    }


    /**
     * 获取所属分类文章
     *
     * @param json {
     *             "id":"int",
     *             "status":文章状态，默认published，all为全部文章类型
     *             }
     * @return Msg
     */
    @PostMapping(value = "getArticlesByType")
    @PassToken
    public String getArticleByType(@RequestBody JSONObject json) {
        log.info("getArticlesByType\t文章ID：{}", json.getIntValue("id"));
        Article.Status status = JSONObject.parseObject(json.getString("status"), Article.Status.class);
        if (status == null) {
            status = Article.Status.PUBLISHED;
        }
        articleJsonService.addTypeVisitsCount(json.getIntValue("id"));
        return articleJsonService.getArticleByTypeJson(
                json.getIntValue("id"), status);
    }

    /**
     * 创建文章
     *
     * @param json {"article":article,"token":token}
     * @return Msg
     */
    @PostMapping(value = "createArticle")
    @UserLoginToken
    public String createArticle(@RequestBody JSONObject json) {
        int userId = JwtUtils.getTokenUserId(json.getString("token"));
        log.info("createArticle\t用户ID：{}", userId);
        // 整理标签
        ArrayList<ArticleLabel> articleLabels = new ArrayList<>();
        JSONObject articleJson = (JSONObject) JSONObject.toJSON(json.get("article"));
        for (Object labelName : articleJson.getObject("labels", List.class)) {
            ArticleLabel temp = new ArticleLabel();
            temp.setName((String) labelName);
            articleLabels.add(temp);
        }
        //整理分类
        String articleTypeName = (String) articleJson.get("articleType");
        ArticleLabel articleType = new ArticleLabel();
        articleType.setName(articleTypeName);

        articleJson.remove("labels");
        articleJson.remove("articleType");
        //整理对象
        Article article = JSONObject.parseObject(articleJson.toJSONString(), Article.class);
        article.setLabels(articleLabels);
        article.setArticleType(articleType);
        return articleJsonService.createArticleJson(article, userId);
    }

    /**
     * 创建文章
     *
     * @param json {"article":article,"token":token}
     * @return Msg
     */
    @PostMapping(value = "updateArticle")
    @UserLoginToken
    public String updateArticle(@RequestBody JSONObject json) {
        int userId = JwtUtils.getTokenUserId(json.getString("token"));
        JSONObject articleJson = (JSONObject) JSONObject.toJSON(json.get("article"));
        articleJson.remove("visitsCount");
        //整理分类
        String articleTypeName = (String) articleJson.get("articleType");
        ArticleLabel articleType = new ArticleLabel();
        articleType.setName(articleTypeName);
        articleJson.remove("articleType");
        // 整理标签
        ArrayList<ArticleLabel> articleLabels = new ArrayList<>();
        for (Object labelName : articleJson.getObject("labels", List.class)) {
            ArticleLabel temp = new ArticleLabel();
            temp.setName((String) labelName);
            articleLabels.add(temp);
        }
        articleJson.remove("labels");
        Article article = JSONObject.parseObject(articleJson.toJSONString(), Article.class);
        article.setArticleType(articleType);
        article.setLabels(articleLabels);
        log.info("updateArticle\t用户ID：{}\t文章ID：{}", userId, article.getId());
        return articleJsonService.updateArticleJson(article, userId);
    }


    /**
     * 获取访问最多的文章
     *
     * @param json {
     *             "limit":"int"
     *             }
     * @return Msg
     */
    @PostMapping(value = "getMostVisitsJson")
    @PassToken
    public String getMostVisitsJson(@RequestBody JSONObject json) {
        log.info("getMostVisitsJson\t限制条数：{}", json.getIntValue("limit"));
        return articleJsonService.getMostVisitsJson(json.getIntValue("limit"));
    }

    /**
     * 设置文章状态
     *
     * @param json { articleId:int, token:"", status:"Article.status" }
     * @return Msg
     */
    @PostMapping(value = "setStatus")
    @UserLoginToken
    public String setStatus(@RequestBody JSONObject json) {
        int userId = JwtUtils.getTokenUserId(json.getString("token"));
        json.remove("token");
        log.info("setStatus\tuserId：{}", userId);
        int articleId = json.getInteger("articleId");
        json.remove("articleId");
        Article statusArticle = JSONObject.parseObject(json.toJSONString(), Article.class);
        return articleJsonService.setStatusJson(userId, articleId, statusArticle.getStatus());
    }

    /**
     * 删除文章
     *
     * @param json { articleId:int, token:"" }
     * @return Msg
     */
    @PostMapping(value = "delArticle")
    @UserLoginToken
    public String delArticle(@RequestBody JSONObject json) {
        int userId = JwtUtils.getTokenUserId(json.getString("token"));
        log.info("delArticle\tuserId：{}", userId);
        return articleJsonService.setStatusJson(userId, json.getInteger("articleId"), Article.Status.DELETED);
    }
}
