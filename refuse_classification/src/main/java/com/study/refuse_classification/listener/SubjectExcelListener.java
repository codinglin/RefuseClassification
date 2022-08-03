package com.study.refuse_classification.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.refuse_classification.entity.SortingItem;
import com.study.refuse_classification.entity.excel.SubjectData;
import com.study.refuse_classification.enums.ResultCodeEnum;
import com.study.refuse_classification.exception.RRException;
import com.study.refuse_classification.service.SortingItemService;


public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    //因为SubjectExcelListener不能交给spring管理，需要自己new，不能注入其他对象
    //不能实现数据库操作
    public SortingItemService sortingItemService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(SortingItemService sortingItemService) {
        this.sortingItemService = sortingItemService;
    }

    //读取excel内容，一行一行读取
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null){
            throw new RRException("文件数据为空", ResultCodeEnum.ERROR.getCode());
        }
        //一行一行读取，每次读取有两个值，第一个值为一级分类，第二个值为二级分类
        //判断一级分类是否重复
        SortingItem existOneSubject = existOneSubject(subjectData.getOneSubjectName());
        if(existOneSubject == null){ //没有相同一级分类，进行添加
            existOneSubject = new SortingItem();
            existOneSubject.setParentId("0");
            existOneSubject.setName(subjectData.getOneSubjectName());//一级分类名称
            sortingItemService.save(existOneSubject);
        }
        String pid=existOneSubject.getId();
        //添加二级分类
        //判断二级分类是否重复
        SortingItem existTwoSubject = existTwoSubject(subjectData.getTwoSubjectName(),pid);
        if(existTwoSubject == null){ //没有相同二级分类，进行添加
            existTwoSubject = new SortingItem();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setName(subjectData.getTwoSubjectName());//二级分类名称
            sortingItemService.save(existTwoSubject);
        }
    }

    //判断一级分类不能重复添加
    private SortingItem existOneSubject(String name){
        QueryWrapper<SortingItem> wrapper=new QueryWrapper<>();
        wrapper.eq("name",name);
        wrapper.eq("parent_id","0");
        SortingItem oneSubject=sortingItemService.getOne(wrapper);
        return oneSubject;
    }

    //判断二级分类不能重复添加
    private SortingItem existTwoSubject(String name,String pid){
        QueryWrapper<SortingItem> wrapper=new QueryWrapper<>();
        wrapper.eq("name",name);
        wrapper.eq("parent_id",pid);
        SortingItem twoSubject=sortingItemService.getOne(wrapper);
        return twoSubject;
    }


    //判断二级分类不能重复添加

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}