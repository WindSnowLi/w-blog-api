package com.hiyj.blog.services;

import com.hiyj.blog.object.ArticleLabel;
import com.hiyj.blog.services.base.ArticleLabelService;
import org.springframework.stereotype.Service;
import com.hiyj.blog.object.Msg;

import java.util.List;

@Service("articleLabelJsonService")
public class ArticleLabelJsonService extends ArticleLabelService {

    /**
     * 通过类型ID获取类型
     *
     * @param id 类型ID
     * @return 标签Json信息
     */
    public String getTypeByIdJson(int id) {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, this.getTypeById(id));
    }

    /**
     * 获取所有标签
     *
     * @return ArticleLabel
     */
    public String getAllLabelJson() {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, this.getAllLabel());
    }

    /**
     * 通过标签ID获取标签
     *
     * @param labelId 标签ID
     * @return 标签对象JSON
     */
    public String getLabelByIdJson(int labelId) {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.getSuccessMsg(), this.getLabelById(labelId));
    }

    /**
     * 获取所有分类信息
     *
     * @return Msg
     */
    public String getTypes() {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, articleLabelMapper.getTypes());
    }

    /**
     * 分页获取标签
     *
     * @param limit 限制数
     * @param page  偏移量量
     * @return Msg
     */
    public String getLabelByPageJson(int limit, int page) {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS,
                getLabelByPage(limit, (page - 1) * limit));
    }
}
