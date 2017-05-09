package com.sellsystem.entity.enumerate;

/**
 * Created by zhangwei on 2017/4/16.
 */
public enum TaskEventEnum {
    create("task_create", "创建"),
    allot("task_allot", "指派"),
    accept("task_accept", "接受"),
    finish("task_finish", "完成"),
    cost("task_cost", "结算"),
    close("task_close", "关闭"),
    refuse("task_refuse", "拒绝"),
    off("task_off", "取消");

    private String event;
    private String value;

    TaskEventEnum(String event, String value) {
        this.event = event;
        this.value = value;
    }

    TaskEventEnum() {
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
