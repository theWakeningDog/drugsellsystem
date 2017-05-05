package com.sellsystem.controller;

import com.sellsystem.entity.Record;
import com.sellsystem.entity.Task;
import com.sellsystem.entity.searchmodel.extend.CustomerSearchModel;
import com.sellsystem.entity.searchmodel.extend.RecordSearchModel;
import com.sellsystem.entity.searchmodel.extend.TaskSearchModel;
import com.sellsystem.service.CustomerService;
import com.sellsystem.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by zhangwei on 2017/3/17.
 */
@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private CustomerService customerService;

    /**
     * 列表
     * @param model
     * @param taskSearchModel
     * @return
     */
    @GetMapping("")
    public String list(Model model, TaskSearchModel taskSearchModel) {
        //搜索条件
        model.addAttribute("taskSearchModel", taskSearchModel);
        //分页数据
        model.addAttribute("taskList", taskService.getList(taskSearchModel).getData().getList());
        return "task/list";
    }

    /**
     * 详情
     * @param model
     * @param taskId
     * @return
     */
    @GetMapping("/view")
    public String view(Model model, String taskId) {
        model.addAttribute("task", taskService.getTask(taskId).getData());
        return "task/view";
    }

    /**
     * 保存
     * @param task
     * @return
     */
    @PostMapping("/save")
    public String save(Task task) {
        if (!StringUtils.isEmpty(task.getId())) {
            taskService.update(task);
        } else {
            taskService.create(task);
        }
        return "redirect:/task";
    }

    /**
     * 编辑
     * @param model
     * @param taskId
     * @return
     */
    @GetMapping("/edit")
    public String edit(Model model, String taskId) {
        if (!StringUtils.isEmpty(taskId)) {
            model.addAttribute("task", taskService.getTask(taskId).getData());
        }
        model.addAttribute("customerList", customerService.getList(new CustomerSearchModel()).getData().getList());
        return "/task/edit";
    }

    /**
     * 指派
     * @param task
     * @return
     */
    @GetMapping("/allot")
    public String allotTask(Task task) {
        taskService.allotTask(task);
        return "redirect:/task";
    }
}
