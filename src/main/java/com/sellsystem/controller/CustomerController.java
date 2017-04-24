package com.sellsystem.controller;

import com.sellsystem.entity.Customer;
import com.sellsystem.entity.searchmodel.extend.CustomerSearchModel;
import com.sellsystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhangwei on 2017/3/17.
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

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
        model.addAttribute("customerList", customerService.getList(customerSearchModel).getData().getList());
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

    @GetMapping("/edit")
    public String edit(Model model, String customerId) {
        if (!StringUtils.isEmpty(customerId)) {
            model.addAttribute("task", customerService.getCustomer(customerId).getData());
        }
        return "/task/edit";
    }
}
