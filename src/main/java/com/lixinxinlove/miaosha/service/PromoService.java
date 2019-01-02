package com.lixinxinlove.miaosha.service;


import com.lixinxinlove.miaosha.service.model.PromoModel;

/**
 * 秒杀商品服务
 */
public interface PromoService {

    PromoModel getPromoByItemId(Integer itemId);

}
