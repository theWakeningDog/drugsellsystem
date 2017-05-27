package com.sellsystem.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 登录用户
 * Created by zhangwei on 2017/3/4/004.
 */
public class User implements Serializable {
    private String id;
    private String no;
    private String name;
    private String account;
    private String password;
    private int age;
    private String tel;         //舍弃
    private String email;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private Date createTime;
    private String remark;
    private Integer delete = 0;
    private String salt = "8d78869f470951332959580424d4bf4f";            //加密密码的盐
    private List<Role> roleList = new ArrayList<>();           //舍弃，用于测试shiro

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
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

    public Integer getDelete() {
        return delete;
    }

    public void setDelete(Integer delete) {
        this.delete = delete;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    /**
     * 密码盐
     * @return
     */
    public String getCredentialsSalt() {
        return this.account + this.salt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", no='" + no + '\'' +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", createTime=" + createTime +
                ", remark='" + remark + '\'' +
                ", delete=" + delete +
                ", salt='" + salt + '\'' +
                ", roleList=" + roleList +
                '}';
    }
}
