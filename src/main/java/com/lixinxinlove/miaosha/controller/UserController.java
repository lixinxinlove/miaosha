package com.lixinxinlove.miaosha.controller;


import com.lixinxinlove.miaosha.controller.viewobject.UserVO;
import com.lixinxinlove.miaosha.error.BusinessException;
import com.lixinxinlove.miaosha.error.EmBusinessError;
import com.lixinxinlove.miaosha.response.CommonReturnType;
import com.lixinxinlove.miaosha.service.UserService;
import com.lixinxinlove.miaosha.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Random;

@RequestMapping("user")
@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")   //跨域请求
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;


    @RequestMapping(value = "/login", method = {RequestMethod.POST}, consumes = CONTENT_TYPE_FORMED)
    public CommonReturnType login(@RequestParam(name = "telphone") String telphone, @RequestParam(name = "password") String password) throws BusinessException {

        if (StringUtils.isEmpty(telphone) || StringUtils.isEmpty(password)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        UserModel userModel = userService.validateLogin(telphone, password);

        //保存用户登录状态
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER", userModel);
        System.out.println("--------------------登录成功------------------");
        return CommonReturnType.create(null);
    }


    /*注册*/
    @RequestMapping(value = "/register", method = {RequestMethod.POST}, consumes = CONTENT_TYPE_FORMED)
    public CommonReturnType register(@RequestParam(name = "optCode") String optCode,
                                     @RequestParam(name = "name") String name,
                                     @RequestParam(name = "gender") String gender,
                                     @RequestParam(name = "age") Integer age,
                                     @RequestParam(name = "telphone") String telphone,
                                     @RequestParam(name = "password") String password) throws BusinessException {


        String inSessionOptCode = (String) this.httpServletRequest.getSession().getAttribute(telphone);

        System.out.println("inSessionOptCode" + inSessionOptCode);

        if (!inSessionOptCode.equals(optCode)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "短信验证码为空");
        }

        //注册
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setAge(age);
        userModel.setGender(Byte.valueOf(gender));
        userModel.setTelphone(telphone);
        userModel.setEncrptPassword(password);
        userModel.setRegisterMode("手机");
        userService.register(userModel);

        return CommonReturnType.create(null);
    }


    @RequestMapping(value = "/getotp", method = {RequestMethod.POST}, consumes = CONTENT_TYPE_FORMED)
    public CommonReturnType getOtp(@RequestParam(name = "telphone") String telphone) {
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 100000;
        String otpCode = String.valueOf(randomInt);
        //关联用户的手机号和验证码
        httpServletRequest.getSession().setAttribute(telphone, otpCode);
        //发送短信给用户
        System.out.println("telphone=" + telphone + "-------opt=" + otpCode);
        return CommonReturnType.create(null);
    }


    @GetMapping("/get")
    public CommonReturnType getUser() throws BusinessException {
        UserModel userModel = userService.getUserById(11);

        if (userModel == null) {
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }

        UserVO userVO = convertFromModel(userModel);
        return CommonReturnType.create(userVO);
    }


    private UserVO convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }


}
