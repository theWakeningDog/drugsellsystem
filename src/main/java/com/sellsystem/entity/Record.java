package com.sellsystem.entity;

import java.util.Date;

/**
 * 任务日志
 * Created by zhangwei on 2017/5/2.
 */
public class Record {
    private String id;
    private Task task;
    private User executor;
    private Customer customer;
    private String action;
    private Drug drug;
    private int drugNumber;
    private Date createTime;

    public Record() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getExecutor() {
        return executor;
    }

    public void setExecutor(User executor) {
        this.executor = executor;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public int getDrugNumber() {
        return drugNumber;
    }

    public void setDrugNumber(int drugNumber) {
        this.drugNumber = drugNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id='" + id + '\'' +
                ", task=" + task +
                ", executor=" + executor +
                ", customer=" + customer +
                ", action='" + action + '\'' +
                ", drug=" + drug +
                ", drugNumber=" + drugNumber +
                ", createTime=" + createTime +
                '}';
    }
}
