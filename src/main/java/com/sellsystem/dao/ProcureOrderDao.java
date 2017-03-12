package com.sellsystem.dao;

import com.sellsystem.entity.ProcureOrder;
import com.sellsystem.entity.searchmodel.extend.ProcureOrderSearchModel;

import java.util.List;

/**
 * Created by zhangwei on 2017/3/12.
 */
public interface ProcureOrderDao {

    /**
     * list
     * @param procureOrderSearchModel
     * @return
     */
    List<ProcureOrder> getList(ProcureOrderSearchModel procureOrderSearchModel);
}
