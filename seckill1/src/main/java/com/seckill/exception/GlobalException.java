package com.seckill.exception;

import com.seckill.result.CodeMsg;

/**
 *
 * @author Darwin
 * @date 2018/5/11
 */
public class GlobalException extends RuntimeException {

    private CodeMsg cm;
    public GlobalException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }

    public void setCm(CodeMsg cm) {
        this.cm = cm;
    }
}
