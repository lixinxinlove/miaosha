package com.lixinxinlove.miaosha.controller;


import com.github.pagehelper.PageInfo;
import com.lixinxinlove.miaosha.controller.viewobject.UserAddressVO;
import com.lixinxinlove.miaosha.dataobject.UserAddressDO;
import com.lixinxinlove.miaosha.error.BusinessException;
import com.lixinxinlove.miaosha.response.CommonReturnType;
import com.lixinxinlove.miaosha.service.UserAddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("address")
@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")   //跨域请求
public class UserAddressController extends BaseController {

    @Autowired
    private UserAddressService userAddressService;


    @RequestMapping(value = "/add", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED, MediaType.APPLICATION_JSON_VALUE})
    public CommonReturnType add(@RequestBody UserAddressVO userAddressVO) throws BusinessException {

        UserAddressDO userAddressDO = new UserAddressDO();
        BeanUtils.copyProperties(userAddressVO, userAddressDO);
        Integer i = userAddressService.addUserAddress(userAddressDO);
        if (i != null && i > 0) {
            return CommonReturnType.create(i);
        } else {
            return CommonReturnType.error(null);
        }
    }


    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public CommonReturnType list(@RequestParam(name = "userId") Integer userId) throws BusinessException {
        List<UserAddressDO> list = userAddressService.getUserAddressList(userId);


        List<UserAddressVO> list1 = new ArrayList<>();
        for (UserAddressDO userAddressDO : list) {
            UserAddressVO userAddressVO = new UserAddressVO();
            BeanUtils.copyProperties(userAddressDO, userAddressVO);
            list1.add(userAddressVO);
        }

        if (list1 != null) {
            return CommonReturnType.create(list1);
        } else {
            return CommonReturnType.create(null);
        }
    }


    @RequestMapping(value = "/listpage", method = {RequestMethod.GET})
    public CommonReturnType list(@RequestParam(name = "userId") Integer userId,
                                 @RequestParam(name = "page") Integer page,
                                 @RequestParam(name = "size") Integer size) throws BusinessException {
        PageInfo<UserAddressDO> list1 = userAddressService.getUserAddressList(userId, page, size);

        if (list1 != null) {
            return CommonReturnType.create(list1);
        } else {
            return CommonReturnType.create(null);
        }
    }


    @RequestMapping(value = "/update", method = {RequestMethod.POST}, consumes = CONTENT_TYPE_FORMED)
    public CommonReturnType updateAddress(UserAddressVO userAddressVO) {

        UserAddressDO userAddressDO = getUserAddressDO2userAddressVO(userAddressVO);

        Integer i = userAddressService.updateAddress(userAddressDO);
        if (i != null && i > 0) {
            return CommonReturnType.create(null);
        } else {
            return CommonReturnType.error(null);
        }
    }


    private UserAddressDO getUserAddressDO2userAddressVO(UserAddressVO userAddressVO) {
        UserAddressDO userAddressDO = new UserAddressDO();
        BeanUtils.copyProperties(userAddressVO, userAddressDO);
        return userAddressDO;
    }


}
