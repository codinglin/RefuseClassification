package com.study.refuse_classification.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.refuse_classification.entity.UserAddress;
import com.study.refuse_classification.entity.vo.UserAddressVO;
import com.study.refuse_classification.mapper.UserAddressMapper;
import com.study.refuse_classification.service.UserAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lin
 * @since 2021-04-04
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {
    @Resource
    private  UserAddressMapper userAddressMapper;

    @Override
    public List<UserAddressVO> getAllAddress(String memberId) {
        QueryWrapper<UserAddress> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",memberId);
        List<UserAddress> userAddressList= userAddressMapper.selectList(wrapper);
        List<UserAddressVO> addressVOS = userAddressList.stream().map(address -> {
            UserAddressVO addressVO = new UserAddressVO();
            BeanUtils.copyProperties(address, addressVO);
            return addressVO;
        }).collect(Collectors.toList());
        return addressVOS;
    }
}
