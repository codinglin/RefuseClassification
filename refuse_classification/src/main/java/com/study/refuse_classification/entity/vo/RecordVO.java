package com.study.refuse_classification.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class RecordVO {
    private String id;

    private String parentId;

    private String name;

    private String category;

    private Date gmtCreate;
}
