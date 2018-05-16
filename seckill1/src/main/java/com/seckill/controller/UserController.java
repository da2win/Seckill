package com.seckill.controller;

import com.seckill.domain.MiaoshaUser;
import com.seckill.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Darwin
 * @date 2018/5/15
 */
@Controller
@RequestMapping("/user")
public class UserController {


    @RequestMapping("/info")
    @ResponseBody
    public Result<MiaoshaUser> info(Model model, MiaoshaUser user) {
        model.addAttribute("user", user);
        return Result.success(user);
    }
}
