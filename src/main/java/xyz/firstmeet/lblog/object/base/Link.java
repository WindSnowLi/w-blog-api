package xyz.firstmeet.lblog.object.base;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class Link {
    @ApiModelProperty(name = "id",value = "链接ID")
    protected int id;
    //    url
    @ApiModelProperty(name = "link",value = "链接值")
    protected String link;
    //    主题
    @ApiModelProperty(name = "title",value = "链接主题")
    protected String title;
    //    描述
    @ApiModelProperty(name = "describe",value = "链接描述")
    protected String describe;
    // 发布时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    // 最后更新时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
