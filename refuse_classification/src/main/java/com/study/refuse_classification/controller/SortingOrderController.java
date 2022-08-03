package com.study.refuse_classification.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.refuse_classification.entity.SortingArticle;
import com.study.refuse_classification.entity.SortingOrder;
import com.study.refuse_classification.entity.form.OrderForm;
import com.study.refuse_classification.entity.form.OrderQuery;
import com.study.refuse_classification.entity.vo.OrderVO;
import com.study.refuse_classification.service.SortingOrderService;
import com.study.refuse_classification.utils.PageUtils;
import com.study.refuse_classification.utils.ResponseEntity;
import com.sun.org.apache.xpath.internal.operations.Or;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lin
 * @since 2021-04-09
 */
@RestController
@RequestMapping("/classification/sortingOrder")
@CrossOrigin
public class SortingOrderController {
    @Autowired
    private SortingOrderService sortingOrderService;

    @PostMapping("addOrder")
    public ResponseEntity addOrder(@RequestBody OrderForm orderForm){
        boolean flag = sortingOrderService.addOrder(orderForm);
        return flag?ResponseEntity.success():ResponseEntity.error();
    }

    @GetMapping("getAllOrder")
    public ResponseEntity getAllOrder(@ApiParam(name = "userId",required = true) @RequestParam("userId") String userId){
        List<OrderVO> orderVOS=sortingOrderService.getAllOrder(userId);
        return ResponseEntity.success().data("list",orderVOS);
    }

    //4.条件查询带分页的方法
    @PostMapping("/pageOrderCondition/{current}/{limit}")
    public ResponseEntity pageOrderCondition(@PathVariable long current,@PathVariable long limit,
                                               @RequestBody OrderQuery orderQuery) {
//        Map<String,Object> params = new HashMap<>();
//        params.put("current",String.valueOf(current));
//        params.put("limit",String.valueOf(limit));
//        params.put("orderQuery",orderQuery);
        Page<OrderVO> orderVOPage=new Page<>(current,limit);
        IPage<OrderVO> page= sortingOrderService.queryBaseAttrPage(orderVOPage,orderQuery);
//        PageUtils page = (PageUtils) sortingOrderService.queryBaseAttrPage(orderVOPage,params);
        return ResponseEntity.success().data("page",page);
    }

    //根据Id修改
    @ApiOperation(value = "接受订单")
    @PostMapping("/receiveOrder")
    public ResponseEntity receiveOrder(@RequestParam("id") String id){
        SortingOrder sortingOrder = sortingOrderService.getById(id);
        sortingOrder.setStatus(2);
        sortingOrderService.updateById(sortingOrder);
        return ResponseEntity.success();
    }

    @ApiOperation(value = "完成订单")
    @PostMapping("/completeOrder")
    public ResponseEntity receiveOrder(@RequestParam("id") String id, @RequestParam("factWeight") BigDecimal factWeight){
        SortingOrder sortingOrder = sortingOrderService.getById(id);
        sortingOrder.setStatus(3);
        sortingOrder.setFactWeight(factWeight);
        sortingOrderService.updateById(sortingOrder);
        return ResponseEntity.success();
    }
}

