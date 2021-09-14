package com.hiyj.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.hiyj.blog.annotation.PassToken;
import com.hiyj.blog.annotation.Permission;
import com.hiyj.blog.model.request.*;
import com.hiyj.blog.object.Article;
import com.hiyj.blog.object.ArticleLabel;
import com.hiyj.blog.object.Msg;
import com.hiyj.blog.object.base.LabelBase;
import com.hiyj.blog.services.ArticleJsonService;
import com.hiyj.blog.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "文章相关", value = "文章相关")
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
     * @param pageModel {
     *                  "page":int,
     *                  "limit":int,
     *                  "sort":"+id/-id,默认-id",
     *                  "status":"文章状态，默认published，all为全部文章类型"
     *                  }
     * @return Msg 内含文章列表
     */
    @ApiOperation(value = "分页获取文章列表")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS, response = ArrayList.class),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getArticlesByPage")
    @PassToken
    public String getArticlesByPage(@RequestBody PageModel<Article.Status> pageModel) {
        return articleJsonService.getArticlesByPageJson(
                pageModel.getLimit(),
                pageModel.getPage(),
                pageModel.getSort(),
                pageModel.getStatus());
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
        return articleJsonService.getArticleIdByPageJson(
                pageModel.getLimit(),
                pageModel.getPage(),
                pageModel.getSort(),
                pageModel.getStatus());
    }

    /**
     * 根据文章ID获取文章信息
     *
     * @param idModel {"id":"articleId"}
     * @return Msg
     */
    @ApiOperation(value = "根据文章ID获取文章详情")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS, response = Article.class),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getArticleById")
    @PassToken
    public String getArticleById(@RequestBody IdModel idModel) {
        articleJsonService.addArticleVisits(idModel.getId());
        return articleJsonService.findArticleJson(idModel.getId());
    }

    /**
     * 根据文章ID获取作者
     *
     * @param idModel {"id":"articleId"}
     * @return Msg
     */
    @ApiOperation(value = "根据文章ID获取作者")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getAuthorByArticleId")
    @PassToken
    public String findArticleAuthor(@RequestBody IdModel idModel) {
        return articleJsonService.findArticleAuthorJson(idModel.getId());
    }

    /**
     * 获取标签所属文章
     *
     * @param idTypeModel {"id":"int",
     *                    content: {
     *                    "limit":"int",
     *                    "page":"int"，
     *                    "sort":   排序方式 默认-id,
     *                    "status": 文章状态
     *                    }
     *                    }
     * @return Msg
     */
    @ApiOperation(value = "分页获取标签所属文章")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getArticlesByLabel")
    @PassToken
    public String getLabelArticles(@RequestBody IdTypeModel<PageModel<Article.Status>> idTypeModel) {
        articleJsonService.addLabelPV(idTypeModel.getId());
        return articleJsonService.getArticlesByLabelJson(idTypeModel.getId(),
                idTypeModel.getContent().getLimit(),
                idTypeModel.getContent().getPage(),
                idTypeModel.getContent().getSort(),
                idTypeModel.getContent().getStatus());
    }

    /**
     * 获取分类所属文章
     * <p>
     *  TODO
     *
     * @param idTypeModel {
     *                    "id":"int",
     *                    "content": {
     *                    "limit":"int",
     *                    "page":"int",
     *                    "sort":"",
     *                    "status":文章状态，默认PUBLISHED，ALL为全部文章类型
     *                    }
     *                    }
     * @return Msg
     */
    @ApiOperation(value = "获取分类所属文章")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getArticlesByType")
    @PassToken
    public String getArticleByType(@RequestBody IdTypeModel<PageModel<Article.Status>> idTypeModel) {
        Article.Status status = idTypeModel.getContent().getStatus();
        if (status == null) {
            status = Article.Status.PUBLISHED;
        }
        articleJsonService.addTypePV(idTypeModel.getId());
        return articleJsonService.getArticleByTypeJson(
                idTypeModel.getId(),
                idTypeModel.getContent().getLimit(),
                idTypeModel.getContent().getPage(),
                idTypeModel.getContent().getSort(),
                status);
    }

    /**
     * 创建文章
     *
     * @param tokenTypeModel {
     *                       "token":token,
     *                       "content: article,"
     *                       }
     * @return Msg
     */
    @ApiOperation(value = "创建文章")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "createArticle")
    @Permission(value = {"CREATE-ARTICLE"})
    public String createArticle(@RequestBody TokenTypeModel<JSONObject> tokenTypeModel) {
        int userId = JwtUtils.getTokenUserId(tokenTypeModel.getToken());
        // 整理标签
        ArrayList<LabelBase> articleLabels = new ArrayList<>();
        JSONObject articleJson = tokenTypeModel.getContent();
        for (Object labelName : articleJson.getObject("labels", List.class)) {
            ArticleLabel temp = new ArticleLabel();
            temp.setName((String) labelName);
            articleLabels.add(temp);
        }
        //整理分类
        String articleTypeName = (String) articleJson.get("articleType");
        LabelBase articleType = new LabelBase();
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
     * 更新文章
     *
     * @param contentModel {
     *                     "content": "article"
     *                     }
     * @return Msg
     */
    @ApiOperation(value = "更新文章")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "updateArticle")
    @Permission(value = {"UPDATE-ARTICLE"})
    public String updateArticle(@RequestBody ContentModel<JSONObject> contentModel) {
        contentModel.getContent().remove("pv");
        //整理分类
        String articleTypeName = (String) contentModel.getContent().get("articleType");
        ArticleLabel articleType = new ArticleLabel();
        articleType.setName(articleTypeName);
        contentModel.getContent().remove("articleType");
        // 整理标签
        ArrayList<LabelBase> articleLabels = new ArrayList<>();
        for (Object labelName : contentModel.getContent().getObject("labels", List.class)) {
            ArticleLabel temp = new ArticleLabel();
            temp.setName((String) labelName);
            articleLabels.add(temp);
        }
        contentModel.getContent().remove("labels");
        Article article = JSONObject.parseObject(contentModel.getContent().toJSONString(), Article.class);
        article.setArticleType(articleType);
        article.setLabels(articleLabels);
        return articleJsonService.updateArticleJson(article);
    }

    /**
     * 获取访问最多的文章
     *
     * @param jsonObject { "limit":"int" }
     * @return Msg
     */
    @ApiOperation(value = "获取访问最多的文章")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getMostPV")
    @PassToken
    public String getMostPV(@RequestBody JSONObject jsonObject) {
        return articleJsonService.getMostPVJson(jsonObject.getIntValue("limit"));
    }

    /**
     * 设置文章状态
     *
     * @param idTypeModel {
     *                    id:文章ID,
     *                    "content": status:"Article.status"
     *                    }
     * @return Msg
     */
    @ApiOperation(value = "根据文章ID设置文章状态")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "setStatus")
    @Permission(value = {"UPDATE-ARTICLE"})
    public String setStatus(@RequestBody IdTypeModel<Article.Status> idTypeModel) {
        return articleJsonService.setStatusJson(idTypeModel.getId(), idTypeModel.getContent());
    }

    /**
     * 删除文章
     *
     * @param idModel { id:int }
     * @return Msg
     */
    @ApiOperation(value = "根据文章ID删除文章")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "delArticle")
    @Permission(value = {"DELETE-ARTICLE"})
    public String delArticle(@RequestBody IdModel idModel) {
        return articleJsonService.setStatusJson(idModel.getId(), Article.Status.DELETED);
    }
}
