package com.seckill.access;

import com.seckill.domain.MiaoshaUser;

/**
 * @author Darwin
 * @date 2018/5/21
 */
public class UserContext {

    private static ThreadLocal<MiaoshaUser> userHolder = new ThreadLocal<MiaoshaUser>();

    public static void setUser(MiaoshaUser user) {
        userHolder.set(user);
    }

    public static MiaoshaUser getUser() {
        return userHolder.get();
    }
}
