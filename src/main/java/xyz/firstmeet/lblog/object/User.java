package xyz.firstmeet.lblog.object;

import com.alibaba.fastjson.annotation.JSONField;
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
public class User implements Serializable {
    //ID
    private int id;
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
}
