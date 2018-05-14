package com.seckill.util;

import java.util.UUID;

/**
 *
 * @author Darwin
 * @date 2018/5/11
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
