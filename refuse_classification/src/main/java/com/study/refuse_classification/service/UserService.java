package com.study.refuse_classification.service;

import com.study.refuse_classification.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lin
 * @since 2021-02-25
 */
public interface UserService extends IService<User> {
    String getWxSession(String code,String nickName,String avatar);

    User getOpenIdMember(String openid);
}
