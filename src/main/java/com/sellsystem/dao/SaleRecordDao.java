package com.sellsystem.dao;

import com.sellsystem.entity.SaleRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhangwei on 2017/5/23.
 */
public interface SaleRecordDao {

    List<SaleRecord> getList();

    /**
     * 新建
     * @param saleRecord
     * @return
     */
    int create(@Param("saleRecord")SaleRecord saleRecord);
}
