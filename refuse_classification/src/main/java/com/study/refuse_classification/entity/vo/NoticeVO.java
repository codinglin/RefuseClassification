package com.study.refuse_classification.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NoticeVO {
    @ApiModelProperty(value = "文章编号")
    private String id;

    @ApiModelProperty(value = "标题")
    private String title;
}
