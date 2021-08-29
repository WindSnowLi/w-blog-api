package com.hiyj.blog.model.request;

import com.hiyj.blog.object.base.CommentBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Getter
@Setter
@ToString
public class ReqCommentModel extends CommentBase {
    //获取的评论状态
    @ApiModelProperty(name = "status", value = "获取的评论状态", required = true)
    protected CommentBase.Status status;
}
