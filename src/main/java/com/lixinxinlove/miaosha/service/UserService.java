package com.lixinxinlove.miaosha.service;

import com.lixinxinlove.miaosha.error.BusinessException;
import com.lixinxinlove.miaosha.service.model.UserModel;

public interface UserService {

    UserModel getUserById(Integer id);

    void register(UserModel userModel) throws BusinessException;

}
