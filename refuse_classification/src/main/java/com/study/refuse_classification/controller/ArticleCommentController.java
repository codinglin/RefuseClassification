package com.study.refuse_classification.controller;


import com.study.refuse_classification.entity.ArticleComment;
import com.study.refuse_classification.entity.SortingQuestion;
import com.study.refuse_classification.entity.form.ArticleForm;
import com.study.refuse_classification.entity.form.CommentForm;
import com.study.refuse_classification.entity.vo.CommentVO;
import com.study.refuse_classification.entity.vo.MyCommentVO;
import com.study.refuse_classification.service.ArticleCommentService;
import com.study.refuse_classification.service.SortingArticleService;
import com.study.refuse_classification.utils.ResponseEntity;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lin
 * @since 2021-03-09
 */
@RestController
@RequestMapping("/classification/article-comment")
@CrossOrigin
public class ArticleCommentController {
    @Autowired
    private ArticleCommentService articleCommentService;

    @Autowired
    private SortingArticleService sortingArticleService;

    //1.添加评论
    @PostMapping("/addComment")
    public ResponseEntity addComment(@RequestBody CommentForm commentForm){
        boolean flag=false;
        List<CommentVO> commentVOS=null;
        if(StringUtils.hasLength(commentForm.getUserId())){
            ArticleComment articleComment = new ArticleComment();
            BeanUtils.copyProperties(commentForm, articleComment);
            flag = articleCommentService.save(articleComment);
            commentVOS = sortingArticleService.CommentListWithTree(commentForm.getArticleId());
        }
        return flag?ResponseEntity.success().data("commentVOS",commentVOS):ResponseEntity.error();
    }

    //根据用户ID获取评论
    @GetMapping("/getAllComment")
    public ResponseEntity getAllComment(@ApiParam(name = "userId",required = true) @RequestParam("userId") String userId){
//        int a=articleCommentService.getCommentCount(userId);
//        System.out.println(a);
        List<MyCommentVO> commentVOS=articleCommentService.getCommentByUserId(userId);
        return ResponseEntity.success().data("list",commentVOS);
    }
}

