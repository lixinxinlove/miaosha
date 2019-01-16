package com.lixinxinlove.miaosha.controller;


import com.lixinxinlove.miaosha.controller.viewobject.OrderVO;
import com.lixinxinlove.miaosha.error.BusinessException;
import com.lixinxinlove.miaosha.error.EmBusinessError;
import com.lixinxinlove.miaosha.response.CommonReturnType;
import com.lixinxinlove.miaosha.service.OrderService;
import com.lixinxinlove.miaosha.service.model.OrderModel;
import com.lixinxinlove.miaosha.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单
 */
@RequestMapping("order")
@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")   //跨域请求
public class OrderController extends BaseController {


    @Autowired
    private OrderService orderService;


    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/createorder", method = {RequestMethod.POST}, consumes = CONTENT_TYPE_FORMED)
    public CommonReturnType createOrder(@RequestParam(name = "itemId") Integer itemId,
                                        @RequestParam(name = "promoId", required = false) Integer promoId,
                                        @RequestParam(name = "amount") Integer amount)
            throws BusinessException {

        //
        if (itemId <= 0) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        if (amount <= 0 || amount > 99) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }


        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");
        Integer userId = userModel.getId();
        OrderModel orderModel = orderService.createOrder(userId, itemId, promoId, amount);

        if (orderModel != null) {
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(orderModel, orderVO);
            return CommonReturnType.create(orderVO);
        } else {
            return CommonReturnType.error(null);
        }
    }

}
