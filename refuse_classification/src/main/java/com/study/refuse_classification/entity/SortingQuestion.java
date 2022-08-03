package com.study.refuse_classification.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2021-03-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SortingQuestion对象", description="")
public class SortingQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "正确选项")
    private String answer;

    @ApiModelProperty(value = "题干")
    private String stem;

    @ApiModelProperty(value = "选项A")
    @TableField("contentA")
    private String contenta;

    @ApiModelProperty(value = "选项B")
    @TableField("contentB")
    private String contentb;

    @ApiModelProperty(value = "选项C")
    @TableField("contentC")
    private String contentc;

    @ApiModelProperty(value = "选项D")
    @TableField("contentD")
    private String contentd;

    @ApiModelProperty(value = "图片")
    private String img;

    private Date gmtCreate;

    private Date gmtModified;


}
