package com.study.refuse_classification.service;

import com.study.refuse_classification.entity.SortingArticle;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.refuse_classification.entity.vo.ArticleVO;
import com.study.refuse_classification.entity.vo.CommentVO;
import com.study.refuse_classification.entity.vo.NoticeVO;
import com.study.refuse_classification.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lin
 * @since 2021-03-09
 */
public interface SortingArticleService extends IService<SortingArticle> {

    ArticleVO ArticleListWithTree(String id);

    List<CommentVO> CommentListWithTree(String id);

    List<NoticeVO> getNoticeByShowIndex(String showIndex);

    PageUtils queryBaseAttrPage(Map<String, Object> params);
}
