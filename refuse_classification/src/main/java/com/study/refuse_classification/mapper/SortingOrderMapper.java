package com.study.refuse_classification.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.refuse_classification.entity.SortingOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.refuse_classification.entity.form.OrderQuery;
import com.study.refuse_classification.entity.vo.OrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lin
 * @since 2021-04-09
 */
public interface SortingOrderMapper extends BaseMapper<SortingOrder> {

    List<OrderVO> getAllOrderByUserId(String userId);

    IPage<OrderVO> getAllOrderByOrderQuery(Page<?> page,@Param("orderQuery") OrderQuery orderQuery);
}
