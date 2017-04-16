package com.sellsystem.entity.enumerate;

/**
 * Created by zhangwei on 2017/4/16.
 */
public enum TaskTarget {
    created("created", "已创建"),
    allotted("allotted", "已指派"),
    accepted("accepted", "已接受"),
    finished("finished", "已完成"),
    cost("cost", "已结算"),
    closed("closed", "已关闭"),
    refused("refused", "已拒绝"),
    offed("offed", "已取消");
    private String target;
    private String value;

    TaskTarget(String target, String value) {
        this.target = target;
        this.value = value;
    }

    TaskTarget() {
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
