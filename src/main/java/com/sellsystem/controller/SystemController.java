package com.sellsystem.controller;

import com.sellsystem.entity.searchmodel.extend.WarehouseSearchModel;
import com.sellsystem.service.SortService;
import com.sellsystem.service.SystemService;
import com.sellsystem.service.WarehouseService;
import com.sellsystem.util.MsgModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Created by zhangwei on 2017/5/22.
 */
@Controller
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private SortService sortService;
    @Autowired
    private SystemService systemService;

    @GetMapping("/warehouse")
    public String warehouse(Model model) {
        model.addAttribute("warehouseList", warehouseService.getWList().getData().getList());
        return "/system/warehouse";
    }

    @GetMapping("/sort")
    public String sort(Model model) {
        model.addAttribute("sortList", sortService.listSort().getData());
        return "/system/sort";
    }

    @GetMapping("/drugUnit")
    public String unit(Model model) {
        model.addAttribute("unitList", systemService.get().getValArr());
        return "/system/drugUnit";
    }

    /**
     *  保存仓库设置
     * @param data
     * @return
     */
    @ResponseBody
    @PostMapping("/warehouse/save")
    public MsgModel warehouseSave(@RequestParam(value = "data[]") ArrayList<String> data)  {
        return warehouseService.createW(data);
    }

    /**
     *  保存类别设置
     * @param data
     * @return
     */
    @ResponseBody
    @PostMapping("/sort/save")
    public MsgModel sortSave(@RequestParam(value = "data[]") ArrayList<String> data)  {
        return sortService.createS(data);
    }

    /**
     *  保存类别设置
     * @param data
     * @return
     */
    @ResponseBody
    @PostMapping("/unit/save")
    public MsgModel unitSave(@RequestParam(value = "data[]") ArrayList<String> data)  {

        return systemService.update(data);
    }
}
