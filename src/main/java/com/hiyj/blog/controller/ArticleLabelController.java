package com.hiyj.blog.controller;

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
import com.hiyj.blog.annotation.PassToken;
import com.hiyj.blog.model.request.IdModel;
import com.hiyj.blog.object.Msg;
import com.hiyj.blog.services.ArticleLabelJsonService;

import java.util.ArrayList;

@Slf4j
@RestController
@Api(tags = "文章标签相关", value = "文章标签相关")
@RequestMapping(value = "/api/articleLabel", produces = "application/json;charset=UTF-8")
public class ArticleLabelController {
    private ArticleLabelJsonService articleLabelJsonService;

    @Autowired
    public void setArticleLabelJsonService(ArticleLabelJsonService articleLabelJsonService) {
        this.articleLabelJsonService = articleLabelJsonService;
    }

    /**
     * 通过标签ID获取标签
     *
     * @param idModel 标签ID  {id:int}
     * @return 标签信息串
     */
    @ApiOperation(value = "通过标签ID获取标签")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS, response = ArrayList.class),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getTypeById")
    @PassToken
    public String getTypeById(@RequestBody IdModel idModel) {
        log.info("getTypeById\t类型ID：{}", idModel.getId());
        return articleLabelJsonService.getTypeByIdJson(idModel.getId());
    }

    /**
     * 获取所有标签
     *
     * @return Msg
     */
    @ApiOperation(value = "获取所有标签")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS, response = ArrayList.class),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getAllLabel")
    @PassToken
    public String getAllLabel() {
        log.info("getAllLabel");
        return articleLabelJsonService.getAllLabelJson();
    }


    /**
     * 通过标签ID获取标签
     *
     * @param idModel 标签ID  {id:int}
     * @return Msg
     */
    @ApiOperation(value = "通过标签ID获取标签")
    @ApiResponses({
            @ApiResponse(code = 20000, message = Msg.MSG_SUCCESS, response = ArrayList.class),
            @ApiResponse(code = -1, message = Msg.MSG_FAIL)
    })
    @PostMapping(value = "getLabelByIdJson")
    @PassToken
    public String getLabelByIdJson(@RequestBody IdModel idModel) {
        log.info("getLabelByIdJson\t标签ID{}", idModel.getId());
        return articleLabelJsonService.getLabelByIdJson(idModel.getId());
    }
}



