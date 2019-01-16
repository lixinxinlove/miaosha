package com.lixinxinlove.miaosha.service.impl;

import com.lixinxinlove.miaosha.dao.UserAddressDOMapper;
import com.lixinxinlove.miaosha.dataobject.UserAddressDO;
import com.lixinxinlove.miaosha.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserAddressServiceImpl implements UserAddressService {

    @Autowired
    private UserAddressDOMapper userAddressDOMapper;

    @Override
    public Integer addUserAddress(UserAddressDO userAddressDO) {
        return userAddressDOMapper.insertSelective(userAddressDO);
    }

    @Override
    public List<UserAddressDO> getUserAddressList(Integer userId) {
        return userAddressDOMapper.selectByUserId(userId);
    }
}
