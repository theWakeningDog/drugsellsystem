package com.sellsystem.dao;

import com.sellsystem.entity.SaleProfit;
import com.sellsystem.entity.searchmodel.extend.SaleProfitSearchModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhangwei on 2017/5/24.
 */
public interface SaleProfitDao {
    List<SaleProfit> getList(@Param("saleProfitSearchModel") SaleProfitSearchModel saleProfitSearchModel);

    int create(@Param("saleProfit") SaleProfit saleProfit);

    int update(@Param("saleProfit") SaleProfit saleProfit);
}
