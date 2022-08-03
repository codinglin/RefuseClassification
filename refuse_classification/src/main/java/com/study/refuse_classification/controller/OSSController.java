package com.study.refuse_classification.controller;

import com.study.refuse_classification.service.OSSService;
import com.study.refuse_classification.utils.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OSSController {

    @Autowired
    private OSSService ossService;

    //上传图片
    @PostMapping
    public ResponseEntity uploadOssFile(MultipartFile file){
        String url = ossService.uploadFileAvatar(file);
        return ResponseEntity.success().data("url",url);
    }
}
