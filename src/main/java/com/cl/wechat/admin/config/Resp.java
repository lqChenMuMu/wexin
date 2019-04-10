package com.cl.wechat.admin.config;

import com.sun.org.apache.bcel.internal.classfile.Code;
import lombok.Data;

import java.io.Serializable;

@Data
public class Resp<T> implements Serializable{

    private T date;

    private Integer code = 0;

    private String msg;

    public Resp() {
    }

    public Resp(T date) {
        this.date = date;
    }

    public Resp(T date, Integer code, String msg) {
        this.date = date;
        this.code = code;
        this.msg = msg;
    }

    public Resp(Throwable e) {
        super();
        this.msg = e.getMessage();
        this.code = 1;
    }
}
