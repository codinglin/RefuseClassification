package com.study.refuse_classification.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class MyCommentVO {
    @ApiModelProperty(value = "评论ID")
    private String id;

    @ApiModelProperty(value = "文章ID")
    private String articleId;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
}
