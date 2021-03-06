package com.sellsystem.controller;

import com.sellsystem.entity.Warehouse;
import com.sellsystem.entity.searchmodel.extend.WarehouseSearchModel;
import com.sellsystem.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 没用到
 * Created by zhangwei on 2017/3/13.
 */
@Controller
@RequestMapping("/warehouse")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    /**
     * 列表
     * @param model
     * @param warehouseSearchModel
     * @return
     */
    @GetMapping("")
    public String list(Model model, WarehouseSearchModel warehouseSearchModel) {
        model.addAttribute("warehouseList", warehouseService.getList(warehouseSearchModel));
        return "/kucun/warehouse";
    }

    /**
     * 详情
     * @param model
     * @param warehouseId
     * @return
     */
    @GetMapping("/warehouse")
    public String warehouse(Model model, String warehouseId) {
        model.addAttribute("warehouse", warehouseService.getWarehouse(warehouseId));
        return "";
    }

    /**
     * 新增
     * @param warehouse
     * @return
     */
    @PostMapping("/create")
    public String create(Warehouse warehouse) {
        warehouseService.create(warehouse);
        return "";
    }

    /**
     * 修改
     * @param warehouse
     * @return
     */
    @PutMapping("/update")
    public String update(Warehouse warehouse) {
        warehouseService.update(warehouse);
        return "";
    }

    /**
     * 删除
     * @param model
     * @param warehouseId
     * @return
     */
    @DeleteMapping("/delete")
    public String delete(Model model, String warehouseId) {
        model.addAttribute("deleteWarehouse", warehouseService.delete(warehouseId));
        return "";
    }
}
