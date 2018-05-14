package com.seckill.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 *
 * @author Darwin
 * @date 2018/5/10
 */
@Service
public class RedisService {
    @Autowired
    JedisPool jedisPool;

    /**
     * Get current object.
     * @param prefix
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // Generate the true key.
            String realKey = prefix.getPrefix() + key;
            String str = jedis.get(realKey);

            T t = stringToBean(str, clazz);
            return t;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * set a object.
     * @param prefix
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // Generate the true key.
            String realKey = prefix.getPrefix() + key;
            String str = beanToString(value);
            if (str == null || str.length() < 0) {
                return false;
            }
            int expireSeconds = prefix.expireSeconds();
            if (expireSeconds <= 0) {
                jedis.set(realKey, str);
            } else {
                jedis.setex(realKey, expireSeconds, str);
            }
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * Increase
     * @param prefix
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> Long incr(KeyPrefix prefix, String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // Generate the true key.
            String realKey = prefix.getPrefix() + key;
            Long incr = jedis.incr(realKey);
            return incr;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * Decrease
     * @param prefix
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> Long decr(KeyPrefix prefix, String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // Generate the true key.
            String realKey = prefix.getPrefix() + key;
            Long decr = jedis.decr(realKey);
            return decr;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * Existence judgement.
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> boolean exist(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // Generate the true key.
            String realKey = prefix.getPrefix() + key;

            Boolean exists = jedis.exists(realKey);
            return exists;
        } finally {
            returnToPool(jedis);
        }
    }

    private <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return "" + value;
        } else if (clazz == String.class) {
            return (String)value;
        } else if (clazz == long.class || clazz == Long.class) {
            return "" + value;
        } else {
            return JSON.toJSONString(value);
        }
    }

    private <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() < 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    private void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

}
