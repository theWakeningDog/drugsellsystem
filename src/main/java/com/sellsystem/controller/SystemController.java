package com.sellsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhangwei on 2017/5/22.
 */
@Controller
@RequestMapping("/system")
public class SystemController {

    @GetMapping("")
    public String warehouse() {
        return "/system/warehouse";
    }
}
