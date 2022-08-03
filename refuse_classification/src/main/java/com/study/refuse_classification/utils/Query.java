package com.study.refuse_classification.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.refuse_classification.xss.SQLFilter;
import org.springframework.util.StringUtils;

import java.util.Map;

public class Query<T> {

    public IPage<T> getPage(Map<String, Object> params) {
        return this.getPage(params, null, false);
    }

    public IPage<T> getPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
        //分页参数
        long curPage = 1;
        long limit = 10;

        if(params.get(com.study.refuse_classification.utils.Constant.PAGE) != null){
            curPage = Long.parseLong((String)params.get(com.study.refuse_classification.utils.Constant.PAGE));
        }
        if(params.get(com.study.refuse_classification.utils.Constant.LIMIT) != null){
            limit = Long.parseLong((String)params.get(com.study.refuse_classification.utils.Constant.LIMIT));
        }

        //分页对象
        Page<T> page = new Page<>(curPage, limit);

        //分页参数
        params.put(com.study.refuse_classification.utils.Constant.PAGE, page);

        //排序字段
        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String orderField = SQLFilter.sqlInject((String)params.get(com.study.refuse_classification.utils.Constant.ORDER_FIELD));
        String order = (String)params.get(com.study.refuse_classification.utils.Constant.ORDER);


        //前端字段排序
        if(StringUtils.hasLength(orderField) && StringUtils.hasLength(order)){
            if(com.study.refuse_classification.utils.Constant.ASC.equalsIgnoreCase(order)) {
                return  page.addOrder(OrderItem.asc(orderField));
            }else {
                return page.addOrder(OrderItem.desc(orderField));
            }
        }

        //没有排序字段，则不排序
        if(!StringUtils.hasLength(defaultOrderField)){
            return page;
        }

        //默认排序
        if(isAsc) {
            page.addOrder(OrderItem.asc(defaultOrderField));
        }else {
            page.addOrder(OrderItem.desc(defaultOrderField));
        }

        return page;
    }
}