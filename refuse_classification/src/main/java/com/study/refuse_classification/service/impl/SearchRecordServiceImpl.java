package com.study.refuse_classification.service.impl;

import com.study.refuse_classification.entity.SearchRecord;
import com.study.refuse_classification.entity.vo.NoticeVO;
import com.study.refuse_classification.entity.vo.RecordVO;
import com.study.refuse_classification.mapper.SearchRecordMapper;
import com.study.refuse_classification.mapper.SortingItemMapper;
import com.study.refuse_classification.service.SearchRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lin
 * @since 2021-04-06
 */
@Service
public class SearchRecordServiceImpl extends ServiceImpl<SearchRecordMapper, SearchRecord> implements SearchRecordService {
    @Resource
    private SearchRecordMapper searchRecordMapper;

    @Resource
    private SortingItemMapper sortingItemMapper;

    @Override
    public List<RecordVO> getRecordByUserId(String userId) {
        List<RecordVO> recordVOS=searchRecordMapper.getRecordByUserId(userId);
        recordVOS = recordVOS.stream().map(record -> {
            record.setCategory(sortingItemMapper.getItemName(record.getParentId()));
            return record;
        }).collect(Collectors.toList());
        return recordVOS;
    }
}
