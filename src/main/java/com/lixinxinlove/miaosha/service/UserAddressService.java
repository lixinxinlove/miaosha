package com.lixinxinlove.miaosha.service;

import com.lixinxinlove.miaosha.dataobject.UserAddressDO;

import java.util.List;

/**
 * 用户的收获地址
 */
public interface UserAddressService {

    Integer addUserAddress(UserAddressDO userAddressDO);

    List<UserAddressDO> getUserAddressList(Integer userId);

}
