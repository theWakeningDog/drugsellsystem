package com.sellsystem.service;

import com.sellsystem.entity.SaleProfit;
import com.sellsystem.entity.searchmodel.extend.SaleProfitSearchModel;

import java.util.List;

/**
 * Created by zhangwei on 2017/5/24.
 */
public interface SaleProfitService {

    List<SaleProfit> getList(SaleProfitSearchModel saleProfitSearchModel);
}
