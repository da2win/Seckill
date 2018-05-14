package com.seckill.redis;

/**
 *
 * @author Darwin
 * @date 2018/5/11
 */
public interface KeyPrefix {

    int expireSeconds();

    String getPrefix();
}
