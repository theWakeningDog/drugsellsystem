package com.sellsystem.service.impl;

import com.sellsystem.dao.SaleRecordDao;
import com.sellsystem.entity.SaleRecord;
import com.sellsystem.service.SaleRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangwei on 2017/5/23.
 */
@Service
public class SaleRecordServiceImpl implements SaleRecordService {

    @Autowired
    private SaleRecordDao saleRecordDao;

    @Override
    public List<SaleRecord> getList() {
        return saleRecordDao.getList();
    }
}
