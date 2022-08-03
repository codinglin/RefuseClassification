package com.study.refuse_classification.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArticleVO {
    @ApiModelProperty(value = "文章编号")
    private String id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "点击量")
    private Integer pageviews;

    @ApiModelProperty(value = "详细内容")
    private String detail;

    @ApiModelProperty(value = "图片")
    private String pic;

    @ApiModelProperty(value = "视频")
    private String audio;

    @ApiModelProperty(value = "文章类型")
    private Integer type;

    @ApiModelProperty(value = "评论数")
    private Integer commentCount;

    @ApiModelProperty(value = "评论")
    private List<CommentVO> commentVOS;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
}
