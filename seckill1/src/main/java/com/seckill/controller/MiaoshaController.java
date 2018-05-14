package com.seckill.controller;

import com.seckill.domain.MiaoshaOrder;
import com.seckill.domain.MiaoshaUser;
import com.seckill.domain.OrderInfo;
import com.seckill.result.CodeMsg;
import com.seckill.service.GoodsService;
import com.seckill.service.MiaoshaService;
import com.seckill.service.OrderService;
import com.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Darwin
 * @date 2018/5/14
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    @RequestMapping("/do_miaosha")
    public String list(Model model, MiaoshaUser user,
                       @RequestParam("goodsId") long goodsId) {
        if (user == null) {
            return "login";
        }
        GoodsVo goodsVo = goodsService.getGoodsVoById(goodsId);
        Integer stock = goodsVo.getStockCount();
        if (stock <= 0) {
            model.addAttribute("errmsg", CodeMsg.MIAOSHA_OVER);
            return "miaosha_fail";
        }

        // Judge whether had got sk-goods.
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            model.addAttribute("errmsg", CodeMsg.REPEAT_MIAOSHA);
            return "miaosha_fail";
        }
        // decrease the stock count. order down
        OrderInfo orderInfo = miaoshaService.miaosha(user, goodsVo);

        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goodsVo);
        return "order_detail";
    }
}
