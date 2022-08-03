package com.study.refuse_classification.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SortQuestionVO {
    @ApiModelProperty(value = "编号")
    private Integer no;

    @ApiModelProperty(value = "正确选项")
    private String answer;

    @ApiModelProperty(value = "题干")
    private String stem;

    @ApiModelProperty(value = "题干")
    private List<OptionVO> options;

    @ApiModelProperty(value = "图片")
    private String img;
}
