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
 * @since 2021-02-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SortingItem对象", description="垃圾分类")
public class SortingItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "物品名")
    private String name;

    @ApiModelProperty(value = "点击数")
    private Integer count;

    @ApiModelProperty(value = "父ID")
    private String parentId;

    @ApiModelProperty(value = "描述")
    private String node;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;


}
