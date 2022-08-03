package com.study.refuse_classification.controller;


import com.study.refuse_classification.entity.SearchRecord;
import com.study.refuse_classification.entity.form.SearchRecordForm;
import com.study.refuse_classification.entity.vo.RecordVO;
import com.study.refuse_classification.service.SearchRecordService;
import com.study.refuse_classification.utils.ResponseEntity;
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
 * @since 2021-04-06
 */
@RestController
@RequestMapping("/classification/search-record")
@CrossOrigin
public class SearchRecordController {
    @Autowired
    private SearchRecordService searchRecordService;

    @PostMapping("addRecord")
    public ResponseEntity addRecord(@RequestBody SearchRecordForm searchRecordForm){
        SearchRecord searchRecord=new SearchRecord();
        BeanUtils.copyProperties(searchRecordForm,searchRecord);
        boolean flag = searchRecordService.save(searchRecord);
        return flag?ResponseEntity.success():ResponseEntity.error();
    }
    @GetMapping("getRecordByUserId")
    public ResponseEntity getRecordByUserId(@RequestParam("userId") String userId){
        List<RecordVO> recordVOS=searchRecordService.getRecordByUserId(userId);
        return ResponseEntity.success().data("list",recordVOS);
    }
}

