package com.study.refuse_classification.controller;

import com.study.refuse_classification.entity.vo.SortingItemVO;
import com.study.refuse_classification.entity.vo.SubjectVO;
import com.study.refuse_classification.service.SortingItemService;
import com.study.refuse_classification.utils.PageUtils;
import com.study.refuse_classification.utils.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lin
 * @since 2021-02-16
 */
@RestController
@RequestMapping("/classification/item")
@CrossOrigin
public class SortingItemController {
    @Autowired
    private SortingItemService sortingItemService;

    //添加垃圾分类
    //获取上传过来的文件，把文件内容读取出来
    @PostMapping("/addItem")
    public ResponseEntity addItem(MultipartFile file){
        //上传过来excel文件
        sortingItemService.saveItem(file,sortingItemService);
        return ResponseEntity.success();
    }

    @GetMapping("getAllSubjects")
    public ResponseEntity getAllSubjects(){
        List<SubjectVO> list = sortingItemService.listWithTree();

        return ResponseEntity.success().data("list",list);
    }

    @GetMapping("getItemSort")
    public ResponseEntity getItemSort(@RequestParam(value = "name") String name){
        List<SortingItemVO> sortingItemVO=sortingItemService.getItemSortByName(name);
        return ResponseEntity.success().data("sortItem",sortingItemVO);
    }

    @GetMapping("popularSearch")
    public ResponseEntity getPopularSearch(){
        List<SortingItemVO> sortingItemVO=sortingItemService.getPopularSearch();
        return ResponseEntity.success().data("sortItem",sortingItemVO);
    }
}

