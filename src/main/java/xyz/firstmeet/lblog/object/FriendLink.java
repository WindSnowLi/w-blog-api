package xyz.firstmeet.lblog.object;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import xyz.firstmeet.lblog.object.base.Link;

@Setter
@Getter
@ToString
public class FriendLink extends Link {
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
        HIDE
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
