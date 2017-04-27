package com.sellsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhangwei on 2017/3/11.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 登陆
     * @return
     */
    @RequestMapping("")
    public String index() {
        return "index";
    }

    /**
     * 首页
     * @return
     */
    @RequestMapping("/home")
    public String home(Model model) {
        model.addAttribute("login", "login in  project");
        return "home";
    }
}
