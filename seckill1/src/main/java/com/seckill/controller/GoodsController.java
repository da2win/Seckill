package com.seckill.controller;

import com.seckill.domain.MiaoshaUser;
import com.seckill.service.GoodsService;
import com.seckill.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * @author Darwin
 * @date 2018/5/11
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/to_list")
    public String toList(Model model, MiaoshaUser user) {
        model.addAttribute("user", user);
        // query list of goods.
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String toDetail(Model model, MiaoshaUser user, @PathVariable("goodsId")long goodsId) {
        model.addAttribute("user", user);
        GoodsVo goodsVo = goodsService.getGoodsVoById(goodsId);
        model.addAttribute("goods", goodsVo);

        long startAt = goodsVo.getStartDate().getTime();
        long endAt = goodsVo.getEndDate().getTime();

        long now = System.currentTimeMillis();
        int remainSecs = 0;
        int miaoshaStatus = 0;
        if (now < startAt) { // The seckill has not begun.
            miaoshaStatus = 0;
            remainSecs = (int) ((startAt - now) / 1000);
        } else if (now > endAt) { // The seckill had finished.
            miaoshaStatus = 2;
            remainSecs = -1;
        } else { // In progress
            miaoshaStatus = 1;
        }
        model.addAttribute("remainSeconds", remainSecs);
        model.addAttribute("miaoshaStatus", miaoshaStatus);

        return "goods_detail";
    }
}
