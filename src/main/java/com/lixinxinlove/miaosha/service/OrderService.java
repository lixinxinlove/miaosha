package com.lixinxinlove.miaosha.service;

import com.lixinxinlove.miaosha.error.BusinessException;
import com.lixinxinlove.miaosha.service.model.OrderModel;

/**
 * 订单服务
 */
public interface OrderService {

    OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException;

}
