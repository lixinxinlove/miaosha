package com.lixinxinlove.miaosha.service.impl;

import com.lixinxinlove.miaosha.dao.ItemDOMapper;
import com.lixinxinlove.miaosha.dao.OrderDOMapper;
import com.lixinxinlove.miaosha.error.BusinessException;
import com.lixinxinlove.miaosha.error.EmBusinessError;
import com.lixinxinlove.miaosha.service.ItemService;
import com.lixinxinlove.miaosha.service.OrderService;
import com.lixinxinlove.miaosha.service.UserService;
import com.lixinxinlove.miaosha.service.model.ItemModel;
import com.lixinxinlove.miaosha.service.model.OrderModel;
import com.lixinxinlove.miaosha.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDOMapper orderDOMapper;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Override
    public OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException {

        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "");
        }

        UserModel userModel=userService.getUserById(userId);



        return null;
    }
}
