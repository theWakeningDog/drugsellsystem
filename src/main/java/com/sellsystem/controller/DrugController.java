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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        if (StringUtils.isEmpty(drugSearchModel.getWarehouseId())) {
            drugSearchModel.setWarehouseId(null);
        }
        model.addAttribute("drugList", drugService.getList(drugSearchModel).getData().getList());

        //搜索条件
        model.addAttribute("drugSearchModel", drugSearchModel);

        //仓库列表
        WarehouseSearchModel warehouseSearchModel = new WarehouseSearchModel();
        warehouseSearchModel.setPageSize(0);
        List<Map<?, ?>> warehouseList = warehouseService.getList(warehouseSearchModel).getData().getList();
        model.addAttribute("warehouseList", warehouseList);

        //仓库中药品总数量
        int drugAllNum = 0;
        for (Map<?, ?> warehouseMap : warehouseList) {
            drugAllNum += Integer.parseInt(warehouseMap.get("drugNum").toString());
        }
        model.addAttribute("drugAllNum", drugAllNum);
        return "drug/list";
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

    @GetMapping("/analyse")
    public String analyse(Model model) {
        model.addAttribute("drugList", drugService.getDrugLessThan30());
        return "/drug/analyse";
    }
}
