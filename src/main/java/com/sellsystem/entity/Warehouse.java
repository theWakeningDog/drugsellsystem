package com.sellsystem.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 仓库
 * Created by zhangwei on 2017/3/4/004.
 */
public class Warehouse {
    private String id;
    private String name;
    private Date createTime;
    private String remark;
    private List<Drug> drugList = new ArrayList<>();
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

    public List<Drug> getDrugList() {
        return drugList;
    }

    public void setDrugList(List<Drug> drugList) {
        this.drugList = drugList;
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
        return "Warehouse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createTime=" + createTime +
                ", remark='" + remark + '\'' +
                ", drugList=" + drugList +
                ", orders=" + orders +
                ", del=" + del +
                '}';
    }
}
