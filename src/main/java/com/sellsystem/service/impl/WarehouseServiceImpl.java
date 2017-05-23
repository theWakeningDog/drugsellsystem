package com.sellsystem.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sellsystem.constant.ClassConstants;
import com.sellsystem.dao.WarehouseDao;
import com.sellsystem.entity.Warehouse;
import com.sellsystem.entity.searchmodel.Sortable;
import com.sellsystem.entity.searchmodel.extend.WarehouseSearchModel;
import com.sellsystem.service.WarehouseService;
import com.sellsystem.util.MsgModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 仓库
 * Created by zhangwei on 2017/3/13.
 */
@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseDao warehouseDao;

    /**
     * 列表
     *
     * @param warehouseSearchModel
     * @return
     */
    public MsgModel<PageInfo<Map<?, ?>>> getList(WarehouseSearchModel warehouseSearchModel) {
        String orderBy = Sortable.getOrderByString(warehouseSearchModel.getOrderBy());
        PageHelper.startPage(warehouseSearchModel.getPageNumber(), warehouseSearchModel.getPageSize(), orderBy);
        List<Map<?, ?>> warehouseList = warehouseDao.getList(warehouseSearchModel);
        PageInfo warehousePageInfo = new PageInfo<>(warehouseList);
        return new MsgModel<>(warehousePageInfo);
    }

    public MsgModel<PageInfo<Warehouse>> getWList() {
//        String orderBy = Sortable.getOrderByString(warehouseSearchModel.getOrderBy());
//        PageHelper.startPage(warehouseSearchModel.getPageNumber(), warehouseSearchModel.getPageSize(), orderBy);
        List<Warehouse> warehouseList = warehouseDao.getWList();
        PageInfo warehousePageInfo = new PageInfo<>(warehouseList);
        return new MsgModel<>(warehousePageInfo);
    }

    /**
     * 详情
     *
     * @param warehouseId
     * @return
     */
    public MsgModel<Warehouse> getWarehouse(String warehouseId) {
        return new MsgModel<>(warehouseDao.getWarehouse(warehouseId));
    }

    /**
     * 新增
     *
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
            msgModel.setStatus(ClassConstants.FAIL);
            msgModel.setMessage("新增失败");
        }
        return msgModel;
    }

    /**
     * 修改
     *
     * @param warehouse
     * @return
     */
    public MsgModel update(Warehouse warehouse) {
        MsgModel msgModel = new MsgModel();
        try {
            warehouseDao.update(warehouse);
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(ClassConstants.FAIL);
            msgModel.setMessage("修改失败");
        }
        return msgModel;
    }

    /**
     * 删除
     *
     * @param warehouseId
     * @return
     */
    public MsgModel delete(String warehouseId) {
        MsgModel msgModel = new MsgModel();
        try {
            warehouseDao.delete(warehouseId);
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(ClassConstants.FAIL);
            msgModel.setMessage("删除失败");
        }
        return msgModel;
    }

    /**
     * 批量创建
     *
     * @param data
     * @return
     */
    @Override
    public MsgModel createW(ArrayList<String> data) {
        MsgModel msgModel = new MsgModel();
        try {
            List<Warehouse> wList = warehouseDao.getWList();
            for (Warehouse w : wList) {
                w.setDel(ClassConstants.successDelete);
                warehouseDao.update(w);
            }
            for (int i = 0; i < data.size(); i++) {
                Warehouse w = new Warehouse();
                w.setName(data.get(i));
                w.setOrders(i);
                w.setCreateTime(new Date());
                warehouseDao.create(w);
            }
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(ClassConstants.FAIL);
            msgModel.setMessage(ClassConstants.WAREHOUSE_OPT_FAIL);
        }
        return msgModel;
    }
}
