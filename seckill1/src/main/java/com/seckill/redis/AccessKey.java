package com.seckill.redis;

/**
 *
 * @author Darwin
 * @date 2018/5/21
 */
public class AccessKey extends BasePrefix{


    private AccessKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static AccessKey withExpire(int expireSeconds) {
        return new AccessKey(expireSeconds, "access");
    }
}
