package com.study.refuse_classification.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.refuse_classification.entity.SortingOrder;
import com.study.refuse_classification.entity.SortingQuestion;
import com.study.refuse_classification.entity.form.OrderForm;
import com.study.refuse_classification.entity.form.OrderQuery;
import com.study.refuse_classification.entity.form.QuestionQuery;
import com.study.refuse_classification.entity.vo.OrderVO;
import com.study.refuse_classification.enums.OrderStatusEnum;
import com.study.refuse_classification.mapper.SortingOrderMapper;
import com.study.refuse_classification.service.SortingOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.refuse_classification.utils.PageUtils;
import com.study.refuse_classification.utils.Query;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lin
 * @since 2021-04-09
 */
@Service
public class SortingOrderServiceImpl extends ServiceImpl<SortingOrderMapper, SortingOrder> implements SortingOrderService {

    @Resource
    private  SortingOrderMapper sortingOrderMapper;

    @Override
    public boolean addOrder(OrderForm orderForm) {
        SortingOrder sortingOrder=new SortingOrder();
        BeanUtils.copyProperties(orderForm,sortingOrder);
//        Timestamp timestamp=new Timestamp(orderForm.getAnticipationTime());
        Date time = new Date(orderForm.getAnticipationTime());
        sortingOrder.setAnticipationTime(time);
        sortingOrder.setStatus(OrderStatusEnum.WaitingToCome.getCode());
        boolean flag = save(sortingOrder);
        return flag;
    }

    @Override
    public List<OrderVO> getAllOrder(String userId) {
        List<OrderVO> orderVOS=sortingOrderMapper.getAllOrderByUserId(userId);
        return orderVOS;
    }

    @Override
    public IPage<OrderVO> queryBaseAttrPage(Page<OrderVO> page, OrderQuery orderQuery) {
        // mybatis学过 动态sql
//        OrderQuery orderQuery= (OrderQuery) params.get("orderQuery");
//        System.out.println(orderQuery.getCompanyId());
        return sortingOrderMapper.getAllOrderByOrderQuery(page,orderQuery);
    }
}
