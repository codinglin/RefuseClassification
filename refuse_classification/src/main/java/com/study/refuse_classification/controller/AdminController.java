package com.study.refuse_classification.controller;


import com.study.refuse_classification.entity.Admin;
import com.study.refuse_classification.entity.SortingPosition;
import com.study.refuse_classification.entity.form.LoginForm;
import com.study.refuse_classification.entity.form.RegisterForm;
import com.study.refuse_classification.service.AdminService;
import com.study.refuse_classification.service.SortingPositionService;
import com.study.refuse_classification.utils.JwtUtils;
import com.study.refuse_classification.utils.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lin
 * @since 2021-04-11
 */
@RestController
@RequestMapping("/classification/admin")
@CrossOrigin
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private SortingPositionService sortingPositionService;

    //登录
    @PostMapping("login")
    public ResponseEntity loginUser(@RequestBody LoginForm loginForm) {
        //member对象封装手机号和密码
        //调用service方法实现登录
        //返回token值，使用jwt生成
        String token = adminService.login(loginForm);
//        return ResponseEntity.success().data("token",token);
//        System.out.println(token);
        return ResponseEntity.success().data("token",token);
    }

    //注册
    @PostMapping("register")
    public ResponseEntity registerUser(@RequestBody RegisterForm registerForm) {
        adminService.register(registerForm);
        return ResponseEntity.success();
    }

    //根据token获取用户信息
    @GetMapping("getAdminInfo")
    public ResponseEntity getMemberInfo(HttpServletRequest request) {
        //调用jwt工具类的方法。根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
//        System.out.println(memberId);
        //查询数据库根据用户id获取用户信息
        Admin admin = adminService.getById(memberId);
//        SortingPosition sortingPosition = sortingPositionService.getById(admin.getCompanyId());
        List<String> roles=new ArrayList<>();
        roles.add("admin");
        return ResponseEntity.success().data("roles",roles).data("name",admin.getNickname()).data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif").data("introduction","I am a super administrator").data("adminId",memberId).data("companyId",admin.getCompanyId());
    }

    @PostMapping("logout")
    public ResponseEntity logout(){
        return ResponseEntity.success();
    }

}

