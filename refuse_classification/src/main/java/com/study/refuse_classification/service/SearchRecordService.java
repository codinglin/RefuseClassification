package com.study.refuse_classification.service;

import com.study.refuse_classification.entity.SearchRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.refuse_classification.entity.vo.RecordVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lin
 * @since 2021-04-06
 */
public interface SearchRecordService extends IService<SearchRecord> {

    List<RecordVO> getRecordByUserId(String userId);
}
