package com.lixinxinlove.miaosha.service.impl;

import com.lixinxinlove.miaosha.dao.ItemDOMapper;
import com.lixinxinlove.miaosha.dao.OrderDOMapper;
import com.lixinxinlove.miaosha.dao.SequenceDOMapper;
import com.lixinxinlove.miaosha.dataobject.OrderDO;
import com.lixinxinlove.miaosha.dataobject.SequenceDO;
import com.lixinxinlove.miaosha.error.BusinessException;
import com.lixinxinlove.miaosha.error.EmBusinessError;
import com.lixinxinlove.miaosha.service.ItemService;
import com.lixinxinlove.miaosha.service.OrderService;
import com.lixinxinlove.miaosha.service.UserService;
import com.lixinxinlove.miaosha.service.model.ItemModel;
import com.lixinxinlove.miaosha.service.model.OrderModel;
import com.lixinxinlove.miaosha.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDOMapper orderDOMapper;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;


    @Autowired
    private SequenceDOMapper sequenceDOMapper;


    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException {

        //1.参数

        ItemModel itemModel = itemService.getItemById(itemId);
        System.out.println(itemModel.toString());
        if (itemModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "商品不存在");
        }


        UserModel userModel = userService.getUserById(userId);

        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "无效用户");
        }

        if (amount <= 0 || amount > 99) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "库存不足");
        }
        System.out.println("校验活动信息前");

        //校验活动信息
        if (promoId != null) {
            //校验商品是否有秒杀活动
            if (promoId.intValue() != itemModel.getPromoModel().getId()) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "活动信息不正确");

                //校验秒杀活动是否进行中
            }else if (itemModel.getPromoModel().getStatus()!=2){
                System.out.println("校验活动信息后");
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "秒杀活动没开始");
            }
        }

        //2.锁库存

        boolean r = itemService.decreaseStock(itemId, amount);
        if (!r) {
            throw new BusinessException(EmBusinessError.STOCK_NOT_BNOUGH);
        }


        System.out.println("订单入库前");
        //3 订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        if (promoId!=null){
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
            orderModel.setOrderPrice(itemModel.getPromoModel().getPromoItemPrice().multiply(new BigDecimal(amount)));
        }else {
            System.out.println("没有秒杀活动");
            orderModel.setItemPrice(itemModel.getPrice());
            orderModel.setOrderPrice(itemModel.getPrice().multiply(new BigDecimal(amount)));
        }

        orderModel.setId(generateOrderNo());
        orderModel.setPromoId(promoId);

        OrderDO orderDO = convertFromOrderModer(orderModel);

        orderDOMapper.insertSelective(orderDO);
        System.out.println("订单入库");
        //4.增加商品销量
        itemService.increaseSales(itemId, amount);
        System.out.println("增加商品销量");

        return orderModel;
    }

    //生成订单号
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    String generateOrderNo() {
        StringBuilder stringBuilder = new StringBuilder();

        //前8位
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-", "");
        stringBuilder.append(nowDate);

        int sequence;
        //中间6位
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
        sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue() + sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);

        String sequenceStr = String.valueOf(sequence);
        for (int i = 0; i < 6 - sequenceStr.length(); i++) {
            stringBuilder.append(0);
        }
        stringBuilder.append(sequenceStr);
        //后两位
        stringBuilder.append("00");

        return stringBuilder.toString();
    }


    private OrderDO convertFromOrderModer(OrderModel orderModel) {
        if (orderModel == null) {
            return null;
        }

        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO);
        orderDO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderDO.setOrderPrice(orderModel.getOrderPrice().doubleValue());

        return orderDO;
    }

}
