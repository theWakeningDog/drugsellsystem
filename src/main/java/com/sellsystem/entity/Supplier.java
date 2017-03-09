package com.sellsystem.entity;

import java.util.Date;

/**
 * 供应商
 * Created by zhangwei on 2017/3/4/004.
 */
public class Supplier {
    //uuid
    private String id;

    private String name;
    //创建时间
    private Date createTIme;
    //备注
    private String remark;

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

    public Date getCreateTIme() {
        return createTIme;
    }

    public void setCreateTIme(Date createTIme) {
        this.createTIme = createTIme;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
