package com.study.refuse_classification.entity.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommentForm {
    @ApiModelProperty(value = "文章ID")
    private String articleId;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "父ID")
    private String parentId;
}
