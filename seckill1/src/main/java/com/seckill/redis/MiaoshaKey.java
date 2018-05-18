package com.seckill.redis;

/**
 *
 * @author Darwin
 * @date 2018/5/16
 */
public class MiaoshaKey extends BasePrefix {

    private MiaoshaKey(String prefix) {
        super(prefix);
    }

    public static MiaoshaKey isGoodsOver = new MiaoshaKey("go");

}
