package com.study.refuse_classification.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderVO {

    private String id;

    @ApiModelProperty(value = "订单类型：1网络订单,2实体订单")
    private Integer type;

    @ApiModelProperty(value = "公司ID")
    private Integer companyId;

    private String addressName;

    @ApiModelProperty(value = "地址")
    private String companyAddress;

    private String addressId;

    private String userName;

    private String tel;

    private String province;

    private String city;

    private String district;

    private String address;

    @ApiModelProperty(value = "状态:1等待上门2等待评价3订单结束")
    private Integer status;

    private Date anticipationTime;

    private BigDecimal evaluateWeight;

    private String itemDetail;

    private BigDecimal factWeight;

    private Date gmtCreate;
}
