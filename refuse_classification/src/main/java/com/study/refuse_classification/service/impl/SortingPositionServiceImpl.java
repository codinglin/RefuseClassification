package com.study.refuse_classification.service.impl;

import com.study.refuse_classification.entity.SortingPosition;
import com.study.refuse_classification.entity.vo.PositionVO;
import com.study.refuse_classification.mapper.SortingPositionMapper;
import com.study.refuse_classification.service.SortingPositionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lin
 * @since 2021-03-13
 */
@Service
public class SortingPositionServiceImpl extends ServiceImpl<SortingPositionMapper, SortingPosition> implements SortingPositionService {

    @Override
    public List<PositionVO> getAllPositions() {
        List<SortingPosition> sortingPositions=list();
        List<PositionVO> positionVOS = sortingPositions.stream().map(position -> {
            PositionVO positionVO = new PositionVO();
            BeanUtils.copyProperties(position, positionVO);
            positionVO.setJoinCluster(position.getJoinCluster()==1?"true":"false");
            return positionVO;
        }).collect(Collectors.toList());
        return positionVOS;
    }
}
