package com.study.refuse_classification.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.study.refuse_classification.config.WechatAuthProperties;
import com.study.refuse_classification.entity.User;
import com.study.refuse_classification.exception.RRException;
import com.study.refuse_classification.mapper.UserMapper;
import com.study.refuse_classification.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.refuse_classification.utils.HttpClientUtils;
import com.study.refuse_classification.utils.JwtUtils;
import com.study.refuse_classification.utils.MD5;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lin
 * @since 2021-02-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public String getWxSession(String code,String nickName,String avatar) {
        try {
            //1 获取code值，临时票据，类似于验证码
            //2 拿着code请求 微信固定的地址，得到两个值 accsess_token 和 openid
            String baseAccessTokenUrl = WechatAuthProperties.SESSION_Host+
                    "?appid=%s" +
                    "&secret=%s" +
                    "&js_code=%s" +
                    "&grant_type=authorization_code";
            //拼接三个参数 ：id  秘钥 和 code值
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    WechatAuthProperties.APP_ID,
                    WechatAuthProperties.APP_SECRET,
                    code
            );
            //使用httpclient发送请求，得到返回结果
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            //从accessTokenInfo字符串获取出来两个值 accsess_token 和 openid
            //把accessTokenInfo字符串转换map集合，根据map里面key获取对应值
            //使用json转换工具 Gson
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String session_key = (String)mapAccessToken.get("session_key");
            String openid = (String)mapAccessToken.get("openid");
            //String unionid = (String)mapAccessToken.get("unionid");
            //把扫描人信息添加数据库里面
            //判断数据表里面是否存在相同微信信息，根据openid判断
            User member = getOpenIdMember(openid);
            if(member == null) {//memeber是空，表没有相同微信数据，进行添加
                member =new User();
                member.setOpenid(openid);
                member.setNickname(nickName);
                member.setAvatar(avatar);
                member.setPassword(MD5.encrypt(session_key));
                save(member);
            }
            //使用jwt根据member对象生成token字符串
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            //最后：返回首页面，通过路径传递token字符串
            return jwtToken;
        }catch(Exception e) {
            throw new RRException("登录失败",20001);
        }
    }

    @Override
    public User getOpenIdMember(String openid) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        User user = baseMapper.selectOne(wrapper);
        return user;
    }
}
