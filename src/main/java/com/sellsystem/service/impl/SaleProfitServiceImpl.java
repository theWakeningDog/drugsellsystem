package com.sellsystem.service.impl;

import com.sellsystem.dao.SaleProfitDao;
import com.sellsystem.entity.SaleProfit;
import com.sellsystem.entity.searchmodel.extend.SaleProfitSearchModel;
import com.sellsystem.service.SaleProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangwei on 2017/5/24.
 */
@Service
public class SaleProfitServiceImpl implements SaleProfitService {

    @Autowired
    private SaleProfitDao saleProfitDao;

    @Override
    public List<SaleProfit> getList(SaleProfitSearchModel saleProfitSearchModel) {
        return saleProfitDao.getList(saleProfitSearchModel);
    }
}
