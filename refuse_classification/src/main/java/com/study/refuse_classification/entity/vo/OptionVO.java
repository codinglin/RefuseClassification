package com.study.refuse_classification.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionVO {
    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "选项")
    private String option;


}
