package com.seckill.controller;

import com.seckill.domain.MiaoshaUser;
import com.seckill.domain.OrderInfo;
import com.seckill.result.CodeMsg;
import com.seckill.result.Result;
import com.seckill.service.GoodsService;
import com.seckill.service.OrderService;
import com.seckill.vo.GoodsVo;
import com.seckill.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Darwin
 * @date 2018/5/17
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> detail(MiaoshaUser user, @RequestParam("orderId") long orderId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        OrderInfo orderInfo = orderService.getOrderById(orderId);
        if (orderInfo == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        Long goodsId = orderInfo.getGoodsId();
        GoodsVo goodsVo = goodsService.getGoodsVoById(goodsId);
        OrderDetailVo detailVo = new OrderDetailVo();
        detailVo.setGoodsVo(goodsVo);
        detailVo.setOrder(orderInfo);
        return Result.success(detailVo);
    }

}
