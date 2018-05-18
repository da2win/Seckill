package com.seckill.controller;

import com.seckill.domain.User;
import com.seckill.rabbitmq.MQSender;
import com.seckill.redis.RedisService;
import com.seckill.redis.UserKey;
import com.seckill.result.CodeMsg;
import com.seckill.result.Result;
import com.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Darwin
 * @date 2018/5/10
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private MQSender sender;


    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    //@RequestMapping("/mq")
    //@ResponseBody
    //public Result<String> mq() {
    //    sender.send("hello, I'm your daddy!");
    //    return Result.success("hello da2win");
    //}
    //
    //@RequestMapping("/mq/topic")
    //@ResponseBody
    //public Result<String> topic() {
    //    sender.sendTopic("hello, I'm your daddy!");
    //    return Result.success("hello da2win");
    //}
    //
    //@RequestMapping("/mq/fanout")
    //@ResponseBody
    //public Result<String> fanout() {
    //    sender.sendFanout("hello, I'm your daddy!");
    //    return Result.success("hello da2win");
    //}
    //
    //@RequestMapping("/mq/header")
    //@ResponseBody
    //public Result<String> header() {
    //    sender.sendHeader("hello, I'm your daddy!");
    //    return Result.success("hello da2win");
    //}

    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello() {
        return Result.success("hello da2win");
    }

    @RequestMapping("/helloError")
    @ResponseBody
    public Result helloError() {
        return Result.error(CodeMsg.SERVER_ERROR);
    }

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name", "Chandler Bing");
        return "hello";
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result dbGet() {
        User user = userService.getById(1);
        return Result.success(user);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result dbTx() {
        boolean rs = userService.tx();
        return Result.success(rs);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet() {
        User user = redisService.get(UserKey.getById, 1 + "", User.class);
        return Result.success(user);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result redisSet() {
        User user = new User();
        user.setId(1);
        user.setName("Joe Tribbiani");
        boolean v1 = redisService.set(UserKey.getById, 1 + "", user);
        return Result.success(v1);
    }
}
