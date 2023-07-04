package com.ys.oa.config.exception;

import lombok.Data;

@Data
public class OaException extends RuntimeException{

    private Integer code;
    private String msg;

    public OaException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }


}
