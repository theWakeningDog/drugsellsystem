package com.sellsystem.service;

import com.github.pagehelper.PageInfo;
import com.sellsystem.entity.ProcureOrder;
import com.sellsystem.entity.searchmodel.extend.ProcureOrderSearchModel;
import com.sellsystem.util.MsgModel;

/**
 * 订单
 * Created by zhangwei on 2017/3/12.
 */
public interface ProcureOrderService {

    /**
     * 列表
     * @param procureOrderSearchModel
     * @return
     */
    public MsgModel<PageInfo<ProcureOrder>> getList(ProcureOrderSearchModel procureOrderSearchModel);
}
