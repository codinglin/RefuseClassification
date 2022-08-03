package com.study.refuse_classification.entity.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderQuery {
    @ApiModelProperty(value = "公司ID")
    private Integer companyId;

    @ApiModelProperty(value = "标题,模糊查询")
    private String userName;

    @ApiModelProperty(value = "电话")
    private String tel;

    @ApiModelProperty(value = "地区")
    private String address;

    @ApiModelProperty(value = "订单类型：1网络订单,2实体订单")
    private Integer type;

    @ApiModelProperty(value = "状态:1等待上门2等待评价3订单结束")
    private Integer status;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;
}
