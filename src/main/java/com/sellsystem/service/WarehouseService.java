package com.sellsystem.service;

import com.github.pagehelper.PageInfo;
import com.sellsystem.entity.Warehouse;
import com.sellsystem.entity.searchmodel.extend.WarehouseSearchModel;
import com.sellsystem.util.MsgModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangwei on 2017/3/18.
 */
public interface WarehouseService {
    /**
     * 列表
     * @param warehouseSearchModel
     * @return
     */
    public MsgModel<PageInfo<Map<?, ?>>> getList(WarehouseSearchModel warehouseSearchModel);

    /**
     * 列表
     * @return
     */
    MsgModel<PageInfo<Warehouse>> getWList();

    /**
     * 详情
     * @param warehouseId
     * @return
     */
    public MsgModel<Warehouse> getWarehouse(String warehouseId);

    /**
     * 新增
     * @param warehouse
     * @return
     */
    public MsgModel<String> create(Warehouse warehouse);

    /**
     * 修改
     * @param warehouse
     * @return
     */
    public MsgModel update(Warehouse warehouse);

    /**
     * 删除
     * @param warehouseId
     * @return
     */
    public MsgModel delete(String warehouseId);

    /**
     * 批量创建
     * @param data
     * @return
     */
    MsgModel createW(ArrayList<String> data);
}
