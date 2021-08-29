package com.hiyj.blog.object.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CommentBase {

    public enum SessionType {
        //文章评论
        ARTICLE,
        //标签评论
        TAG,
        //分类评论
        TYPE,
        //关于信息评论
        ABOUT,
        //留言信息
        MESSAGE,
        //所有
        ALL
    }

    public enum Status {
        //通过
        PASS,
        //审核
        VERIFY,
        //删除
        DELETE,
        //所有
        ALL
    }

    //评论ID
    @ApiModelProperty(hidden = true)
    protected int id;

    //评论的目标ID
    @ApiModelProperty(name = "targetId", value = "评论的目标ID", required = true)
    protected Integer targetId;

    //会话类型
    @ApiModelProperty(name = "sessionType", value = "会话类型", required = true)
    protected CommentBase.SessionType sessionType;
}
