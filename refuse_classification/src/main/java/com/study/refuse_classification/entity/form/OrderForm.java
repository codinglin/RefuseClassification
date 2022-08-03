package com.study.refuse_classification.entity.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderForm {
    @ApiModelProperty(value = "订单类型：1网络订单,2实体订单")
    private Integer type;

    @ApiModelProperty(value = "公司ID")
    private Integer companyId;

    @ApiModelProperty(value = "用户id")
    private String userId;

    private String addressId;

    private Long anticipationTime;

    private BigDecimal evaluateWeight;

    private String itemDetail;
}
