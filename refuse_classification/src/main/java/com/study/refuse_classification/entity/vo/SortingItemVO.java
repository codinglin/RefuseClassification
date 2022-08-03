package com.study.refuse_classification.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SortingItemVO {
    @ApiModelProperty(value = "编号")
    private String id;

    @ApiModelProperty(value = "物品名")
    private String name;

    @ApiModelProperty(value = "点击数")
    private Integer count;

    @ApiModelProperty(value = "父ID")
    private String parentId;

    @ApiModelProperty(value = "类别")
    private String sortName;

    @ApiModelProperty(value = "描述")
    private String node;
}
