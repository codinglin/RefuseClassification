package com.study.refuse_classification.service;

import com.study.refuse_classification.entity.SortingQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.refuse_classification.entity.vo.SortQuestionVO;
import com.study.refuse_classification.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lin
 * @since 2021-03-05
 */
public interface SortingQuestionService extends IService<SortingQuestion> {

    List<SortQuestionVO> queryBaseQuestion();

    PageUtils queryBaseAttrPage(Map<String, Object> params);
}
