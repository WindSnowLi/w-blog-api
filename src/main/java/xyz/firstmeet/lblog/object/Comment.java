package xyz.firstmeet.lblog.object;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import xyz.firstmeet.lblog.object.base.CommentBase;

import java.util.Date;

@ApiModel
@Getter
@Setter
@ToString
public class Comment extends CommentBase {

    //评论人
    @ApiModelProperty(name = "fromUser", value = "评论人", required = true)
    protected int fromUser;

    //所属根会话ID，为空则为根会话
    @ApiModelProperty(name = "parentId", value = "所属根会话ID，为空则为根会话")
    protected Integer parentId;

    //评论内容
    @ApiModelProperty(name = "content", value = "评论内容", required = true)
    protected String content;

    //评论时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(hidden = true)
    protected Date time;

    //给谁的评论回复，为空则为根会话
    @ApiModelProperty(name = "toUser", value = "给谁的评论回复，为空则为根会话")
    protected Integer toUser;

    //评论状态，评论需要审核
    @ApiModelProperty(hidden = true)
    protected CommentBase.Status status;
}
