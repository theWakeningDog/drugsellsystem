package com.sellsystem.entity;

import java.util.Date;

/**
 * 销售记录
 * Created by zhangwei on 2017/5/23.
 */
public class SaleRecord {
    private String id;
    private Drug drug;
    private Integer number;
    private Date saleDate;

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

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    @Override
    public String toString() {
        return "SaleRecord{" +
                "id='" + id + '\'' +
                ", drug=" + drug +
                ", number=" + number +
                ", saleDate=" + saleDate +
                '}';
    }
}
