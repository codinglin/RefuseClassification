package com.study.refuse_classification.enums;

public enum ResultCodeEnum {
    SUCCESS(20000,"成功"),
    ERROR(20001,"失败");

    private Integer code;
    private String label;

    public Integer getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }
    private ResultCodeEnum(Integer code, String label) {
        this.code=code;
        this.label=label;
    }
}
