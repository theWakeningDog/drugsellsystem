package com.sellsystem.entity;

import java.util.Date;

/**
 * 采购单
 * Created by zhangwei on 2017/3/4/004.
 */
public class ProcureOrder {
    private String id;
    //供应商
    private Supplier supplier;
    //采购员
    private Procurer procurer;
    //药品编号
    private String drugNo;
    //药品名称
    private String drugName;
    //药品产地
    private String drugOrigin;
    //药品单位
    private String drugUnit;
    //药品数量
    private String drugNumber;
    //药品进价
    private Double drugPurchase;
    //一项药品总价格
    private Double drugTotalPurchase;
    //所有药品总价格
    private Double drugTotalPrice;
    //所属仓库
    private Warehouse warehouse;
    private Date createTime;
    private String mark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Procurer getProcurer() {
        return procurer;
    }

    public void setProcurer(Procurer procurer) {
        this.procurer = procurer;
    }

    public String getDrugNo() {
        return drugNo;
    }

    public void setDrugNo(String drugNo) {
        this.drugNo = drugNo;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugOrigin() {
        return drugOrigin;
    }

    public void setDrugOrigin(String drugOrigin) {
        this.drugOrigin = drugOrigin;
    }

    public String getDrugUnit() {
        return drugUnit;
    }

    public void setDrugUnit(String drugUnit) {
        this.drugUnit = drugUnit;
    }

    public String getDrugNumber() {
        return drugNumber;
    }

    public void setDrugNumber(String drugNumber) {
        this.drugNumber = drugNumber;
    }

    public Double getDrugPurchase() {
        return drugPurchase;
    }

    public void setDrugPurchase(Double drugPurchase) {
        this.drugPurchase = drugPurchase;
    }

    public Double getDrugTotalPurchase() {
        return drugTotalPurchase;
    }

    public void setDrugTotalPurchase(Double drugTotalPurchase) {
        this.drugTotalPurchase = drugTotalPurchase;
    }

    public Double getDrugTotalPrice() {
        return drugTotalPrice;
    }

    public void setDrugTotalPrice(Double drugTotalPrice) {
        this.drugTotalPrice = drugTotalPrice;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
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
