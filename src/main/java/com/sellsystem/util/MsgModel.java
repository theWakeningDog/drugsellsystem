package com.sellsystem.util;

import static com.sellsystem.constant.ClassConstants.SUCCESS;

/**
 * 消息提示
 * Created by zhangwei on 2017/3/4/004.
 */
public class MsgModel<T> {
    private int status;
    private String message;
    private T data;

    public MsgModel(T data) {
        this.status = SUCCESS;
        this.data = data;
    }

    public MsgModel() {
        this.status = SUCCESS;
        this.message = "ok";
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
