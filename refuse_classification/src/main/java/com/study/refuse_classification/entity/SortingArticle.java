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
 * @since 2021-03-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SortingArticle对象", description="")
public class SortingArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "点击量")
    private Integer pageviews;

    @ApiModelProperty(value = "详细内容")
    private String detail;

    @ApiModelProperty(value = "图片")
    private String pic;

    @ApiModelProperty(value = "视频")
    private String audio;

    @ApiModelProperty(value = "文章类型")
    private Integer type;

    @ApiModelProperty(value = "主页面展示")
    private Integer showIndex;

    private Date gmtCreate;

    private Date gmtModified;


}
