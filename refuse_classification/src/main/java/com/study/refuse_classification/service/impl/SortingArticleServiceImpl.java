package com.study.refuse_classification.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.study.refuse_classification.entity.ArticleComment;
import com.study.refuse_classification.entity.SortingArticle;
import com.study.refuse_classification.entity.User;
import com.study.refuse_classification.entity.form.ArticleQuery;
import com.study.refuse_classification.entity.vo.ArticleVO;
import com.study.refuse_classification.entity.vo.CommentVO;
import com.study.refuse_classification.entity.vo.NoticeVO;
import com.study.refuse_classification.mapper.ArticleCommentMapper;
import com.study.refuse_classification.mapper.SortingArticleMapper;
import com.study.refuse_classification.mapper.UserMapper;
import com.study.refuse_classification.service.SortingArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.refuse_classification.utils.PageUtils;
import com.study.refuse_classification.utils.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lin
 * @since 2021-03-09
 */
@Service
public class SortingArticleServiceImpl extends ServiceImpl<SortingArticleMapper, SortingArticle> implements SortingArticleService {
    @Resource
    private ArticleCommentMapper articleCommentMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public ArticleVO ArticleListWithTree(String id) {
        SortingArticle sortingArticle = getById(id);
        ArticleVO articleVO=new ArticleVO();
        sortingArticle.setPageviews(sortingArticle.getPageviews()+1);
        updateById(sortingArticle);
        BeanUtils.copyProperties(sortingArticle,articleVO);
        List<ArticleComment> articleComments = getComments(sortingArticle.getId(),articleVO);
        List<CommentVO> commentVOS=listWithTree(articleComments);
        articleVO.setCommentVOS(commentVOS);
        return articleVO;
    }

    @Override
    public List<CommentVO> CommentListWithTree(String id) {
        QueryWrapper<ArticleComment> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id",id);
        List<ArticleComment> articleComments= articleCommentMapper.selectList(wrapper);
        List<CommentVO> commentVOS=listWithTree(articleComments);
        return  commentVOS;
    }

    @Override
    public List<NoticeVO> getNoticeByShowIndex(String showIndex) {
        QueryWrapper<SortingArticle> wrapper = new QueryWrapper<>();
        wrapper.eq("show_index",showIndex);
        List<SortingArticle> sortingArticles= baseMapper.selectList(wrapper);
        List<NoticeVO> noticeVOS = sortingArticles.stream().map(comment -> {
            NoticeVO noticeVO = new NoticeVO();
            BeanUtils.copyProperties(comment, noticeVO);
            return noticeVO;
        }).collect(Collectors.toList());
        return noticeVOS;
    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params) {
        //构建条件
        QueryWrapper<SortingArticle> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        // mybatis学过 动态sql
        ArticleQuery articleQuery= (ArticleQuery) params.get("articleQuery");
        String name = articleQuery.getTitle();
        Integer level = articleQuery.getShowIndex();
        String begin = articleQuery.getBegin();
        String end = articleQuery.getEnd();
        //判断条件值是否为空，如果不为空拼接条件
        if(StringUtils.hasLength(name)) {
            //构建条件
            wrapper.like("title",name);
        }
        if(!StringUtils.isEmpty(level)) {
            wrapper.eq("show_index",level);
        }
        if(StringUtils.hasLength(begin)) {
            wrapper.ge("gmt_create",begin);
        }
        if(StringUtils.hasLength(end)) {
            wrapper.le("gmt_create",end);
        }
        //排序
        wrapper.orderByDesc("gmt_create");
        IPage<SortingArticle> page = this.page(
                new Query<SortingArticle>().getPage(params),
                wrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        List<SortingArticle> records = page.getRecords(); //数据list集合
        pageUtils.setList(records);
        return pageUtils;
    }

    private List<ArticleComment> getComments(String articleId,ArticleVO articleVO){
        QueryWrapper<ArticleComment> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id",articleId);
        List<ArticleComment> articleComments= articleCommentMapper.selectList(wrapper);
        articleVO.setCommentCount(articleComments.size());
        return articleComments;
    }

    public List<CommentVO> listWithTree(List<ArticleComment> entities) {
        List<CommentVO> commentVOS = entities.stream().map(comment -> {
            CommentVO commentVO = new CommentVO();
            User user=userMapper.selectById(comment.getUserId());
            BeanUtils.copyProperties(comment, commentVO);
            commentVO.setNickName(user.getNickname());
            commentVO.setUserPic(user.getAvatar());
            return commentVO;
        }).collect(Collectors.toList());

        List<CommentVO> list = commentVOS.stream().filter(commentVO ->
                commentVO.getParentId().equals("0")
        ).map((menu)->{
            menu.setChildren(getChildrenData(menu,commentVOS));
            return menu;
        }).collect(Collectors.toList());
        return list;
    }

    private List<CommentVO> getChildrenData(CommentVO root, List<CommentVO> all) {
        List<CommentVO> children = all.stream().filter(commentVO ->
                commentVO.getParentId().equals(root.getId())
        ).map(commentVO -> {
            commentVO.setChildren(getChildrenData(commentVO,all));
            return commentVO;
        }).collect(Collectors.toList());
        return children;
    }
}
