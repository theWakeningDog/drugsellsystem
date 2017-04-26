package com.sellsystem.dao;

import com.sellsystem.entity.Customer;
import com.sellsystem.entity.searchmodel.extend.CustomerSearchModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhangwei on 2017/3/18.
 */
public interface CustomerDao {

    /**
     * 列表
     * @param customerSearchModel
     * @return
     */
    List<Customer> getList(@Param("customerSearchModel") CustomerSearchModel customerSearchModel);

    /**
     * 详情
     * @param customerId
     * @return
     */
    Customer getCustomer(@Param("customerId") String customerId);

    /**
     * 新增
     * @param customer
     * @return
     */
    int create(@Param("customer") Customer customer);

    /**
     * 修改
     * @param customer
     * @return
     */
    int update(@Param("customer") Customer customer);

    /**
     * 删除
     * @param customerIdList
     * @return
     */
    int delete(@Param("customerIdList") List<String> customerIdList);
}
