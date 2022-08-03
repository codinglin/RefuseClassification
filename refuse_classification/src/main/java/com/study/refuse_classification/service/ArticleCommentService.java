package com.study.refuse_classification.service;

import com.study.refuse_classification.entity.ArticleComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.refuse_classification.entity.vo.MyCommentVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lin
 * @since 2021-03-09
 */
public interface ArticleCommentService extends IService<ArticleComment> {

    List<MyCommentVO> getCommentByUserId(String userId);

    int getCommentCount(String userId);
}
