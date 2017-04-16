package com.sellsystem.entity;

import java.util.ArrayList;
import java.util.Date;

/**
 * 任务
 * Created by zhangwei on 2017/3/17.
 */
public class Task {
    public static final String sellType = "销售";
    public static final String buyType = "购买";
    private String id;
    private String name;
    //供应商
    private Supplier supplier;
    //客户
    private Customer customer;
    //任务状态
    private String state;
    //任务类型，购买和销售
    private String type;
    //任务级别
    private String level;
    //处理人
    private User executor;
    //创建人
    private User createUser;
    //附件：任务详情
    private ArrayList<String>  attachment = new ArrayList<>();
    private String description;
    private Date createTime;
    private Date startTime;
    private Date endTime;
    private Boolean isDelete;

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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public User getExecutor() {
        return executor;
    }

    public void setExecutor(User executor) {
        this.executor = executor;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public ArrayList<String> getAttachment() {
        return attachment;
    }

    public void setAttachment(ArrayList<String> attachment) {
        this.attachment = attachment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }
}
