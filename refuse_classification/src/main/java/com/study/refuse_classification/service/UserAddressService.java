package com.study.refuse_classification.service;

import com.study.refuse_classification.entity.UserAddress;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.refuse_classification.entity.vo.UserAddressVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lin
 * @since 2021-04-04
 */
public interface UserAddressService extends IService<UserAddress> {

    List<UserAddressVO> getAllAddress(String memberId);
}
