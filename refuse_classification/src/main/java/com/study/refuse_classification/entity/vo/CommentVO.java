package com.study.refuse_classification.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CommentVO {
    @ApiModelProperty(value = "评论ID")
    private String id;

    @ApiModelProperty(value = "文章ID")
    private String articleId;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "用户头像")
    private String userPic;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "父ID")
    private String parentId;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "子评论")
    private List<CommentVO> children;
}
