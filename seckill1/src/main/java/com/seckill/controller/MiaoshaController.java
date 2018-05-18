package com.seckill.controller;

import com.seckill.domain.MiaoshaUser;
import com.seckill.rabbitmq.MQSender;
import com.seckill.rabbitmq.MiaoshaMessage;
import com.seckill.redis.GoodsKey;
import com.seckill.redis.RedisService;
import com.seckill.result.CodeMsg;
import com.seckill.result.Result;
import com.seckill.service.GoodsService;
import com.seckill.service.MiaoshaService;
import com.seckill.service.OrderService;
import com.seckill.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Darwin
 * @date 2018/5/14
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MQSender sender;

    private Map<Long, Boolean> localOverMap = new HashMap();

    @RequestMapping(value = "/do_miaosha", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> list(MiaoshaUser user,
                                @RequestParam("goodsId") long goodsId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        // 内存标记, 减少redis访问量
        Boolean over = localOverMap.get(goodsId);
        if (over) {
            return Result.error(CodeMsg.MIAOSHA_OVER);
        }
        Long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock, "" + goodsId);
        if (stock < 0) {
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.MIAOSHA_OVER);
        }


        // 入队
        MiaoshaMessage mm = new MiaoshaMessage();
        mm.setUser(user);
        mm.setGoodsId(goodsId);
        sender.sendMiaoshaMessage(mm);

        //GoodsVo goodsVo = goodsService.getGoodsVoById(goodsId);
        //Integer stock = goodsVo.getStockCount();
        //if (stock <= 0) {
        //    return Result.error(CodeMsg.MIAOSHA_OVER);
        //}
        //
        //// Judge whether had got sk-goods.
        //MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        //if (order != null) {
        //    return Result.error(CodeMsg.REPEAT_MIAOSHA);
        //}
        //// decrease the stock count. order down
        //OrderInfo orderInfo = miaoshaService.miaosha(user, goodsVo);

        return Result.success(0); // 排队中
    }

    /**
     * orderId: 成功
     * -1: 秒杀失败
     *  0: 正在排队
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> miaoshaResult(MiaoshaUser user,
                                      @RequestParam("goodsId") long goodsId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long result = miaoshaService.getMiaoshaResult(user.getId(), goodsId);
        return Result.success(result);
    }

    /**
     * 系统初始化
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        if (goodsList == null) {
            return;
        }
        for (GoodsVo goods : goodsList) {
            redisService.set(GoodsKey.getMiaoshaGoodsStock, "" + goods.getId(), goods.getStockCount());
            localOverMap.put(goods.getId(), false);
        }

    }
}
