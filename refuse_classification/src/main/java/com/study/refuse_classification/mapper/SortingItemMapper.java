package com.study.refuse_classification.mapper;

import com.study.refuse_classification.entity.SortingItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lin
 * @since 2021-02-16
 */
public interface SortingItemMapper extends BaseMapper<SortingItem> {
    @Select("SELECT name FROM sorting_item WHERE id=#{Id}")
    String getItemName(@Param("Id")String Id);
}
