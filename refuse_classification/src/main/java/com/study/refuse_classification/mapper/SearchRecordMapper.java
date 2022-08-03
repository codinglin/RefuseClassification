package com.study.refuse_classification.mapper;

import com.study.refuse_classification.entity.SearchRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.refuse_classification.entity.vo.RecordVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lin
 * @since 2021-04-06
 */
public interface SearchRecordMapper extends BaseMapper<SearchRecord> {

    List<RecordVO> getRecordByUserId(String userId);
}
