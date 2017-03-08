package com.sellsystem.controller;

import com.sellsystem.entity.searchmodel.extend.DrugSearchModel;
import com.sellsystem.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 药品
 * Created by Administrator on 2017/3/4/004.
 */
@Controller
@RequestMapping(value = "/drug")
public class DrugController {

    @Autowired
    private DrugService drugService;

    @GetMapping("/list")
    public String list(Model model, DrugSearchModel drugSearchModel) {
        model.addAttribute("drugList", drugService.getList(drugSearchModel).getData());
        return "index";
    }

    @GetMapping("/li")
    public String li(Model model, DrugSearchModel drugSearchModel) {
        //model.addAttribute("drugList", drugService.getList(drugSearchModel).getData());
        return "drug";
    }

    @GetMapping("/two")
    public String two(Model model, DrugSearchModel drugSearchModel) {
        //model.addAttribute("drugList", drugService.getList(drugSearchModel).getData());
        return "user";
    }

    @GetMapping("/three")
    public String three(Model model, DrugSearchModel drugSearchModel) {
        //model.addAttribute("drugList", drugService.getList(drugSearchModel).getData());
        return "procureOrder";
    }
}
