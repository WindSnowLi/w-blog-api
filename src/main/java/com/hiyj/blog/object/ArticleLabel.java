package com.hiyj.blog.object;

import com.hiyj.blog.object.base.LabelBase;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ApiModel
@Getter
@Setter
@ToString
public class ArticleLabel extends LabelBase implements Serializable {

}
