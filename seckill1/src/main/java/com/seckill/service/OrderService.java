package com.seckill.service;

import com.seckill.dao.OrderDao;
import com.seckill.domain.MiaoshaOrder;
import com.seckill.domain.MiaoshaUser;
import com.seckill.domain.OrderInfo;
import com.seckill.redis.OrderKey;
import com.seckill.redis.RedisService;
import com.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 *
 * @author Darwin
 * @date 2018/5/14
 */
@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private RedisService redisService;

    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(Long userId, long goodsId) {
        return redisService.get(OrderKey.getMiaoshaOrderByUidGid, userId + "_" +goodsId, MiaoshaOrder.class);
    }

    @Transactional
    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goodsVo) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        orderInfo.setGoodsPrice(goodsVo.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        orderDao.insert(orderInfo);

        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goodsVo.getId());
        miaoshaOrder.setOrderId(orderInfo.getId());
        miaoshaOrder.setUserId(user.getId());
        orderDao.insertMiaoshaOrder(miaoshaOrder);

        redisService.set(OrderKey.getMiaoshaOrderByUidGid, user.getId() + "_" + goodsVo.getId(), miaoshaOrder);
        return orderInfo;

    }

    public OrderInfo getOrderById(long orderId) {
        return orderDao.getOrderById(orderId);
    }
}
