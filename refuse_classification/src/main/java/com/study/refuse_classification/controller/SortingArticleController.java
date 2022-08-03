package com.study.refuse_classification.controller;

import com.study.refuse_classification.entity.ArticleComment;
import com.study.refuse_classification.entity.SortingArticle;
import com.study.refuse_classification.entity.form.ArticleForm;
import com.study.refuse_classification.entity.form.ArticleQuery;
import com.study.refuse_classification.entity.vo.ArticleVO;
import com.study.refuse_classification.entity.vo.NoticeVO;
import com.study.refuse_classification.service.SortingArticleService;
import com.study.refuse_classification.utils.PageUtils;
import com.study.refuse_classification.utils.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lin
 * @since 2021-03-09
 */
@RestController
@RequestMapping("/classification/sortingArticle")
@CrossOrigin
public class SortingArticleController {
    @Autowired
    private SortingArticleService sortingArticleService;

    @GetMapping("getArticleById")
    public ResponseEntity getAllSubjects(@RequestParam(value = "Id") String Id){
        ArticleVO articleVO = sortingArticleService.ArticleListWithTree(Id);
        return ResponseEntity.success().data("article",articleVO);
    }

    @GetMapping("getAllArticle")
    public ResponseEntity getAllArticle(){
        List<SortingArticle> sortingArticles=sortingArticleService.list();
        return ResponseEntity.success().data("list",sortingArticles);
    }

    //获取通知栏消息
    @GetMapping("getNotice")
    public ResponseEntity getNotice(@RequestParam(value = "showIndex") String showIndex){
        List<NoticeVO> noticeVOS = sortingArticleService.getNoticeByShowIndex(showIndex);
        return ResponseEntity.success().data("noticeVOS",noticeVOS);
    }

    //添加文章
    @PostMapping("/addArticle")
    public ResponseEntity addArticle(@RequestBody ArticleForm articleForm){
        SortingArticle sortingArticle=new SortingArticle();
        BeanUtils.copyProperties(articleForm,sortingArticle);
        boolean flag = sortingArticleService.save(sortingArticle);
        return flag?ResponseEntity.success():ResponseEntity.error();
    }

    @PostMapping("/pageArticleCondition/{current}/{limit}")
    public ResponseEntity pageTeacherCondition(@PathVariable long current,@PathVariable long limit,
                                               @RequestBody(required = false) ArticleQuery articleQuery) {
        Map<String,Object> params = new HashMap<>();
        params.put("current",String.valueOf(current));
        params.put("limit",String.valueOf(limit));
        params.put("articleQuery",articleQuery);
        PageUtils page = sortingArticleService.queryBaseAttrPage(params);
        return ResponseEntity.success().data("page",page);
    }

    //根据Id修改
    @ApiOperation(value = "根据ID修改文章")
    @PutMapping("/{id}")
    public ResponseEntity updateById(@PathVariable("id") String id,
                                     @RequestBody SortingArticle article){
        SortingArticle sortingArticle = sortingArticleService.getById(id);
        BeanUtils.copyProperties(article,sortingArticle);
        sortingArticleService.updateById(sortingArticle);
        return ResponseEntity.success();
    }

    //根据讲师id进行查询
    @GetMapping("/getArticle/{id}")
    public ResponseEntity getArticle(@PathVariable String id) {
        SortingArticle sortingArticle = sortingArticleService.getById(id);
        return ResponseEntity.success().data("article",sortingArticle);
    }

    @ApiOperation(value = "根据ID删除文章")
    @DeleteMapping("/{id}")
    public ResponseEntity removeArticle(@ApiParam(name = "id",value = "文章ID",required = true) @PathVariable("id") String id){
        boolean flag = sortingArticleService.removeById(id);
        if(flag){
            return ResponseEntity.success();
        }else{
            return ResponseEntity.error();
        }
    }

    @PostMapping("/updateArticle")
    public ResponseEntity updateArticle(@RequestBody SortingArticle sortingArticle) {
        boolean flag = sortingArticleService.updateById(sortingArticle);
        return flag?ResponseEntity.success():ResponseEntity.error();
    }
}

