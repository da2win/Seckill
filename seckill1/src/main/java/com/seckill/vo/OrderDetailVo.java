package com.seckill.vo;

import com.seckill.domain.OrderInfo;

/**
 *
 * @author Darwin
 * @date 2018/5/17
 */
public class OrderDetailVo {

    private GoodsVo goodsVo;
    private OrderInfo order;

    public GoodsVo getGoodsVo() {
        return goodsVo;
    }

    public void setGoodsVo(GoodsVo goodsVo) {
        this.goodsVo = goodsVo;
    }

    public OrderInfo getOrder() {
        return order;
    }

    public void setOrder(OrderInfo order) {
        this.order = order;
    }
}
