package com.seckill.result;

/**
 * @author Darwin
 * @date 2018/5/10
 */
public class CodeMsg {
    private int code;
    private String msg;

    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "server error");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "Argument check exception：%s");
    public static CodeMsg REQUEST_FREQUENT = new CodeMsg(500102, "Too frequent access");
    public static CodeMsg REQUEST_ILLEGAL = new CodeMsg(500103, "请求非法");

    // 登录模块 5002XX
    public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或者已经失效");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "Password can not be empty");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "PhoneNO can not be empty");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500213, "Mobile phone number format error");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214, "Mobile is not exists");
    public static CodeMsg PASSWORD_INCORRECT = new CodeMsg(500215, "Password is incorrect");

    // 订单模块 5004XX
    public static CodeMsg ORDER_NOT_EXIST = new CodeMsg(500400, "The order is not exists");

    // 秒杀模块 5005XX
    public static CodeMsg MIAOSHA_OVER = new CodeMsg(500500, "The goods have been sold out");
    public static CodeMsg REPEAT_MIAOSHA = new CodeMsg(500501, "Don't repeat the SECKILL");
    public static CodeMsg MIAOSHA_FAIL = new CodeMsg(500501, "The seckill has failed.");

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }

    @Override
    public String toString() {
        return "CodeMsg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
