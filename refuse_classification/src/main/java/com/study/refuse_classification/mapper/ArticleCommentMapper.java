package com.study.refuse_classification.mapper;

import com.study.refuse_classification.entity.ArticleComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.refuse_classification.entity.vo.MyCommentVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lin
 * @since 2021-03-09
 */
public interface ArticleCommentMapper extends BaseMapper<ArticleComment> {
    List<MyCommentVO> getAllCommentById(@Param("userId")String userId);

    @Select("SELECT COUNT(*) FROM article_comment WHERE user_id=#{userId}")
    Integer getCommentCount(@Param("userId")String userId);
}
