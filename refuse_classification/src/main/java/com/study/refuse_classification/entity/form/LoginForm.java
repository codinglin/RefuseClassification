package com.study.refuse_classification.entity.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginForm {
    @ApiModelProperty(value = "手机号")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;
}
