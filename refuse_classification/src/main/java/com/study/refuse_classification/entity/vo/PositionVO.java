package com.study.refuse_classification.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PositionVO {
    private Integer id;

    private String name;

    private String latitude;

    private String longitude;

    @ApiModelProperty(value = "地址")
    private String address;

    private String joinCluster;

    private Integer width;

    private Integer height;

    private String iconPath;
}
