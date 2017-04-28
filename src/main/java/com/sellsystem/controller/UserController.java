package com.sellsystem.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
     * 用户查询.（测试shiro权限）
     * @return
     */
    @RequestMapping("/userList")
    public String userInfo(){
        return "userInfo";
    }

    /**
     * 用户添加（测试shiro权限）
     * @return
     */
    @RequestMapping("/userAdd")
    @RequiresPermissions("userInfo:add")
    public String userInfoAdd(){
        return "userInfoAdd";
    }

}
