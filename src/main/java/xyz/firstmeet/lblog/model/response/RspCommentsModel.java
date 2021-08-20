package xyz.firstmeet.lblog.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import xyz.firstmeet.lblog.object.Comment;

import java.util.List;

@ApiModel
@Getter
@Setter
@ToString
public class RspCommentsModel extends Comment {

    //子评论列表，用于应答时生成记录表
    @JSONField(serialzeFeatures = {SerializerFeature.WriteMapNullValue})
    @ApiModelProperty(name = "childList", value = "子评论列表，用于应答时生成记录表")
    protected List<Comment> childList;
}
