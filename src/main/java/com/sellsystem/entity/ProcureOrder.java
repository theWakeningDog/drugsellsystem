package com.sellsystem.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 采购单
 * Created by zhangwei on 2017/3/4/004.
 */
public class ProcureOrder {
    private String id;
    //供应商
    private Supplier supplier;
    //采购员
    //private Procurer procurer;
    //药品
    private List<Drug> drugList = new ArrayList<>();
   /* //药品编号
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
    private Warehouse warehouse;*/

   private Boolean isDelete;
    private Date createTime;
    private String remark;

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

    /*public Procurer getProcurer() {
        return procurer;
    }

    public void setProcurer(Procurer procurer) {
        this.procurer = procurer;
    }*/

    public List<Drug> getDrugList() {
        return drugList;
    }

    public void setDrugList(List<Drug> drugList) {
        this.drugList = drugList;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
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
}
