package com.sellsystem.entity.searchmodel.extend;

import com.sellsystem.entity.searchmodel.PageSearchModel;

/**
 * Created by zhangwei on 2017/3/13.
 */
public class WarehouseSearchModel extends PageSearchModel {
    public WarehouseSearchModel() {
    }

    public WarehouseSearchModel init() {
        this.setPageSize(0);
        return this;
    }
}
