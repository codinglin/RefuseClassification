package com.study.refuse_classification.entity.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ArticleForm {
    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "详细内容")
    private String detail;

    @ApiModelProperty(value = "图片")
    private String pic;

    @ApiModelProperty(value = "视频")
    private String audio;

    @ApiModelProperty(value = "文章类型")
    private Integer type;

    @ApiModelProperty(value = "主页面展示")
    private Integer showIndex;
}
