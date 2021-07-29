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
public class ArticleLabel implements Serializable {
    //label ID
    private int id;
    //label名称
    private String name;
    //封面
    private String coverPic;
    //浏览次数
    private int visitsCount;
    //所属文章总数
    private int num;
}
