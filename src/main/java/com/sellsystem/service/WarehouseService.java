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
}
