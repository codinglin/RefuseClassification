package com.study.refuse_classification.service;

import com.study.refuse_classification.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.refuse_classification.entity.form.LoginForm;
import com.study.refuse_classification.entity.form.RegisterForm;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lin
 * @since 2021-04-11
 */
public interface AdminService extends IService<Admin> {

    void register(RegisterForm registerForm);

    String login(LoginForm loginForm);
}
