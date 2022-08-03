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
@ApiModel(value="ArticleComment对象", description="")
public class ArticleComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "文章ID")
    private String articleId;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "父ID")
    private String parentId;

    private Date gmtCreate;

    private Date gmtModified;


}
