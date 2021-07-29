package xyz.firstmeet.lblog.services;

import org.springframework.stereotype.Service;
import xyz.firstmeet.lblog.object.Msg;
import xyz.firstmeet.lblog.services.base.ArticleLabelService;

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
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.getSuccessMsg(), this.getAllLabel());
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
}
