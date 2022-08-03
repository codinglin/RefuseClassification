package com.study.refuse_classification.controller;


import com.study.refuse_classification.entity.User;
import com.study.refuse_classification.service.UserService;
import com.study.refuse_classification.utils.JwtUtils;
import com.study.refuse_classification.utils.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lin
 * @since 2021-02-25
 */
@RestController
@RequestMapping("/classification/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;
    //登录与注册
    @PostMapping("login")
    public ResponseEntity loginUser(@RequestParam(value = "code") String code,@RequestParam(value="nickName")String nickName,@RequestParam(value="avatar")String avatar) {
        //返回token值，使用jwt生成
        System.out.println(code);
        String token = userService.getWxSession(code,nickName,avatar);
        return ResponseEntity.success().data("token",token);
    }
    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public ResponseEntity getMemberInfo(HttpServletRequest request) {
        //调用jwt工具类的方法。根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据用户id获取用户信息
        //User member = userService.getById(memberId);
        //System.out.println(memberId);
        return ResponseEntity.success().data("memberId",memberId);
    }
}

