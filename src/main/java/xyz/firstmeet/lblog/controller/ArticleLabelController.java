package xyz.firstmeet.lblog.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.firstmeet.lblog.annotation.PassToken;
import xyz.firstmeet.lblog.services.ArticleLabelJsonService;

@Slf4j
@RestController
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
     * @param json 标签ID  {id:int}
     * @return 标签信息串
     */
    @PostMapping(value = "getTypeById")
    @PassToken
    public String getTypeById(@RequestBody JSONObject json) {
        log.info("getTypeById\t类型ID：{}", json.getIntValue("id"));
        return articleLabelJsonService.getTypeByIdJson(json.getIntValue("id"));
    }

    /**
     * 获取所有标签
     *
     * @return Msg
     */
    @PostMapping(value = "getAllLabel")
    @PassToken
    public String getAllLabel() {
        log.info("getAllLabel");
        return articleLabelJsonService.getAllLabelJson();
    }


    /**
     * 通过标签ID获取标签
     *
     * @param json 标签ID  {id:int}
     * @return Msg
     */
    @PostMapping(value = "getLabelByIdJson")
    @PassToken
    public String getLabelByIdJson(@RequestBody JSONObject json) {
        log.info("getLabelByIdJson\t标签ID{}", json.getIntValue("id"));
        return articleLabelJsonService.getLabelByIdJson(json.getIntValue("id"));
    }
}



