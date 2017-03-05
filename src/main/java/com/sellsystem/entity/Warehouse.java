package com.sellsystem.entity;

import java.util.Date;

/**
 * 仓库
 * Created by zhangwei on 2017/3/4/004.
 */
public class Warehouse {
    private String id;
    private String name;
    private Date createTime;
    private String mark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
