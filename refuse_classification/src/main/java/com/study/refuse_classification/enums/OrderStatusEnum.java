package com.study.refuse_classification.enums;

public enum OrderStatusEnum {
    WaitingToCome(1,"等待上门"),
    WaitingForEvaluation(2,"等待评价"),
    EndOfOrder(3,"订单结束");

    private Integer code;
    private String label;

    public Integer getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    private OrderStatusEnum(Integer code, String label) {
        this.code=code;
        this.label=label;
    }
}
