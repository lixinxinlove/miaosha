package com.lixinxinlove.miaosha.service.impl;

import com.lixinxinlove.miaosha.dao.UserDOMapper;
import com.lixinxinlove.miaosha.dao.UserPasswordDOMapper;
import com.lixinxinlove.miaosha.dataobject.UserDO;
import com.lixinxinlove.miaosha.dataobject.UserPasswordDO;
import com.lixinxinlove.miaosha.error.BusinessException;
import com.lixinxinlove.miaosha.error.EmBusinessError;
import com.lixinxinlove.miaosha.service.UserService;
import com.lixinxinlove.miaosha.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户
 */


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;

    @Override
    public UserModel getUserById(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if (userDO == null) {
            return null;
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        return convertFromDataObject(userDO, userPasswordDO);
    }


    /*注册*/
    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        if (StringUtils.isEmpty(userModel.getName())
                || userModel.getGender() == null
                || userModel.getAge() == null
                || StringUtils.isEmpty(userModel.getTelphone())
                || StringUtils.isEmpty(userModel.getEncrptPassword())) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        UserDO userDO = convertFromModel(userModel);
        userDOMapper.insertSelective(userDO);
        userModel.setId(userDO.getId());

        UserPasswordDO userPasswordDO = convertPasswordFromModel(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);
    }

    private UserDO convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }

        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel, userDO);
        return userDO;
    }


    private UserPasswordDO convertPasswordFromModel(UserModel userModel) {
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setUserId(userModel.getId());
        userPasswordDO.setEncrptPassword(userModel.getEncrptPassword());
        return userPasswordDO;
    }


    /**
     * 转换
     *
     * @param userDO
     * @param userPasswordDO
     * @return
     */
    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO) {
        if (userDO == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO, userModel);
        if (userPasswordDO != null) {
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }
        return userModel;
    }


}
