package com.lixinxinlove.miaosha.dao;

import com.lixinxinlove.miaosha.dataobject.ItemStockDO;

public interface ItemStockDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemStockDO record);

    int insertSelective(ItemStockDO record);

    ItemStockDO selectByPrimaryKey(Integer id);

    ItemStockDO selectByItemId(Integer itemId);

    int updateByPrimaryKeySelective(ItemStockDO record);

    int updateByPrimaryKey(ItemStockDO record);

    /**
     * 减去库充
     *
     * @param itemId
     * @param amount
     * @return
     */
    int decreaseStock(Integer itemId, Integer amount);
}