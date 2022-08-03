package com.study.refuse_classification.entity.excel;

import lombok.Data;

import java.util.List;

//一级分类
@Data
public class OneSubject {
    private String id;
    private String title;
    //一个一级分类有多个二级分类
    private List<TwoSubject> children;
}
