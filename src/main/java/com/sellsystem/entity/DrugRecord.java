package com.sellsystem.entity;

import java.util.Date;

/**
 * 药品记录
 * Created by zhangwei on 2017/5/31.
 */
public class DrugRecord {
    private String id;
    private Drug drug;
    private Integer number;
    private String action;
    private String type;
    private String remark;
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "DrugRecord{" +
                "id='" + id + '\'' +
                ", drug=" + drug +
                ", number=" + number +
                ", action='" + action + '\'' +
                ", type='" + type + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
