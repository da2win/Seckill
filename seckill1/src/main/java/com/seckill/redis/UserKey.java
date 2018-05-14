package com.seckill.redis;

/**
 *
 * @author Darwin
 * @date 2018/5/11
 */
public class UserKey extends BasePrefix {

    private UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getById = new UserKey("id");

    public static UserKey getByName = new UserKey("name");

}
