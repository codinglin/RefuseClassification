package com.study.refuse_classification.entity;

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
 * @since 2021-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SortingPosition对象", description="")
public class SortingPosition implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String latitude;

    private String longitude;

    @ApiModelProperty(value = "地址")
    private String address;

    private Integer joinCluster;

    private Integer width;

    private Integer height;

    private String iconPath;

    private Date gmtCreate;

    private Date gmtModified;


}
