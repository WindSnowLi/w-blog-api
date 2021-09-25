package com.hiyj.blog.object;

import com.hiyj.blog.object.base.Link;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ApiModel
@Setter
@Getter
@ToString
public class FriendLink extends Link implements Serializable {
    public enum Status {
        // 通过
        PASS,
        // 申请中
        APPLY,
        // 拒绝
        REFUSE,
        //所有
        ALL,
        // 隐藏状态
        HIDE,
        // 逻辑删除
        DELETE
    }

    //    申请者邮箱
    @ApiModelProperty(name = "email", value = "申请者邮箱")
    protected String email;
    //    友链状态
    @ApiModelProperty(name = "status", value = "友链状态")
    protected FriendLink.Status status;

    // 链接封面
    @ApiModelProperty(name = "coverPic", value = "链接封面")
    protected String coverPic;
}
