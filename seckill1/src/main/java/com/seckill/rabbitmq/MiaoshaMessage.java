package com.seckill.rabbitmq;

import com.seckill.domain.MiaoshaUser;

/**
 *
 * @author Darwin
 * @date 2018/5/18
 */
public class MiaoshaMessage {
    private MiaoshaUser user;
    private Long goodsId;

    public MiaoshaUser getUser() {
        return user;
    }

    public void setUser(MiaoshaUser user) {
        this.user = user;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
}
