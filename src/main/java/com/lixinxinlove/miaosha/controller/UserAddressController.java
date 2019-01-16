package com.lixinxinlove.miaosha.controller;


import com.lixinxinlove.miaosha.controller.viewobject.UserAddressVO;
import com.lixinxinlove.miaosha.dataobject.UserAddressDO;
import com.lixinxinlove.miaosha.error.BusinessException;
import com.lixinxinlove.miaosha.response.CommonReturnType;
import com.lixinxinlove.miaosha.service.UserAddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("address")
@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")   //跨域请求
public class UserAddressController extends BaseController {

    @Autowired
    private UserAddressService userAddressService;


    @RequestMapping(value = "/add", method = {RequestMethod.POST}, consumes = CONTENT_TYPE_FORMED)
    public CommonReturnType add(UserAddressVO userAddressVO) throws BusinessException {


        UserAddressDO userAddressDO = new UserAddressDO();
        BeanUtils.copyProperties(userAddressVO, userAddressDO);
        Integer i = userAddressService.addUserAddress(userAddressDO);
        if (i != null && i > 0) {
            return CommonReturnType.create(null);
        } else {
            return CommonReturnType.error(null);
        }
    }


    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public CommonReturnType list(@RequestParam(name = "userId") Integer userId) throws BusinessException {

        List<UserAddressDO> list = userAddressService.getUserAddressList(userId);

        //List<UserAddressDO> list = new ArrayList<>();
       // UserAddressDO userAddressDO = new UserAddressDO();
       // userAddressDO.setReceiverName("李欣欣");
       // list.add(userAddressDO);
        if (list != null) {
            return CommonReturnType.create(list);
        } else {
            return CommonReturnType.create(null);
        }
    }

}
