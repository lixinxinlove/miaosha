package com.lixinxinlove.miaosha.service.impl;

import com.lixinxinlove.miaosha.dao.ItemDOMapper;
import com.lixinxinlove.miaosha.dao.ItemStockDOMapper;
import com.lixinxinlove.miaosha.dataobject.ItemDO;
import com.lixinxinlove.miaosha.dataobject.ItemStockDO;
import com.lixinxinlove.miaosha.error.BusinessException;
import com.lixinxinlove.miaosha.error.EmBusinessError;
import com.lixinxinlove.miaosha.service.ItemService;
import com.lixinxinlove.miaosha.service.model.ItemModel;
import com.lixinxinlove.miaosha.validator.ValidatorImpl;
import com.lixinxinlove.miaosha.validator.ValidatorResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


/**
 * 商品
 */

@Service
public class ItemServiceImpl implements ItemService {


    @Autowired
    private ValidatorImpl validator;  //参数校验

    @Autowired
    private ItemDOMapper itemDOMapper;

    @Autowired
    private ItemStockDOMapper itemStockDOMapper;  //库存


    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        //1. 参数校验
        ValidatorResult result = validator.validate(itemModel);

        if (result.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        ItemDO itemDO = convertItemDOFromItemMode(itemModel);
        //写入数据库
        itemDOMapper.insertSelective(itemDO);

        itemModel.setId(itemDO.getId());

        ItemStockDO itemStockDO = convertItemStockDOFromItemModel(itemModel);
        itemStockDOMapper.insertSelective(itemStockDO);

        //return this.getItemById(itemModel.getId());

        return itemModel;
    }

    @Override
    public List<ItemModel> listItem() {
        return null;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
        if (itemDO == null) {
            return null;
        }

        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());

        if (itemStockDO == null) {
            return null;
        }

        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO, itemModel);
        itemModel.setStock(itemStockDO.getStock());
        itemModel.setPrice(new BigDecimal(itemDO.getPrice()));

        return itemModel;
    }

    private ItemDO convertItemDOFromItemMode(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }

        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel, itemDO);
        itemDO.setPrice(itemModel.getPrice().doubleValue());
        return itemDO;
    }


    private ItemStockDO convertItemStockDOFromItemModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }

        ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setItemId(itemModel.getId());
        itemStockDO.setStock(itemModel.getStock());

        return itemStockDO;
    }


}
