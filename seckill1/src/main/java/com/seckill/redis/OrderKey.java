package com.seckill.redis;

/**
 *
 * @author Darwin
 * @date 2018/5/11
 */
public class OrderKey extends BasePrefix {

    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
