package com.sellsystem.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * （舍弃）用于测试Shiro
 * Created by zhangwei on 2017/4/26.
 */
public class Role {
    private String name;
    private List<String> permissionNameList = new ArrayList<>();
    private List<RolePermission> permissionList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPermissionNameList() {
        return permissionNameList;
    }

    public void setPermissionNameList(List<String> permissionNameList) {
        this.permissionNameList = permissionNameList;
    }

    public List<RolePermission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<RolePermission> permissionList) {
        this.permissionList = permissionList;
    }
}
