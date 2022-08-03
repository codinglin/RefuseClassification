package com.study.refuse_classification.service;

import com.study.refuse_classification.entity.SortingPosition;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.refuse_classification.entity.vo.PositionVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lin
 * @since 2021-03-13
 */
public interface SortingPositionService extends IService<SortingPosition> {

    List<PositionVO> getAllPositions();
}
