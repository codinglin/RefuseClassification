package com.study.refuse_classification.controller;


import com.study.refuse_classification.entity.UserAddress;
import com.study.refuse_classification.entity.form.AddressForm;
import com.study.refuse_classification.entity.form.AddressUpdateForm;
import com.study.refuse_classification.entity.vo.UserAddressVO;
import com.study.refuse_classification.service.UserAddressService;
import com.study.refuse_classification.utils.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lin
 * @since 2021-04-04
 */
@RestController
@RequestMapping("/classification/userAddress")
@CrossOrigin
public class UserAddressController {
    @Autowired
    private UserAddressService userAddressService;

    //添加地址
    @PostMapping("/addAddress")
    public ResponseEntity addArticle(@RequestBody AddressForm addressForm){
        UserAddress userAddress=new UserAddress();
        BeanUtils.copyProperties(addressForm,userAddress);
        boolean flag = userAddressService.save(userAddress);
        return flag?ResponseEntity.success():ResponseEntity.error();
    }

    //获取用户地址
    @GetMapping("/getAllAddress")
    public ResponseEntity getAllAddress(@RequestParam(value = "memberId") String memberId){
        List<UserAddressVO> userAddressVOS= userAddressService.getAllAddress(memberId);
        return ResponseEntity.success().data("list",userAddressVOS);
    }

    @PutMapping("/updateAddress")
    public ResponseEntity updateAddress(@RequestBody AddressUpdateForm addressUpdateForm){
        UserAddress userAddress=new UserAddress();
        System.out.println(addressUpdateForm.getId());
        BeanUtils.copyProperties(addressUpdateForm,userAddress);
        boolean flag = userAddressService.updateById(userAddress);
        return flag?ResponseEntity.success():ResponseEntity.error();
    }

    @DeleteMapping("/removeAddress")
    public ResponseEntity removeAddress(@ApiParam(name = "id",required = true) @RequestParam("id") String id){
        boolean flag = userAddressService.removeById(id);
        if(flag){
            return ResponseEntity.success();
        }else{
            return ResponseEntity.error();
        }
    }
}


