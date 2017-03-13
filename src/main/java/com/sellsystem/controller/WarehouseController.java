package com.sellsystem.controller;

import com.sellsystem.entity.searchmodel.extend.WarehouseSearchModel;
import com.sellsystem.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
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
        return "";
    }
}
