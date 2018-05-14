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

    // 登录模块 5002XX
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "Password can not be empty");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "PhoneNO can not be empty");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500213, "Mobile phone number format error");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214, "Mobile is not exists");
    public static CodeMsg PASSWORD_INCORRECT = new CodeMsg(500215, "Password is incorrect");

    // 秒杀模块 5005XX
    public static CodeMsg MIAOSHA_OVER = new CodeMsg(500500, "The goods have been sold out");
    public static CodeMsg REPEAT_MIAOSHA = new CodeMsg(500501, "Don't repeat the SECKILL");

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
