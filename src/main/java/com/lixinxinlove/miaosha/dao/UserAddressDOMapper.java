package com.lixinxinlove.miaosha.dao;

import com.lixinxinlove.miaosha.dataobject.UserAddressDO;

import java.util.List;

public interface UserAddressDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAddressDO record);

    int insertSelective(UserAddressDO record);

    UserAddressDO selectByPrimaryKey(Integer id);

    List<UserAddressDO> selectByUserId(Integer userId);

    int updateByPrimaryKeySelective(UserAddressDO record);

    int updateByPrimaryKey(UserAddressDO record);
}