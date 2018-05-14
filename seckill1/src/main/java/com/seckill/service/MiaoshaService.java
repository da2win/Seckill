package com.seckill.service;

import com.seckill.domain.Goods;
import com.seckill.domain.MiaoshaUser;
import com.seckill.domain.OrderInfo;
import com.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Darwin
 * @date 2018/5/14
 */
@Service
public class MiaoshaService {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goodsVo) {
        // decrease count
        goodsService.reduceStock(goodsVo);
        return orderService.createOrder(user, goodsVo);
    }
}
