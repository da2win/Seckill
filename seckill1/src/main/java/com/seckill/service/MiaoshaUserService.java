package com.seckill.service;

import com.seckill.dao.MiaoshaUserDao;
import com.seckill.domain.MiaoshaUser;
import com.seckill.exception.GlobalException;
import com.seckill.redis.RedisService;
import com.seckill.redis.SeckillUserKey;
import com.seckill.redis.UserKey;
import com.seckill.result.CodeMsg;
import com.seckill.util.MD5Util;
import com.seckill.util.UUIDUtil;
import com.seckill.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Darwin
 * @date 2018/5/11
 */
@Service
public class MiaoshaUserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    private MiaoshaUserDao miaoshaUserDao;
    @Autowired
    private RedisService redisService;

    public MiaoshaUser getById(Long id) {
        // Get cache.
        MiaoshaUser user = redisService.get(SeckillUserKey.getById, "" + id, MiaoshaUser.class);
        if (user != null) {
            return user;
        }
        // Get database.
        user = miaoshaUserDao.getById(id);
        if (user != null) {
            redisService.set(SeckillUserKey.getById, "" + id, user);
        }
        return user;
    }

    public boolean updatePassword(String token, long id, String passwordNew) {
        // Get user
        MiaoshaUser user = getById(id);
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        // Update db.
        MiaoshaUser tobeUpdate = new MiaoshaUser();
        tobeUpdate.setId(id);
        tobeUpdate.setPassword(MD5Util.formPassToDBPass(passwordNew, user.getSalt()));
        miaoshaUserDao.update(tobeUpdate);
        // handle cache.
        redisService.del(SeckillUserKey.getById, "" + id);
        user.setPassword(tobeUpdate.getPassword());
        redisService.set(SeckillUserKey.token, token, user);
        return true;
    }

    public String login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile(),
                password = loginVo.getPassword();
        MiaoshaUser user = getById(Long.valueOf(mobile));
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        // Check password.
        String dbPass = user.getPassword();
        String dbSalt = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(password, dbSalt);
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_INCORRECT);
        }
        String token = UUIDUtil.uuid();
        addCookie(response, user, token);
        return token;
    }

    private void addCookie(HttpServletResponse response, MiaoshaUser user, String token) {
        // Generate cookie.
        redisService.set(SeckillUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(SeckillUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public MiaoshaUser getByToken(String token, HttpServletResponse response) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        MiaoshaUser user = redisService.get(SeckillUserKey.token, token, MiaoshaUser.class);
        if (user != null) {
            // Extend period of validity.
            addCookie(response, user, token);
        }
        return user;
    }
}
