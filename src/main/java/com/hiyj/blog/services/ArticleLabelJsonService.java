package com.hiyj.blog.services;

import com.hiyj.blog.services.base.ArticleLabelService;
import org.springframework.stereotype.Service;
import com.hiyj.blog.object.Msg;

@Service("articleLabelJsonService")
public class ArticleLabelJsonService extends ArticleLabelService {

    /**
     * 通过标签ID获取标签
     *
     * @param id 标签ID
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
     * 获取用户所有分类信息
     *
     * @param userId 用户ID
     * @return Msg
     */
    public String getAllTypeByUserIdJson(int userId) {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, articleLabelMapper.getAllTypeByUserId(userId));
    }


    /**
     * 获取文章所有分类
     *
     * @return Msg
     */
    public String getAllTypeJson() {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, articleLabelMapper.getTypes());
    }

    public String getAllLabelsJson() {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, getAllLabels());
    }

    public String getHotLabelsJson() {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, getHotLabels());
    }

}
