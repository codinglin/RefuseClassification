package com.study.refuse_classification.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.study.refuse_classification.entity.SortingArticle;
import com.study.refuse_classification.entity.SortingQuestion;
import com.study.refuse_classification.entity.form.QuestionQuery;
import com.study.refuse_classification.entity.vo.OptionVO;
import com.study.refuse_classification.entity.vo.SortQuestionVO;
import com.study.refuse_classification.mapper.SortingQuestionMapper;
import com.study.refuse_classification.service.SortingQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.refuse_classification.utils.PageUtils;
import com.study.refuse_classification.utils.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lin
 * @since 2021-03-05
 */
@Service
public class SortingQuestionServiceImpl extends ServiceImpl<SortingQuestionMapper, SortingQuestion> implements SortingQuestionService {

    @Override
    public List<SortQuestionVO> queryBaseQuestion() {
        List<SortingQuestion> sortingQuestions=list();
//        for (SortingQuestion i:sortingQuestions){
//            System.out.println(i.getStem());
//        }
        AtomicInteger i= new AtomicInteger(0);
        List<SortQuestionVO> sortQuestionVOS = sortingQuestions.stream().map(sortItem -> {
            SortQuestionVO sortQuestionVO = new SortQuestionVO();
            BeanUtils.copyProperties(sortItem, sortQuestionVO);
            List<OptionVO> list=new ArrayList<>();
            sortQuestionVO.setNo(i.incrementAndGet());
            OptionVO optionVO;
            if(StringUtils.hasLength(sortItem.getContenta())){
                optionVO=new OptionVO(sortItem.getContenta(),"A");
                list.add(optionVO);
            }
            if(StringUtils.hasLength(sortItem.getContentb())){
                optionVO=new OptionVO(sortItem.getContentb(),"B");
                list.add(optionVO);
            }
            if(StringUtils.hasLength(sortItem.getContentc())){
                optionVO=new OptionVO(sortItem.getContentc(),"C");
                list.add(optionVO);
            }
            if(StringUtils.hasLength(sortItem.getContentd())){
                optionVO=new OptionVO(sortItem.getContentd(),"D");
                list.add(optionVO);
            }
            sortQuestionVO.setOptions(list);
            return sortQuestionVO;
        }).collect(Collectors.toList());
        return sortQuestionVOS;
    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params) {
        //构建条件
        QueryWrapper<SortingQuestion> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        // mybatis学过 动态sql
        QuestionQuery questionQuery= (QuestionQuery) params.get("questionQuery");
        String name = questionQuery.getStem();
        String begin = questionQuery.getBegin();
        String end = questionQuery.getEnd();
        //判断条件值是否为空，如果不为空拼接条件
        if(StringUtils.hasLength(name)) {
            //构建条件
            wrapper.like("stem",name);
        }
        if(StringUtils.hasLength(begin)) {
            wrapper.ge("gmt_create",begin);
        }
        if(StringUtils.hasLength(end)) {
            wrapper.le("gmt_create",end);
        }
        //排序
        wrapper.orderByDesc("gmt_create");
        IPage<SortingQuestion> page = this.page(
                new Query<SortingQuestion>().getPage(params),
                wrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        List<SortingQuestion> records = page.getRecords(); //数据list集合
        pageUtils.setList(records);
        return pageUtils;
    }
}
