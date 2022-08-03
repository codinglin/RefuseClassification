package com.study.refuse_classification.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class SubjectVO {
    private String id;
    private String parentId;
    private String title;
   // private Integer sort;
    private List<SubjectVO> children;
}
