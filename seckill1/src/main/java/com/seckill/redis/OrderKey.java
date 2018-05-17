package com.seckill.redis;

/**
 *
 * @author Darwin
 * @date 2018/5/11
 */
public class OrderKey extends BasePrefix {

    private OrderKey(String prefix) {
        super(prefix);
    }

    public static OrderKey getMiaoshaOrderByUidGid = new OrderKey("miaoug");
}
