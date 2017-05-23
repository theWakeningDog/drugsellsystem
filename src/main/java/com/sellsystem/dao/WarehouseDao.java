package com.sellsystem.dao;

import com.sellsystem.entity.Warehouse;
import com.sellsystem.entity.searchmodel.extend.WarehouseSearchModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangwei on 2017/3/13.
 */
public interface WarehouseDao {

    /**
     * 列表
     * @param warehouseSearchModel
     * @return
     */
    List<Map<?, ?>> getList(WarehouseSearchModel warehouseSearchModel);

    /**
     * 详情
     * @param warehouseId
     * @return
     */
    Warehouse getWarehouse(String warehouseId);

    /**
     * 新增
     * @param warehouse
     * @return
     */
    int create(@Param("warehouse") Warehouse warehouse);

    /**
     * 修改
     * @param warehouse
     * @return
     */
    int update(@Param("warehouse") Warehouse warehouse);

    /**
     * 删除
     * @param warehouseId
     * @return
     */
    int delete(String warehouseId);

    List<Warehouse> getWList();
}
