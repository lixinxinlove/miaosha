package com.lixinxinlove.miaosha.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
        return userAddressDOMapper.insert(userAddressDO);
    }

    @Override
    public Integer updateAddress(UserAddressDO userAddressDO) {
        Integer i = userAddressDOMapper.updateByPrimaryKeySelective(userAddressDO);
        return i;
    }

    @Override
    public List<UserAddressDO> getUserAddressList(Integer userId) {
        return userAddressDOMapper.selectByUserId(userId);
    }

    @Override
    public PageInfo<UserAddressDO> getUserAddressList(Integer userId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<UserAddressDO> userAddressDOList = userAddressDOMapper.selectByUserId(userId);
        PageInfo<UserAddressDO> pageInfo = new PageInfo<UserAddressDO>(userAddressDOList);
        return pageInfo;
    }
}
