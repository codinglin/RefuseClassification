package com.study.refuse_classification.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.refuse_classification.entity.Admin;
import com.study.refuse_classification.entity.form.LoginForm;
import com.study.refuse_classification.entity.form.RegisterForm;
import com.study.refuse_classification.exception.RRException;
import com.study.refuse_classification.mapper.AdminMapper;
import com.study.refuse_classification.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.refuse_classification.utils.JwtUtils;
import com.study.refuse_classification.utils.MD5;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lin
 * @since 2021-04-11
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Override
    public void register(RegisterForm registerForm) {
        //获取注册的数据
        String code = registerForm.getCode(); //验证码
        String mobile = registerForm.getPhone(); //手机号
        String nickname = registerForm.getNickname(); //昵称
        String password = registerForm.getPassword(); //密码

        //非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(nickname)) {
            throw new RRException("注册失败",20001);
        }

        //判断验证码
        //获取redis验证码
//        String redisCode = redisTemplate.opsForValue().get(mobile);
//        if(!code.equals(redisCode)) {
//            throw new RRException("注册失败",20001);
//        }
        //判断手机号是否重复，表里面存在相同手机号不进行添加
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("phone",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0) {
            throw new RRException("注册失败",20001);
        }

        //数据添加数据库中
        Admin admin = new Admin();
        admin.setPhone(mobile);
        admin.setNickname(nickname);
        admin.setPassword(MD5.encrypt(password));//密码需要加密的
        admin.setIsDisabled(false);//用户不禁用
        admin.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(admin);
    }

    @Override
    public String login(LoginForm loginForm) {
        //获取登录手机号和密码
        String mobile = loginForm.getUsername();
        String password = loginForm.getPassword();

//        System.out.println(mobile);
//        System.out.println(password);
        //手机号和密码非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new RRException("登录失败",20001);
        }

        //判断手机号是否正确
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("phone",mobile);
        Admin admin = baseMapper.selectOne(wrapper);
        //判断查询对象是否为空
        if(admin == null) {//没有这个手机号
            throw new RRException("登录失败",20001);
        }

        //判断密码
        //因为存储到数据库密码肯定加密的
        //把输入的密码进行加密，再和数据库密码进行比较
        //加密方式 MD5
        if(!MD5.encrypt(password).equals(admin.getPassword())) {
            throw new RRException("登录失败",20001);
        }

        //判断用户是否禁用
        if(admin.getIsDisabled()) {
            throw new RRException("登录失败",20001);
        }

        //登录成功
        //生成token字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(admin.getId(), admin.getNickname());
        return jwtToken;
    }
}
