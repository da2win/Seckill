package com.seckill.rabbitmq;

import com.seckill.domain.MiaoshaOrder;
import com.seckill.domain.MiaoshaUser;
import com.seckill.redis.RedisService;
import com.seckill.service.GoodsService;
import com.seckill.service.MiaoshaService;
import com.seckill.service.OrderService;
import com.seckill.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Darwin
 * @date 2018/5/17
 */
@Service
public class MQReceiver {

    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    @Autowired
    private RedisService redisService;

    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String message) {
        log.info("receive message:" + message);
        MiaoshaMessage mm = RedisService.stringToBean(message, MiaoshaMessage.class);
        MiaoshaUser user = mm.getUser();
        long goodsId = mm.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoById(goodsId);
        Integer stockCount = goods.getStockCount();
        if (stockCount <= 0) {
            return;
        }
        // Judge whether had got sk-goods.
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return;
        }
        // Generate miaosha order.
        miaoshaService.miaosha(user, goods);
    }

    //@RabbitListener(queues = MQConfig.QUEUE)
    //public void receive(String message) {
    //    log.info("receive message:" + message);
    //}
    //
    //@RabbitListener(queues = MQConfig.TOPIC_QUEUE_1)
    //public void receiveTopic1(String message) {
    //    log.info("topic queue1 message:" + message);
    //}
    //
    //@RabbitListener(queues = MQConfig.TOPIC_QUEUE_2)
    //public void receiveTopic2(String message) {
    //    log.info("topic queue2 message:" + message);
    //}
    //
    //@RabbitListener(queues = MQConfig.HEADERS_QUEUE)
    //public void receiveHeaders(byte[] message) {
    //
    //    log.info("headers message:" + new String(message));
    //}


}
