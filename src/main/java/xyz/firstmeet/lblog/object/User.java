package xyz.firstmeet.lblog.object;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@ApiModel
public class User implements Serializable {

    //账户平台
    public enum Platform {
        GITEE,
        GITHUB,
        LOCAL
    }

    public enum Status {
        //        停用
        STOP,
        //        正常使用
        NORMAL
    }

    //ID
    protected int id;
    //账户
    private String account;
    //密码
    @JSONField(serialize = false)
    private String password;
    //昵称
    private String nickname;
    //头像链接
    private String avatar;
    //QQ
    private String qq;
    //个人介绍
    private String introduction;
    //角色
    private ArrayList<String> roles;
    //帐号状态
    private Status status;
}
