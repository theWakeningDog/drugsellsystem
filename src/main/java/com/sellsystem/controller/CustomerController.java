package com.sellsystem.controller;

import com.sellsystem.entity.Customer;
import com.sellsystem.entity.searchmodel.extend.CustomerSearchModel;
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

import java.util.Arrays;

/**
 * Created by zhangwei on 2017/3/17.
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private TaskService taskService;

    /**
     * 列表
     * @param model
     * @param customerSearchModel
     * @return
     */
    @GetMapping("")
    public String list(Model model, CustomerSearchModel customerSearchModel) {
        //搜索条件
        model.addAttribute("customerSearchModel", customerSearchModel);
        //分页数据
        model.addAttribute("customerList", customerService.getList(customerSearchModel.init()).getData().getList());
        return "customer/list";
    }

    /**
     * 详情
     * @param model
     * @param customerId
     * @return
     */
    @GetMapping("/view")
    public String view(Model model, String customerId) {
        model.addAttribute("customer", customerService.getCustomer(customerId).getData());
        TaskSearchModel taskSearchModel = new TaskSearchModel();
        taskSearchModel.setCustomerId(customerId);
        model.addAttribute("taskList", taskService.getList(taskSearchModel.init()).getData().getList());
        return "customer/view";
    }

    /**
     * 保存
     * @param customer
     * @return
     */
    @PostMapping("/save")
    public String save(Customer customer) {
        if (!StringUtils.isEmpty(customer.getId())) {
            customerService.update(customer);
        } else {
            customerService.create(customer);
        }
        return "redirect:/customer";
    }

    /**
     * 编辑
     * @param model
     * @param customerId
     * @return
     */
    @GetMapping("/edit")
    public String edit(Model model, String customerId) {
        if (!StringUtils.isEmpty(customerId)) {
            model.addAttribute("customer", customerService.getCustomer(customerId).getData());
        }
        return "/customer/edit";
    }

    @GetMapping("/delete")
    public String delete(String ids) {
        customerService.delete(Arrays.asList(ids.split(",")));
        return "redirect:/customer";
    }
}
