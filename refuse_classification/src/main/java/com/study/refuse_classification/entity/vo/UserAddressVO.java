package com.study.refuse_classification.entity.vo;

import lombok.Data;

@Data
public class UserAddressVO {
    private String id;

    private String name;

    private String tel;

    private String province;

    private String city;

    private String district;

    private String address;

    private Boolean isDefault;
}
