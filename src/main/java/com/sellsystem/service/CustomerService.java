package com.sellsystem.service;

import com.github.pagehelper.PageInfo;
import com.sellsystem.entity.Customer;
import com.sellsystem.entity.Task;
import com.sellsystem.entity.searchmodel.extend.CustomerSearchModel;
import com.sellsystem.entity.searchmodel.extend.TaskSearchModel;
import com.sellsystem.util.MsgModel;

import java.util.List;

/**
 * Created by zhangwei on 2017/3/18.
 */
public interface CustomerService {

    /**
     * 列表
     * @param customerSearchModel
     * @return
     */
    MsgModel<PageInfo<Customer>> getList(CustomerSearchModel customerSearchModel);

    /**
     * 详情
     * @param customerId
     * @return
     */
    MsgModel<Customer> getCustomer(String customerId);

    /**
     * 新增
     * @param customer
     * @return
     */
    MsgModel<String> create(Customer customer);

    /**
     * 修改
     * @param customer
     * @return
     */
    MsgModel update(Customer customer);

    /**
     * 删除
     * @param customerIdList
     * @return
     */
    MsgModel delete(List<String> customerIdList);

}
