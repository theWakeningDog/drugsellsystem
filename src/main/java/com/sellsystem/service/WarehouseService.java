package com.sellsystem.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sellsystem.dao.WarehouseDao;
import com.sellsystem.entity.Warehouse;
import com.sellsystem.entity.searchmodel.Sortable;
import com.sellsystem.entity.searchmodel.extend.WarehouseSearchModel;
import com.sellsystem.util.MsgModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangwei on 2017/3/13.
 */
@Service
public class WarehouseService {

    @Autowired
    private WarehouseDao warehouseDao;

    /**
     * 列表
     * @param warehouseSearchModel
     * @return
     */
    public MsgModel<PageInfo<Warehouse>> getList(WarehouseSearchModel warehouseSearchModel) {
        String orderBy = Sortable.getOrderByString(warehouseSearchModel.getOrderBy());
        PageHelper.startPage(warehouseSearchModel.getPageNumber(), warehouseSearchModel.getPageSize(), orderBy);
        List<Warehouse> warehouseList = warehouseDao.getList(warehouseSearchModel);
        PageInfo<Warehouse> warehousePageInfo = new PageInfo<>(warehouseList);
        return new MsgModel<>(warehousePageInfo);
    }

    /**
     * 详情
     * @param warehouseId
     * @return
     */
    public MsgModel<Warehouse> getWarehouse(String warehouseId) {
        return new MsgModel<>(warehouseDao.getWarehouse(warehouseId));
    }

    /**
     * 新增
     * @param warehouse
     * @return
     */
    public MsgModel<String> create(Warehouse warehouse) {
        MsgModel msgModel = new MsgModel();
        try {
            warehouse.setCreateTime(new Date());
            warehouseDao.create(warehouse);
            msgModel.setMessage(warehouse.getId());
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(MsgModel.FAIL);
            msgModel.setMessage("新增失败");
        }
        return  msgModel;
    }

    public MsgModel update(Warehouse warehouse) {
        MsgModel msgModel = new MsgModel();
        try {
            warehouseDao.update(warehouse);
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(MsgModel.FAIL);
            msgModel.setMessage("修改失败");
        }
        return  msgModel;
    }

    /**
     * 删除
     * @param warehouseId
     * @return
     */
    public MsgModel delete(String warehouseId) {
        MsgModel msgModel = new MsgModel();
        try {
            warehouseDao.delete(warehouseId);
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(MsgModel.FAIL);
            msgModel.setMessage("删除失败");
        }
        return msgModel;
    }
}
