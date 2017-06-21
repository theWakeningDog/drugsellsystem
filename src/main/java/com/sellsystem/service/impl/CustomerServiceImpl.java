package com.sellsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sellsystem.constant.ClassConstants;
import com.sellsystem.dao.CustomerDao;
import com.sellsystem.entity.Customer;
import com.sellsystem.entity.User;
import com.sellsystem.entity.searchmodel.Sortable;
import com.sellsystem.entity.searchmodel.extend.CustomerSearchModel;
import com.sellsystem.service.CustomerService;
import com.sellsystem.shiro.ShiroUtils;
import com.sellsystem.util.MsgModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangwei on 2017/4/20.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    /**
     * 列表
     * @param customerSearchModel
     * @return
     */
    @Override
    public MsgModel<PageInfo<Customer>> getList(CustomerSearchModel customerSearchModel) {
//        String orderBy = Sortable.getOrderByString(customerSearchModel.getOrderBy());
//        PageHelper.startPage(customerSearchModel.getPageNumber(), customerSearchModel.getPageSize(), orderBy);
        List<Customer> customerList = customerDao.getList(customerSearchModel);
        PageInfo<Customer> customerPageInfo = new PageInfo<>(customerList);
        return new MsgModel<>(customerPageInfo);
    }

    /**
     * 详情
     * @param customerId
     * @return
     */
    @Override
    public MsgModel<Customer> getCustomer(String customerId) {
        return new MsgModel<>(customerDao.getCustomer(customerId));
    }

    /**
     * 新增
     * @param customer
     * @return
     */
    @Override
    public MsgModel<String> create(Customer customer) {
        MsgModel msgModel = new MsgModel();
        try {
            customer.setCreateUser(ShiroUtils.getUser());
            customer.setCreateTime(new Date());
            customerDao.create(customer);
            msgModel.setData(customer.getId());
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(ClassConstants.FAIL);
            msgModel.setData("新建失败");
        }
        return msgModel;
    }

    /**
     * 修改
     * @param customer
     * @return
     */
    @Override
    public MsgModel update(Customer customer) {
        MsgModel msgModel = new MsgModel();
        try {
            //创建人置为空，不修改
            customer.setCreateUser(null);
            customerDao.update(customer);
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(ClassConstants.FAIL);
            msgModel.setData("修改失败");
        }
        return msgModel;
    }

    /**
     * 删除
     * @param customerIdList
     * @return
     */
    @Override
    public MsgModel delete(List<String> customerIdList) {
        MsgModel msgModel = new MsgModel();
        try {
            //customerDao.delete(customerIdList);
            for (String customerId : customerIdList) {
                Customer customer = customerDao.getCustomer(customerId);
                customer.setDel(ClassConstants.successDelete);
                customerDao.update(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            msgModel.setStatus(ClassConstants.FAIL);
            msgModel.setMessage("删除失败");
        }
        return msgModel;
    }
}
