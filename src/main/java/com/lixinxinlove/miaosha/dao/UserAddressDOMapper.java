package com.lixinxinlove.miaosha.dao;

import com.lixinxinlove.miaosha.dataobject.UserAddressDO;

public interface UserAddressDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAddressDO record);

    int insertSelective(UserAddressDO record);

    UserAddressDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserAddressDO record);

    int updateByPrimaryKey(UserAddressDO record);
}