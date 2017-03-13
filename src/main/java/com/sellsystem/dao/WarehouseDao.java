package com.sellsystem.dao;

import com.sellsystem.entity.Warehouse;
import com.sellsystem.entity.searchmodel.extend.WarehouseSearchModel;

import java.util.List;

/**
 * Created by zhangwei on 2017/3/13.
 */
public interface WarehouseDao {

    /**
     * 列表
     * @param warehouseSearchModel
     * @return
     */
    List<Warehouse> getList(WarehouseSearchModel warehouseSearchModel);
}
