package com.sellsystem.entity;

import java.util.Date;

/**
 * 类别
 * Created by zhangwei on 2017/3/4/004.
 */
public class Sort {
    private String id;
    private String name;
    private String pid;
    private Date createTime;
    private String remark;
    private Integer orders;
    private Integer del = 0;

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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }

    @Override
    public String toString() {
        return "Sort{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pid='" + pid + '\'' +
                ", createTime=" + createTime +
                ", remark='" + remark + '\'' +
                ", orders=" + orders +
                ", del=" + del +
                '}';
    }
}
