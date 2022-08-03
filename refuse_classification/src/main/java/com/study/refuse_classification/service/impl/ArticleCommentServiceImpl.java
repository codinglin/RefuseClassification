package com.study.refuse_classification.service.impl;

import com.study.refuse_classification.entity.ArticleComment;
import com.study.refuse_classification.entity.vo.MyCommentVO;
import com.study.refuse_classification.mapper.ArticleCommentMapper;
import com.study.refuse_classification.service.ArticleCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lin
 * @since 2021-03-09
 */
@Service
public class ArticleCommentServiceImpl extends ServiceImpl<ArticleCommentMapper, ArticleComment> implements ArticleCommentService {
    @Resource
    private ArticleCommentMapper articleCommentMapper;

    @Override
    public List<MyCommentVO> getCommentByUserId(String userId) {
        return articleCommentMapper.getAllCommentById(userId);
    }

    @Override
    public int getCommentCount(String userId) {
        return articleCommentMapper.getCommentCount(userId);
    }
}
