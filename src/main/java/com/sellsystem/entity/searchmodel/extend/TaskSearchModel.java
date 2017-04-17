package com.sellsystem.entity.searchmodel.extend;

import com.sellsystem.entity.searchmodel.PageSearchModel;

/**
 * Created by zhangwei on 2017/3/18.
 */
public class TaskSearchModel extends PageSearchModel {
    private String name;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
