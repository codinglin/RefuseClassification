package com.study.refuse_classification.service;

import org.springframework.web.multipart.MultipartFile;

public interface OSSService {
    String uploadFileAvatar(MultipartFile file);
}
