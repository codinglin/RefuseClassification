package com.study.refuse_classification.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.refuse_classification.entity.SortingOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.refuse_classification.entity.form.OrderForm;
import com.study.refuse_classification.entity.form.OrderQuery;
import com.study.refuse_classification.entity.vo.OrderVO;
import com.study.refuse_classification.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lin
 * @since 2021-04-09
 */
public interface SortingOrderService extends IService<SortingOrder> {

    boolean addOrder(OrderForm orderForm);

    List<OrderVO> getAllOrder(String userId);

    IPage<OrderVO> queryBaseAttrPage(Page<OrderVO> page, OrderQuery orderQuery);
}
