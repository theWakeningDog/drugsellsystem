package com.sellsystem.entity;

import java.util.Date;

/**
 * 药品
 * Created by zhangwei on 2017/3/4/004.
 */
public class Drug {
    private String id;
    private String no;//舍弃
    private String name;
    //产地
    private String origin;
    //单位
    private String unit;
    private int number;
    //进价
    private Double purchase;
    //零售价
    private Double retail;
    //有效期
    private Date period;
    //类别
    private Sort sort;
    //仓库
    private Warehouse warehouse;
    //通用名
    private String commonName;//舍弃
    private Date createTime;
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Double getPurchase() {
        return purchase;
    }

    public void setPurchase(Double purchase) {
        this.purchase = purchase;
    }

    public Double getRetail() {
        return retail;
    }

    public void setRetail(Double retail) {
        this.retail = retail;
    }

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
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

    @Override
    public String toString() {
        return "Drug{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", origin='" + origin + '\'' +
                ", unit='" + unit + '\'' +
                ", number=" + number +
                ", purchase=" + purchase +
                ", retail=" + retail +
                ", period=" + period +
                ", sort=" + sort +
                ", warehouse=" + warehouse +
                ", createTime=" + createTime +
                ", remark='" + remark + '\'' +
                '}';
    }
}
