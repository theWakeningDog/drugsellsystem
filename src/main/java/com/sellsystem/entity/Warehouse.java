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
    //药材数量
    private int drugNum;
    private List<Drug> drugList = new ArrayList<>();

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

    public int getDrugNum() {
        return drugNum;
    }

    public void setDrugNum(int drugNum) {
        this.drugNum = drugNum;
    }

    public List<Drug> getDrugList() {
        return drugList;
    }

    public void setDrugList(List<Drug> drugList) {
        this.drugList = drugList;
    }
}
