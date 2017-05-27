package com.sellsystem.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangwei on 2017/5/27.
 */
public class Setting {
    private Integer id;
    private String keys = "drugUnit";
    private ArrayList<String> valArr = new ArrayList<String>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public ArrayList<String> getValArr() {
        return valArr;
    }

    public void setValArr(ArrayList<String> valArr) {
        this.valArr = valArr;
    }
}
