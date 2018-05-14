package com.seckill.service;

import com.seckill.dao.UserDao;
import com.seckill.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Darwin
 * @date 2018/5/10
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getById(int id) {
        return userDao.getById(id);
    }

    @Transactional
    public boolean tx() {
        User u1 = new User();
        u1.setId(3);
        u1.setName("333");
        userDao.insert(u1);

        User u2 = new User();
        u1.setId(1);
        u1.setName("1111s");
        userDao.insert(u2);

        return true;
    }
}
