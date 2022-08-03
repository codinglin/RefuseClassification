package com.study.refuse_classification.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author lin
 * @since 2021-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SortingOrder对象", description="")
public class SortingOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "订单类型：1网络订单,2实体订单")
    private Integer type;

    @ApiModelProperty(value = "公司ID")
    private Integer companyId;

    @ApiModelProperty(value = "用户id")
    private String userId;

    private String addressId;

    @ApiModelProperty(value = "状态:1等待上门2等待评价3订单结束")
    private Integer status;

    private Date anticipationTime;

    private BigDecimal evaluateWeight;

    private String itemDetail;

    private BigDecimal factWeight;

    private Date gmtCreate;

    private Date gmtModified;


}
