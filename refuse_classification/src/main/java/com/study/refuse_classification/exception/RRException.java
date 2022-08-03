package com.study.refuse_classification.exception;

import com.study.refuse_classification.enums.ResultCodeEnum;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RRException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private Integer code = ResultCodeEnum.ERROR.getCode();

    public RRException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public RRException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public RRException(String msg, Integer code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public RRException(String msg, Integer code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }


}

