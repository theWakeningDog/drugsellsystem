package com.sellsystem.controller;

import com.sellsystem.entity.searchmodel.extend.TaskSearchModel;
import com.sellsystem.entity.searchmodel.extend.UserSearchModel;
import com.sellsystem.service.TaskService;
import com.sellsystem.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zhangwei on 2017/3/11.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TaskService t;

    /**
     * 用户列表
     * @param model
     * @param userSearchModel
     * @return
     */
    @GetMapping("")
    public String list(Model model, UserSearchModel userSearchModel) {
        model.addAttribute("userSearchModel", userSearchModel);
        model.addAttribute("userList", userService.getList(userSearchModel.init()).getData().getList());
        return "/user/list";
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @GetMapping("/delete")
    public String delete(String ids) {
        if (!StringUtils.isEmpty(ids)) {
            List<String> idList = Arrays.asList(ids.split(","));
            userService.delete(idList);
        }
        return "redirect:/user";
    }

    /**
     * 详情
     *
     * @param model
     * @param userId
     * @return
     */
    @GetMapping("/view")
    public String saleView(Model model, String userId) {
        TaskSearchModel taskSearchModel = new TaskSearchModel();
        taskSearchModel.setExecutor(userId);
        model.addAttribute("taskList", t.getList(taskSearchModel).getData().getList());
        model.addAttribute("user", userService.getUser(userId).getData());
        return "user/view";
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
