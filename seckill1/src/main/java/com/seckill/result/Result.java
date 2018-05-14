package com.seckill.result;

/**
 * @author Darwin
 * @date 2018/5/10
 */
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    private Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    private Result(CodeMsg cm) {
        if (cm == null) {
            return;
        }
        this.msg = cm.getMsg();
        this.code = cm.getCode();
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(data);
    }

    public static Result error(CodeMsg cm) {
        return new Result(cm);
    }

    public Result() {}

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

}
