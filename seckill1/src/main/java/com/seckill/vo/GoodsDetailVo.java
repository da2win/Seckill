package com.seckill.vo;

import com.seckill.domain.MiaoshaUser;

/**
 *
 * @author Darwin
 * @date 2018/5/16
 */
public class GoodsDetailVo {
    private int remainSecs = 0;
    private int miaoshaStatus = 0;
    private GoodsVo goodsVo;
    private MiaoshaUser user;

    public int getRemainSecs() {
        return remainSecs;
    }

    public void setRemainSecs(int remainSecs) {
        this.remainSecs = remainSecs;
    }

    public int getMiaoshaStatus() {
        return miaoshaStatus;
    }

    public void setMiaoshaStatus(int miaoshaStatus) {
        this.miaoshaStatus = miaoshaStatus;
    }

    public GoodsVo getGoodsVo() {
        return goodsVo;
    }

    public void setGoodsVo(GoodsVo goodsVo) {
        this.goodsVo = goodsVo;
    }

    public MiaoshaUser getUser() {
        return user;
    }

    public void setUser(MiaoshaUser user) {
        this.user = user;
    }
}
