package xyz.firstmeet.lblog.object;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OtherUser {
    private int id;
    //其他平台身份识别ID
    private String other_id;
    //平台
    private User.Platform other_platform;
    //用户ID
    private int user_id;
    //密钥信息
    private String access_token;
    //密钥过期时刷新密钥
    private String refresh_token;
    //授权类别
    private String scope;
    //密钥有效期
    private int expires_in;
    //密钥创建时间
    private int created_at;
}
