package com.lixinxinlove.miaosha.service;

import com.lixinxinlove.miaosha.error.BusinessException;
import com.lixinxinlove.miaosha.service.model.ItemModel;

import java.util.List;

/**
 * 商品
 */
public interface ItemService {

    //创建商品
    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    //商品列表预览
    List<ItemModel> listItem();

    //商品详情浏览
    ItemModel getItemById(Integer id);


    boolean decreaseStock(Integer itemId, Integer amount);


    //商品销量增加
    void increaseSales(Integer itemId,Integer amount) throws BusinessException;
}
