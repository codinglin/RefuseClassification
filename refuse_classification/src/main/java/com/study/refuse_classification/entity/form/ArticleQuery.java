package com.study.refuse_classification.entity.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ArticleQuery {
    @ApiModelProperty(value = "标题,模糊查询")
    private String title;

    @ApiModelProperty(value = "主页面展示,0不显示，1显示在banner,2显示在通知")
    private Integer showIndex;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;
}
