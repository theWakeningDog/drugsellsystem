package com.sellsystem.controller;

import com.sellsystem.entity.Drug;
import com.sellsystem.entity.Warehouse;
import com.sellsystem.entity.searchmodel.extend.DrugSearchModel;
import com.sellsystem.entity.searchmodel.extend.WarehouseSearchModel;
import com.sellsystem.service.DrugService;
import com.sellsystem.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 药品
 * Created by Administrator on 2017/3/4/004.
 */
@Controller
@RequestMapping(value = "/drug")
public class DrugController {

    @Autowired
    private DrugService drugService;
    @Autowired
    private WarehouseService warehouseService;

    /**
     * 列表
     * @param model
     * @param drugSearchModel
     * @return
     */
    @GetMapping("")
    public String list(Model model, DrugSearchModel drugSearchModel) {
        model.addAttribute("drugList", drugService.getList(drugSearchModel).getData().getList());
        WarehouseSearchModel warehouseSearchModel = new WarehouseSearchModel();
        warehouseSearchModel.setPageSize(0);
        List<Warehouse> warehouseList = warehouseService.getList(warehouseSearchModel).getData().getList();
        List<Warehouse> warehouses = new ArrayList<>();
        for (Warehouse warehouse : warehouseList) {
            warehouse.setDrugNum(warehouse.getDrugList().size());
            warehouses.add(warehouse);
        }
        model.addAttribute("warehouseList", warehouses);
        return "drug/drugList";
    }

    /**
     * 详情
     * @param model
     * @param drugId
     * @return
     */
    @GetMapping("/view")
    public String view(Model model, String drugId) {
        model.addAttribute("drug", drugService.getDrug(drugId).getData());
        return "drug/view";
    }

    /**
     * 新增
     * @param drug
     * @return
     */
    @PostMapping("/create")
    public String create(Drug drug) {
        drugService.create(drug);
        return "/drug/edit";
    }

    @PutMapping("/update")
    public String three(Drug drug) {
        drugService.update(drug);
        return "/drug/edit";
    }


}
