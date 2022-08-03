package com.study.refuse_classification.entity.form;

import lombok.Data;

@Data
public class AddressUpdateForm {
    private String id;

    private String userId;

    private String name;

    private String tel;

    private String province;

    private String city;

    private String district;

    private String address;

    private Boolean isDefault;
}
