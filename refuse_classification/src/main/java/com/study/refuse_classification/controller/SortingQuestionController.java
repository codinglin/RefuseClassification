package com.study.refuse_classification.controller;


import com.study.refuse_classification.entity.SortingQuestion;
import com.study.refuse_classification.entity.form.QuestionQuery;
import com.study.refuse_classification.entity.vo.SortQuestionVO;
import com.study.refuse_classification.service.SortingItemService;
import com.study.refuse_classification.service.SortingQuestionService;
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
 * @since 2021-03-05
 */
@RestController
@RequestMapping("/classification/sorting-question")
@CrossOrigin
public class SortingQuestionController {

    @Autowired
    private SortingQuestionService sortingQuestionService;

    // 查询问题
    @GetMapping("/pageQuestion")
    public ResponseEntity pageQuestion(){
        List<SortQuestionVO> sortQuestionVOS = sortingQuestionService.queryBaseQuestion();
        return ResponseEntity.success().data("questions",sortQuestionVOS);
    }

    // 添加问题
    @PostMapping("/addQuestion")
    public ResponseEntity addQuestion(@RequestBody SortingQuestion sortingQuestion){
        boolean flag = sortingQuestionService.save(sortingQuestion);
        return flag?ResponseEntity.success():ResponseEntity.error();
    }

    //2.逻辑删除的方法
    @ApiOperation(value = "根据ID删除问题")
    @DeleteMapping("/{id}")
    public ResponseEntity removeQuestion(@ApiParam(name = "id",value = "问题ID",required = true) @PathVariable("id") String id){
        boolean flag = sortingQuestionService.removeById(id);
        if(flag){
            return ResponseEntity.success();
        }else{
            return ResponseEntity.error();
        }
    }

    //4.条件查询带分页的方法
    @PostMapping("/pageQuestionCondition/{current}/{limit}")
    public ResponseEntity pageQuestionCondition(@PathVariable long current,@PathVariable long limit,
                                               @RequestBody(required = false) QuestionQuery questionQuery) {
        Map<String,Object> params = new HashMap<>();
        params.put("current",String.valueOf(current));
        params.put("limit",String.valueOf(limit));
        params.put("questionQuery",questionQuery);
        PageUtils page = sortingQuestionService.queryBaseAttrPage(params);
        return ResponseEntity.success().data("page",page);
    }

    //根据id进行查询
    @GetMapping("/getQuestion/{id}")
    public ResponseEntity getQuestion(@PathVariable String id) {
        SortingQuestion sortingQuestion = sortingQuestionService.getById(id);
        return ResponseEntity.success().data("question",sortingQuestion);
    }

    //修改功能
    @PostMapping("/updateQuestion")
    public ResponseEntity updateQuestion(@RequestBody SortingQuestion sortingQuestion) {
        boolean flag = sortingQuestionService.updateById(sortingQuestion);
        return flag?ResponseEntity.success():ResponseEntity.error();
    }

    //根据Id修改
    @ApiOperation(value = "根据ID修改")
    @PutMapping("/{id}")
    public ResponseEntity updateById(@PathVariable("id") String id,
                                     @RequestBody SortingQuestion question){
        SortingQuestion sortingQuestion = sortingQuestionService.getById(id);
        BeanUtils.copyProperties(question,sortingQuestion);
        sortingQuestionService.updateById(sortingQuestion);
        return ResponseEntity.success();
    }
}

