package com.sellsystem.controller;

import com.sellsystem.entity.searchmodel.extend.ProcureOrderSearchModel;
import com.sellsystem.service.ProcureOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhangwei on 2017/3/12.
 */
@Controller
@RequestMapping("/procureOrder")
public class ProcureOrderController {

    @Autowired
    private ProcureOrderService procureOrderService;

    public String list(Model model, ProcureOrderSearchModel procureOrderSearchModel) {
        model.addAttribute("procureOrderList", procureOrderService.getList(procureOrderSearchModel).getData().getList());
        return "/procureOrder/list";
    }
}
