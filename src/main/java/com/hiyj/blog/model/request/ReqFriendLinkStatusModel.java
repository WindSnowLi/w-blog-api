package com.hiyj.blog.model.request;

import com.hiyj.blog.object.FriendLink;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@ToString
@Setter
@Getter
public class ReqFriendLinkStatusModel {
    //    状态
    @ApiModelProperty(value = "友链状态", required = true)
    private FriendLink.Status status;
}
