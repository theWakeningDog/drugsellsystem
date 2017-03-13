package com.sellsystem.controller;

import com.sellsystem.entity.Drug;
import com.sellsystem.entity.searchmodel.extend.DrugSearchModel;
import com.sellsystem.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 药品
 * Created by Administrator on 2017/3/4/004.
 */
@Controller
@RequestMapping(value = "/drug")
public class DrugController {

    @Autowired
    private DrugService drugService;

    /**
     * 列表
     * @param model
     * @param drugSearchModel
     * @return
     */
    @GetMapping("")
    public String list(Model model, DrugSearchModel drugSearchModel) {
        model.addAttribute("drugList", drugService.getList(drugSearchModel).getData().getList());
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
