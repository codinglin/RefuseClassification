package com.study.refuse_classification.enums;

public enum OrderTypeEnum {
    OnlineOrder(1,"网络订单"),
    OfflineOrder(2,"实体订单");

    private Integer code;
    private String label;

    public Integer getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    private OrderTypeEnum(Integer code, String label) {
        this.code=code;
        this.label=label;
    }
}
