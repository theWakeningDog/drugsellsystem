package com.sellsystem.controller;

import com.sellsystem.entity.Customer;
import com.sellsystem.entity.Drug;
import com.sellsystem.entity.Task;
import com.sellsystem.entity.User;
import com.sellsystem.entity.searchmodel.extend.*;
import com.sellsystem.service.*;
import com.sellsystem.util.MsgModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangwei on 2017/3/17.
 */
@Controller
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private SortService sortService;
    @Autowired
    private DrugService drugService;
    @Autowired
    private UserService userService;
    @Autowired
    private SystemService systemService;

    /**
     * 列表
     *
     * @param model
     * @param taskSearchModel
     * @return
     */
    @GetMapping("")
    public String purchaseList(Model model, TaskSearchModel taskSearchModel) {
        //搜索条件
        model.addAttribute("taskSearchModel", taskSearchModel);
        //分页数据
        List<Task> purchaseTaskList = new ArrayList<>();
        List<Task> taskList = taskService.getList(taskSearchModel.init()).getData().getList();
        for (Task t : taskList) {
            if ("购买".equals(t.getType())) {
                purchaseTaskList.add(t);
            }
        }
        model.addAttribute("taskList", purchaseTaskList);
        //搜索的user
        User user = userService.getUser(taskSearchModel.getExecutor()).getData();
        model.addAttribute("selectUser", user);
        return "task/purchaseList";
    }

    /**
     * 采购详情
     *
     * @param model
     * @param taskId
     * @return
     */
    @GetMapping("/view")
    public String purchaseView(Model model, String taskId) {
        model.addAttribute("task", taskService.getTask(taskId).getData());
        return "task/purchaseView";
    }

    /**
     * 保存
     *
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
        return "redirect:/purchase";
    }

    /**
     * 编辑
     *
     * @param model
     * @param taskId
     * @return
     */
    @GetMapping("/edit")
    public String edit(Model model, String taskId) {
        if (!StringUtils.isEmpty(taskId)) {
            Task task = taskService.getTask(taskId).getData();
            model.addAttribute("task", task);
        }
//        model.addAttribute("customerList", customerService.getList(new CustomerSearchModel().init()).getData().getList());
//        model.addAttribute("userList", userService.getList(new UserSearchModel().init()).getData().getList());
        return "/task/purchaseEdit";
    }

    /**
     * 用户列表
     * @return
     */
    @ResponseBody
    @GetMapping("/user/list")
    public List<User> userList(UserSearchModel userSearchModel) {
        return userService.getList(userSearchModel.init()).getData().getList();
    }

    /**
     * 客户列表
     * @return
     */
    @ResponseBody
    @GetMapping("/customer/list")
    public List<Customer> customerList() {
        return customerService.getList(new CustomerSearchModel().init()).getData().getList();
    }

    @GetMapping("/delete")
    public String delete(String ids) {
        if (!StringUtils.isEmpty(ids)) {
            List<String> idList = Arrays.asList(ids.split(","));
            taskService.delete(idList);
        }
        return "redirect:/purchase";
    }

    /*============================任务流程开始======================================*/

    /**
     * 指派
     *
     * @param task
     * @return
     */
//    @GetMapping("/allot")
//    public String allot(Task task) {
//        taskService.allotTask(task);
//        return "redirect:/purchase";
//    }

    /**
     * 填写回执表单
     *
     * @param task
     * @return
     */
    @GetMapping("/receipt")
    public String receipt(Model model, Task task, boolean page) {
        model.addAttribute("task", taskService.getTask(task.getId()).getData());
        model.addAttribute("warehouseList", warehouseService.getWList().getData().getList());
        model.addAttribute("sortList", sortService.listSort().getData());
        model.addAttribute("drugList", drugService.getList((new DrugSearchModel()).init()).getData().getList());
        model.addAttribute("unitList", systemService.get().getValArr());
        if (page) return "/task/receiptPurchase";
        else return "/task/receiptSale";
    }

    @ResponseBody
    @GetMapping("/drugList")
    public List<Drug> drugList() {
        return drugService.getList((new DrugSearchModel()).init()).getData().getList();
    }

    /**
     * 完成,药品的相应变化
     *
     * @param receiptForm
     * @return
     */
    @ResponseBody
    @PostMapping("/finish")
    public MsgModel finish(@RequestBody ReceiptForm receiptForm) {
        return receiptForm.getType() ? taskService.finishPurchaseTask(receiptForm.getTask(), receiptForm.getDrugList()) : taskService.finishSaleTask(receiptForm.getTask(), receiptForm.getDrugList());
    }

    /**
     * 关闭
     *
     * @param task
     * @return
     */
    @GetMapping("/close")
    public String close(Task task) {
        taskService.closeTask(task);
        return "redirect:/purchase";
    }

    /**
     * 取消
     *
     * @param task
     * @return
     */
    @GetMapping("/off")
    public String off(Task task) {
        taskService.offTask(task);
        return "redirect:/purchase";
    }
    /*============================任务流程结束======================================*/

    @GetMapping("/detail")
    public String detail(Model model, String taskId) {
        model.addAttribute("drugTaskList", taskService.getDrugByTask(taskId));
        model.addAttribute("task", taskService.getTask(taskId).getData());
        return "/task/purchaseDetail";
    }

    /**
     * 前端日期处理
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
    }

        public static class ReceiptForm {
        private Task task;
        private List<Drug> drugList = new ArrayList<>();
        private Boolean type = true;   //true:购买，false:销售

        public Task getTask() {
            return task;
        }

        public void setTask(Task task) {
            this.task = task;
        }

        public List<Drug> getDrugList() {
            return drugList;
        }

        public void setDrugList(List<Drug> drugList) {
            this.drugList = drugList;
        }

        public Boolean getType() {
            return type;
        }

        public void setType(Boolean type) {
            this.type = type;
        }
    }
}
