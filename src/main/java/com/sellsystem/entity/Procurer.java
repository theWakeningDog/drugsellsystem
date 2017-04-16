package com.sellsystem.entity;

import java.util.Date;

/**
 * 采购员[删除]
 * Created by zhangwei on 2017/3/4/004.
 */
public class Procurer {
    private String id;
    private String name;
    private int age;
    private String tel;
    private String email;
    private Date CreateTIme;
    private String remark;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTIme() {
        return CreateTIme;
    }

    public void setCreateTIme(Date createTIme) {
        CreateTIme = createTIme;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }
}
