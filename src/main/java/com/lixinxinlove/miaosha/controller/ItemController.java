package com.lixinxinlove.miaosha.controller;

import com.lixinxinlove.miaosha.controller.viewobject.ItemVO;
import com.lixinxinlove.miaosha.error.BusinessException;
import com.lixinxinlove.miaosha.response.CommonReturnType;
import com.lixinxinlove.miaosha.service.ItemService;
import com.lixinxinlove.miaosha.service.model.ItemModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品
 */

@RequestMapping("item")
@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")   //跨域请求
public class ItemController extends BaseController {


    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/create", method = {RequestMethod.POST}, consumes = CONTENT_TYPE_FORMED)
    public CommonReturnType createItem(@RequestParam(name = "title") String title,
                                       @RequestParam(name = "description") String description,
                                       @RequestParam(name = "price") BigDecimal price,
                                       @RequestParam(name = "stock") Integer stock,
                                       @RequestParam(name = "imgUrl") String imgUrl) throws BusinessException {

        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);

        ItemModel itemModel1ForReturn = itemService.createItem(itemModel);

        ItemVO itemVO = convertVOFromMode(itemModel1ForReturn);

        return CommonReturnType.create(itemVO);
    }


    /**
     * 获取单条商品
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    public CommonReturnType getItem(@RequestParam(name = "id") Integer id) {
        ItemModel itemModel = itemService.getItemById(id);
        ItemVO itemVO = convertVOFromMode(itemModel);
        return CommonReturnType.create(itemVO);
    }


    /**
     * 商品列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public CommonReturnType getList() {
        List<ItemModel> itemModelList = itemService.listItem();
        List<ItemVO> itemVOList = itemModelList.stream().map(itemModel -> convertVOFromMode(itemModel)).collect(Collectors.toList());
        return CommonReturnType.create(itemVOList);
    }


    private ItemVO convertVOFromMode(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel, itemVO);
        return itemVO;
    }

}
