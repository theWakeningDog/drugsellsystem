package com.sellsystem.util;

import java.util.List;

/**
 * 消息提示
 * Created by zhangwei on 2017/3/4/004.
 */
public class MsgModel<T> {
    public static final int SUCCESS = 1;
    public static final int FAIL = 0;
    private int status;
    private String message;
    private T data;

    public MsgModel(T data) {
        this.status = SUCCESS;
        this.data = data;
    }

    public MsgModel() {
    }

    public MsgModel(String message, T data) {
        this.message = "ok";
        this.data = data;
    }

    public MsgModel(int status, String message, T data) {
        this.status = SUCCESS;
        this.message = "ok";
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
