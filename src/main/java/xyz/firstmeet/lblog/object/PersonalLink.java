package xyz.firstmeet.lblog.object;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PersonalLink implements Serializable {
    private int id;
    //链接
    private String link;
    //链接别名
    private String name;
    //所属用户
    private int user_id;
}
